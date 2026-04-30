package com.airticket.service;

import com.airticket.model.Booking;
import com.airticket.model.Flight;
import com.airticket.repository.BookingRepository;
import com.airticket.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FlightRepository flightRepository;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    public Booking createBooking(Booking booking) {
        Flight flight = flightRepository.findById(booking.getFlightId())
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        if (flight.getAvailableSeats() < booking.getSeatsBooked()) {
            throw new RuntimeException("Not enough available seats");
        }

        // Calculate total amount
        booking.setTotalAmount(flight.getPrice() * booking.getSeatsBooked());

        // Decrease available seats
        flight.setAvailableSeats(flight.getAvailableSeats() - booking.getSeatsBooked());
        flightRepository.save(flight);

        booking.setBookingStatus("CONFIRMED");
        return bookingRepository.save(booking);
    }

    public Booking cancelBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Restore seats
        Flight flight = flightRepository.findById(booking.getFlightId()).orElse(null);
        if (flight != null) {
            flight.setAvailableSeats(flight.getAvailableSeats() + booking.getSeatsBooked());
            flightRepository.save(flight);
        }

        booking.setBookingStatus("CANCELLED");
        return bookingRepository.save(booking);
    }

    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    public List<Booking> getBookingsByEmail(String email) {
        return bookingRepository.findByPassengerEmail(email);
    }
}
