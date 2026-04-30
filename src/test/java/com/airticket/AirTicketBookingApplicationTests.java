package com.airticket;

import com.airticket.model.Flight;
import com.airticket.service.FlightService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AirTicketBookingApplicationTests {

    @Autowired
    private FlightService flightService;

    @Test
    void contextLoads() {
        assertNotNull(flightService);
    }

    @Test
    void testCreateAndRetrieveFlight() {
        Flight flight = new Flight("TEST101", "TestAir", "Delhi", "Mumbai",
                "10:00", "12:00", 5000.0, 150);
        Flight saved = flightService.saveFlight(flight);
        assertNotNull(saved.getId());
        assertEquals("TEST101", saved.getFlightNumber());
    }

    @Test
    void testGetAllFlights() {
        assertNotNull(flightService.getAllFlights());
    }

    @Test
    void testFlightPrice() {
        Flight flight = new Flight("TEST202", "TestAir", "Chennai", "Kolkata",
                "14:00", "16:00", 4500.0, 100);
        Flight saved = flightService.saveFlight(flight);
        assertEquals(4500.0, saved.getPrice());
        assertEquals(100, saved.getAvailableSeats());
    }
}
