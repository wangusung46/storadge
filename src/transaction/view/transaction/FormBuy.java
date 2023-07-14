package transaction.view.transaction;

import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import transaction.combobox.ComboItems;
import transaction.model.category.Category;
import transaction.model.category.CategoryJdbc;
import transaction.model.category.CategoryJdbcImplement;
import transaction.model.item.Item;
import transaction.model.item.ItemJdbc;
import transaction.model.item.ItemJdbcImplement;
import transaction.model.supplier.Supplier;
import transaction.model.supplier.SupplierJdbc;
import transaction.model.supplier.SupplierJdbcImplement;
import transaction.model.transaction.Buy;
import transaction.model.transaction.BuyJdbc;
import transaction.model.transaction.BuyJdbcImplement;
import transaction.model.transaction.response.ResponseListTableBuy;
import transaction.model.unit.Unit;
import transaction.model.unit.UnitJdbc;
import transaction.model.unit.UnitJdbcImplement;

public class FormBuy extends javax.swing.JInternalFrame {

    private static final long serialVersionUID = 1L;

    private static BuyJdbc buyJdbc;
    private static SupplierJdbc supplierJdbc;
    private static CategoryJdbc categoryJdbc;
    private static ItemJdbc itemJdbc;
    private static UnitJdbc unitJdbc;
    private Boolean clickTable;
    private final DefaultTableModel defaultTableModel;

    public FormBuy() {
        initComponents();
        buyJdbc = new BuyJdbcImplement();
        supplierJdbc = new SupplierJdbcImplement();
        categoryJdbc = new CategoryJdbcImplement();
        itemJdbc = new ItemJdbcImplement();
        unitJdbc = new UnitJdbcImplement();

        jButtonCount.setEnabled(false);
        jButtonSave.setEnabled(false);
        jButtonUpdate.setEnabled(false);
        jButtonDelete.setEnabled(false);
        jTextFieldTotal.setEnabled(false);
        jTextFieldSellPrice.setEnabled(false);
        defaultTableModel = new DefaultTableModel();

        defaultTableModel.addColumn("ID");
        defaultTableModel.addColumn("Supplier");
        defaultTableModel.addColumn("Kategori");
        defaultTableModel.addColumn("Barang");
        defaultTableModel.addColumn("Satuan");
        defaultTableModel.addColumn("Jumlah Barang");
        defaultTableModel.addColumn("Harga Beli");
        defaultTableModel.addColumn("Total");
        defaultTableModel.addColumn("Harga Jual");
        defaultTableModel.addColumn("Tanggal");
        jTableBuy.setModel(defaultTableModel);

        jTableBuy.getColumnModel().getColumn(0).setMinWidth(0);
        jTableBuy.getColumnModel().getColumn(0).setMaxWidth(0);

        loadTable();
        loadSupplier();
        loadCategory();
        loadItem();
        loadUnit();
    }

    @SuppressWarnings("unchecked")
    private void loadSupplier() {
        List<Supplier> suppliers = supplierJdbc.selectSuppliers("%%");
        DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel();
        suppliers.forEach(supplier -> {
            defaultComboBoxModel.addElement(new ComboItems(supplier.getId(), supplier.getName()));
        });
        jComboBoxSupplier.setModel(defaultComboBoxModel);
    }

    @SuppressWarnings("unchecked")
    private void loadCategory() {
        List<Category> categorys = categoryJdbc.selectCategorys("%%");
        DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel();
        categorys.forEach(category -> {
            defaultComboBoxModel.addElement(new ComboItems(category.getId(), category.getName()));
        });
        jComboBoxCategory.setModel(defaultComboBoxModel);
    }

    @SuppressWarnings("unchecked")
    private void loadItem() {
        List<Item> items = itemJdbc.selectItems("%%");
        DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel();
        items.forEach(item -> {
            defaultComboBoxModel.addElement(new ComboItems(item.getId(), item.getName()));
        });
        jComboBoxItem.setModel(defaultComboBoxModel);
    }

    @SuppressWarnings("unchecked")
    private void loadUnit() {
        List<Unit> units = unitJdbc.selectUnits("%%");
        DefaultComboBoxModel defaultComboBoxModel = new DefaultComboBoxModel();
        units.forEach(unit -> {
            defaultComboBoxModel.addElement(new ComboItems(unit.getId(), unit.getName()));
        });
        jComboBoxUnit.setModel(defaultComboBoxModel);
    }

    private void loadTable() {
        defaultTableModel.getDataVector().removeAllElements();
        defaultTableModel.fireTableDataChanged();
        List<ResponseListTableBuy> buys = buyJdbc.selectBuys("%" + jTextFieldSearch.getText() + "%");
        Object[] objects = new Object[10];
        for (ResponseListTableBuy buy : buys) {
            objects[0] = buy.getId();
            objects[1] = buy.getSupplier().getName();
            objects[2] = buy.getCategory().getName();
            objects[3] = buy.getItem().getName();
            objects[4] = buy.getUnit().getName();
            objects[5] = buy.getCountItem();
            objects[6] = buy.getBuyPrice();
            objects[7] = new BigDecimal(buy.getCountItem() * buy.getBuyPrice().intValue());
            objects[8] = buy.getSellPrice();
            objects[9] = buy.getDate();
            defaultTableModel.addRow(objects);
        }
    }

    private void performSave() {
        if (!jTextFieldCountUnit.getText().isEmpty()) {
            if (!jTextFieldAmountBuy.getText().isEmpty()) {
                if (!jTextFieldSellPrice.getText().isEmpty()) {
                    if (Integer.parseInt(jTextFieldSellPrice.getText()) > Integer.parseInt(jTextFieldAmountBuy.getText())) {
                        Buy buy = new Buy();
                        buy.setId(0L);
                        buy.getSupplier().setId(((ComboItems) jComboBoxSupplier.getSelectedItem()).getKey());
                        buy.getCategory().setId(((ComboItems) jComboBoxCategory.getSelectedItem()).getKey());
                        buy.getItem().setId(((ComboItems) jComboBoxItem.getSelectedItem()).getKey());
                        buy.getUnit().setId(((ComboItems) jComboBoxUnit.getSelectedItem()).getKey());
                        buy.setCountItem(Integer.valueOf(jTextFieldCountUnit.getText()));
                        buy.setBuyPrice(new BigDecimal(jTextFieldAmountBuy.getText()));
                        buy.setSellPrice(new BigDecimal(jTextFieldSellPrice.getText()));
                        buy.setDate(LocalDateTime.now());
                        buyJdbc.insertBuy(buy);
                        loadTable();
                        empty();
                        jButtonCount.setEnabled(false);
                        jButtonSave.setEnabled(false);
                        jButtonUpdate.setEnabled(false);
                        jButtonDelete.setEnabled(false);
                        jTextFieldSellPrice.setEnabled(false);
                        JOptionPane.showMessageDialog(null, "Berhasil menyimpan data", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Harga penjualan harus lebih dari pembelian", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Harga penjualan tidak boleh kosong", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Harga pembelian tidak boleh kosong", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Jumlah satuan tidak boleh kosong", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void performUpdate() {
        if (clickTable) {
            if (!jTextFieldCountUnit.getText().isEmpty()) {
                if (!jTextFieldAmountBuy.getText().isEmpty()) {
                    if (Integer.parseInt(jTextFieldSellPrice.getText()) > Integer.parseInt(jTextFieldAmountBuy.getText())) {
                        Buy buy = new Buy();
                        buy.setId(Long.valueOf(defaultTableModel.getValueAt(jTableBuy.getSelectedRow(), 0).toString()));
                        buy.getSupplier().setId(((ComboItems) jComboBoxSupplier.getSelectedItem()).getKey());
                        buy.getCategory().setId(((ComboItems) jComboBoxCategory.getSelectedItem()).getKey());
                        buy.getItem().setId(((ComboItems) jComboBoxItem.getSelectedItem()).getKey());
                        buy.getUnit().setId(((ComboItems) jComboBoxUnit.getSelectedItem()).getKey());
                        buy.setCountItem(Integer.valueOf(jTextFieldCountUnit.getText()));
                        buy.setBuyPrice(new BigDecimal(jTextFieldAmountBuy.getText()));
                        buy.setSellPrice(new BigDecimal(jTextFieldSellPrice.getText()));
                        buy.setDate(LocalDateTime.now());
                        buyJdbc.updateBuy(buy);

                        loadTable();
                        empty();
                        jButtonCount.setEnabled(false);
                        jButtonSave.setEnabled(false);
                        jButtonUpdate.setEnabled(false);
                        jButtonDelete.setEnabled(false);
                        jTextFieldSellPrice.setEnabled(false);
                        JOptionPane.showMessageDialog(null, "Berhasil merubah data", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Harga penjualan harus lebih dari pembelian", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Harga pembelian tidak boleh kosong", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Jumlah satuan tidak boleh kosong", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Hapus atau edit harus klik tabel", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void performDelete() {
        if (clickTable) {
            if (JOptionPane.showConfirmDialog(null, "Apakah anda ingin menghapus data dengan id " + defaultTableModel.getValueAt(jTableBuy.getSelectedRow(), 0).toString() + " ?", "Warning", JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                buyJdbc.deleteBuy(Long.valueOf(defaultTableModel.getValueAt(jTableBuy.getSelectedRow(), 0).toString()));
                loadTable();
                empty();
                JOptionPane.showMessageDialog(null, "Berhasil manghapus data", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Hapus atau edit harus klik tabel", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void empty() {
        jTextFieldCountUnit.setText("");
        jTextFieldAmountBuy.setText("");
        jTextFieldTotal.setText("");
        jTextFieldSearch.setText("");
        jTextFieldSellPrice.setText("");
    }

    private void filterNumber(KeyEvent keyEvent) {
        if (!Character.isDigit(keyEvent.getKeyChar())) {
            keyEvent.consume();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        panelSupplier = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jComboBoxSupplier = new javax.swing.JComboBox<>();
        panelKategori = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jComboBoxCategory = new javax.swing.JComboBox<>();
        panelNamaBarang = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jComboBoxItem = new javax.swing.JComboBox<>();
        panelSatuan = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jTextFieldCountUnit = new javax.swing.JTextField();
        jComboBoxUnit = new javax.swing.JComboBox<>();
        panelJumlah = new javax.swing.JPanel();
        jButtonCount = new javax.swing.JButton();
        panelHargaBeli = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jhbeli = new javax.swing.JLabel();
        jTextFieldAmountBuy = new javax.swing.JTextField();
        panelTotal = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jtotal = new javax.swing.JLabel();
        jTextFieldTotal = new javax.swing.JTextField();
        panelTotal2 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jtotal1 = new javax.swing.JLabel();
        jTextFieldSellPrice = new javax.swing.JTextField();
        panelTombol = new javax.swing.JPanel();
        jButtonSave = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jButtonUpdate = new javax.swing.JButton();
        panelCari = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldSearch = new javax.swing.JTextField();
        panelTabel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableBuy = new javax.swing.JTable();

        setBackground(new java.awt.Color(0, 0, 0));
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setForeground(new java.awt.Color(0, 0, 0));
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jPanel7.setBackground(new java.awt.Color(102, 102, 255));
        jPanel7.setForeground(new java.awt.Color(0, 0, 255));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("FORM PEMASUKAN BARANG");
        jPanel7.add(jLabel1);

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.setLayout(new java.awt.GridLayout(0, 2));

        panelSupplier.setLayout(new java.awt.GridLayout(1, 0));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Supplier");
        jLabel8.setPreferredSize(new java.awt.Dimension(50, 24));
        panelSupplier.add(jLabel8);

        jComboBoxSupplier.setPreferredSize(new java.awt.Dimension(50, 24));
        panelSupplier.add(jComboBoxSupplier);

        jPanel2.add(panelSupplier);

        panelKategori.setLayout(new java.awt.GridLayout(1, 0));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Kategori");
        jLabel9.setPreferredSize(new java.awt.Dimension(50, 24));
        panelKategori.add(jLabel9);

        jComboBoxCategory.setPreferredSize(new java.awt.Dimension(50, 24));
        panelKategori.add(jComboBoxCategory);

        jPanel2.add(panelKategori);

        panelNamaBarang.setLayout(new java.awt.GridLayout(1, 0));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Barang");
        jLabel3.setPreferredSize(new java.awt.Dimension(50, 24));
        panelNamaBarang.add(jLabel3);

        jComboBoxItem.setPreferredSize(new java.awt.Dimension(50, 24));
        panelNamaBarang.add(jComboBoxItem);

        jPanel2.add(panelNamaBarang);

        panelSatuan.setLayout(new java.awt.GridLayout(1, 0));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Satuan");
        jLabel5.setPreferredSize(new java.awt.Dimension(50, 24));
        panelSatuan.add(jLabel5);

        jPanel6.setLayout(new java.awt.GridLayout(1, 0));

        jTextFieldCountUnit.setPreferredSize(new java.awt.Dimension(50, 24));
        jTextFieldCountUnit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldCountUnitKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldCountUnitKeyTyped(evt);
            }
        });
        jPanel6.add(jTextFieldCountUnit);

        jComboBoxUnit.setPreferredSize(new java.awt.Dimension(50, 24));
        jPanel6.add(jComboBoxUnit);

        panelSatuan.add(jPanel6);

        jPanel2.add(panelSatuan);

        panelJumlah.setLayout(new java.awt.GridLayout(1, 0));

        jButtonCount.setText("TOTAL");
        jButtonCount.setPreferredSize(new java.awt.Dimension(50, 24));
        jButtonCount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonCountMouseClicked(evt);
            }
        });
        jButtonCount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCountActionPerformed(evt);
            }
        });
        panelJumlah.add(jButtonCount);

        jPanel2.add(panelJumlah);

        panelHargaBeli.setLayout(new java.awt.GridLayout(1, 0));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Harga Pembelian");
        jLabel11.setPreferredSize(new java.awt.Dimension(50, 24));
        panelHargaBeli.add(jLabel11);

        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));

        jhbeli.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jhbeli.setForeground(new java.awt.Color(255, 255, 255));
        jhbeli.setText("Rp");
        jhbeli.setPreferredSize(new java.awt.Dimension(50, 24));
        jPanel4.add(jhbeli);

        jTextFieldAmountBuy.setPreferredSize(new java.awt.Dimension(50, 24));
        jTextFieldAmountBuy.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldAmountBuyKeyReleased(evt);
            }
        });
        jPanel4.add(jTextFieldAmountBuy);

        panelHargaBeli.add(jPanel4);

        jPanel2.add(panelHargaBeli);

        panelTotal.setLayout(new java.awt.GridLayout(1, 0));

        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));

        jtotal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtotal.setForeground(new java.awt.Color(255, 255, 255));
        jtotal.setText("Rp");
        jtotal.setPreferredSize(new java.awt.Dimension(50, 24));
        jPanel3.add(jtotal);

        jTextFieldTotal.setPreferredSize(new java.awt.Dimension(50, 24));
        jPanel3.add(jTextFieldTotal);

        panelTotal.add(jPanel3);

        jPanel2.add(panelTotal);

        panelTotal2.setLayout(new java.awt.GridLayout(1, 0));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Harga Penjualan");
        jLabel12.setPreferredSize(new java.awt.Dimension(50, 24));
        panelTotal2.add(jLabel12);

        jPanel8.setLayout(new javax.swing.BoxLayout(jPanel8, javax.swing.BoxLayout.LINE_AXIS));

        jtotal1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jtotal1.setForeground(new java.awt.Color(255, 255, 255));
        jtotal1.setText("Rp");
        jtotal1.setPreferredSize(new java.awt.Dimension(50, 24));
        jPanel8.add(jtotal1);

        jTextFieldSellPrice.setPreferredSize(new java.awt.Dimension(50, 24));
        jTextFieldSellPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldSellPriceKeyTyped(evt);
            }
        });
        jPanel8.add(jTextFieldSellPrice);

        panelTotal2.add(jPanel8);

        jPanel2.add(panelTotal2);

        panelTombol.setBackground(new java.awt.Color(0, 0, 0));
        panelTombol.setMinimumSize(new java.awt.Dimension(200, 24));
        panelTombol.setLayout(new java.awt.GridLayout(1, 0));

        jButtonSave.setText("SIMPAN");
        jButtonSave.setPreferredSize(new java.awt.Dimension(50, 24));
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });
        panelTombol.add(jButtonSave);

        jButtonDelete.setText("HAPUS");
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });
        panelTombol.add(jButtonDelete);

        jButtonUpdate.setText("EDIT");
        jButtonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateActionPerformed(evt);
            }
        });
        panelTombol.add(jButtonUpdate);

        panelCari.setBackground(new java.awt.Color(0, 0, 0));
        panelCari.setLayout(new java.awt.GridLayout(1, 0));

        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.X_AXIS));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("CARI");
        jLabel2.setPreferredSize(new java.awt.Dimension(50, 24));
        jPanel5.add(jLabel2);

        jTextFieldSearch.setPreferredSize(new java.awt.Dimension(50, 24));
        jTextFieldSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldSearchKeyReleased(evt);
            }
        });
        jPanel5.add(jTextFieldSearch);

        panelCari.add(jPanel5);

        panelTabel.setBackground(new java.awt.Color(0, 0, 0));
        panelTabel.setLayout(new java.awt.GridLayout(1, 0));

        jTableBuy.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableBuy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableBuyMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableBuy);

        panelTabel.add(jScrollPane1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelTombol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelCari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelTabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(panelTombol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(panelCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(panelTabel, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                .addGap(1, 1, 1))
        );

        getContentPane().add(jPanel1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        performSave();
    }//GEN-LAST:event_jButtonSaveActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        performDelete();
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateActionPerformed
        performUpdate();
    }//GEN-LAST:event_jButtonUpdateActionPerformed

    private void jTableBuyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableBuyMouseClicked
        clickTable = true;
        jButtonDelete.setEnabled(true);
    }//GEN-LAST:event_jTableBuyMouseClicked

    private void jButtonCountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonCountMouseClicked
        if (!jTextFieldCountUnit.getText().isEmpty()) {
            if (!jTextFieldAmountBuy.getText().isEmpty()) {
                jButtonCount.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Harga pembelian tidak boleh kosong", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Jumlah satuan tidak boleh kosong", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButtonCountMouseClicked

    private void jTextFieldSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSearchKeyReleased
        loadTable();
    }//GEN-LAST:event_jTextFieldSearchKeyReleased

    private void jTextFieldAmountBuyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAmountBuyKeyReleased
        if (!jTextFieldCountUnit.getText().isEmpty() && !jTextFieldAmountBuy.getText().isEmpty()) {
            jButtonCount.setEnabled(true);
        } else {
            jButtonCount.setEnabled(false);
            jButtonSave.setEnabled(false);
            jTextFieldSellPrice.setEnabled(false);
        }
    }//GEN-LAST:event_jTextFieldAmountBuyKeyReleased

    private void jTextFieldCountUnitKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCountUnitKeyReleased
        if (!jTextFieldCountUnit.getText().isEmpty() && !jTextFieldAmountBuy.getText().isEmpty()) {
            jButtonCount.setEnabled(true);
        } else {
            jButtonCount.setEnabled(false);
            jButtonSave.setEnabled(false);
            jTextFieldSellPrice.setEnabled(false);
        }
    }//GEN-LAST:event_jTextFieldCountUnitKeyReleased

    private void jTextFieldCountUnitKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldCountUnitKeyTyped
        filterNumber(evt);
    }//GEN-LAST:event_jTextFieldCountUnitKeyTyped

    private void jTextFieldSellPriceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSellPriceKeyTyped
        filterNumber(evt);
    }//GEN-LAST:event_jTextFieldSellPriceKeyTyped

    private void jButtonCountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCountActionPerformed
        jTextFieldTotal.setText(Integer.toString(Integer.parseInt(jTextFieldAmountBuy.getText()) * Integer.parseInt(jTextFieldCountUnit.getText())));
        jButtonSave.setEnabled(true);
        jButtonUpdate.setEnabled(true);
        jTextFieldSellPrice.setEnabled(true);
    }//GEN-LAST:event_jButtonCountActionPerformed
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(() -> {
            new FormBuy().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCount;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JButton jButtonUpdate;
    private javax.swing.JComboBox<String> jComboBoxCategory;
    private javax.swing.JComboBox<String> jComboBoxItem;
    private javax.swing.JComboBox<String> jComboBoxSupplier;
    private javax.swing.JComboBox<String> jComboBoxUnit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableBuy;
    private javax.swing.JTextField jTextFieldAmountBuy;
    private javax.swing.JTextField jTextFieldCountUnit;
    private javax.swing.JTextField jTextFieldSearch;
    private javax.swing.JTextField jTextFieldSellPrice;
    private javax.swing.JTextField jTextFieldTotal;
    private javax.swing.JLabel jhbeli;
    private javax.swing.JLabel jtotal;
    private javax.swing.JLabel jtotal1;
    private javax.swing.JPanel panelCari;
    private javax.swing.JPanel panelHargaBeli;
    private javax.swing.JPanel panelJumlah;
    private javax.swing.JPanel panelKategori;
    private javax.swing.JPanel panelNamaBarang;
    private javax.swing.JPanel panelSatuan;
    private javax.swing.JPanel panelSupplier;
    private javax.swing.JPanel panelTabel;
    private javax.swing.JPanel panelTombol;
    private javax.swing.JPanel panelTotal;
    private javax.swing.JPanel panelTotal2;
    // End of variables declaration//GEN-END:variables

}
