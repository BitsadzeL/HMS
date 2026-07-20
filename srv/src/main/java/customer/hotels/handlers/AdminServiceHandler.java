package customer.hotels.handlers;

import cds.gen.adminservice.AdminService_;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;
import cds.gen.hms.Rooms;
import com.sap.cds.ql.Select;
import com.sap.cds.services.ErrorStatuses;
import com.sap.cds.services.ServiceException;
import com.sap.cds.services.cds.CdsDeleteEventContext;
import com.sap.cds.services.cds.CqnService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.Before;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;

import cds.gen.hms.Hotels;
import cds.gen.hms.Hotels_;
import cds.gen.hms.Reservations_;
import cds.gen.hms.Rooms_;


@Component
@ServiceName(AdminService_.CDS_NAME)
public class AdminServiceHandler implements EventHandler {

    private final PersistenceService db;

    public AdminServiceHandler(PersistenceService db) {
        this.db = db;
    }



    // Rule: Delete hotel only if it has no rooms and no active reservations
    @Before(event = CqnService.EVENT_DELETE, entity = AdminService_.CDS_NAME)
    public void beforeDeleteHotel(CdsDeleteEventContext context) {



        Hotels hotel = db.run(Select.from(context.getCqn().ref()))
                .listOf(Hotels.class)
                .stream().findFirst().orElse(null);

        if (hotel == null) {
            return;
        }

        // Get all room IDs belonging to this hotel
        List<String> roomIds = db.run(
                        Select.from(Rooms_.class)
                                .columns(r -> r.ID())
                                .where(r -> r.hotel_ID().eq(hotel.getId())))
                .listOf(Rooms.class)
                .stream()
                .map(Rooms::getId)
                .collect(Collectors.toList());

        if (!roomIds.isEmpty()) {
            throw new ServiceException(ErrorStatuses.CONFLICT,
                    "Cannot delete hotel: it still has rooms assigned.");
        }

        long activeReservationCount = db.run(
                        Select.from(Reservations_.class)
                                .where(r -> r.room_ID().in(roomIds)
                                        .and(r.status().eq("active"))))
                .rowCount();

        if (activeReservationCount > 0) {
            throw new ServiceException(ErrorStatuses.CONFLICT,
                    "Cannot delete hotel: it has active reservations.");
        }
    }
}