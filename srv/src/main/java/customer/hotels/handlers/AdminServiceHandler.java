package customer.hotels.handlers;

import cds.gen.adminservice.AdminService_;
import cds.gen.adminservice.Guests_;
import cds.gen.adminservice.Hotels_;
import cds.gen.adminservice.Managers_;
import cds.gen.adminservice.Rooms_;

import cds.gen.hms.Guests;
import cds.gen.hms.Hotels;
import cds.gen.hms.Managers;
import cds.gen.hms.Rooms;

import customer.hotels.validators.GuestValidator;
import customer.hotels.validators.HotelValidator;
import customer.hotels.validators.ManagerValidator;
import customer.hotels.validators.RoomValidator;

import com.sap.cds.ql.Select;
import com.sap.cds.services.cds.CdsDeleteEventContext;
import com.sap.cds.services.cds.CqnService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.Before;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@ServiceName(AdminService_.CDS_NAME)
@RequiredArgsConstructor
public class AdminServiceHandler implements EventHandler {

    private final PersistenceService db;
    private final HotelValidator hotelValidator;
    private final RoomValidator roomValidator;
    private final GuestValidator guestValidator;
    private final ManagerValidator managerValidator;

    @Before(event = CqnService.EVENT_DELETE, entity = Hotels_.CDS_NAME)
    public void beforeDeleteHotel(CdsDeleteEventContext context) {
        Hotels hotel = db.run(Select.from(context.getCqn().ref()))
                .listOf(Hotels.class)
                .stream().findFirst().orElse(null);

        if (hotel != null) {
            hotelValidator.assertDeletable(hotel.getId());
        }
    }

    @Before(event = CqnService.EVENT_DELETE, entity = Rooms_.CDS_NAME)
    public void beforeDeleteRoom(CdsDeleteEventContext context) {
        Rooms room = db.run(Select.from(context.getCqn().ref()))
                .listOf(Rooms.class)
                .stream().findFirst().orElse(null);

        if (room != null) {
            roomValidator.assertDeletable(room.getId());
        }
    }

    @Before(event = CqnService.EVENT_DELETE, entity = Guests_.CDS_NAME)
    public void beforeDeleteGuest(CdsDeleteEventContext context) {
        Guests guest = db.run(Select.from(context.getCqn().ref()))
                .listOf(Guests.class)
                .stream().findFirst().orElse(null);

        if (guest != null) {
            guestValidator.assertDeletable(guest.getId());
        }
    }

    @Before(event = CqnService.EVENT_DELETE, entity = Managers_.CDS_NAME)
    public void beforeDeleteManager(CdsDeleteEventContext context) {
        Managers manager = db.run(Select.from(context.getCqn().ref()))
                .listOf(Managers.class)
                .stream().findFirst().orElse(null);

        if (manager != null) {
            managerValidator.assertDeletable(manager);
        }
    }
}