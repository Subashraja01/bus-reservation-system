package busresv;

import java.sql.*;
import java.util.Scanner;

public class BookingService {

    public static void bookSeat(int userId) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Bus ID: ");
        int busId = sc.nextInt();  
        sc.nextLine(); 

        System.out.println("Enter Passenger Name: ");
        String passengerName = sc.nextLine();

        System.out.println("Enter Departure Location: ");
        String departureLocation = sc.nextLine();

        System.out.println("Enter Destination Location: ");
        String destinationLocation = sc.nextLine();

        System.out.println("Enter number of seats: ");
        int seatsBooked = sc.nextInt();

        Connection con = DBUtil.getConnection();
        String checkSeatsQuery = "SELECT total_seats - (SELECT COALESCE(SUM(seats_booked), 0) FROM booking WHERE bus_id = ?) FROM bus WHERE bus_id = ?";
        PreparedStatement pstCheck = con.prepareStatement(checkSeatsQuery);
        pstCheck.setInt(1, busId);
        pstCheck.setInt(2, busId);

        ResultSet rs = pstCheck.executeQuery();
        if (rs.next()) {
            int availableSeats = rs.getInt(1);
            if (availableSeats < seatsBooked) {
                System.out.println("❌ Not enough seats available. Only " + availableSeats + " seats left.");
                return;
            }
        }

        String query = "INSERT INTO booking (user_id, bus_id, seats_booked, booking_date, departure_location, destination_location, passenger_name) VALUES (?, ?, ?, CURRENT_TIMESTAMP, ?, ?, ?)";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setInt(1, userId);
        pst.setInt(2, busId);
        pst.setInt(3, seatsBooked);
        pst.setString(4, departureLocation);
        pst.setString(5, destinationLocation);
        pst.setString(6, passengerName);

        int rows = pst.executeUpdate();
        if (rows > 0) {
            System.out.println("✅ Booking successful!");
        } else {
            System.out.println("❌ Booking failed. Please try again.");
        }

        con.close();
    }
    public static void viewBookings(int userId) throws Exception {
    Connection con = DBUtil.getConnection();
    
    String query = "SELECT booking_id, bus_id, seats_booked, booking_date, passenger_name, departure_location, destination_location " +
                   "FROM booking WHERE user_id = ?";
    PreparedStatement pst = con.prepareStatement(query);
    pst.setInt(1, userId);
    
    ResultSet rs = pst.executeQuery();
    
    boolean hasBookings = false;
    while (rs.next()) {
        hasBookings = true;
        System.out.println("\n🚌 Booking ID: " + rs.getInt("booking_id"));
        System.out.println("   Bus ID: " + rs.getInt("bus_id"));
        System.out.println("   Passenger Name: " + rs.getString("passenger_name"));
        System.out.println("   Seats Booked: " + rs.getInt("seats_booked"));
        System.out.println("   From: " + rs.getString("departure_location"));
        System.out.println("   To: " + rs.getString("destination_location"));
        System.out.println("   Date: " + rs.getTimestamp("booking_date"));
    }

    if (!hasBookings) {
        System.out.println("❌ No bookings found.");
    }

    con.close();
}
    public static void cancelBooking(int userId) throws Exception {
    Scanner sc = new Scanner(System.in);
    
    System.out.print("Enter Booking ID to cancel: ");
    int bookingId = sc.nextInt();

    Connection con = DBUtil.getConnection();

    // Only cancel if the booking belongs to the current user
    String query = "DELETE FROM booking WHERE booking_id = ? AND user_id = ?";
    PreparedStatement pst = con.prepareStatement(query);
    pst.setInt(1, bookingId);
    pst.setInt(2, userId);

    int rows = pst.executeUpdate();

    if (rows > 0) {
        System.out.println("✅ Booking cancelled successfully.");
    } else {
        System.out.println("❌ Booking not found or you are not authorized to cancel it.");
    }

    con.close();
}


}
