package org.example.awrms.service.impl;

import org.example.awrms.dto.BookingDTO;
import org.example.awrms.entity.*;
import org.example.awrms.repository.*;
import org.example.awrms.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private TreatPackageRepository treatPackageRepository;


    @Override
    @Transactional
    public boolean saveBooking(BookingDTO bookingDTO) {
        try{
            TreatPackage treatPackage = treatPackageRepository.findByName(bookingDTO.getPackageName())
                    .orElseThrow(() -> new RuntimeException("Package not found"));

            Doctor doctor = doctorRepository.findByEmail(bookingDTO.getDoctorEmail())
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));
            Accommodation accommodation = accommodationRepository.findByName(bookingDTO.getAccommodationName())
                    .orElseThrow(() -> new RuntimeException("Accommodation not found"));

            User user = userRepository.findByEmail(bookingDTO.getUserEmail());

            Booking booking = new Booking();
            booking.setUser(user);
            booking.setDoctor(doctor);
            booking.setAccommodation(accommodation);
            booking.setTreatPackage(treatPackage);
            booking.setEstimateDays(bookingDTO.getEstimateDays());
            booking.setBookingDate(LocalDate.parse(bookingDTO.getBookingDate()));
            booking.setCheckoutDate(LocalDate.parse(bookingDTO.getCheckoutDate()));
            booking.setPackagePrice(bookingDTO.getPackagePrice());
            booking.setDoctorFee(bookingDTO.getDoctorFee());
            booking.setAccommodationPrice(bookingDTO.getAccommodationFee());
            booking.setTotalPrice(bookingDTO.getTotalPrice());
            booking.setActive(true);

            bookingRepository.save(booking);

            accommodation.setBooked("Yes");
            doctor.setBooked("Yes");

            accommodationRepository.save(accommodation);
            doctorRepository.save(doctor);

            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<BookingDTO> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private BookingDTO convertToDTO(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setPackageName(booking.getTreatPackage().getName());
        bookingDTO.setDoctorEmail(booking.getDoctor().getEmail());
        bookingDTO.setAccommodationName(booking.getAccommodation().getName());
        bookingDTO.setUserEmail(booking.getUser().getEmail());
        bookingDTO.setEstimateDays(booking.getEstimateDays());
        bookingDTO.setBookingDate(booking.getBookingDate().toString());
        bookingDTO.setCheckoutDate(booking.getCheckoutDate().toString());
        bookingDTO.setPackagePrice(booking.getPackagePrice());
        bookingDTO.setDoctorFee(booking.getDoctorFee());
        bookingDTO.setAccommodationFee(booking.getAccommodationPrice());
        bookingDTO.setTotalPrice(booking.getTotalPrice());
        return bookingDTO;
    }

    @Override
    public List<BookingDTO> getBookingByUserEmail(String email) {
        List<Booking> bookings = bookingRepository.findByUserEmail(email);
        return bookings.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public int getTotalBookings() { return (int) bookingRepository.count(); }

    @Override
    public List<Map<String, Object>> getBookingsPerDay() {return bookingRepository.findBookingsPerDay();}

    @Override
    public List<Map<String, Object>> getTotalPricePerBooking() {return bookingRepository.findTotalPricePerBooking();}
}
