package customer.hotels.validators;

import cds.gen.hms.ReservationStatus;
import cds.gen.hms.Reservations;

import customer.hotels.dao.ReservationsDAO;

import com.sap.cds.services.ErrorStatuses;
import com.sap.cds.services.ServiceException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/** Business rule validation for Reservations. */
@Component
@RequiredArgsConstructor
public class ReservationValidator {

    private final ReservationsDAO reservationsDAO;


    public void assertValidDates(LocalDate checkIn, LocalDate checkOut) {
        if (checkIn == null || checkOut == null) {
            throw new ServiceException(ErrorStatuses.BAD_REQUEST,
                    "checkIn and checkOut are required.");
        }
        if (!checkIn.isAfter(LocalDate.now())) {
            throw new ServiceException(ErrorStatuses.BAD_REQUEST,
                    "Check-in date must be in the future.");
        }
        if (!checkOut.isAfter(checkIn)) {
            throw new ServiceException(ErrorStatuses.BAD_REQUEST,
                    "Check-out date must be later than check-in date.");
        }
    }


    public void assertNoOverlap(String roomId, LocalDate checkIn, LocalDate checkOut,
                                String excludingReservationId) {
        List<Reservations> activeOnRoom =
                reservationsDAO.findActiveByRoom(roomId, excludingReservationId);

        for (Reservations other : activeOnRoom) {
            boolean overlaps = checkIn.isBefore(other.getCheckOut())
                    && checkOut.isAfter(other.getCheckIn());
            if (overlaps) {
                throw new ServiceException(ErrorStatuses.CONFLICT,
                        "Room is already booked for the requested dates.");
            }
        }
    }


    public void assertOnlyDatesChanged(String roomId, String guestId, String status) {
        if (roomId != null || guestId != null || status != null) {
            throw new ServiceException(ErrorStatuses.BAD_REQUEST,
                    "Only checkIn and checkOut can be updated.");
        }
    }


    public Reservations requireReservation(String reservationId) {
        return reservationsDAO.findById(reservationId)
                .orElseThrow(() -> new ServiceException(ErrorStatuses.NOT_FOUND, "Reservation not found."));
    }


    public void assertActive(String status) {
        if (!ReservationStatus.ACTIVE.equals(status)) {
            throw new ServiceException(ErrorStatuses.CONFLICT,
                    "Only active reservations can be cancelled.");
        }
    }
}