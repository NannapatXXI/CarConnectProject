/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.carservice.gui;

import com.mycompany.carservice.entity.CSVHandler;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.AbstractCellEditor;


public class AdminPage extends javax.swing.JFrame {
    private ArrayList<String[]> users;

    private CSVHandler csvHandler;


   public AdminPage() {
       
        initComponents();
         SetupUi();
        csvHandler = new CSVHandler("src/main/data/user.csv");
          getContentPane().setBackground(java.awt.Color.BLACK);

         loadCsvData();
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setVisible(true);

       
      //  addButtonColumn();

}
   private void SetupUi() {
        UIManager.put("Table.selectionBackground", new Color(0, 0, 0));
        UIManager.put("Table.selectionForeground", Color.WHITE);
        UIManager.put("Table.alternateRowColor", Color.GRAY);
       
        homeBtn.setBorderPainted(false); 
        bookingBtn.setBorderPainted(false); 
        historyBtn.setBorderPainted(false); 
        profileBtn.setBorderPainted(false); 
    
    }
     private void loadCsvData() {
         
         
         
    // อ่าน CSV ทั้งหมดมาเก็บใน List
    users = new ArrayList<>(csvHandler.readCSV());
    if (users.isEmpty()) return;

    // --- สร้าง headers (เพิ่ม Action) ---
    String[] headers = new String[users.get(0).length + 1];
    System.arraycopy(users.get(0), 0, headers, 0, users.get(0).length);
    headers[headers.length - 1] = "Action";

    // โมเดลตาราง
    DefaultTableModel model = new DefaultTableModel(headers, 0);

    // --- เพิ่ม TableRowSorter สำหรับ filter ---
    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
    jTable1.setRowSorter(sorter);
    jTextField1.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
        private void filter() {
             String text = jTextField1.getText();
             if (text.trim().length() == 0) {
             sorter.setRowFilter(null); // แสดงทั้งหมด
            } else {
                // 🔹 กรองเฉพาะคอลัมน์ Name (index 1)
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1));
            }
        }

    @Override
    public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
    @Override
    public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
    @Override
    public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }
    });
        // --- เติมข้อมูลแต่ละแถว ---
        for (int i = 1; i < users.size(); i++) {
            String[] userRow = users.get(i);
            if (userRow == null || userRow.length == 0) continue; // ข้ามแถวว่าง

             String[] row = new String[userRow.length + 1];
    
            
            for (int j = 0; j < userRow.length; j++) {
                 row[j] = userRow[j];
            }

            row[row.length - 1] = "Update"; // ช่อง Action
             model.addRow(row);
         }



    jTable1.setModel(model);

    // --- เพิ่ม renderer + editor สำหรับปุ่ม ---
    TableColumn actionColumn = jTable1.getColumn("Action");
    actionColumn.setCellRenderer(new ButtonRenderer());
    actionColumn.setCellEditor(new ButtonEditor(jTable1, csvHandler));
    }

    // Renderer สำหรับปุ่ม  / Renderer = แสดงผล
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() { setOpaque(true); }//เพื่อให้ปุ่มวาดพื้นหลังของมันเอง
        @Override //JTable จะเรียกใช้เอง
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            setText("Update");
            return this ;//คืน JButton ที่เป็น renderer ให้ JTable เอาไปวาดแทน cell 
        }
    }

    // Editor สำหรับปุ่ม  /Editor = “รับ event คลิก แล้วทำงาน
    class ButtonEditor extends AbstractCellEditor implements TableCellEditor {//TableCellEditor คือ interface ที่ JTable ใช้เมื่อต้องการ component ที่ตอบสนองการแก้ไข
        private JButton button;
        private JTable table;
        private CSVHandler csvHandler;
        //JTable จะเรียกใช้เอง
        public ButtonEditor(JTable table, CSVHandler handler) {
            this.table = table;
            this.csvHandler = handler;
            button = new JButton("Update");
            button.addActionListener(e -> {
                int row = table.getEditingRow();//แถวที่กดปุ่ม
                if (row != -1) {
                    String[] data = users.get(row + 1); // row+1 เพราะ users[0] เป็น header
                    data[1] = table.getValueAt(row, 1).toString();
                    data[2] = table.getValueAt(row, 2).toString();
                    data[3] = table.getValueAt(row, 3).toString();
                    data[4] = "complete"; // เปลี่ยน status เป็น complete

                   
                    csvHandler.writeCSV(users);

                    
                    loadCsvData();

                    System.out.println("Updated row " + row);
                }
                fireEditingStopped();//แจ้ง JTable ว่า editor เสร็จสิ้นแล้ว
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            button.setText("Update");
            return button;
        }

        @Override
        public Object getCellEditorValue() { return "Update"; }
    }



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        homeBtn = new javax.swing.JButton();
        adminBtn = new javax.swing.JButton();
        profileBtn = new javax.swing.JButton();
        historyBtn = new javax.swing.JButton();
        bookingBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        username = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1200, 800));

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jTable1);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 660, 560));

        jButton1.setText("TestWindow");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 630, -1, -1));
        jPanel3.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 130, -1));

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Search : Name");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, -1, -1));

        jPanel2.setBackground(new java.awt.Color(28, 24, 24));
        jPanel2.setForeground(new java.awt.Color(255, 153, 0));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        homeBtn.setBackground(new java.awt.Color(28, 24, 24));
        homeBtn.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        homeBtn.setForeground(new java.awt.Color(255, 255, 255));
        homeBtn.setText("Home");
        homeBtn.setPreferredSize(new java.awt.Dimension(164, 90));
        homeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homeBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                homeBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                homeBtnMouseExited(evt);
            }
        });
        jPanel2.add(homeBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 164, 90));

        adminBtn.setBackground(new java.awt.Color(255, 157, 0));
        adminBtn.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        adminBtn.setText("Admin");
        adminBtn.setPreferredSize(new java.awt.Dimension(164, 90));
        jPanel2.add(adminBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 560, 164, 90));

        profileBtn.setBackground(new java.awt.Color(28, 24, 24));
        profileBtn.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        profileBtn.setForeground(new java.awt.Color(255, 255, 255));
        profileBtn.setText("Profile");
        profileBtn.setPreferredSize(new java.awt.Dimension(164, 90));
        profileBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                profileBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                profileBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                profileBtnMouseExited(evt);
            }
        });
        jPanel2.add(profileBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 430, 164, 90));

        historyBtn.setBackground(new java.awt.Color(28, 24, 24));
        historyBtn.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        historyBtn.setForeground(new java.awt.Color(255, 255, 255));
        historyBtn.setText("History");
        historyBtn.setPreferredSize(new java.awt.Dimension(164, 90));
        historyBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                historyBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                historyBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                historyBtnMouseExited(evt);
            }
        });
        jPanel2.add(historyBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 164, 90));

        bookingBtn.setBackground(new java.awt.Color(28, 24, 24));
        bookingBtn.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        bookingBtn.setForeground(new java.awt.Color(255, 255, 255));
        bookingBtn.setText("Booking");
        bookingBtn.setPreferredSize(new java.awt.Dimension(164, 90));
        bookingBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bookingBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bookingBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bookingBtnMouseExited(evt);
            }
        });
        jPanel2.add(bookingBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 164, 90));

        jLabel2.setText("jLabel2");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, -1));

        jPanel1.setBackground(new java.awt.Color(58, 58, 58));
        jPanel1.setPreferredSize(new java.awt.Dimension(1200, 104));

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("User :");

        username.setFont(new java.awt.Font("Helvetica Neue", 0, 24)); // NOI18N
        username.setForeground(new java.awt.Color(255, 255, 255));
        username.setText("....");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1206, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 825, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 104, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 692, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 734, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void profileBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profileBtnMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_profileBtnMouseClicked

    private void profileBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profileBtnMouseEntered
        profileBtn.setBackground(Color.GRAY);
    }//GEN-LAST:event_profileBtnMouseEntered

    private void profileBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profileBtnMouseExited
        profileBtn.setBackground(new Color(28,24,24));
    }//GEN-LAST:event_profileBtnMouseExited

    private void historyBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_historyBtnMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_historyBtnMouseClicked

    private void historyBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_historyBtnMouseEntered
         historyBtn.setBackground(Color.GRAY);
    }//GEN-LAST:event_historyBtnMouseEntered

    private void historyBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_historyBtnMouseExited
        historyBtn.setBackground(new Color(28,24,24));
    }//GEN-LAST:event_historyBtnMouseExited

    private void bookingBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bookingBtnMouseClicked
         dispose();
         new BookingPage("Test");
    }//GEN-LAST:event_bookingBtnMouseClicked

    private void bookingBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bookingBtnMouseEntered
        bookingBtn.setBackground(Color.GRAY);
    }//GEN-LAST:event_bookingBtnMouseEntered

    private void bookingBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bookingBtnMouseExited
        bookingBtn.setBackground(new Color(28,24,24));
    }//GEN-LAST:event_bookingBtnMouseExited

    private void homeBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeBtnMouseExited
        homeBtn.setBackground(new Color(28,24,24));
    }//GEN-LAST:event_homeBtnMouseExited

    private void homeBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeBtnMouseEntered
        homeBtn.setBackground(Color.GRAY);
    }//GEN-LAST:event_homeBtnMouseEntered

    private void homeBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeBtnMouseClicked
        dispose();
        new HomePage();
    }//GEN-LAST:event_homeBtnMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton adminBtn;
    private javax.swing.JButton bookingBtn;
    private javax.swing.JButton historyBtn;
    private javax.swing.JButton homeBtn;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton profileBtn;
    private javax.swing.JLabel username;
    // End of variables declaration//GEN-END:variables
}
