package db_objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

public class Create_Data {

    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private String validateVoucherData(String voucher_Type, Timestamp posting_Date, String selectedLedger,
                                       int cheque_No, Timestamp cheque_Date,String narration,int amount) {
        if (isNullOrEmpty(voucher_Type)) {
            return "Voucher Type is required.";
        }
        if (posting_Date == null) {
            return "Posting Date is required.";
        }
        if (isNullOrEmpty(selectedLedger)) {
            return "Ledger Name is required.";
        }
        if (cheque_Date == null) {
            return "Cheque Date is required.";
        }
        if (isNullOrEmpty(narration)) {
            return "Narration is required.";
        }
        if (amount < 0) {
            return "Amount must be non-negative.";
        }
        if (cheque_No < 0) {
            return "Cheque Number must be non-negative.";
        }
        return null;
    }

    public boolean create_voucher(String voucher_Type, Timestamp posting_Date, String selectedLedger, String transferType,
                                  int cheque_No, Timestamp cheque_Date, String narration, int amount) {

        String errorMessage = validateVoucherData(voucher_Type, posting_Date, selectedLedger, cheque_No, cheque_Date, narration, amount);
        if (errorMessage != null) {
            System.out.println("Error: " + errorMessage);
            return false;
        }
        String sql = "INSERT INTO voucher(voucher_Type, posting_Date, ledger_Name, transfer_Type, cheque_No, cheque_Date, narration, amount) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/acc_entry", "root", "");
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, voucher_Type);
            pstmt.setTimestamp(2, posting_Date);
            pstmt.setString(3, selectedLedger);
            pstmt.setString(4, transferType);
            pstmt.setInt(5, cheque_No);
            pstmt.setTimestamp(6, cheque_Date);
            pstmt.setString(7, narration);
            pstmt.setInt(8, amount);

            int rowsInserted = pstmt.executeUpdate();
            System.out.println(rowsInserted + " record inserted");
            return true;

        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return false;
        }
    }
    
    public boolean CreateLedger(String new_Ledger, String acc_Group) {
        String sql = "INSERT INTO ledger (ledger_Name, acc_Group) VALUES (?, ?)";
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/acc_entry", "root", "");
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, new_Ledger);
            pstmt.setString(2, acc_Group);
            int rowsInserted = pstmt.executeUpdate();
            System.out.println(rowsInserted + " record inserted");
            return rowsInserted > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean CreateLedgerGroup(String group_Name) {
        String sql = "INSERT INTO ledger (ledger_Name, acc_Group) VALUES (?,?)";
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/acc_entry", "root", "");
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, group_Name); 
            pstmt.setString(2, group_Name);
            int rowsInserted = pstmt.executeUpdate();
            System.out.println(rowsInserted + " record inserted");
            return rowsInserted > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean CreateItemGroup(String group_Name) {
        String sql = "INSERT INTO inventory (item_Name, item_Group) VALUES (?,?)";
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/acc_entry", "root", "");
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, group_Name); 
            pstmt.setString(2, group_Name);
            int rowsInserted = pstmt.executeUpdate();
            System.out.println(rowsInserted + " record inserted");
            return rowsInserted > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean CreateItem(String item_Name, String item_Group, int item_quantity, float item_price) {
        String sql = "INSERT INTO inventory (item_Name, item_Group, item_quantity, item_price) VALUES (?,?,?,?)";
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/acc_entry", "root", "");
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, item_Name); 
            pstmt.setString(2, item_Group);
            pstmt.setInt(3, item_quantity);
            pstmt.setFloat(4, item_price);
            int rowsInserted = pstmt.executeUpdate();
            System.out.println(rowsInserted + " record inserted");
            return rowsInserted > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean SaveInvoiceRecord(Timestamp time, Timestamp chequeDate, String invoiceType, String account, String transactionWith, String narration, String receiveType, int chequeNo, int total) {
        String sql = "INSERT INTO invoice(date, invoice_Type, account, transaction_with, narration, receive_Type, total, cheque_Date, cheque_No) VALUES(?,?,?,?,?,?,?,?,?)";
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/acc_entry", "root", "");
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setTimestamp(1, time);
            pstmt.setString(2, invoiceType);
            pstmt.setString(3, account);
            pstmt.setString(4, transactionWith);
            pstmt.setString(5, narration);
            pstmt.setString(6, receiveType);
            pstmt.setInt(7, total);
            pstmt.setTimestamp(8, chequeDate);
            pstmt.setInt(9, chequeNo);

            int rowsInserted = pstmt.executeUpdate();
            System.out.println(rowsInserted + " record inserted");
            return rowsInserted > 0;

        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return false;
        }
    }
    
    public boolean SavePurchaseRecord(Timestamp time, Timestamp chequeDate, String account, String transaction_with, String tranx_Type, String narration, int cheque_No, int total) {
        String sql = "INSERT INTO purchase(date, cheque_Date, account, transaction_with, tranx_Type, narration, cheque_No, total) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/acc_entry", "root", "");
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setTimestamp(1, time);
            pstmt.setTimestamp(2, chequeDate);
            pstmt.setString(3, account);
            pstmt.setString(4, transaction_with);
            pstmt.setString(5, tranx_Type);
            pstmt.setString(6, narration);
            pstmt.setInt(7, cheque_No);
            pstmt.setInt(8, total);

            int rowsInserted = pstmt.executeUpdate();
            System.out.println(rowsInserted + " record inserted");
            return rowsInserted > 0;

        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return false;
        }
    }
}
