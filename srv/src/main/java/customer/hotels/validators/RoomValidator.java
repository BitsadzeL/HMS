package customer.hotels.validators;

import cds.gen.hms.Rooms;

import customer.hotels.dao.ReservationsDAO;
import customer.hotels.dao.RoomsDAO;

import com.sap.cds.services.ErrorStatuses;
import com.sap.cds.services.ServiceException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class RoomValidator {

    private final RoomsDAO roomsDAO;
    private final ReservationsDAO reservationsDAO;


    public void assertDeletable(String roomId) {
        if (reservationsDAO.countActiveByRoom(roomId) > 0) {
            throw new ServiceException(ErrorStatuses.CONFLICT,
                    "Cannot delete room: it has active reservations.");
        }
    }


    public Rooms requireRoom(String roomId) {
        return roomsDAO.findById(roomId)
                .orElseThrow(() -> new ServiceException(ErrorStatuses.NOT_FOUND, "Room not found."));
    }


    public void assertNotBlocked(Rooms room) {
        if (Boolean.FALSE.equals(room.getAvailable())) {
            throw new ServiceException(ErrorStatuses.CONFLICT,
                    "Room is currently blocked by hotel management.");
        }
    }
}