package customer.hotels.dao;

import cds.gen.hms.Hotels;
import cds.gen.hms.Hotels_;

import com.sap.cds.Result;
import com.sap.cds.ql.Select;
import com.sap.cds.ql.cqn.CqnSelect;
import com.sap.cds.services.persistence.PersistenceService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/** Data access object for Hotels. */
@Component
@RequiredArgsConstructor
public class HotelsDAO {

    /** The CAP persistence service used for database operations. */
    private final PersistenceService db;

    /**
     * Get a hotel by its ID.
     * @param hotelId ID of the hotel
     * @return the hotel, if found
     */
    public Optional<Hotels> findById(String hotelId) {
        CqnSelect select = Select.from(Hotels_.class).where(h -> h.ID().eq(hotelId));
        Result result = db.run(select);
        return result.first(Hotels.class);
    }
}