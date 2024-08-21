package db_objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddUser {
    private Connection con;

    public AddUser() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/acc_entry", "root", "");
    }

    public boolean insertUser(String username, String password, String role) {
        String query = "INSERT INTO users (Name, Pass, Role) VALUES (?, ?, ?)";
        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, role);
            int rowsAffected = pst.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
