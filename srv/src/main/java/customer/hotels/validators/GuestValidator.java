package customer.hotels.validators;

import customer.hotels.dao.ReservationsDAO;

import com.sap.cds.services.ErrorStatuses;
import com.sap.cds.services.ServiceException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class GuestValidator {

    private final ReservationsDAO reservationsDAO;


    public void assertDeletable(String guestId) {
        if (reservationsDAO.countActiveByGuest(guestId) > 0) {
            throw new ServiceException(ErrorStatuses.CONFLICT,
                    "Cannot delete guest: they have active reservations.");
        }
    }
}