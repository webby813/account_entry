package javaassignment.pages;

import db_objects.Create_Data;
import db_objects.Retrieve_Data;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import javaassignment.UserRole;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class LedgerList extends javax.swing.JFrame {
    
    private UserRole userRole;
    
    public LedgerList(UserRole role) {
        this.userRole = role;
        initComponents();
        configureUIForRole();
        populateLedgerList();
    }
    
    private void configureUIForRole() {
        if (userRole == UserRole.AUDITOR) {
            addLedgerBtn.setVisible(false);
        }
    }

    private void fetchData(String selectedGroup) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/acc_entry", "root", "");
            Statement stmt = con.createStatement();

            String query;
            if ("Select Account Group".equals(selectedGroup)) {
                // Fetch all records if no specific group is selected
                query = "SELECT * FROM ledger";
            } else {
                // Fetch records based on the selected group
                query = "SELECT * FROM ledger WHERE acc_Group = '" + selectedGroup + "'";
            }

            ResultSet rs = stmt.executeQuery(query);

            DefaultTableModel model = (DefaultTableModel) Ledger_Table.getModel();
            model.setRowCount(0); // Clear existing rows
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("ledger_Name"),
                    rs.getString("acc_Group")
                });
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void populateLedgerList() {
        Retrieve_Data retrieveData = new Retrieve_Data();
        List<String> ledgers = retrieveData.fetchLedgers();
        selected_Group.removeAllItems();
        for (String ledger : ledgers) {
            selected_Group.addItem(ledger);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Ledger_Table = new javax.swing.JTable();
        new_ledger = new javax.swing.JTextField();
        selected_Group = new javax.swing.JComboBox<>();
        previousbtn = new javax.swing.JButton();
        next_btn = new javax.swing.JButton();
        addLedgerBtn = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Ledger List");

        jLabel2.setText("Ledger Name");

        jLabel3.setText("Account Group");

        Ledger_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Ledger Name", "Account Group"
            }
        ));
        jScrollPane1.setViewportView(Ledger_Table);

        selected_Group.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selected_GroupActionPerformed(evt);
            }
        });

        previousbtn.setText("<");
        previousbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousbtnActionPerformed(evt);
            }
        });

        next_btn.setText(">");

        addLedgerBtn.setText("Add");
        addLedgerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addLedgerBtnActionPerformed(evt);
            }
        });

        jButton4.setText("Clear");

        jButton5.setBackground(new java.awt.Color(255, 51, 51));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Close Form");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(addLedgerBtn)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton4))
                                    .addComponent(previousbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton5)
                                    .addComponent(next_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(selected_Group, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(new_ledger, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(new_ledger, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(selected_Group, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(58, 58, 58)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(previousbtn)
                            .addComponent(next_btn))
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton4)
                            .addComponent(addLedgerBtn)
                            .addComponent(jButton5))))
                .addContainerGap(114, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void previousbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousbtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_previousbtnActionPerformed

    private void addLedgerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addLedgerBtnActionPerformed
        String newLedger = new_ledger.getText().trim();
        String selectedGroup = selected_Group.getSelectedItem().toString();

        try {
            if (newLedger.isEmpty() || "Select Account Group".equals(selectedGroup)) {
                JOptionPane.showMessageDialog(this, "Please make sure input is not empty and group is selected.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                Create_Data createData = new Create_Data();
                boolean success = createData.CreateLedger(newLedger, selectedGroup);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Record inserted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to insert data. Please check the fields and try again.", "Insertion Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_addLedgerBtnActionPerformed

    private void selected_GroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selected_GroupActionPerformed
        Object selectedItem = selected_Group.getSelectedItem();
        if (selectedItem != null) {
            String selected = selectedItem.toString();
            System.out.println("Selected group: " + selected); // Debug output
            fetchData(selected);
        } else {
            System.out.println("No item selected."); // Debug output
            // Optionally, handle this case if needed
        }
    }//GEN-LAST:event_selected_GroupActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UserRole role = UserRole.AUDITOR;
                new LedgerList(role).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Ledger_Table;
    private javax.swing.JButton addLedgerBtn;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField new_ledger;
    private javax.swing.JButton next_btn;
    private javax.swing.JButton previousbtn;
    private javax.swing.JComboBox<String> selected_Group;
    // End of variables declaration//GEN-END:variables
}
