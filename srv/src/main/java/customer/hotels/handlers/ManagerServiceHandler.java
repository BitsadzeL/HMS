package customer.hotels.handlers;

import cds.gen.managerservice.ManagerService_;
import cds.gen.managerservice.Rooms_;

import cds.gen.hms.Rooms;

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
@ServiceName(ManagerService_.CDS_NAME)
@RequiredArgsConstructor
public class ManagerServiceHandler implements EventHandler {

    private final PersistenceService db;
    private final RoomValidator roomValidator;

    @Before(event = CqnService.EVENT_DELETE, entity = Rooms_.CDS_NAME)
    public void beforeDeleteRoom(CdsDeleteEventContext context) {
        Rooms room = db.run(Select.from(context.getCqn().ref()))
                .listOf(Rooms.class)
                .stream().findFirst().orElse(null);

        if (room != null) {
            roomValidator.assertDeletable(room.getId());
        }
    }
}