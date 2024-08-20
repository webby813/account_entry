package db_objects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import javaassignment.pages.Items;

public class Update_Data {

    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/acc_entry", "root", "");
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private String validateItemData(String itemName, int quantity, float price) {
        if (isNullOrEmpty(itemName)) {
            return "Item Name is required.";
        }
        if (quantity < 0) {
            return "Quantity must be non-negative.";
        }
        if (price < 0) {
            return "Price must be non-negative.";
        }
        return null;
    }

    public boolean itemExists(String itemName) {
        String query = "SELECT * FROM inventory WHERE item_Name = ?";
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, itemName);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            return false;
        }
    }

    public void handleExistingItem(String itemName, int itemQty, float itemPrice, Items parentFrame) {
        Update_Data itemUpdater = new Update_Data();

        int response = JOptionPane.showConfirmDialog(parentFrame,
                "Item already exists. Do you want to update it?",
                "Item Exists",
                JOptionPane.YES_NO_OPTION);

        StringBuilder message = new StringBuilder();

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement("SELECT * FROM inventory WHERE item_Name = ?")) {
            pstmt.setString(1, itemName);
            ResultSet rs = pstmt.executeQuery();

            if (response == JOptionPane.YES_OPTION) {
                if (rs.next()) {
                    boolean updated = false;
                    if (itemQty > 0) {
                        updated = itemUpdater.updateItemQuantity(itemName, itemQty);
                        message.append("Quantity updated. ");
                    }

                    if (itemPrice != -1 && itemPrice != rs.getFloat("item_price")) {
                        updated = itemUpdater.updateItemPrice(itemName, itemPrice);
                        message.append("Price updated. ");
                    }

                    if (updated) {
                        JOptionPane.showMessageDialog(parentFrame, message.toString(), "Update Successful", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(parentFrame, "No changes were made.", "Update Info", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(parentFrame, "Update cancelled.", "Cancelled", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parentFrame, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean updateItemQuantity(String itemName, int quantityToAdd) {
        String validationError = validateItemData(itemName, quantityToAdd, 0);
        if (validationError != null) {
            System.out.println("Error: " + validationError);
            return false;
        }
        String sql = "UPDATE inventory SET item_quantity = item_quantity + ? WHERE item_Name = ?";
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, quantityToAdd);
            pstmt.setString(2, itemName);
            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateItemPrice(String itemName, float newPrice) {
        String validationError = validateItemData(itemName, 0, newPrice);
        if (validationError != null) {
            System.out.println("Error: " + validationError);
            return false;
        }
        String sql = "UPDATE inventory SET item_price = ? WHERE item_Name = ?";
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setFloat(1, newPrice);
            pstmt.setString(2, itemName);
            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            return false;
        }
    }
}
