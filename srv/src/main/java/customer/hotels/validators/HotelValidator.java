package customer.hotels.validators;

import customer.hotels.dao.ReservationsDAO;
import customer.hotels.dao.RoomsDAO;

import com.sap.cds.services.ErrorStatuses;
import com.sap.cds.services.ServiceException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class HotelValidator {

    private final RoomsDAO roomsDAO;
    private final ReservationsDAO reservationsDAO;


    public void assertDeletable(String hotelId) {
        List<String> roomIds = roomsDAO.findRoomIdsByHotel(hotelId);

        if (roomIds.isEmpty()) {
            return;
        }

        if (reservationsDAO.countActiveByRooms(roomIds) > 0) {
            throw new ServiceException(ErrorStatuses.CONFLICT,
                    "Cannot delete hotel: it has active reservations.");
        }

        throw new ServiceException(ErrorStatuses.CONFLICT,
                "Cannot delete hotel: it still has rooms assigned.");
    }
}