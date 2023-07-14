package transaction.view.report;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import transaction.model.config.GlobalConfigJdbc;
import transaction.model.config.GlobalConfigJdbcImplement;

public class FormReport extends javax.swing.JInternalFrame {

    private static final long serialVersionUID = 1L;
    private static GlobalConfigJdbc globalConfigJdbc;

    public FormReport() {
        initComponents();
        globalConfigJdbc = new GlobalConfigJdbcImplement();

        jDateChooserFrom.setDateFormatString("dd MMMM yyyy");
        jDateChooserTo.setDateFormatString("dd MMMM yyyy");
        jDateChooserFrom.getDateEditor().setEnabled(false);
        jDateChooserTo.getDateEditor().setEnabled(false);
    }

    @SuppressWarnings("unchecked")
    private void summaryItemBuy() {
        try {
            HashMap parameter = new HashMap();
            parameter.put("Header", globalConfigJdbc.selectGlobalConfigValue(3L));
            parameter.put("City", globalConfigJdbc.selectGlobalConfigValue(5L));
            parameter.put("Logo", "src\\transaction\\report\\Logo.png");
            parameter.put("FromDate", new SimpleDateFormat("yyyy-MM-dd  00:00:00").format(jDateChooserFrom.getDate()));
            parameter.put("ToDate", new SimpleDateFormat("yyyy-MM-dd  23:59:59").format(jDateChooserTo.getDate()));
            InputStream file = getClass().getResourceAsStream("/transaction/report/summaryItemBuy.jrxml");
            JasperDesign JasperDesign = JRXmlLoader.load(file);
            JasperReport JasperReport = JasperCompileManager.compileReport(JasperDesign);
            JasperPrint JasperPrint = JasperFillManager.fillReport(JasperReport, parameter, globalConfigJdbc.getConnection());
            JasperViewer.viewReport(JasperPrint, false);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    private void summaryItemSell() {
        try {
            HashMap parameter = new HashMap();
            parameter.put("Header", globalConfigJdbc.selectGlobalConfigValue(3L));
            parameter.put("City", globalConfigJdbc.selectGlobalConfigValue(5L));
            parameter.put("Logo", "src\\transaction\\report\\Logo.png");
            parameter.put("FromDate", new SimpleDateFormat("yyyy-MM-dd  00:00:00").format(jDateChooserFrom.getDate()));
            parameter.put("ToDate", new SimpleDateFormat("yyyy-MM-dd  23:59:59").format(jDateChooserTo.getDate()));
            InputStream file = getClass().getResourceAsStream("/transaction/report/summaryItemSell.jrxml");
            JasperDesign JasperDesign = JRXmlLoader.load(file);
            JasperReport JasperReport = JasperCompileManager.compileReport(JasperDesign);
            JasperPrint JasperPrint = JasperFillManager.fillReport(JasperReport, parameter, globalConfigJdbc.getConnection());
            JasperViewer.viewReport(JasperPrint, false);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    private void summaryItemStock() {
        try {
            HashMap parameter = new HashMap();
            parameter.put("Header", globalConfigJdbc.selectGlobalConfigValue(3L));
            parameter.put("City", globalConfigJdbc.selectGlobalConfigValue(5L));
            parameter.put("Logo", "src\\transaction\\report\\Logo.png");
            parameter.put("FromDate", new SimpleDateFormat("yyyy-MM-dd  00:00:00").format(jDateChooserFrom.getDate()));
            parameter.put("ToDate", new SimpleDateFormat("yyyy-MM-dd  23:59:59").format(jDateChooserTo.getDate()));
            InputStream file = getClass().getResourceAsStream("/transaction/report/summaryItemStock.jrxml");
            JasperDesign JasperDesign = JRXmlLoader.load(file);
            JasperReport JasperReport = JasperCompileManager.compileReport(JasperDesign);
            JasperPrint JasperPrint = JasperFillManager.fillReport(JasperReport, parameter, globalConfigJdbc.getConnection());
            JasperViewer.viewReport(JasperPrint, false);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    private void summaryItemProfit() {
        try {
            HashMap parameter = new HashMap();
            parameter.put("Header", globalConfigJdbc.selectGlobalConfigValue(3L));
            parameter.put("City", globalConfigJdbc.selectGlobalConfigValue(5L));
            parameter.put("Logo", "src\\transaction\\report\\Logo.png");
            parameter.put("FromDate", new SimpleDateFormat("yyyy-MM-dd  00:00:00").format(jDateChooserFrom.getDate()));
            parameter.put("ToDate", new SimpleDateFormat("yyyy-MM-dd  23:59:59").format(jDateChooserTo.getDate()));
            InputStream file = getClass().getResourceAsStream("/transaction/report/ItemDetail.jrxml");
            JasperDesign JasperDesign = JRXmlLoader.load(file);
            JasperReport JasperReport = JasperCompileManager.compileReport(JasperDesign);
            JasperPrint JasperPrint = JasperFillManager.fillReport(JasperReport, parameter, globalConfigJdbc.getConnection());
            JasperViewer.viewReport(JasperPrint, false);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    private void supplier() {
        try {
            HashMap parameter = new HashMap();
            parameter.put("Header", globalConfigJdbc.selectGlobalConfigValue(3L));
            parameter.put("City", globalConfigJdbc.selectGlobalConfigValue(5L));
            parameter.put("Logo", "src\\transaction\\report\\Logo.png");
            InputStream file = getClass().getResourceAsStream("/transaction/report/Supplier.jrxml");
            JasperDesign JasperDesign = JRXmlLoader.load(file);
            JasperReport JasperReport = JasperCompileManager.compileReport(JasperDesign);
            JasperPrint JasperPrint = JasperFillManager.fillReport(JasperReport, parameter, globalConfigJdbc.getConnection());
            JasperViewer.viewReport(JasperPrint, false);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Boolean dateChooser() {
        return jDateChooserTo.getDate() != null && jDateChooserTo.getDate() != null;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jDateChooserFrom = new com.toedter.calendar.JDateChooser();
        jDateChooserTo = new com.toedter.calendar.JDateChooser();
        jPanel11 = new javax.swing.JPanel();
        jButtonBuy = new javax.swing.JButton();
        jButtonSell = new javax.swing.JButton();
        jButtonStock = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jButtonSupplier = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1080, 400));
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jPanel6.setBackground(new java.awt.Color(102, 102, 255));
        jPanel6.setForeground(new java.awt.Color(0, 0, 255));

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("FORM LAPORAN");
        jPanel6.add(jLabel2);

        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.LINE_AXIS));

        jPanel3.setLayout(new java.awt.GridLayout(1, 0));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Dari :");
        jLabel3.setPreferredSize(new java.awt.Dimension(10, 16));
        jPanel3.add(jLabel3);

        jPanel5.add(jPanel3);

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Sampai :");
        jLabel1.setPreferredSize(new java.awt.Dimension(10, 16));
        jPanel1.add(jLabel1);

        jPanel5.add(jPanel1);

        jPanel4.add(jPanel5);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jDateChooserFrom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jDateChooserTo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateChooserFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooserTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanel11.setLayout(new java.awt.GridLayout(1, 0));

        jButtonBuy.setText("LAPORAN PEMBELIAN BARANG");
        jButtonBuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuyActionPerformed(evt);
            }
        });
        jPanel11.add(jButtonBuy);

        jButtonSell.setText("LAPORAN PENJUALAN BARANG");
        jButtonSell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSellActionPerformed(evt);
            }
        });
        jPanel11.add(jButtonSell);

        jButtonStock.setText("LAPORAN STOCK BARANG");
        jButtonStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStockActionPerformed(evt);
            }
        });
        jPanel11.add(jButtonStock);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel10Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, 647, Short.MAX_VALUE))
                    .addContainerGap()))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 103, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel10Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, 0)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        jPanel12.setLayout(new java.awt.GridLayout(1, 0));

        jButtonSupplier.setText("LAPORAN SUPPLIER");
        jButtonSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSupplierActionPerformed(evt);
            }
        });
        jPanel12.add(jButtonSupplier);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(jPanel2);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonBuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuyActionPerformed
        if (dateChooser()) {
            summaryItemBuy();
        } else {
            JOptionPane.showMessageDialog(null, "Tanggal tidak boleh kosong", "Warning", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonBuyActionPerformed

    private void jButtonSellActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSellActionPerformed
        if (dateChooser()) {
            summaryItemSell();
        } else {
            JOptionPane.showMessageDialog(null, "Tanggal tidak boleh kosong", "Warning", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonSellActionPerformed

    private void jButtonStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStockActionPerformed
        if (dateChooser()) {
            summaryItemStock();
        } else {
            JOptionPane.showMessageDialog(null, "Tanggal tidak boleh kosong", "Warning", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonStockActionPerformed

    private void jButtonSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSupplierActionPerformed
        supplier();
    }//GEN-LAST:event_jButtonSupplierActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new FormReport().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBuy;
    private javax.swing.JButton jButtonSell;
    private javax.swing.JButton jButtonStock;
    private javax.swing.JButton jButtonSupplier;
    private com.toedter.calendar.JDateChooser jDateChooserFrom;
    private com.toedter.calendar.JDateChooser jDateChooserTo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel9;
    // End of variables declaration//GEN-END:variables

}
