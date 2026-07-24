package customer.hotels.dao;

import cds.gen.hms.Guests;
import cds.gen.hms.Guests_;

import com.sap.cds.Result;
import com.sap.cds.ql.Select;
import com.sap.cds.ql.cqn.CqnSelect;
import com.sap.cds.services.persistence.PersistenceService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class GuestsDAO {


    private final PersistenceService db;


    public Optional<Guests> findById(String guestId) {
        CqnSelect select = Select.from(Guests_.class).where(g -> g.ID().eq(guestId));
        Result result = db.run(select);
        return result.first(Guests.class);
    }
}