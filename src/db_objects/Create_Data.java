package db_objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

public class Create_Data {

    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private String validateVoucherData(String voucher_Type, 
                                       Timestamp posting_Date,
                                       String selectedLedger,
                                       int cheque_No, 
                                       Timestamp cheque_Date,
                                       String narration,
                                       int amount) {
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

    public boolean create_voucher(String voucher_Type, 
                                  Timestamp posting_Date,
                                  String selectedLedger,
                                  int cheque_No, 
                                  Timestamp cheque_Date,
                                  String narration,
                                  int amount) {

        // SQL query for inserting data
        String sql = "INSERT INTO voucher(voucher_Type, posting_Date, ledger_Name, cheque_No, cheque_Date, narration, amount) VALUES(?,?,?,?,?,?,?)";

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/acc_entry", "root", "");
             PreparedStatement cstmt = con.prepareStatement(sql)) {

            // Set parameters
            cstmt.setString(1, voucher_Type);
            cstmt.setTimestamp(2, posting_Date);
            cstmt.setString(3, selectedLedger);
            cstmt.setInt(4, cheque_No);
            cstmt.setTimestamp(5, cheque_Date);
            cstmt.setString(6, narration);
            cstmt.setInt(7, amount);

            // Execute the statement
            int rowsInserted = cstmt.executeUpdate();
            System.out.println(rowsInserted + " record inserted");
            return true;

        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return false;
        }
    }
}
