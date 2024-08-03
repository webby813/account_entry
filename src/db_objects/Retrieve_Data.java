package db_objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Retrieve_Data {
    
    public List<String> fetchLedgerGroup() {
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
    
    public List<String> fetchLedgers() {
        List<String> ledgers = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/acc_entry", "root", "");
            stmt = con.createStatement();
            // SQL query to select rows where ledger_name is not equal to ledger_group
            String query = "SELECT ledger_name FROM ledger WHERE ledger_name <> acc_Group";
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
    
    public List<String> fetchItemGroup() {
        List<String> itemGroups = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/acc_entry", "root", "");
            Statement stmt = con.createStatement();

            String query = "SELECT DISTINCT item_Group FROM inventory";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                itemGroups.add(rs.getString("item_Group"));
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return itemGroups;
    }
    
    public static int fetchNextNumber(String tableName, String columnName) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        int nextNumber = 1;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/acc_entry", "root", "");
            stmt = con.createStatement();

            String query = String.format("SELECT MAX(%s) AS max_number FROM %s", columnName, tableName);
            rs = stmt.executeQuery(query);

            if (rs.next()) {
                int maxNumber = rs.getInt("max_number");
                nextNumber = maxNumber + 1;
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
        return nextNumber;
    }
    
    
    
}