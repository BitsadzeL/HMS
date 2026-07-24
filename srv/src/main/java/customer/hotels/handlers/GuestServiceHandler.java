package customer.hotels.handlers;

import cds.gen.guestservice.GuestService_;
import cds.gen.guestservice.Guests;
import cds.gen.guestservice.Guests_;
import cds.gen.guestservice.Reservations;
import cds.gen.guestservice.Reservations_;

import cds.gen.hms.Rooms;
import cds.gen.hms.Rooms_;

import com.sap.cds.ql.Select;
import com.sap.cds.ql.Update;
import com.sap.cds.services.ErrorStatuses;
import com.sap.cds.services.ServiceException;
import com.sap.cds.services.cds.CdsDeleteEventContext;
import com.sap.cds.services.cds.CqnService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.After;
import com.sap.cds.services.handler.annotations.Before;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;
import cds.gen.guestservice.ReservationsCancelContext;
import cds.gen.hms.ReservationStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import com.sap.cds.services.handler.annotations.On;
@Component
@ServiceName(GuestService_.CDS_NAME)
public class GuestServiceHandler implements EventHandler {

    private final PersistenceService db;

    public GuestServiceHandler(PersistenceService db) {
        this.db = db;
    }

    // Rule: Delete guest only if they have no active reservations
    @Before(event = CqnService.EVENT_DELETE, entity = Guests_.CDS_NAME)
    public void beforeDeleteGuest(CdsDeleteEventContext context) {

        Guests guest = db.run(Select.from(context.getCqn().ref()))
                .listOf(Guests.class)
                .stream().findFirst().orElse(null);

        if (guest == null) return;

        long activeReservationCount = db.run(
                        Select.from(Reservations_.class)
                                .where(r -> r.guest_ID().eq(guest.getId())
                                        .and(r.status().eq(ReservationStatus.ACTIVE))))
                .rowCount();

        if (activeReservationCount > 0) {
            throw new ServiceException(ErrorStatuses.CONFLICT,
                    "Cannot delete guest: they have active reservations.");
        }
    }

    // Rule: validate reservation dates + room availability on creation
    @Before(event = CqnService.EVENT_CREATE, entity = Reservations_.CDS_NAME)
    public void beforeCreateReservation(List<Reservations> reservations) {

        for (Reservations res : reservations) {

            LocalDate checkIn = res.getCheckIn();
            LocalDate checkOut = res.getCheckOut();
            String roomId = res.getRoomId();

            if (checkIn == null || checkOut == null || roomId == null) {
                throw new ServiceException(ErrorStatuses.BAD_REQUEST,
                        "room, checkIn and checkOut are required.");
            }

            if (!checkIn.isAfter(LocalDate.now())) {
                throw new ServiceException(ErrorStatuses.BAD_REQUEST,
                        "Check-in date must be in the future.");
            }

            if (!checkOut.isAfter(checkIn)) {
                throw new ServiceException(ErrorStatuses.BAD_REQUEST,
                        "Check-out date must be later than check-in date.");
            }

            Rooms room = db.run(Select.from(Rooms_.class)
                            .where(r -> r.ID().eq(roomId)))
                    .listOf(Rooms.class)
                    .stream().findFirst().orElse(null);

            if (room == null) {
                throw new ServiceException(ErrorStatuses.NOT_FOUND,
                        "Room not found.");
            }

            if (Boolean.FALSE.equals(room.getAvailable())) {
                throw new ServiceException(ErrorStatuses.CONFLICT,
                        "Room is currently blocked by hotel management.");
            }

            List<Reservations> overlapping = db.run(Select.from(Reservations_.class)
                            .where(r -> r.room_ID().eq(roomId)
                                    .and(r.status().eq(ReservationStatus.ACTIVE))))
                    .listOf(Reservations.class);

            for (Reservations other : overlapping) {
                boolean overlaps = checkIn.isBefore(other.getCheckOut())
                        && checkOut.isAfter(other.getCheckIn());
                if (overlaps) {
                    throw new ServiceException(ErrorStatuses.CONFLICT,
                            "Room is already booked for the requested dates.");
                }
            }
        }
    }


    @Before(event = CqnService.EVENT_UPDATE, entity = Reservations_.CDS_NAME)
    public void beforeUpdateReservation(List<Reservations> reservations) {

        for (Reservations res : reservations) {

            String reservationId = res.getId();

            // Reject changes to anything except checkIn/checkOut
            if (res.getRoomId() != null || res.getGuestId() != null || res.getStatus() != null) {
                throw new ServiceException(ErrorStatuses.BAD_REQUEST,
                        "Only checkIn and checkOut can be updated.");
            }

            Reservations existing = db.run(Select.from(Reservations_.class)
                            .where(r -> r.ID().eq(reservationId)))
                    .listOf(Reservations.class)
                    .stream().findFirst().orElse(null);

            if (existing == null) continue;

            LocalDate newCheckIn = res.getCheckIn() != null ? res.getCheckIn() : existing.getCheckIn();
            LocalDate newCheckOut = res.getCheckOut() != null ? res.getCheckOut() : existing.getCheckOut();

            if (!newCheckIn.isAfter(LocalDate.now())) {
                throw new ServiceException(ErrorStatuses.BAD_REQUEST,
                        "Check-in date must be in the future.");
            }

            if (!newCheckOut.isAfter(newCheckIn)) {
                throw new ServiceException(ErrorStatuses.BAD_REQUEST,
                        "Check-out date must be later than check-in date.");
            }

            String roomId = existing.getRoomId();

            List<Reservations> overlapping = db.run(Select.from(Reservations_.class)
                            .where(r -> r.room_ID().eq(roomId)
                                    .and(r.status().eq(ReservationStatus.ACTIVE))
                                    .and(r.ID().ne(reservationId))))
                    .listOf(Reservations.class);

            for (Reservations other : overlapping) {
                boolean overlaps = newCheckIn.isBefore(other.getCheckOut())
                        && newCheckOut.isAfter(other.getCheckIn());
                if (overlaps) {
                    throw new ServiceException(ErrorStatuses.CONFLICT,
                            "New dates overlap with an existing reservation for this room.");
                }
            }
        }
    }


    @On(event = ReservationsCancelContext.CDS_NAME)
    public void onCancelReservation(ReservationsCancelContext context) {

        Reservations res = db.run(Select.from(context.getCqn().ref()))
                .listOf(Reservations.class)
                .stream().findFirst().orElse(null);

        if (res == null) {
            throw new ServiceException(ErrorStatuses.NOT_FOUND, "Reservation not found.");
        }

        if (!ReservationStatus.ACTIVE.equals(res.getStatus())) {
            throw new ServiceException(ErrorStatuses.CONFLICT, "Only active reservations can be cancelled.");
        }

        db.run(Update.entity(Reservations_.class)
                .data("status", ReservationStatus.CANCELLED)
                .where(r -> r.ID().eq(res.getId())));

        context.setCompleted();
    }

}