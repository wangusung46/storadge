package transaction.view.supplier;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import transaction.model.supplier.Supplier;
import transaction.model.supplier.SupplierJdbc;
import transaction.model.supplier.SupplierJdbcImplement;

public class FormSupplier extends javax.swing.JInternalFrame {

    private static final long serialVersionUID = 1L;
    private static SupplierJdbc supplierJdbc;
    private Boolean clickTable;
    private final DefaultTableModel defaultTableModel;

    public FormSupplier() {
        initComponents();
        supplierJdbc = new SupplierJdbcImplement();
        defaultTableModel = new DefaultTableModel();

        defaultTableModel.addColumn("ID");
        defaultTableModel.addColumn("Nama");
        defaultTableModel.addColumn("Nomor Telepon");
        defaultTableModel.addColumn("Email");
        defaultTableModel.addColumn("Alamat");
        jTableSupplier.setModel(defaultTableModel);
        
        jTableSupplier.getColumnModel().getColumn(0).setMinWidth(0);
        jTableSupplier.getColumnModel().getColumn(0).setMaxWidth(0);

        loadTable();
    }

    private void loadTable() {
        defaultTableModel.getDataVector().removeAllElements();
        defaultTableModel.fireTableDataChanged();
        List<Supplier> suppliers = supplierJdbc.selectSuppliers("%" + jTextFieldSearch.getText() + "%");
        Object[] objects = new Object[5];
        for (Supplier supplier : suppliers) {
            objects[0] = supplier.getId();
            objects[1] = supplier.getName();
            objects[2] = supplier.getHandPhone();
            objects[3] = supplier.getEmail();
            objects[4] = supplier.getAddress();
            defaultTableModel.addRow(objects);
        }
        clickTable = false;
    }

    private void performSave() {
        if (!jTextFieldName.getText().isEmpty()) {
            if (!jTextFieldName.getText().isEmpty()) {
                if (!jTextFieldName.getText().isEmpty()) {
                    if (!jTextFieldName.getText().isEmpty()) {
                        Supplier supplier = new Supplier();
                        supplier.setId(0L);
                        supplier.setName(jTextFieldName.getText());
                        supplier.setHandPhone(jTextFieldTelp.getText());
                        supplier.setEmail(jTextFieldEmail.getText());
                        supplier.setAddress(jTextFieldAddress.getText());
                        supplierJdbc.insertSupplier(supplier);
                        loadTable();
                        empty();
                        JOptionPane.showMessageDialog(null, "Berhasil menyimpan data", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Alamat tidak boleh kosong", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Email tidak boleh kosong", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Nomor handphone tidak boleh kosong", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nama tidak boleh kosong", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void performUpdate() {
        if (clickTable) {
            if (!jTextFieldName.getText().isEmpty()) {
                if (!jTextFieldName.getText().isEmpty()) {
                    if (!jTextFieldName.getText().isEmpty()) {
                        if (!jTextFieldName.getText().isEmpty()) {
                            Supplier supplier = new Supplier();
                            supplier.setId(Long.parseLong(defaultTableModel.getValueAt(jTableSupplier.getSelectedRow(), 0).toString()));
                            supplier.setName(jTextFieldName.getText());
                            supplier.setHandPhone(jTextFieldTelp.getText());
                            supplier.setEmail(jTextFieldEmail.getText());
                            supplier.setAddress(jTextFieldAddress.getText());
                            supplierJdbc.updateSupplier(supplier);
                            loadTable();
                            empty();
                            JOptionPane.showMessageDialog(null, "Berhasil merubah data", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Alamat tidak boleh kosong", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Email tidak boleh kosong", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Nomor handphone tidak boleh kosong", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Nama tidak boleh kosong", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Hapus atau edit harus klik tabel", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void performDelete() {
        if (clickTable) {
            if (JOptionPane.showConfirmDialog(null, "Apakah anda ingin menghapus data dengan id " + defaultTableModel.getValueAt(jTableSupplier.getSelectedRow(), 0).toString() + " ?", "Warning", JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                supplierJdbc.deleteSupplier(Long.valueOf(defaultTableModel.getValueAt(jTableSupplier.getSelectedRow(), 0).toString()));
                loadTable();
                empty();
                JOptionPane.showMessageDialog(null, "Berhasil manghapus data", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Hapus atau edit harus klik tabel", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void empty() {
        jTextFieldName.setText("");
        jTextFieldTelp.setText("");
        jTextFieldEmail.setText("");
        jTextFieldAddress.setText("");
        jTextFieldSearch.setText("");
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        panelNamaSupplier = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldName = new javax.swing.JTextField();
        panelTelepon = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldTelp = new javax.swing.JTextField();
        panelEmail = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldEmail = new javax.swing.JTextField();
        panelAlamat = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldAddress = new javax.swing.JTextField();
        panelTombol = new javax.swing.JPanel();
        jButtonSave = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jButtonUpdate = new javax.swing.JButton();
        panelCari = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldSearch = new javax.swing.JTextField();
        panelTabel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableSupplier = new javax.swing.JTable();

        setBackground(new java.awt.Color(0, 51, 51));
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jPanel2.setBackground(new java.awt.Color(0, 51, 51));

        jPanel5.setBackground(new java.awt.Color(102, 102, 255));
        jPanel5.setForeground(new java.awt.Color(0, 0, 255));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("FORM SUPPLIER");
        jPanel5.add(jLabel1);

        jPanel4.setLayout(new java.awt.GridLayout(0, 2));

        panelNamaSupplier.setLayout(new java.awt.GridLayout(1, 0));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Nama Supplier");
        panelNamaSupplier.add(jLabel9);
        panelNamaSupplier.add(jTextFieldName);

        jPanel4.add(panelNamaSupplier);

        panelTelepon.setLayout(new java.awt.GridLayout(1, 0));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Telepon");
        panelTelepon.add(jLabel4);
        panelTelepon.add(jTextFieldTelp);

        jPanel4.add(panelTelepon);

        panelEmail.setLayout(new java.awt.GridLayout(1, 0));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Email");
        panelEmail.add(jLabel7);
        panelEmail.add(jTextFieldEmail);

        jPanel4.add(panelEmail);

        panelAlamat.setLayout(new java.awt.GridLayout(1, 0));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Alamat");
        panelAlamat.add(jLabel3);
        panelAlamat.add(jTextFieldAddress);

        jPanel4.add(panelAlamat);

        panelTombol.setMinimumSize(new java.awt.Dimension(200, 24));
        panelTombol.setLayout(new java.awt.GridLayout(1, 0));

        jButtonSave.setText("SIMPAN");
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

        panelCari.setLayout(new java.awt.GridLayout(1, 0));

        jPanel11.setLayout(new javax.swing.BoxLayout(jPanel11, javax.swing.BoxLayout.X_AXIS));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("CARI");
        jPanel11.add(jLabel2);

        jTextFieldSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldSearchKeyReleased(evt);
            }
        });
        jPanel11.add(jTextFieldSearch);

        panelCari.add(jPanel11);

        panelTabel.setLayout(new java.awt.GridLayout(1, 0));

        jTableSupplier.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableSupplierMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableSupplier);

        panelTabel.add(jScrollPane1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelTombol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelCari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelTabel, javax.swing.GroupLayout.DEFAULT_SIZE, 953, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(panelTombol, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(panelCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(panelTabel, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                .addGap(1, 1, 1))
        );

        getContentPane().add(jPanel2);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTableSupplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableSupplierMouseClicked
        clickTable = true;
    }//GEN-LAST:event_jTableSupplierMouseClicked

    private void jTextFieldSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSearchKeyReleased
        loadTable();
    }//GEN-LAST:event_jTextFieldSearchKeyReleased

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        performSave();
    }//GEN-LAST:event_jButtonSaveActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        performDelete();
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateActionPerformed
        performUpdate();
    }//GEN-LAST:event_jButtonUpdateActionPerformed
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(() -> {
            new FormSupplier().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JButton jButtonUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableSupplier;
    private javax.swing.JTextField jTextFieldAddress;
    private javax.swing.JTextField jTextFieldEmail;
    private javax.swing.JTextField jTextFieldName;
    private javax.swing.JTextField jTextFieldSearch;
    private javax.swing.JTextField jTextFieldTelp;
    private javax.swing.JPanel panelAlamat;
    private javax.swing.JPanel panelCari;
    private javax.swing.JPanel panelEmail;
    private javax.swing.JPanel panelNamaSupplier;
    private javax.swing.JPanel panelTabel;
    private javax.swing.JPanel panelTelepon;
    private javax.swing.JPanel panelTombol;
    // End of variables declaration//GEN-END:variables
}
