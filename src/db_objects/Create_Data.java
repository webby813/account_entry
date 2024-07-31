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
             PreparedStatement cstmt = con.prepareStatement(sql)) {

            cstmt.setString(1, voucher_Type);
            cstmt.setTimestamp(2, posting_Date);
            cstmt.setString(3, selectedLedger);
            cstmt.setString(4, transferType);
            cstmt.setInt(5, cheque_No);
            cstmt.setTimestamp(6, cheque_Date);
            cstmt.setString(7, narration);
            cstmt.setInt(8, amount);

            int rowsInserted = cstmt.executeUpdate();
            System.out.println(rowsInserted + " record inserted");
            return true;

        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return false;
        }
    }
}
