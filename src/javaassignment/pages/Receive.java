package javaassignment.pages;

import db_objects.Create_Data;
import db_objects.Retrieve_Data;
import static db_objects.Retrieve_Data.fetchNextNumber;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Receive extends javax.swing.JFrame {
    Timestamp currentTime;
    private int currentVoucherNo;
    private int nextVoucherNo;
    private String formattedDate;
    
    public Receive() {
        initComponents();
        currentTime = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = dateFormat.format(currentTime);
        
        posting_Date.setText(formattedDate);
        cheque_Date.setText(formattedDate);
        voucher_No.setEditable(false);
        cheque_Date.setEditable(false);
        
        nextVoucherNo = fetchNextNumber("voucher", "voucher_No");
        currentVoucherNo = nextVoucherNo;
        voucher_No.setText(String.valueOf(nextVoucherNo));
        
        populateLedgerList();
    }
    
    private void populateLedgerList() {
        Retrieve_Data retrieveData = new Retrieve_Data();
        List<String> ledgers = retrieveData.fetchLedgerGroup();
        ledger_list.removeAllItems();
        for (String ledger : ledgers) {
            ledger_list.addItem(ledger);
        }
    }
    
    private void populateFieldsWithVoucherData(int voucherNo) {
        Retrieve_Data retrieveData = new Retrieve_Data();
        List<String> voucherData = retrieveData.fetchVoucherData(voucherNo);
        if (!voucherData.isEmpty()) {
            voucher_No.setText(voucherData.get(0));
            posting_Date.setText(voucherData.get(1));
            ledger_list.setSelectedItem(voucherData.get(2));
            transfer_Type.setSelectedItem(voucherData.get(3));
            cheque_No.setText(voucherData.get(4));
            cheque_Date.setText(voucherData.get(5));
            narration.setText(voucherData.get(6));
            amount.setText(voucherData.get(7));

            // Set fields to non-editable
            voucher_No.setEditable(false);
            posting_Date.setEditable(false);
            ledger_list.setEnabled(false);
            transfer_Type.setEnabled(false);
            cheque_No.setEditable(false);
            cheque_Date.setEditable(false);
            narration.setEditable(false);
            amount.setEditable(false);
        } else {
            JOptionPane.showMessageDialog(this, "No data found for voucher number: " + voucherNo, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearFields() {
        voucher_No.setText(String.valueOf(currentVoucherNo));
        posting_Date.setText(formattedDate);
        ledger_list.setSelectedIndex(0);
        transfer_Type.setSelectedIndex(0);
        cheque_No.setText("");
        cheque_Date.setText(formattedDate);
        narration.setText("");
        amount.setText("");

        voucher_No.setEditable(false);
        posting_Date.setEditable(true);
        ledger_list.setEnabled(true);
        transfer_Type.setEnabled(true);
        cheque_No.setEditable(false);
        cheque_Date.setEditable(true);
        narration.setEditable(true);
        amount.setEditable(true);
    }
    
        private void exportTableToExcel() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Invoice Data");

        // Create header information
        Row headerInfoRow1 = sheet.createRow(0);
        headerInfoRow1.createCell(0).setCellValue("Voucher No");
        headerInfoRow1.createCell(1).setCellValue(voucher_No.getText());

        Row headerInfoRow2 = sheet.createRow(1);
        headerInfoRow2.createCell(0).setCellValue("Voucher Type");
        headerInfoRow2.createCell(1).setCellValue(voucher_Type.getSelectedItem().toString());

        Row headerInfoRow3 = sheet.createRow(2);
        headerInfoRow3.createCell(0).setCellValue("Posting Date");
        headerInfoRow3.createCell(1).setCellValue(posting_Date.getText().toString());

        Row headerInfoRow4 = sheet.createRow(3);
        headerInfoRow4.createCell(0).setCellValue("From ");
        headerInfoRow4.createCell(1).setCellValue(ledger_list.getSelectedItem().toString());

        Row headerInfoRow5 = sheet.createRow(4);
        headerInfoRow5.createCell(0).setCellValue("Amount");
        headerInfoRow5.createCell(1).setCellValue(amount.getText());

        Row headerInfoRow6 = sheet.createRow(5);
        headerInfoRow6.createCell(0).setCellValue("Transfer type");
        headerInfoRow6.createCell(1).setCellValue(transfer_Type.getSelectedItem().toString());

        Row headerInfoRow7 = sheet.createRow(6);
        headerInfoRow7.createCell(0).setCellValue("Cheque No");
        headerInfoRow7.createCell(1).setCellValue(cheque_No.getText());

        Row headerInfoRow8 = sheet.createRow(7);
        headerInfoRow8.createCell(0).setCellValue("Cheque Date");
        headerInfoRow8.createCell(1).setCellValue(cheque_Date.getText());

        Row headerInfoRow9 = sheet.createRow(8);
        headerInfoRow9.createCell(0).setCellValue("Narration");
        headerInfoRow9.createCell(1).setCellValue(narration.getText());

        // Export to Excel file
        try (FileOutputStream fileOut = new FileOutputStream("src/ExportedReport/Payment Voucher.xlsx")) {
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        narration = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        cheque_Date = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        amount = new javax.swing.JTextField();
        submit = new javax.swing.JButton();
        Export = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        voucher_No = new javax.swing.JTextField();
        voucher_Type = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        posting_Date = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        ledger_list = new javax.swing.JComboBox<>();
        transfer_Type = new javax.swing.JComboBox<>();
        cheque_No = new javax.swing.JTextField();
        PreviousBtn = new javax.swing.JButton();
        NextBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel10.setText("Chq. Date");

        jLabel11.setText("Amount");

        submit.setText("Save & New");
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitActionPerformed(evt);
            }
        });

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

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Receive Voucher");

        jLabel2.setText("Voucher No : ");

        voucher_Type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Receive", "Payment" }));

        jLabel4.setText("Posting Date : ");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Particulars");

        jLabel6.setText("Ledger ");

        jLabel7.setText("Cash/Bank");

        jLabel8.setText("Chq. No");

        jLabel9.setText("Narration");

        ledger_list.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select ledger", "Berjaya Food" }));

        transfer_Type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cash", "Bank" }));

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(335, 335, 335)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(ledger_list, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(transfer_Type, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(voucher_No, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(cheque_No, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(27, 27, 27)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(voucher_Type, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(cheque_Date, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(PreviousBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(NextBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(submit))
                                            .addComponent(narration))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(posting_Date, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                                            .addComponent(amount)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(Export)
                                        .addGap(110, 110, 110)))))))
                .addContainerGap(54, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addGap(49, 49, 49))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(voucher_No, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(voucher_Type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(posting_Date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(ledger_list, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(amount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(transfer_Type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cheque_No, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(cheque_Date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(narration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(submit)
                    .addComponent(Export)
                    .addComponent(jButton5)
                    .addComponent(PreviousBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NextBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(156, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void submitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitActionPerformed
        String voucherType = voucher_Type.getSelectedItem().toString();
        String ledger = ledger_list.getSelectedItem().toString();
        String narrationText = narration.getText().trim();
        String chequeNoText = cheque_No.getText().trim();
        String amountText = amount.getText().trim();
        String transferType = transfer_Type.getSelectedItem().toString();

        int chequeNo = 0;
        int amountValue = 0;

        try {
            if (!chequeNoText.isEmpty()) {
                chequeNo = Integer.parseInt(chequeNoText);
            }
            if (!amountText.isEmpty()) {
                amountValue = Integer.parseInt(amountText);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input format. Please enter valid numbers for cheque number and amount.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (voucherType.isEmpty() || ledger.isEmpty() || narrationText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Create_Data createData = new Create_Data();
        boolean success = createData.create_voucher(voucherType, currentTime, ledger, "receive", chequeNo, currentTime, narrationText, amountValue);

        if (success) {
            JOptionPane.showMessageDialog(this, "Record inserted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to insert data. Please check the fields and try again.", "Insertion Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_submitActionPerformed

    private void PreviousBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PreviousBtnActionPerformed
        if (currentVoucherNo > 1) {
            currentVoucherNo--;
            populateFieldsWithVoucherData(currentVoucherNo);
        } else {
            JOptionPane.showMessageDialog(this, "This is the first voucher", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_PreviousBtnActionPerformed

    private void NextBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NextBtnActionPerformed
        if (currentVoucherNo < nextVoucherNo - 1) {
            currentVoucherNo++;
            populateFieldsWithVoucherData(currentVoucherNo);
        } else if (currentVoucherNo == nextVoucherNo - 1) {
            currentVoucherNo++;
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "This is the latest voucher.");
        }
    }//GEN-LAST:event_NextBtnActionPerformed

    private void ExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportActionPerformed
        exportTableToExcel();
    }//GEN-LAST:event_ExportActionPerformed


    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Receive().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Export;
    private javax.swing.JButton NextBtn;
    private javax.swing.JButton PreviousBtn;
    private javax.swing.JTextField amount;
    private javax.swing.JTextField cheque_Date;
    private javax.swing.JTextField cheque_No;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JComboBox<String> ledger_list;
    private javax.swing.JTextField narration;
    private javax.swing.JTextField posting_Date;
    private javax.swing.JButton submit;
    private javax.swing.JComboBox<String> transfer_Type;
    private javax.swing.JTextField voucher_No;
    private javax.swing.JComboBox<String> voucher_Type;
    // End of variables declaration//GEN-END:variables
}
