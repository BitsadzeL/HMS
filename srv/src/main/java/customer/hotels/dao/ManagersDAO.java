package customer.hotels.dao;

import cds.gen.hms.Managers;
import cds.gen.hms.Managers_;

import com.sap.cds.Result;
import com.sap.cds.ql.Select;
import com.sap.cds.ql.cqn.CqnSelect;
import com.sap.cds.services.persistence.PersistenceService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class ManagersDAO {


    private final PersistenceService db;


    public Optional<Managers> findById(String managerId) {
        CqnSelect select = Select.from(Managers_.class).where(m -> m.ID().eq(managerId));
        Result result = db.run(select);
        return result.first(Managers.class);
    }


    public long countByHotelExcluding(String hotelId, String excludingManagerId) {
        CqnSelect select = Select.from(Managers_.class)
                .where(m -> m.hotel_ID().eq(hotelId).and(m.ID().ne(excludingManagerId)));
        return db.run(select).rowCount();
    }
}