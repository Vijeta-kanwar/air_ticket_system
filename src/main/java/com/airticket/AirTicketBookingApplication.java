package com.airticket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AirTicketBookingApplication {
    public static void main(String[] args) {
        SpringApplication.run(AirTicketBookingApplication.class, args);
        System.out.println("=========================================================");
        System.out.println("  ✈  Air Ticket Booking System Started Successfully     ");
        System.out.println("  Access at: http://localhost:8080                       ");
        System.out.println("=========================================================");
    }
}
