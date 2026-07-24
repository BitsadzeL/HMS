package customer.hotels.handlers;

import cds.gen.adminservice.AdminService_;
import cds.gen.adminservice.Hotels_;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

import cds.gen.hms.Rooms;
import cds.gen.hms.Rooms_;
import cds.gen.hms.Reservations_;
import cds.gen.hms.Hotels;
import cds.gen.adminservice.Guests_;
import cds.gen.adminservice.Managers_;
import com.sap.cds.ql.Select;
import com.sap.cds.services.ErrorStatuses;
import com.sap.cds.services.ServiceException;
import com.sap.cds.services.cds.CdsDeleteEventContext;
import com.sap.cds.services.cds.CqnService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.Before;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;


@Component
@ServiceName(AdminService_.CDS_NAME)
public class AdminServiceHandler implements EventHandler {

    private final PersistenceService db;

    public AdminServiceHandler(PersistenceService db) {
        this.db = db;
    }


    @Before(event = CqnService.EVENT_DELETE, entity = Hotels_.CDS_NAME)
    public void beforeDeleteHotel(CdsDeleteEventContext context) {

        Hotels hotel = db.run(Select.from(context.getCqn().ref()))
                .listOf(Hotels.class)
                .stream().findFirst().orElse(null);

        if (hotel == null) return;

        List<String> roomIds = db.run(
                        Select.from(Rooms_.class)
                                .columns(r -> r.ID())
                                .where(r -> r.hotel_ID().eq(hotel.getId())))
                .listOf(Rooms.class)
                .stream()
                .map(Rooms::getId)
                .collect(Collectors.toList());

        if (roomIds.isEmpty()) return; // no rooms — safe to delete

        long activeReservationCount = db.run(
                        Select.from(Reservations_.class)
                                .where(r -> r.room_ID().in(roomIds)
                                        .and(r.status().eq("active"))))
                .rowCount();

        if (activeReservationCount > 0) {
            throw new ServiceException(ErrorStatuses.CONFLICT,
                    "Cannot delete hotel: it has active reservations.");
        }

        throw new ServiceException(ErrorStatuses.CONFLICT,
                "Cannot delete hotel: it still has rooms assigned.");
    }



    @Before(event = CqnService.EVENT_DELETE, entity = cds.gen.adminservice.Rooms_.CDS_NAME)
    public void beforeDeleteRoom(CdsDeleteEventContext context) {

        Rooms room = db.run(Select.from(context.getCqn().ref()))
                .listOf(cds.gen.hms.Rooms.class)
                .stream().findFirst().orElse(null);

        if (room == null) return;

        long activeReservationCount = db.run(
                        Select.from(Reservations_.class)
                                .where(r -> r.room_ID().eq(room.getId())
                                        .and(r.status().eq("active"))))
                .rowCount();

        if (activeReservationCount > 0) {
            throw new ServiceException(ErrorStatuses.CONFLICT,
                    "Cannot delete room: it has active reservations.");
        }
    }


    // Rule: Delete guest only if they have no active reservations
    @Before(event = CqnService.EVENT_DELETE, entity = Guests_.CDS_NAME)
    public void beforeDeleteGuest(CdsDeleteEventContext context) {

        cds.gen.hms.Guests guest = db.run(Select.from(context.getCqn().ref()))
                .listOf(cds.gen.hms.Guests.class)
                .stream().findFirst().orElse(null);

        if (guest == null) return;

        long activeReservationCount = db.run(
                        Select.from(Reservations_.class)
                                .where(r -> r.guest_ID().eq(guest.getId())
                                        .and(r.status().eq("active"))))
                .rowCount();

        if (activeReservationCount > 0) {
            throw new ServiceException(ErrorStatuses.CONFLICT,
                    "Cannot delete guest: they have active reservations.");
        }
    }

    // Rule: Delete manager only if the hotel has another manager
    @Before(event = CqnService.EVENT_DELETE, entity = Managers_.CDS_NAME)
    public void beforeDeleteManager(CdsDeleteEventContext context) {

        cds.gen.hms.Managers manager = db.run(Select.from(context.getCqn().ref()))
                .listOf(cds.gen.hms.Managers.class)
                .stream().findFirst().orElse(null);

        if (manager == null) return;

        long remainingManagersCount = db.run(
                        Select.from(cds.gen.hms.Managers_.class)
                                .where(m -> m.hotel_ID().eq(manager.getHotelId())
                                        .and(m.ID().ne(manager.getId()))))
                .rowCount();

        if (remainingManagersCount == 0) {
            throw new ServiceException(ErrorStatuses.CONFLICT,
                    "Cannot delete manager: hotel must have at least one manager.");
        }
    }



}