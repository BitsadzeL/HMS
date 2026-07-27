package customer.hotels.dao;

import cds.gen.hms.Rooms;
import cds.gen.hms.Rooms_;

import com.sap.cds.Result;
import com.sap.cds.ql.Select;
import com.sap.cds.ql.Update;
import com.sap.cds.ql.cqn.CqnSelect;
import com.sap.cds.ql.cqn.CqnUpdate;
import com.sap.cds.services.persistence.PersistenceService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class RoomsDAO {


    private final PersistenceService db;


    public Optional<Rooms> findById(String roomId) {
        CqnSelect select = Select.from(Rooms_.class).where(r -> r.ID().eq(roomId));
        Result result = db.run(select);
        return result.first(Rooms.class);
    }


    public List<String> findRoomIdsByHotel(String hotelId) {
        CqnSelect select = Select.from(Rooms_.class)
                .columns(r -> r.ID())
                .where(r -> r.hotel_ID().eq(hotelId));
        return db.run(select).listOf(Rooms.class)
                .stream()
                .map(Rooms::getId)
                .toList();
    }


    public void updateAvailability(String roomId, boolean available) {
        CqnUpdate update = Update.entity(Rooms_.class)
                .data("available", available)
                .where(r -> r.ID().eq(roomId));
        db.run(update);
    }
}