package busresv;
import java.sql.*;
import java.util.Scanner;

public class UserService {

    public static void registerUser() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        Connection con = DBUtil.getConnection();
        String query = "INSERT INTO user_account (username, password) VALUES (?, ?)";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, username);
        pst.setString(2, password);
        pst.executeUpdate();
        System.out.println("✅ Registration successful.");
        con.close();
    }

    public static int loginUser() throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();

        Connection con = DBUtil.getConnection();
        String query = "SELECT user_id FROM user_account WHERE username = ? AND password = ?";
        PreparedStatement pst = con.prepareStatement(query);
        pst.setString(1, username);
        pst.setString(2, password);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            System.out.println("✅ Login successful.");
            return rs.getInt("user_id");
        } else {
            System.out.println("❌ Invalid credentials.");
            return -1;
        }
    }
}

