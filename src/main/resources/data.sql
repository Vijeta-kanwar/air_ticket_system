-- Sample flight data
INSERT INTO flights (flight_number, airline, source, destination, departure_time, arrival_time, price, available_seats) VALUES
('AI101', 'Air India', 'Delhi', 'Mumbai', '08:00', '10:30', 5500.00, 150),
('6E202', 'IndiGo', 'Mumbai', 'Bangalore', '09:30', '11:30', 4200.00, 180),
('SG303', 'SpiceJet', 'Chennai', 'Kolkata', '14:00', '16:30', 4800.00, 120),
('UK404', 'Vistara', 'Bangalore', 'Hyderabad', '11:00', '12:15', 3500.00, 100),
('AI505', 'Air India', 'Delhi', 'Goa', '06:00', '08:30', 6200.00, 140),
('6E606', 'IndiGo', 'Pune', 'Delhi', '18:00', '20:30', 5100.00, 160);

-- Sample booking data
INSERT INTO bookings (passenger_name, passenger_email, passenger_phone, flight_id, seats_booked, total_amount, booking_status) VALUES
('Rahul Sharma', 'rahul@example.com', '9876543210', 1, 2, 11000.00, 'CONFIRMED'),
('Priya Patel', 'priya@example.com', '9876543211', 2, 1, 4200.00, 'CONFIRMED');
