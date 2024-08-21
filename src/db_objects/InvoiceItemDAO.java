package db_objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class InvoiceItemDAO {

    // Method to insert a record into the invoice_item table
    public boolean insertInvoiceItem(String itemName, int quantity, float pricePerItem, float total, String transactionWith, String voucherNo) {
        String sql = "INSERT INTO invoice_item (item_name, quantity, price_per_item, total, transaction_with, voucher_No) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/acc_entry", "root", "");
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, itemName);
            pstmt.setInt(2, quantity);
            pstmt.setFloat(3, pricePerItem);
            pstmt.setFloat(4, total);
            pstmt.setString(5, transactionWith);
            pstmt.setString(6, voucherNo);

            int rowsInserted = pstmt.executeUpdate();
            System.out.println(rowsInserted + " record inserted into invoice_item");
            return rowsInserted > 0;

        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return false;
        }
    }
    
    public boolean SaveInvoiceItem(int voucherNo, String itemName, int quantity, float pricePerItem, float totalForItem, String transactionWith) {
        String sql = "INSERT INTO invoice_item (item_name, quantity, price_per_item, total, transaction_with, voucher_No) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/acc_entry", "root", "");
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, itemName);
            pstmt.setInt(2, quantity);
            pstmt.setFloat(3, pricePerItem);
            pstmt.setFloat(4, totalForItem);
            pstmt.setString(5, transactionWith);
            pstmt.setInt(6, voucherNo);

            pstmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
            return false;
        }
    }

}
