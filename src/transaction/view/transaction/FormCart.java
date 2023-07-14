package transaction.view.transaction;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import transaction.combobox.ComboItems;
import transaction.model.category.Category;
import transaction.model.item.Item;
import transaction.model.transaction.Buy;
import transaction.model.transaction.BuyJdbc;
import transaction.model.transaction.BuyJdbcImplement;
import transaction.model.transaction.Cart;
import transaction.model.transaction.CartJdbc;
import transaction.model.transaction.CartJdbcImplement;
import transaction.model.transaction.response.ResponseListTableCart;
import transaction.model.unit.Unit;

public class FormCart extends javax.swing.JInternalFrame {

    private static final long serialVersionUID = 1L;

    private static CartJdbc cartJdbc;
    private static BuyJdbc buyJdbc;
    private Boolean clickTable;
    private final DefaultTableModel defaultTableModel;

    public FormCart() {
        initComponents();
        cartJdbc = new CartJdbcImplement();
        buyJdbc = new BuyJdbcImplement();

        defaultTableModel = new DefaultTableModel();
        jTableCart.setModel(defaultTableModel);
        defaultTableModel.addColumn("ID");
        defaultTableModel.addColumn("Kategori");
        defaultTableModel.addColumn("Barang");
        defaultTableModel.addColumn("Satuan");
        defaultTableModel.addColumn("Jumlah Jual");
        defaultTableModel.addColumn("Harga Jual");

        jTableCart.getColumnModel().getColumn(0).setMinWidth(0);
        jTableCart.getColumnModel().getColumn(0).setMaxWidth(0);

        loadTable();
        loadBuyCategory();
        loadBuyItem();
        loadBuyUnit();
        loadDate();
        loadPayment();
    }

    private void loadTable() {
        defaultTableModel.getDataVector().removeAllElements();
        defaultTableModel.fireTableDataChanged();
        List<ResponseListTableCart> carts = cartJdbc.selectCarts("%" + jTextFieldSearch.getText() + "%");
        Object[] objects = new Object[6];
        for (ResponseListTableCart cart : carts) {
            objects[0] = cart.getId();
            objects[1] = cart.getNameCategory();
            objects[2] = cart.getNameItem();
            objects[3] = cart.getNameUnit();
            objects[4] = cart.getAmountSell();
            objects[5] = cart.getPriceSell();
            defaultTableModel.addRow(objects);
        }
        jTextFieldPriceTotal.setText(cartJdbc.totalPriceCart().toString());
        clickTable = false;
    }

    @SuppressWarnings("unchecked")
    private void loadBuyCategory() {
        List<Category> categorys = buyJdbc.selectBuyCategory();
        DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel();
        for (Category category : categorys) {
            defaultComboBoxModel.addElement(new ComboItems(category.getId(), category.getName()));
        }
        jComboBoxCategory.setModel(defaultComboBoxModel);
    }

    @SuppressWarnings("unchecked")
    private void loadBuyItem() {
        List<Item> items = buyJdbc.selectBuyItem(((ComboItems) jComboBoxCategory.getSelectedItem()).getKey());
        DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel();
        for (Item item : items) {
            defaultComboBoxModel.addElement(new ComboItems(item.getId(), item.getName()));
        }
        jComboBoxItem.setModel(defaultComboBoxModel);
    }

    @SuppressWarnings("unchecked")
    private void loadBuyUnit() {
        List<Unit> units = buyJdbc.selectBuyUnit(
                ((ComboItems) jComboBoxCategory.getSelectedItem()).getKey(),
                ((ComboItems) jComboBoxItem.getSelectedItem()).getKey()
        );
        DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel();
        for (Unit unit : units) {
            defaultComboBoxModel.addElement(new ComboItems(unit.getId(), unit.getName()));
        }
        jComboBoxUnit.setModel(defaultComboBoxModel);
    }

    @SuppressWarnings("unchecked")
    private void loadDate() {
        List<Buy> buys = buyJdbc.selectDate(
                ((ComboItems) jComboBoxCategory.getSelectedItem()).getKey(),
                ((ComboItems) jComboBoxItem.getSelectedItem()).getKey(),
                ((ComboItems) jComboBoxUnit.getSelectedItem()).getKey()
        );
        DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel();
        for (Buy buy : buys) {
            defaultComboBoxModel.addElement(new ComboItems(buy.getId(), buy.getDate().toString()));
        }
        jComboBoxDate.setModel(defaultComboBoxModel);
    }

    private void loadPayment() {
        Buy buy = buyJdbc.selectBuy(
                ((ComboItems) jComboBoxCategory.getSelectedItem()).getKey(),
                ((ComboItems) jComboBoxItem.getSelectedItem()).getKey(),
                ((ComboItems) jComboBoxUnit.getSelectedItem()).getKey(),
                ((ComboItems) jComboBoxDate.getSelectedItem()).getKey()
        );
        jTextFieldPriceBuy.setText(buy.getBuyPrice().toString());
        jTextFieldPriceSell.setText(buy.getSellPrice().toString());
    }

    private void performSave() {
        if (!jTextFieldAmountSell.getText().isEmpty()) {
            Cart cart = new Cart();
            cart.setId(0L);
            cart.getBuy().setId(buyJdbc.selectBuy(
                    ((ComboItems) jComboBoxCategory.getSelectedItem()).getKey(),
                    ((ComboItems) jComboBoxItem.getSelectedItem()).getKey(),
                    ((ComboItems) jComboBoxUnit.getSelectedItem()).getKey(),
                    ((ComboItems) jComboBoxDate.getSelectedItem()).getKey()
            ).getId());
            cart.setCartAmount(Integer.valueOf(jTextFieldAmountSell.getText()));
            cartJdbc.insertCart(cart);

            loadTable();
            empty();
            JOptionPane.showMessageDialog(null, "Berhasil menyimpan data", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Data transaksi tidak boleh kosong", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void performDelete() {
        if (clickTable) {
            if (JOptionPane.showConfirmDialog(null, "Apakah anda ingin menghapus data dengan id " + defaultTableModel.getValueAt(jTableCart.getSelectedRow(), 0).toString() + " ?", "Warning", JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                cartJdbc.deleteCart(Long.parseLong(defaultTableModel.getValueAt(jTableCart.getSelectedRow(), 0).toString()));
                loadTable();
                empty();
                JOptionPane.showMessageDialog(null, "Berhasil manghapus data", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Hapus atau edit harus klik tabel", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void performReset() {
        if (JOptionPane.showConfirmDialog(null, "Apakah anda ingin mereset keranjang ?", "Warning", JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
            cartJdbc.resetCart();
            loadTable();
            empty();
            JOptionPane.showMessageDialog(null, "Berhasil mereset data", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void empty() {
        jTextFieldAmountSell.setText("");
        jTextFieldSearch.setText("");
    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        panelNamaKategori = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jComboBoxCategory = new javax.swing.JComboBox<>();
        panelNamaBarang = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jComboBoxItem = new javax.swing.JComboBox<>();
        panelSatuan = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jComboBoxUnit = new javax.swing.JComboBox<>();
        panelTanggalJual1 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jComboBoxDate = new javax.swing.JComboBox<>();
        panelHargaBeli = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldPriceBuy = new javax.swing.JTextField();
        panelTanggalJual = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jTextFieldPriceSell = new javax.swing.JTextField();
        panelJumlahJual = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jTextFieldAmountSell = new javax.swing.JTextField();
        panelTombol3 = new javax.swing.JPanel();
        jButtonAdd = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jButtonReset = new javax.swing.JButton();
        panelTombol4 = new javax.swing.JPanel();
        panelTotal = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldPriceTotal = new javax.swing.JTextField();
        panelCari = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldSearch = new javax.swing.JTextField();
        panelTabel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableCart = new javax.swing.JTable();

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

        jPanel3.setLayout(new java.awt.GridLayout(0, 2));

        panelNamaKategori.setLayout(new java.awt.GridLayout(1, 0));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Kategori");
        panelNamaKategori.add(jLabel8);

        jComboBoxCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCategoryActionPerformed(evt);
            }
        });
        panelNamaKategori.add(jComboBoxCategory);

        jPanel3.add(panelNamaKategori);

        panelNamaBarang.setLayout(new java.awt.GridLayout(1, 0));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Nama Barang");
        panelNamaBarang.add(jLabel3);

        jComboBoxItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxItemActionPerformed(evt);
            }
        });
        panelNamaBarang.add(jComboBoxItem);

        jPanel3.add(panelNamaBarang);

        panelSatuan.setLayout(new java.awt.GridLayout(1, 0));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Satuan");
        panelSatuan.add(jLabel5);

        jComboBoxUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxUnitActionPerformed(evt);
            }
        });
        panelSatuan.add(jComboBoxUnit);

        jPanel3.add(panelSatuan);

        panelTanggalJual1.setLayout(new java.awt.GridLayout(1, 0));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Tanggal Pembelian");
        panelTanggalJual1.add(jLabel14);

        jComboBoxDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxDateActionPerformed(evt);
            }
        });
        panelTanggalJual1.add(jComboBoxDate);

        jPanel3.add(panelTanggalJual1);

        panelHargaBeli.setLayout(new java.awt.GridLayout(1, 0));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Harga Pemasukan");
        panelHargaBeli.add(jLabel10);

        jTextFieldPriceBuy.setEditable(false);
        panelHargaBeli.add(jTextFieldPriceBuy);

        jPanel3.add(panelHargaBeli);

        panelTanggalJual.setLayout(new java.awt.GridLayout(1, 0));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Harga Pengeluaran");
        panelTanggalJual.add(jLabel13);

        jTextFieldPriceSell.setEditable(false);
        panelTanggalJual.add(jTextFieldPriceSell);

        jPanel3.add(panelTanggalJual);

        panelJumlahJual.setLayout(new java.awt.GridLayout(1, 0));

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setText("Jumlah Penjualan");
        panelJumlahJual.add(jLabel18);
        panelJumlahJual.add(jTextFieldAmountSell);

        jPanel3.add(panelJumlahJual);

        jPanel2.add(jPanel3);

        panelTombol3.setMinimumSize(new java.awt.Dimension(200, 24));
        panelTombol3.setLayout(new java.awt.GridLayout(1, 0));

        jButtonAdd.setText("Tambah Ke Keranjang");
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });
        panelTombol3.add(jButtonAdd);

        jButtonDelete.setText("Hapus Keranjang");
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });
        panelTombol3.add(jButtonDelete);

        jButtonReset.setText("Reset Keranjang");
        jButtonReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetActionPerformed(evt);
            }
        });
        panelTombol3.add(jButtonReset);

        jPanel2.add(panelTombol3);

        panelTombol4.setMinimumSize(new java.awt.Dimension(200, 24));
        panelTombol4.setLayout(new java.awt.GridLayout(1, 0));

        panelTotal.setLayout(new java.awt.GridLayout(1, 0));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Total");
        panelTotal.add(jLabel7);

        jTextFieldPriceTotal.setEditable(false);
        panelTotal.add(jTextFieldPriceTotal);

        panelTombol4.add(panelTotal);

        jPanel2.add(panelTombol4);

        panelCari.setLayout(new java.awt.GridLayout(1, 0));

        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.X_AXIS));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("CARI");
        jPanel6.add(jLabel2);

        jTextFieldSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldSearchKeyReleased(evt);
            }
        });
        jPanel6.add(jTextFieldSearch);

        panelCari.add(jPanel6);

        jPanel2.add(panelCari);

        panelTabel.setLayout(new java.awt.GridLayout(1, 0));

        jTableCart.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableCart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableCartMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableCart);

        panelTabel.add(jScrollPane1);

        jPanel2.add(panelTabel);

        getContentPane().add(jPanel2);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        performDelete();
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jButtonResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetActionPerformed
        performReset();
    }//GEN-LAST:event_jButtonResetActionPerformed

    private void jTableCartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableCartMouseClicked
        clickTable = true;
    }//GEN-LAST:event_jTableCartMouseClicked

    private void jComboBoxCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCategoryActionPerformed
        loadBuyItem();
        loadBuyUnit();
        loadDate();
        loadPayment();
    }//GEN-LAST:event_jComboBoxCategoryActionPerformed

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        if (!jTextFieldAmountSell.getText().isEmpty()) {
            Buy buy = buyJdbc.selectStock(
                    ((ComboItems) jComboBoxCategory.getSelectedItem()).getKey(),
                    ((ComboItems) jComboBoxItem.getSelectedItem()).getKey(),
                    ((ComboItems) jComboBoxUnit.getSelectedItem()).getKey(),
                    ((ComboItems) jComboBoxDate.getSelectedItem()).getKey()
            );
            if (buy.getCountItem() >= Integer.valueOf(jTextFieldAmountSell.getText())) {
                performSave();
            } else {
                JOptionPane.showMessageDialog(null, "Jumlah stok hanya " + buy.getCountItem(), "Warning", JOptionPane.INFORMATION_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Jumlah penjualan tidak boleh kosong", "Warning", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButtonAddActionPerformed

    private void jComboBoxItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxItemActionPerformed
        loadBuyUnit();
        loadDate();
        loadPayment();
    }//GEN-LAST:event_jComboBoxItemActionPerformed

    private void jComboBoxUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxUnitActionPerformed
        loadDate();
        loadPayment();
    }//GEN-LAST:event_jComboBoxUnitActionPerformed

    private void jComboBoxDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxDateActionPerformed
        loadPayment();
    }//GEN-LAST:event_jComboBoxDateActionPerformed

    private void jTextFieldSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSearchKeyReleased
        loadTable();
    }//GEN-LAST:event_jTextFieldSearchKeyReleased
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(() -> {
            new FormCart().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonReset;
    private javax.swing.JComboBox<String> jComboBoxCategory;
    private javax.swing.JComboBox<String> jComboBoxDate;
    private javax.swing.JComboBox<String> jComboBoxItem;
    private javax.swing.JComboBox<String> jComboBoxUnit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableCart;
    private javax.swing.JTextField jTextFieldAmountSell;
    private javax.swing.JTextField jTextFieldPriceBuy;
    private javax.swing.JTextField jTextFieldPriceSell;
    private javax.swing.JTextField jTextFieldPriceTotal;
    private javax.swing.JTextField jTextFieldSearch;
    private javax.swing.JPanel panelCari;
    private javax.swing.JPanel panelHargaBeli;
    private javax.swing.JPanel panelJumlahJual;
    private javax.swing.JPanel panelNamaBarang;
    private javax.swing.JPanel panelNamaKategori;
    private javax.swing.JPanel panelSatuan;
    private javax.swing.JPanel panelTabel;
    private javax.swing.JPanel panelTanggalJual;
    private javax.swing.JPanel panelTanggalJual1;
    private javax.swing.JPanel panelTombol3;
    private javax.swing.JPanel panelTombol4;
    private javax.swing.JPanel panelTotal;
    // End of variables declaration//GEN-END:variables

}
