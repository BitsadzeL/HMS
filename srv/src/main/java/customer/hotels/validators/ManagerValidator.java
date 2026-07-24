package customer.hotels.validators;

import cds.gen.hms.Managers;

import customer.hotels.dao.ManagersDAO;

import com.sap.cds.services.ErrorStatuses;
import com.sap.cds.services.ServiceException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ManagerValidator {

    private final ManagersDAO managersDAO;


    public void assertDeletable(Managers manager) {
        long remaining = managersDAO.countByHotelExcluding(manager.getHotelId(), manager.getId());
        if (remaining == 0) {
            throw new ServiceException(ErrorStatuses.CONFLICT,
                    "Cannot delete manager: hotel must have at least one manager.");
        }
    }
}