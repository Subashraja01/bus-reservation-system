package busresv;

import java.sql.*;
import java.util.*;

public class BusService {

    public static void viewAllBuses() throws Exception {
        Connection con = DBUtil.getConnection();
        String query = "SELECT * FROM bus";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        System.out.println("\n=== Available Buses ===");
        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("bus_id") +
                               ", Name: " + rs.getString("bus_name") +
                               ", Seats: " + rs.getInt("total_seats"));
        }
        con.close();
    }

    public static void addBus() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter bus name: ");
        String name = sc.nextLine();
        System.out.print("Enter total seats: ");
        int seats = sc.nextInt();

        Connection con = DBUtil.getConnection();
        String query = "INSERT INTO bus (bus_name, total_seats) VALUES (?, ?)";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, name);
        pst.setInt(2, seats);
        pst.executeUpdate();
        System.out.println("✅ Bus added.");
        con.close();
    }
}
