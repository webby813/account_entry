package javaassignment.pages;

import db_objects.PurchaseItemDAO;
import db_objects.Create_Data;
import db_objects.CustomTableModel;
import db_objects.InvoiceItemDAO;
import db_objects.Retrieve_Data;
import static db_objects.Retrieve_Data.fetchNextNumber;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import javaassignment.UserRole;
import static javaassignment.UserRole.ACCOUNTANT;
import static javaassignment.UserRole.AUDITOR;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class Invoice extends javax.swing.JFrame {
    Timestamp currentTime;
    private UserRole userRole;
    private int currentVoucherNo;
    private int nextVoucherNo;
    private String formattedDate;
    
    public Invoice(UserRole role) {
        initComponents();
        this.userRole = role;
        permissionDistribute(role);
        
        currentTime = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = dateFormat.format(currentTime);
        
        date.setEditable(false);
        Invoice_Table.setModel(new CustomTableModel());
        
         // Add the TableModelListener to calculate totals

        Invoice_Table.getModel().addTableModelListener(e -> {
        if (e.getColumn() == 1 || e.getColumn() == 2) { // Quantity or Price Per Item column
            int row = e.getFirstRow();
            Object quantityObj = Invoice_Table.getValueAt(row, 1);
            Object priceObj = Invoice_Table.getValueAt(row, 2);

            if (quantityObj != null && priceObj != null) {
                try {
                    int quantity = Integer.parseInt(quantityObj.toString());
                    float pricePerItem = Float.parseFloat(priceObj.toString());
                    float total = quantity * pricePerItem;
                    Invoice_Table.setValueAt(total, row, 3);
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(this, "Invalid input. Please enter numeric values for quantity and price.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    });

        
        date.setText(formattedDate);
        cheque_Date.setText(formattedDate);
        voucher_No.setEditable(false);
        date.setEditable(false);
        
        nextVoucherNo = fetchNextNumber("invoice", "voucher_No");
        currentVoucherNo = nextVoucherNo;
        voucher_No.setText(String.valueOf(nextVoucherNo));
        
        populateLedgerList();
    }
    
    private void permissionDistribute(UserRole role){
        switch(role){
            case AUDITOR -> {
                auditorCantVisit();
            }
            case ACCOUNTANT -> {
            }
            default -> System.out.println("Empty");
        }
    }
    
    private void auditorCantVisit(){
        Save.setVisible(false);
    }
    
    private void populateLedgerList() {
        Retrieve_Data retrieveData = new Retrieve_Data();
        List<String> ledgers = retrieveData.fetchLedgers();
        transaction_with.removeAllItems();
        for (String ledger : ledgers) {
            transaction_with.addItem(ledger);
        }
    }
    
    private void populateFieldsWithVoucherData(int voucherNo) {
        Retrieve_Data retrieveData = new Retrieve_Data();
        List<String> voucherData = retrieveData.fetchInvoiceData(voucherNo);

        if (!voucherData.isEmpty()) {
            // Populate the form fields with voucher data
            date.setText(voucherData.get(0));
            voucher_No.setText(voucherData.get(1));
            invoice_Type.setSelectedItem(voucherData.get(2));
            fromAccount.setSelectedItem(voucherData.get(3));
            transaction_with.setSelectedItem(voucherData.get(4));
            narration.setText(voucherData.get(5));
            receive_Type.setSelectedItem(voucherData.get(6));
            cheque_No.setText(voucherData.get(7));
            cheque_Date.setText(voucherData.get(8));

            // Lock the fields to prevent editing
            date.setEditable(false);
            voucher_No.setEditable(false);
            invoice_Type.setEnabled(false);
            fromAccount.setEnabled(false);
            transaction_with.setEnabled(false);
            narration.setEditable(false);
            receive_Type.setEnabled(false);
            cheque_No.setEditable(false);
            cheque_Date.setEditable(false);

            // Clear the table before populating new data
            CustomTableModel model = (CustomTableModel) Invoice_Table.getModel();
            model.setRowCount(0);

            // Fetch the items associated with the voucher
            List<String[]> items = retrieveData.fetchInvoiceItems(voucherNo);

            // Populate the table with the fetched items
            for (String[] item : items) {
                model.addRow(new Object[]{item[0], item[1], item[2], item[3]});
            }

            // Add an extra empty row
            model.addRow(new Object[]{"", "", "", ""});
        } else {
            JOptionPane.showMessageDialog(this, "No data found for voucher number: " + voucherNo, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void exportTableToExcel() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Invoice Data");

        // Create header information
        Row headerInfoRow1 = sheet.createRow(0);
        headerInfoRow1.createCell(0).setCellValue("Date");
        headerInfoRow1.createCell(1).setCellValue(date.getText());

        Row headerInfoRow2 = sheet.createRow(1);
        headerInfoRow2.createCell(0).setCellValue("Voucher No");
        headerInfoRow2.createCell(1).setCellValue(voucher_No.getText());

        Row headerInfoRow3 = sheet.createRow(2);
        headerInfoRow3.createCell(0).setCellValue("Invoice Type");
        headerInfoRow3.createCell(1).setCellValue(invoice_Type.getSelectedItem().toString());

        Row headerInfoRow4 = sheet.createRow(3);
        headerInfoRow4.createCell(0).setCellValue("From Account");
        headerInfoRow4.createCell(1).setCellValue(fromAccount.getSelectedItem().toString());

        Row headerInfoRow5 = sheet.createRow(4);
        headerInfoRow5.createCell(0).setCellValue("Transaction With");
        headerInfoRow5.createCell(1).setCellValue(transaction_with.getSelectedItem().toString());

        Row headerInfoRow6 = sheet.createRow(5);
        headerInfoRow6.createCell(0).setCellValue("Narration");
        headerInfoRow6.createCell(1).setCellValue(narration.getText());

        Row headerInfoRow7 = sheet.createRow(6);
        headerInfoRow7.createCell(0).setCellValue("Cheque No");
        headerInfoRow7.createCell(1).setCellValue(cheque_No.getText());

        Row headerInfoRow8 = sheet.createRow(7);
        headerInfoRow8.createCell(0).setCellValue("Cheque Date");
        headerInfoRow8.createCell(1).setCellValue(cheque_Date.getText());

        Row headerInfoRow9 = sheet.createRow(8);
        headerInfoRow9.createCell(0).setCellValue("Receive Type");
        headerInfoRow9.createCell(1).setCellValue(receive_Type.getSelectedItem().toString());

        Row headerInfoRow10 = sheet.createRow(9);
        headerInfoRow10.createCell(0).setCellValue("Total");
        headerInfoRow10.createCell(1).setCellValue(total.getText());

        // Create a header row for table data
        Row tableHeaderRow = sheet.createRow(11);
        for (int i = 0; i < Invoice_Table.getColumnCount(); i++) {
            Cell cell = tableHeaderRow.createCell(i);
            cell.setCellValue(Invoice_Table.getColumnName(i));
        }

        // Populate the sheet with data from the table
        for (int i = 0; i < Invoice_Table.getRowCount(); i++) {
            Row row = sheet.createRow(i + 12);
            for (int j = 0; j < Invoice_Table.getColumnCount(); j++) {
                Cell cell = row.createCell(j);
                Object value = Invoice_Table.getValueAt(i, j);
                if (value != null) {
                    if (value instanceof Number) {
                        cell.setCellValue(((Number) value).doubleValue());
                    } else {
                        cell.setCellValue(value.toString());
                    }
                }
            }
        }

        // Adjust column widths
        for (int i = 0; i < Invoice_Table.getColumnCount(); i++) {
            sheet.autoSizeColumn(i);
        }

        // Export to Excel file
        try (FileOutputStream fileOut = new FileOutputStream("InvoiceData.xlsx")) {
            workbook.write(fileOut);
            JOptionPane.showMessageDialog(this, "Data exported successfully to InvoiceData.xlsx", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error occurred while exporting data to Excel", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    
    private void clearFields() {
        date.setText(formattedDate);
        voucher_No.setText(String.valueOf(currentVoucherNo));
        invoice_Type.setSelectedIndex(0);
        fromAccount.setSelectedIndex(0);
        transaction_with.setSelectedIndex(0);
        narration.setText("");
        receive_Type.setSelectedIndex(0);
        cheque_No.setText("");
        cheque_Date.setText(formattedDate);

        date.setEditable(false);
        voucher_No.setEditable(false);
        invoice_Type.setEnabled(true);
        fromAccount.setEnabled(true);
        transaction_with.setEnabled(true);
        narration.setEditable(true);
        receive_Type.setEnabled(true);
        cheque_No.setEditable(true);
        cheque_Date.setEditable(true);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        invoice_Type = new javax.swing.JComboBox<>();
        fromAccount = new javax.swing.JComboBox<>();
        PreviousBtn = new javax.swing.JButton();
        NextBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        Save = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        Export = new javax.swing.JButton();
        date = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        voucher_No = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Invoice_Table = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        narration = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cheque_No = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        receive_Type = new javax.swing.JComboBox<>();
        total = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        cheque_Date = new javax.swing.JTextField();
        transaction_with = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Invoice");

        invoice_Type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cash", "Bank" }));

        fromAccount.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Assets", "Revenue", "Owner's Equity", "Expenses" }));

        PreviousBtn.setText("<");
        PreviousBtn.setPreferredSize(new java.awt.Dimension(50, 23));
        PreviousBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PreviousBtnActionPerformed(evt);
            }
        });

        NextBtn.setText(">");
        NextBtn.setPreferredSize(new java.awt.Dimension(50, 23));
        NextBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NextBtnActionPerformed(evt);
            }
        });

        jLabel2.setText("Date");

        Save.setText("Save");
        Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveActionPerformed(evt);
            }
        });

        jLabel3.setText("Type");

        Export.setText("Export");
        Export.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(255, 51, 51));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Back to home");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel4.setText("Voucher No");

        jLabel6.setText("Account");

        jLabel7.setText("Transaction with");

        Invoice_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Item", "Quantity", "Nett price", "Total"
            }
        ));
        jScrollPane1.setViewportView(Invoice_Table);

        jLabel5.setText("Narration");

        jLabel8.setText("Chq No.");

        jLabel9.setText("Receive / Receiveable (RM)");

        receive_Type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Receive", "Receiveable" }));
        receive_Type.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                receive_TypeActionPerformed(evt);
            }
        });

        jLabel10.setText("Chq Date");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(366, 366, 366)
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))
                                .addGap(42, 42, 42)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(cheque_No, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(narration, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(58, 58, 58)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(receive_Type, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(26, 26, 26)
                                        .addComponent(total, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cheque_Date, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fromAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(invoice_Type, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(transaction_with, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(voucher_No, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(11, 11, 11))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(PreviousBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(NextBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(Save)
                                        .addGap(18, 18, 18)
                                        .addComponent(Export)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton5)))))
                        .addGap(43, 43, 43))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(voucher_No, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(invoice_Type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(fromAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(transaction_with, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(narration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(receive_Type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cheque_No, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(cheque_Date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PreviousBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NextBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Save)
                    .addComponent(Export)
                    .addComponent(jButton5))
                .addGap(56, 56, 56))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        this.dispose();

    }//GEN-LAST:event_jButton5ActionPerformed

    private void PreviousBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PreviousBtnActionPerformed
        if (currentVoucherNo > 1) {
            currentVoucherNo--;
            populateFieldsWithVoucherData(currentVoucherNo);
        } else {
            JOptionPane.showMessageDialog(this, "This is the first invoice", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_PreviousBtnActionPerformed

    private void receive_TypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_receive_TypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_receive_TypeActionPerformed

    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveActionPerformed
        String invoiceType = invoice_Type.getSelectedItem().toString();
        String account = fromAccount.getSelectedItem().toString();
        String transactionWith = transaction_with.getSelectedItem().toString();
        String _narration = narration.getText();
        String receiveType = receive_Type.getSelectedItem().toString();
        String chequeNoText = cheque_No.getText().trim();
        String totalText = total.getText().trim();
        String voucherNoText = voucher_No.getText().trim(); // Get the voucher_No from the text field

        if (invoiceType.isEmpty() || account.isEmpty() || transactionWith.isEmpty() || _narration.isEmpty() || receiveType.isEmpty() || totalText.isEmpty() || voucherNoText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int chequeNo = 0;
        int total = 0;
        int voucherNo = 0;

        try {
            if (!chequeNoText.isEmpty()) {
                chequeNo = Integer.parseInt(chequeNoText);
            }
            if (!totalText.isEmpty()) {
                total = Integer.parseInt(totalText);
            }
            if (!voucherNoText.isEmpty()) {
                voucherNo = Integer.parseInt(voucherNoText); // Parse the voucher_No
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input format. Please enter valid numbers for cheque number and amount.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        InvoiceItemDAO invoiceItemDAO = new InvoiceItemDAO();
        boolean atLeastOneRowFilled = false;

        for (int i = 0; i < Invoice_Table.getRowCount(); i++) {
            Object itemNameObj = Invoice_Table.getValueAt(i, 0);
            Object quantityObj = Invoice_Table.getValueAt(i, 1);
            Object pricePerItemObj = Invoice_Table.getValueAt(i, 2);
            Object totalForItemObj = Invoice_Table.getValueAt(i, 3);

            // Check if at least one row is filled
            if (itemNameObj != null && quantityObj != null && pricePerItemObj != null && totalForItemObj != null) {
                atLeastOneRowFilled = true;
                break;
            }
        }

        if (!atLeastOneRowFilled) {
            JOptionPane.showMessageDialog(this, "Please fill at least one row in the table.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (int i = 0; i < Invoice_Table.getRowCount(); i++) {
            Object itemNameObj = Invoice_Table.getValueAt(i, 0);
            Object quantityObj = Invoice_Table.getValueAt(i, 1);
            Object pricePerItemObj = Invoice_Table.getValueAt(i, 2);
            Object totalForItemObj = Invoice_Table.getValueAt(i, 3);

            if (itemNameObj != null && quantityObj != null && pricePerItemObj != null && totalForItemObj != null) {
                String itemName = itemNameObj.toString();
                int quantity = Integer.parseInt(quantityObj.toString());
                float pricePerItem = Float.parseFloat(pricePerItemObj.toString());
                float totalForItem = Float.parseFloat(totalForItemObj.toString());

                boolean itemSuccess = invoiceItemDAO.SaveInvoiceItem(voucherNo, itemName, quantity, pricePerItem, totalForItem, transactionWith);

                if (!itemSuccess) {
                    JOptionPane.showMessageDialog(this, "Failed to insert item: " + itemName, "Insertion Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }

        Create_Data createData = new Create_Data();
        boolean success = createData.SaveInvoiceRecord(currentTime, currentTime, invoiceType, account, transactionWith, _narration, receiveType, chequeNo, total,voucherNo);

        if (success) {
            JOptionPane.showMessageDialog(this, "Invoice saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to save invoice.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_SaveActionPerformed


    private void NextBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NextBtnActionPerformed
        if (currentVoucherNo < nextVoucherNo - 1) {
            currentVoucherNo++;
            populateFieldsWithVoucherData(currentVoucherNo);
        } else if (currentVoucherNo == nextVoucherNo - 1) {
            currentVoucherNo++;
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "This is the latest invoice.");
        }
    }//GEN-LAST:event_NextBtnActionPerformed

    private void ExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportActionPerformed
        
        exportTableToExcel();
        
    }//GEN-LAST:event_ExportActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UserRole role = UserRole.AUDITOR;
                new Invoice(role).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Export;
    private javax.swing.JTable Invoice_Table;
    private javax.swing.JButton NextBtn;
    private javax.swing.JButton PreviousBtn;
    private javax.swing.JButton Save;
    private javax.swing.JTextField cheque_Date;
    private javax.swing.JTextField cheque_No;
    private javax.swing.JTextField date;
    private javax.swing.JComboBox<String> fromAccount;
    private javax.swing.JComboBox<String> invoice_Type;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField narration;
    private javax.swing.JComboBox<String> receive_Type;
    private javax.swing.JTextField total;
    private javax.swing.JComboBox<String> transaction_with;
    private javax.swing.JTextField voucher_No;
    // End of variables declaration//GEN-END:variables
}
