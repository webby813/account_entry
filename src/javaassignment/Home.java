package javaassignment;

import static javaassignment.UserRole.ACCOUNTANT;
import static javaassignment.UserRole.AUDITOR;
import javax.swing.JFrame;

public class Home extends javax.swing.JFrame {
    private UserRole userRole;

    public Home(UserRole role) {
        this.userRole = role;
        initComponents();
        permissionDistribute(role);
    }
    
    private void permissionDistribute(UserRole role){
        switch(role){
            case AUDITOR:
                System.out.println(role);
                auditorCantVisit();
                break;
            case ACCOUNTANT:
                System.out.println(role);
                accountantCantVisit();
                break;
            default:
                System.out.println("Empty");
        }
    }
    
    private void accountantCantVisit(){
        IvtReportPageBtn.setVisible(false);
        ComPageBtn.setVisible(false);
    }
    
    private void auditorCantVisit(){
        PmtPageBtn.setVisible(false);
        ReceivePageBtn.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PmtPageBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        ReceivePageBtn = new javax.swing.JButton();
        LedgerLPageBtn = new javax.swing.JButton();
        LedgerGPageBtn = new javax.swing.JButton();
        ReportPageBtn = new javax.swing.JButton();
        InvoicePageBtn = new javax.swing.JButton();
        PurchasePageBtn = new javax.swing.JButton();
        ItemsPage = new javax.swing.JButton();
        ItemGrpPage = new javax.swing.JButton();
        IvtReportPageBtn = new javax.swing.JButton();
        ComPageBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton13 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        PmtPageBtn.setText("Payment");
        PmtPageBtn.setMinimumSize(new java.awt.Dimension(76, 23));
        PmtPageBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PmtPageBtnActionPerformed(evt);
            }
        });

        jLabel1.setText("Company Name");

        ReceivePageBtn.setText("Receive");
        ReceivePageBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReceivePageBtnActionPerformed(evt);
            }
        });

        LedgerLPageBtn.setText("Ledger List");
        LedgerLPageBtn.setMinimumSize(new java.awt.Dimension(88, 23));
        LedgerLPageBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LedgerLPageBtnActionPerformed(evt);
            }
        });

        LedgerGPageBtn.setText("Ledger Group");
        LedgerGPageBtn.setMinimumSize(new java.awt.Dimension(103, 23));
        LedgerGPageBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LedgerGPageBtnActionPerformed(evt);
            }
        });

        ReportPageBtn.setText("Report");
        ReportPageBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReportPageBtnActionPerformed(evt);
            }
        });

        InvoicePageBtn.setText("Invoice");
        InvoicePageBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InvoicePageBtnActionPerformed(evt);
            }
        });

        PurchasePageBtn.setText("Purchase");
        PurchasePageBtn.setMinimumSize(new java.awt.Dimension(76, 23));
        PurchasePageBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PurchasePageBtnActionPerformed(evt);
            }
        });

        ItemsPage.setText("Items");
        ItemsPage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemsPageActionPerformed(evt);
            }
        });

        ItemGrpPage.setText("Item Group");
        ItemGrpPage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemGrpPageActionPerformed(evt);
            }
        });

        IvtReportPageBtn.setText("Inventory Report");
        IvtReportPageBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IvtReportPageBtnActionPerformed(evt);
            }
        });

        ComPageBtn.setText("Company Info");
        ComPageBtn.setMinimumSize(new java.awt.Dimension(104, 23));
        ComPageBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComPageBtnActionPerformed(evt);
            }
        });

        jLabel2.setText("Mobile : ");

        jLabel3.setText("Telephone : ");

        jLabel4.setText("Email : ");

        jLabel5.setText("Date ");

        jButton13.setText("X");
        jButton13.setMinimumSize(new java.awt.Dimension(104, 23));
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(79, 79, 79)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(84, 84, 84)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 245, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(ComPageBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(IvtReportPageBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ItemGrpPage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ItemsPage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(PurchasePageBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(InvoicePageBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ReportPageBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(LedgerGPageBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(LedgerLPageBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ReceivePageBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(PmtPageBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(81, 81, 81))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5))
                .addGap(33, 33, 33)
                .addComponent(PmtPageBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ReceivePageBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LedgerLPageBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LedgerGPageBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ReportPageBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(InvoicePageBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PurchasePageBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ItemsPage, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ItemGrpPage, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(IvtReportPageBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ComPageBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(14, 14, 14))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PmtPageBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PmtPageBtnActionPerformed
        JFrame route = new javaassignment.pages.Payment();
        route.setVisible(true);
    }//GEN-LAST:event_PmtPageBtnActionPerformed

    private void ReceivePageBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReceivePageBtnActionPerformed
        JFrame route = new javaassignment.pages.Receive();
        route.setVisible(true);
    }//GEN-LAST:event_ReceivePageBtnActionPerformed

    private void LedgerLPageBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LedgerLPageBtnActionPerformed
        JFrame route = new javaassignment.pages.LedgerList(userRole);
        route.setVisible(true);
    }//GEN-LAST:event_LedgerLPageBtnActionPerformed

    private void LedgerGPageBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LedgerGPageBtnActionPerformed
        JFrame route = new javaassignment.pages.LedgerGroup(userRole);
        route.setVisible(true);
    }//GEN-LAST:event_LedgerGPageBtnActionPerformed

    private void ReportPageBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReportPageBtnActionPerformed
        JFrame route = new javaassignment.pages.Report();
        route.setVisible(true);
    }//GEN-LAST:event_ReportPageBtnActionPerformed

    private void InvoicePageBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InvoicePageBtnActionPerformed
        JFrame route = new javaassignment.pages.Invoice(userRole);
        route.setVisible(true);
    }//GEN-LAST:event_InvoicePageBtnActionPerformed

    private void PurchasePageBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PurchasePageBtnActionPerformed
        JFrame route = new javaassignment.pages.Purchase(userRole);
        route.setVisible(true);
    }//GEN-LAST:event_PurchasePageBtnActionPerformed

    private void ItemsPageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemsPageActionPerformed
        JFrame route = new javaassignment.pages.Items(userRole);
        route.setVisible(true);
    }//GEN-LAST:event_ItemsPageActionPerformed

    private void ItemGrpPageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemGrpPageActionPerformed
        JFrame route = new javaassignment.pages.ItemGroup(userRole);
        route.setVisible(true);
    }//GEN-LAST:event_ItemGrpPageActionPerformed

    private void IvtReportPageBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IvtReportPageBtnActionPerformed
        JFrame route = new javaassignment.pages.InventoryReport();
        route.setVisible(true);
    }//GEN-LAST:event_IvtReportPageBtnActionPerformed

    private void ComPageBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComPageBtnActionPerformed
        JFrame route = new javaassignment.pages.CompanyInfo();
        route.setVisible(true);
    }//GEN-LAST:event_ComPageBtnActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        System.exit(1);
    }//GEN-LAST:event_jButton13ActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Create a UserRole instance, for example, UserRole.AUDITOR
                UserRole role = UserRole.AUDITOR;
                new Home(role).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ComPageBtn;
    private javax.swing.JButton InvoicePageBtn;
    private javax.swing.JButton ItemGrpPage;
    private javax.swing.JButton ItemsPage;
    private javax.swing.JButton IvtReportPageBtn;
    private javax.swing.JButton LedgerGPageBtn;
    private javax.swing.JButton LedgerLPageBtn;
    private javax.swing.JButton PmtPageBtn;
    private javax.swing.JButton PurchasePageBtn;
    private javax.swing.JButton ReceivePageBtn;
    private javax.swing.JButton ReportPageBtn;
    private javax.swing.JButton jButton13;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    // End of variables declaration//GEN-END:variables
}
