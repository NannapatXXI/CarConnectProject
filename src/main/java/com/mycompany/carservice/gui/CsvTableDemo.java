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


public class CsvTableDemo extends javax.swing.JFrame {
    private ArrayList<String[]> users;

    private CSVHandler csvHandler;


   public CsvTableDemo() {
        setupTable();
        initComponents();
        csvHandler = new CSVHandler("src/main/data/user.csv");
         loadCsvData();
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setVisible(true);

       
      //  addButtonColumn();

}
   private void setupTable() {
        UIManager.put("Table.selectionBackground", new Color(0, 0, 0));
        UIManager.put("Table.selectionForeground", Color.WHITE);
        UIManager.put("Table.alternateRowColor", Color.GRAY);
        UIManager.put("Button.arc", 20);
        UIManager.put("Component.arc", 999);
        UIManager.put("ProgressBar.arc", 999);
        UIManager.put("TextComponent.arc", 999);
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

    // --- เติมข้อมูลแต่ละแถว ---
        for (int i = 1; i < users.size(); i++) {
             String[] row = new String[users.get(i).length + 1];
             System.arraycopy(users.get(i), 0, row, 0, users.get(i).length);
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

        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPasswordField1 = new javax.swing.JPasswordField();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1200, 800));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 148, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jTextField1.setText("jTextField1");

        jButton1.setText("jButton1");

        jPasswordField1.setText("jPasswordField1");

        jButton2.setFont(new java.awt.Font("Helvetica Neue", 0, 24)); // NOI18N
        jButton2.setText("jButton2");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jPanel2.setBackground(new java.awt.Color(255, 102, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 163, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(168, 168, 168))
            .addGroup(layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(103, 103, 103)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 598, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(222, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74)
                        .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(89, 89, 89)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(57, 57, 57))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
