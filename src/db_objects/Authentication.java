package db_objects;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javaassignment.Home;
import javaassignment.UserRole;
import javax.swing.JFrame;

public class Authentication {

    public UserRole checkRole(String userRole) {
        if ("accountant".equals(userRole)) {
            return UserRole.ACCOUNTANT;
        } else {
            return UserRole.AUDITOR;
        }
    }

    public void login(String username, String password, JFrame currentFrame) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/acc_entry", "root", "");
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM users WHERE Name = ?");
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String accPass = rs.getString("Pass");
                String userRole = rs.getString("Role");
                if (accPass.equals(password) && userRole != null) {
                    System.out.println("Login Success");
                    System.out.println(userRole);
                    UserRole role = checkRole(userRole);
                    JFrame home = new Home(role);
                    currentFrame.dispose();
                    home.setVisible(true);
                } else {
                    System.out.println("Failed login");
                }
            } else {
                System.out.println("User not found");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
