package customer.hotels.handlers;

import cds.gen.managerservice.ManagerService_;
import cds.gen.managerservice.Rooms_;
import org.springframework.stereotype.Component;

import cds.gen.hms.Reservations_;
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

@Component
@ServiceName(ManagerService_.CDS_NAME)
public class ManagerServiceHandler implements EventHandler {

    private final PersistenceService db;

    public ManagerServiceHandler(PersistenceService db) {
        this.db = db;
    }

    // Rule: Delete room only if it has no active reservations
    @Before(event = CqnService.EVENT_DELETE, entity = Rooms_.CDS_NAME)
    public void beforeDeleteRoom(CdsDeleteEventContext context) {

        Rooms room = db.run(Select.from(context.getCqn().ref()))
                .listOf(Rooms.class)
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
}