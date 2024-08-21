package db_objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Retrieve_Data {
    
     public List<Object[]> fetchUsers() {
        List<Object[]> users = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/acc_entry", "root", "");
            stmt = con.createStatement();
            String query = "SELECT Name, Role, Pass, Phone, Email FROM users";
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String username = rs.getString("Name");
                String role = rs.getString("Role");
                String password = rs.getString("Pass");
                String phone = rs.getString("Phone");
                String email = rs.getString("Email");
                users.add(new Object[]{username, role, password, phone, email});
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
        return users;
    }
    
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
    
    public List<String[]> fetchInvoiceItems(int voucherNo) {
        List<String[]> items = new ArrayList<>();
        String sql = "SELECT item_name, quantity, price_per_item, total FROM invoice_item WHERE voucher_no = ?";

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/acc_entry", "root", "");
            PreparedStatement pstmt = con.prepareStatement(sql);  // Removed the extra closing parenthesis
            pstmt.setInt(1, voucherNo);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String itemName = rs.getString("item_name");
                String quantity = rs.getString("quantity");
                String pricePerItem = rs.getString("price_per_item");
                String total = rs.getString("total");
                items.add(new String[]{itemName, quantity, pricePerItem, total});
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }

    public List<String[]> fetchPurchaseItems(int voucherNo) {
     List<String[]> items = new ArrayList<>();
     String sql = "SELECT item_name, quantity, price_per_item, total FROM purchase_item WHERE voucher_no = ?";

     try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/acc_entry", "root", "");
          PreparedStatement pstmt = con.prepareStatement(sql)) {

         pstmt.setInt(1, voucherNo);
         try (ResultSet rs = pstmt.executeQuery()) {
             while (rs.next()) {
                 String itemName = rs.getString("item_name");
                 String quantity = rs.getString("quantity");
                 String pricePerItem = rs.getString("price_per_item");
                 String total = rs.getString("total");
                 items.add(new String[]{itemName, quantity, pricePerItem, total});
             }
         }
     } catch (Exception e) {
            e.printStackTrace();
        }


     System.out.println("Fetched items: " + items.size());
     return items;
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
    
    public List<String> fetchVoucherData(int voucherNo) {
        List<String> voucherData = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/acc_entry", "root", "");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM voucher WHERE voucher_No = ?")) {

            stmt.setInt(1, voucherNo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                voucherData.add(rs.getString("voucher_No"));
                voucherData.add(rs.getString("posting_Date"));
                voucherData.add(rs.getString("ledger_Name"));
                voucherData.add(rs.getString("transfer_Type"));
                voucherData.add(rs.getString("cheque_No"));
                voucherData.add(rs.getString("cheque_Date"));
                voucherData.add(rs.getString("narration"));
                voucherData.add(rs.getString("amount"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return voucherData;
    }
    
    public List<String> fetchInvoiceData(int invoiceNo){
        List<String> invoiceData = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/acc_entry", "root", "");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM invoice WHERE voucher_No = ?")){
            
            stmt.setInt(1, invoiceNo);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                invoiceData.add(rs.getString("date"));
                invoiceData.add(rs.getString("voucher_No"));
                invoiceData.add(rs.getString("invoice_Type"));
                invoiceData.add(rs.getString("account"));
                invoiceData.add(rs.getString("transaction_with"));
                invoiceData.add(rs.getString("narration"));
                invoiceData.add(rs.getString("receive_Type"));
                invoiceData.add(rs.getString("total"));
                invoiceData.add(rs.getString("cheque_No"));
                invoiceData.add(rs.getString("cheque_Date"));
            }
            
        }catch(Exception e){
            
        }
        return invoiceData;
    }
    
    public List<String> fetchPurchaseData(int invoiceNo){
        List<String> invoiceData = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/acc_entry", "root", "");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM purchase WHERE voucher_No = ?")){
            
            stmt.setInt(1, invoiceNo);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                invoiceData.add(rs.getString("date"));
                invoiceData.add(rs.getString("voucher_No"));
                invoiceData.add(rs.getString("tranx_Type"));
                invoiceData.add(rs.getString("account"));
                invoiceData.add(rs.getString("transaction_with"));
                invoiceData.add(rs.getString("narration"));
                invoiceData.add(rs.getString("total"));
                invoiceData.add(rs.getString("cheque_No"));
                invoiceData.add(rs.getString("cheque_Date"));
            }
            
        }catch(Exception e){
            
        }
        return invoiceData;
    }
}