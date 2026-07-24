package customer.hotels.dao;

import cds.gen.hms.ReservationStatus;
import cds.gen.hms.Reservations;
import cds.gen.hms.Reservations_;

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
public class ReservationsDAO {


    private final PersistenceService db;


    public Optional<Reservations> findById(String reservationId) {
        CqnSelect select = Select.from(Reservations_.class).where(r -> r.ID().eq(reservationId));
        Result result = db.run(select);
        return result.first(Reservations.class);
    }


    public long countActiveByRooms(List<String> roomIds) {
        CqnSelect select = Select.from(Reservations_.class)
                .where(r -> r.room_ID().in(roomIds).and(r.status().eq(ReservationStatus.ACTIVE)));
        return db.run(select).rowCount();
    }


    public long countActiveByRoom(String roomId) {
        CqnSelect select = Select.from(Reservations_.class)
                .where(r -> r.room_ID().eq(roomId).and(r.status().eq(ReservationStatus.ACTIVE)));
        return db.run(select).rowCount();
    }


    public long countActiveByGuest(String guestId) {
        CqnSelect select = Select.from(Reservations_.class)
                .where(r -> r.guest_ID().eq(guestId).and(r.status().eq(ReservationStatus.ACTIVE)));
        return db.run(select).rowCount();
    }


    public List<Reservations> findActiveByRoom(String roomId, String excludingReservationId) {
        CqnSelect select = Select.from(Reservations_.class)
                .where(r -> {
                    var predicate = r.room_ID().eq(roomId).and(r.status().eq(ReservationStatus.ACTIVE));
                    return excludingReservationId != null
                            ? predicate.and(r.ID().ne(excludingReservationId))
                            : predicate;
                });
        return db.run(select).listOf(Reservations.class);
    }


    public void updateStatus(String reservationId, String status) {
        CqnUpdate update = Update.entity(Reservations_.class)
                .data("status", status)
                .where(r -> r.ID().eq(reservationId));
        db.run(update);
    }
}