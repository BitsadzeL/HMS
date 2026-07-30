package customer.hotels.handlers;

import cds.gen.hms.Managers_;
import cds.gen.managerservice.ManagerService_;
import cds.gen.managerservice.Rooms_;

import cds.gen.hms.Rooms;


import com.sap.cds.services.cds.CdsCreateEventContext;
import com.sap.cds.services.cds.CdsReadEventContext;
import com.sap.cds.services.handler.annotations.After;
import com.sap.cds.services.request.UserInfo;
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

import java.util.List;
import java.util.Optional;


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


    @Before(event = CqnService.EVENT_CREATE, entity = Rooms_.CDS_NAME)
    public void setHotelOnCreate(CdsCreateEventContext context) {
        UserInfo userInfo = context.getUserInfo();
        String loginName = userInfo.getName();

        List<cds.gen.hms.Managers> managers = db.run(
                Select.from(Managers_.class).where(m -> m.loginName().eq(loginName))
        ).listOf(cds.gen.hms.Managers.class);

        Optional<cds.gen.hms.Managers> manager = managers.stream().findFirst();

        manager.ifPresent(m -> {
            String hotelId = m.getHotelId();
            context.getCqn().entries().forEach(entry -> entry.put("hotel_ID", hotelId));
        });
    }

//    @After(event = CqnService.EVENT_READ, entity = Rooms_.CDS_NAME)
//    public void filterRoomsAfterRead(CdsReadEventContext context, List<Rooms> rooms) {
//        UserInfo userInfo = context.getUserInfo();
//        String loginName = userInfo.getName();
//
//        List<cds.gen.hms.Managers> managers = db.run(
//                Select.from(Managers_.class).where(m -> m.loginName().eq(loginName))
//        ).listOf(cds.gen.hms.Managers.class);
//
//        Optional<cds.gen.hms.Managers> manager = managers.stream().findFirst();
//        String hotelId = manager.map(cds.gen.hms.Managers::getHotelId).orElse("__NO_MATCH__");
//
//        rooms.removeIf(room -> !hotelId.equals(room.getHotelId()));
//    }


    @After(event = CqnService.EVENT_READ, entity = Rooms_.CDS_NAME)
    public void filterRoomsAfterRead(CdsReadEventContext context, List<cds.gen.managerservice.Rooms> rooms) {
        UserInfo userInfo = context.getUserInfo();
        String loginName = userInfo.getName();

        List<cds.gen.hms.Managers> managers = db.run(
                Select.from(Managers_.class).where(m -> m.loginName().eq(loginName))
        ).listOf(cds.gen.hms.Managers.class);

        Optional<cds.gen.hms.Managers> manager = managers.stream().findFirst();
        String hotelId = manager.map(cds.gen.hms.Managers::getHotelId).orElse("__NO_MATCH__");

        rooms.removeIf(room -> !hotelId.equals(room.getHotelId()));
    }

}