package busresv;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
    public static Connection getConnection() throws Exception {
        String url = "jdbc:postgresql://localhost:5432/jdbcdemo"; 
        String username = "postgres";
        String password = "Lakshmi@2001"; 
       

        return DriverManager.getConnection(url, username, password);
    }
}
