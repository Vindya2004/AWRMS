package org.example.awrms.service;

import org.example.awrms.dto.BookingDTO;

import java.util.List;
import java.util.Map;

public interface BookingService {
    boolean saveBooking(BookingDTO bookingDTO);

    List<BookingDTO> getAllBookings();

    List<BookingDTO> getBookingByUserEmail(String email);

    int getTotalBookings();

    List<Map<String, Object>> getBookingsPerDay();

    List<Map<String, Object>> getTotalPricePerBooking();
}
