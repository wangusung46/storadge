package transaction.view.transaction;

import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
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
import transaction.model.transaction.BuyJdbc;
import transaction.model.transaction.BuyJdbcImplement;
import transaction.model.transaction.CartJdbc;
import transaction.model.transaction.CartJdbcImplement;
import transaction.model.transaction.Payment;
import transaction.model.transaction.PaymentJdbc;
import transaction.model.transaction.PaymentJdbcImplement;
import transaction.model.transaction.Sell;
import transaction.model.transaction.SellJdbc;
import transaction.model.transaction.SellJdbcImplement;
import transaction.model.transaction.response.ResponseListCartId;
import transaction.model.transaction.response.ResponseListTablePayment;
import transaction.model.transaction.response.ResponseListTableSell;

public class FormSell extends javax.swing.JInternalFrame {

    private static final long serialVersionUID = 1L;
    private static SellJdbc sellJdbc;
    private static BuyJdbc buyJdbc;
    private static CartJdbc cartJdbc;
    private static PaymentJdbc paymentJdbc;
    private static GlobalConfigJdbc globalConfigJdbc;
    private final DefaultTableModel defaultTableModelPayment;
    private final DefaultTableModel defaultTableModelSell;

    public FormSell() {
        initComponents();
        sellJdbc = new SellJdbcImplement();
        buyJdbc = new BuyJdbcImplement();
        cartJdbc = new CartJdbcImplement();
        paymentJdbc = new PaymentJdbcImplement();
        globalConfigJdbc = new GlobalConfigJdbcImplement();
        jButtonPrint.setEnabled(false);

        defaultTableModelPayment = new DefaultTableModel();
        jTablePayment.setModel(defaultTableModelPayment);
        defaultTableModelPayment.addColumn("ID");
        defaultTableModelPayment.addColumn("Total");
        defaultTableModelPayment.addColumn("Pembayaran");
        defaultTableModelPayment.addColumn("Kembailian");
        defaultTableModelPayment.addColumn("Tanggal");

        jTablePayment.getColumnModel().getColumn(0).setMinWidth(0);
        jTablePayment.getColumnModel().getColumn(0).setMaxWidth(0);

        defaultTableModelSell = new DefaultTableModel();
        jTableSell.setModel(defaultTableModelSell);
        defaultTableModelSell.addColumn("ID");
        defaultTableModelSell.addColumn("Kategori");
        defaultTableModelSell.addColumn("Barang");
        defaultTableModelSell.addColumn("Satuan");
        defaultTableModelSell.addColumn("Jumlah Jual");
        defaultTableModelSell.addColumn("Harga Jual");
        defaultTableModelSell.addColumn("Total");

        jTableSell.getColumnModel().getColumn(0).setMinWidth(0);
        jTableSell.getColumnModel().getColumn(0).setMaxWidth(0);

        loadTablePayment();
    }

    private void loadTablePayment() {
        defaultTableModelPayment.getDataVector().removeAllElements();
        defaultTableModelPayment.fireTableDataChanged();
        List<ResponseListTablePayment> payments = paymentJdbc.selectPayments("%" + jTextFieldSearchPayment.getText() + "%");
        Object[] objects = new Object[5];
        for (ResponseListTablePayment payment : payments) {
            objects[0] = payment.getId();
            objects[1] = payment.getTotal();
            objects[2] = payment.getCash();
            objects[3] = payment.getChange();
            objects[4] = payment.getDate();
            defaultTableModelPayment.addRow(objects);
        }
        jTextFieldPriceTotal.setText(cartJdbc.totalPriceCart().toString());
    }

    private void loadTableSell() {
        defaultTableModelSell.getDataVector().removeAllElements();
        defaultTableModelSell.fireTableDataChanged();
        List<ResponseListTableSell> sells = sellJdbc.selectSells("%" + jTextFieldSearchSell.getText() + "%",
                Long.valueOf(defaultTableModelPayment.getValueAt(jTablePayment.getSelectedRow(), 0).toString())
        );
        Object[] objects = new Object[7];
        for (ResponseListTableSell sell : sells) {
            objects[0] = sell.getId();
            objects[1] = sell.getBuy().getCategory().getName();
            objects[2] = sell.getBuy().getItem().getName();
            objects[3] = sell.getBuy().getUnit().getName();
            objects[4] = sell.getSellAmount();
            objects[5] = sell.getBuy().getSellPrice();
            objects[6] = new BigDecimal(sell.getSellAmount() * sell.getBuy().getSellPrice().intValue());
            defaultTableModelSell.addRow(objects);
        }
    }

    private void performSave() {
        if (cartJdbc.totalPriceCart() > 0) {
            if (!jTextFieldPriceTotal.getText().isEmpty()
                    && !jTextFieldPayment.getText().isEmpty()
                    && !jTextFieldChange.getText().isEmpty()) {

                Payment payment = new Payment();
                payment.setId(0L);
                payment.setCash(new BigDecimal(jTextFieldPayment.getText()));
                payment.setDate(LocalDateTime.now());
                paymentJdbc.insertPayment(payment);
                Long idPayment = sellJdbc.lastInsertId().getId();
                List<ResponseListCartId> carts = cartJdbc.selectCartsId();
                for (ResponseListCartId cart : carts) {
                    Sell sell = new Sell();
                    sell.setId(0L);
                    sell.getBuy().setId(buyJdbc.selectBuy(
                            cart.getIdCategory(),
                            cart.getIdItem(),
                            cart.getIdUnit(),
                            cart.getIdBuy()
                    ).getId());
                    sell.setSellAmount(cart.getCartAmount());
                    sell.getPayment().setId(idPayment);
                    sellJdbc.insertSell(sell);

                    defaultTableModelSell.fireTableDataChanged();
                    empty();
                }
                defaultTableModelSell.getDataVector().removeAllElements();
                cartJdbc.resetCart();
                loadTablePayment();
                JOptionPane.showMessageDialog(null, "Berhasil menyimpan data", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Data transaksi tidak boleh kosong", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Keranjang kosong", "Warning", JOptionPane.WARNING_MESSAGE);
        }

    }

    @SuppressWarnings("unchecked")
    private void invoice() {
        try {
            HashMap parameter = new HashMap();
            parameter.put("Header", globalConfigJdbc.selectGlobalConfigValue(3L));
            parameter.put("City", globalConfigJdbc.selectGlobalConfigValue(5L));
            parameter.put("Logo", "src\\transaction\\report\\Logo.png");
            parameter.put("IdPayment", Long.valueOf(defaultTableModelPayment.getValueAt(jTablePayment.getSelectedRow(), 0).toString()));
            InputStream file = getClass().getResourceAsStream("/transaction/report/Invoice.jrxml");
            JasperDesign JasperDesign = JRXmlLoader.load(file);
            JasperReport JasperReport = JasperCompileManager.compileReport(JasperDesign);
            JasperPrint JasperPrint = JasperFillManager.fillReport(JasperReport, parameter, globalConfigJdbc.getConnection());
            JasperViewer.viewReport(JasperPrint, false);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void empty() {
        jTextFieldSearchPayment.setText("");
        jTextFieldSearchSell.setText("");
        jTextFieldPayment.setText("");
        jTextFieldChange.setText("");
    }

    private void filterString(KeyEvent keyEvent) {
        if (Character.isDigit(keyEvent.getKeyChar())) {
            keyEvent.consume();
        }
    }

    private void filterNumber(KeyEvent keyEvent) {
        if (!Character.isDigit(keyEvent.getKeyChar())) {
            keyEvent.consume();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panelTombol4 = new javax.swing.JPanel();
        panelTotal = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldPriceTotal = new javax.swing.JTextField();
        panelBayar = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldPayment = new javax.swing.JTextField();
        panelTombol5 = new javax.swing.JPanel();
        jButtonCountPayment = new javax.swing.JButton();
        panelKembalian = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldChange = new javax.swing.JTextField();
        panelTombol = new javax.swing.JPanel();
        jButtonSave = new javax.swing.JButton();
        panelCari = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldSearchPayment = new javax.swing.JTextField();
        panelTabel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTablePayment = new javax.swing.JTable();
        panelCari1 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldSearchSell = new javax.swing.JTextField();
        panelTabel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableSell = new javax.swing.JTable();
        panelCetak = new javax.swing.JPanel();
        jButtonPrint = new javax.swing.JButton();

        setBackground(new java.awt.Color(0, 51, 51));
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jPanel2.setBackground(new java.awt.Color(0, 51, 51));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel7.setBackground(new java.awt.Color(102, 102, 255));
        jPanel7.setForeground(new java.awt.Color(0, 0, 255));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("FORM PENGELUARAN BARANG");
        jPanel7.add(jLabel1);

        jPanel2.add(jPanel7);

        panelTombol4.setMinimumSize(new java.awt.Dimension(200, 24));
        panelTombol4.setLayout(new java.awt.GridLayout(1, 0));

        panelTotal.setLayout(new java.awt.GridLayout(1, 0));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Total");
        panelTotal.add(jLabel7);

        jTextFieldPriceTotal.setEditable(false);
        panelTotal.add(jTextFieldPriceTotal);

        panelTombol4.add(panelTotal);

        panelBayar.setLayout(new java.awt.GridLayout(1, 0));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Pembayaran");
        panelBayar.add(jLabel4);

        jTextFieldPayment.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldPaymentKeyTyped(evt);
            }
        });
        panelBayar.add(jTextFieldPayment);

        panelTombol4.add(panelBayar);

        jPanel2.add(panelTombol4);

        panelTombol5.setMinimumSize(new java.awt.Dimension(200, 24));
        panelTombol5.setLayout(new java.awt.GridLayout(1, 0));

        jButtonCountPayment.setText("JUMLAH");
        jButtonCountPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCountPaymentActionPerformed(evt);
            }
        });
        panelTombol5.add(jButtonCountPayment);

        jPanel2.add(panelTombol5);

        panelKembalian.setLayout(new java.awt.GridLayout(1, 0));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Kembalian");
        panelKembalian.add(jLabel6);

        jTextFieldChange.setEditable(false);
        panelKembalian.add(jTextFieldChange);

        jPanel2.add(panelKembalian);

        panelTombol.setMinimumSize(new java.awt.Dimension(200, 24));
        panelTombol.setLayout(new java.awt.GridLayout(1, 0));

        jButtonSave.setText("SIMPAN");
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });
        panelTombol.add(jButtonSave);

        jPanel2.add(panelTombol);

        panelCari.setLayout(new java.awt.GridLayout(1, 0));

        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.X_AXIS));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("CARI");
        jPanel6.add(jLabel2);

        jTextFieldSearchPayment.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldSearchPaymentKeyReleased(evt);
            }
        });
        jPanel6.add(jTextFieldSearchPayment);

        panelCari.add(jPanel6);

        jPanel2.add(panelCari);

        panelTabel.setLayout(new java.awt.GridLayout(1, 0));

        jTablePayment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTablePayment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTablePaymentMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTablePayment);

        panelTabel.add(jScrollPane1);

        jPanel2.add(panelTabel);

        panelCari1.setLayout(new java.awt.GridLayout(1, 0));

        jPanel8.setLayout(new javax.swing.BoxLayout(jPanel8, javax.swing.BoxLayout.X_AXIS));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("CARI");
        jPanel8.add(jLabel3);

        jTextFieldSearchSell.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldSearchSellKeyReleased(evt);
            }
        });
        jPanel8.add(jTextFieldSearchSell);

        panelCari1.add(jPanel8);

        jPanel2.add(panelCari1);

        panelTabel1.setLayout(new java.awt.GridLayout(1, 0));

        jTableSell.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTableSell);

        panelTabel1.add(jScrollPane2);

        jPanel2.add(panelTabel1);

        panelCetak.setLayout(new java.awt.GridLayout(1, 0));

        jButtonPrint.setText("CETAK");
        jButtonPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPrintActionPerformed(evt);
            }
        });
        panelCetak.add(jButtonPrint);

        jPanel2.add(panelCetak);

        getContentPane().add(jPanel2);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTablePaymentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTablePaymentMouseClicked
        loadTableSell();
        jButtonPrint.setEnabled(true);
    }//GEN-LAST:event_jTablePaymentMouseClicked

    private void jButtonPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPrintActionPerformed
        invoice();
    }//GEN-LAST:event_jButtonPrintActionPerformed

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        performSave();
        jButtonPrint.setEnabled(false);
    }//GEN-LAST:event_jButtonSaveActionPerformed

    private void jButtonCountPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCountPaymentActionPerformed
        if (!jTextFieldPayment.getText().isEmpty()) {
            if (Integer.parseInt(jTextFieldPayment.getText()) >= Integer.parseInt(jTextFieldPriceTotal.getText())) {
                jTextFieldChange.setText(String.valueOf(
                        Integer.parseInt(jTextFieldPayment.getText())
                        - Integer.parseInt(jTextFieldPriceTotal.getText())
                ));
            } else {
                JOptionPane.showMessageDialog(null, "Pembayaran kurang dari total pembayaran barang", "Warning", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Pembayaran tidak boleh kosong", "Warning", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButtonCountPaymentActionPerformed

    private void jTextFieldSearchPaymentKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSearchPaymentKeyReleased
        loadTablePayment();
    }//GEN-LAST:event_jTextFieldSearchPaymentKeyReleased

    private void jTextFieldSearchSellKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSearchSellKeyReleased
        loadTableSell();
    }//GEN-LAST:event_jTextFieldSearchSellKeyReleased

    private void jTextFieldPaymentKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPaymentKeyTyped
        filterNumber(evt);
        jTextFieldChange.setText("");
    }//GEN-LAST:event_jTextFieldPaymentKeyTyped
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(() -> {
            new FormSell().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCountPayment;
    private javax.swing.JButton jButtonPrint;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTablePayment;
    private javax.swing.JTable jTableSell;
    private javax.swing.JTextField jTextFieldChange;
    private javax.swing.JTextField jTextFieldPayment;
    private javax.swing.JTextField jTextFieldPriceTotal;
    private javax.swing.JTextField jTextFieldSearchPayment;
    private javax.swing.JTextField jTextFieldSearchSell;
    private javax.swing.JPanel panelBayar;
    private javax.swing.JPanel panelCari;
    private javax.swing.JPanel panelCari1;
    private javax.swing.JPanel panelCetak;
    private javax.swing.JPanel panelKembalian;
    private javax.swing.JPanel panelTabel;
    private javax.swing.JPanel panelTabel1;
    private javax.swing.JPanel panelTombol;
    private javax.swing.JPanel panelTombol4;
    private javax.swing.JPanel panelTombol5;
    private javax.swing.JPanel panelTotal;
    // End of variables declaration//GEN-END:variables

}
