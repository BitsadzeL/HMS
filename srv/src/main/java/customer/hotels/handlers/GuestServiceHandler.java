package customer.hotels.handlers;

import cds.gen.guestservice.GuestService_;
import cds.gen.guestservice.Guests;
import cds.gen.guestservice.Guests_;
import cds.gen.guestservice.Reservations;
import cds.gen.guestservice.ReservationsCancelContext;
import cds.gen.guestservice.Reservations_;
import com.sap.cds.services.cds.CdsCreateEventContext;
import com.sap.cds.services.request.UserInfo;
import cds.gen.hms.ReservationStatus;

import customer.hotels.dao.ReservationsDAO;
import customer.hotels.validators.GuestValidator;
import customer.hotels.validators.ReservationValidator;
import customer.hotels.validators.RoomValidator;

import com.sap.cds.ql.Select;
import com.sap.cds.services.ErrorStatuses;
import com.sap.cds.services.ServiceException;
import com.sap.cds.services.cds.CdsDeleteEventContext;
import com.sap.cds.services.cds.CqnService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.Before;
import com.sap.cds.services.handler.annotations.On;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@ServiceName(GuestService_.CDS_NAME)
@RequiredArgsConstructor
public class GuestServiceHandler implements EventHandler {

    private final PersistenceService db;
    private final GuestValidator guestValidator;
    private final ReservationValidator reservationValidator;
    private final RoomValidator roomValidator;
    private final ReservationsDAO reservationsDAO;

    @Before(event = CqnService.EVENT_DELETE, entity = Guests_.CDS_NAME)
    public void beforeDeleteGuest(CdsDeleteEventContext context) {
        Guests guest = db.run(Select.from(context.getCqn().ref()))
                .listOf(Guests.class)
                .stream().findFirst().orElse(null);

        if (guest != null) {
            guestValidator.assertDeletable(guest.getId());
        }
    }

    @Before(event = CqnService.EVENT_CREATE, entity = Reservations_.CDS_NAME)
    public void beforeCreateReservation(List<Reservations> reservations) {
        for (Reservations res : reservations) {
            reservationValidator.assertValidDates(res.getCheckIn(), res.getCheckOut());

            var room = roomValidator.requireRoom(res.getRoomId());
            roomValidator.assertNotBlocked(room);

            reservationValidator.assertNoOverlap(res.getRoomId(), res.getCheckIn(), res.getCheckOut(), null);
        }
    }


    @Before(event = CqnService.EVENT_CREATE, entity = Reservations_.CDS_NAME)
    public void setGuestOnCreate(CdsCreateEventContext context) {
        UserInfo userInfo = context.getUserInfo();
        String loginName = userInfo.getName();

        List<Guests> guests = db.run(
                Select.from(Guests_.class).where(g -> g.loginName().eq(loginName))
        ).listOf(Guests.class);

        Optional<Guests> guest = guests.stream().findFirst();

        guest.ifPresent(g -> {
            String guestId = g.getId();
            context.getCqn().entries().forEach(entry -> entry.put("guest_ID", guestId));
        });
    }

    @Before(event = CqnService.EVENT_UPDATE, entity = Reservations_.CDS_NAME)
    public void beforeUpdateReservation(List<Reservations> reservations) {
        for (Reservations res : reservations) {
            //reservationValidator.assertOnlyDatesChanged(res.getRoomId(), res.getGuestId(), res.getStatus());

            cds.gen.hms.Reservations existing = reservationsDAO.findById(res.getId()).orElse(null);
            if (existing == null) continue;

            var newCheckIn = res.getCheckIn() != null ? res.getCheckIn() : existing.getCheckIn();
            var newCheckOut = res.getCheckOut() != null ? res.getCheckOut() : existing.getCheckOut();

            reservationValidator.assertValidDates(newCheckIn, newCheckOut);
            reservationValidator.assertNoOverlap(existing.getRoomId(), newCheckIn, newCheckOut, res.getId());
        }
    }

    @On(event = ReservationsCancelContext.CDS_NAME)
    public void onCancelReservation(ReservationsCancelContext context) {
        Reservations res = db.run(Select.from(context.getCqn().ref()))
                .listOf(Reservations.class)
                .stream().findFirst()
                .orElseThrow(() -> new ServiceException(ErrorStatuses.NOT_FOUND, "Reservation not found."));

        reservationValidator.assertActive(res.getStatus());
        reservationsDAO.updateStatus(res.getId(), ReservationStatus.CANCELLED);

        context.setCompleted();
    }
}