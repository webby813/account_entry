package db_objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Retrieve_Data {
    public List<String> fetchLedgers() {
        List<String> ledgers = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/acc_entry", "root", "");
            stmt = con.createStatement();
            // Adjusted SQL query to select rows where ledger_name and acc_group are the same
            String query = "SELECT ledger_name FROM ledger WHERE ledger_name = acc_group";
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                ledgers.add(rs.getString("ledger_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ledgers;
    }
}