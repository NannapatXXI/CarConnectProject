/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.carservice.gui;

 import com.formdev.flatlaf.FlatLightLaf; // FlatLaf
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class CsvTableDemo extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CsvTableDemo.class.getName());

   public CsvTableDemo() {
    initComponents();
    setSize(1200, 800);        
    setLocationRelativeTo(null); // จัดกลางหน้าจอ

    setVisible(true);
     // โหลด CSV
    loadCsvData("/data.csv");
     setupTable();             // ปรับแต่ง JTable
}
   private void loadCsvData(String resourcePath) {
    try (InputStream is = getClass().getResourceAsStream(resourcePath);
         BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

        String line;
        boolean isFirstLine = true;
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);    // เคลียร์ row เก่า
        model.setColumnCount(0); // เคลียร์ column เก่า

        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            if (isFirstLine) {
                for (String col : values) {
                    model.addColumn(col);
                }
                isFirstLine = false;
            } else {
                model.addRow(values);
            }
        }

    } catch (IOException | NullPointerException e) {
        e.printStackTrace();
    }
}
private void setupTable() {
    // ปรับความสูง, สี, Grid
    jTable1.setFillsViewportHeight(true);
    jTable1.setRowHeight(30);
    jTable1.setShowGrid(false);
    jTable1.setIntercellSpacing(new Dimension(0, 0));
    jTable1.setSelectionBackground(new Color(51, 153, 255));
    jTable1.setSelectionForeground(Color.WHITE);

    // Header โมเดิร์น
    JTableHeader header = jTable1.getTableHeader();
    header.setFont(new Font("Segoe UI", Font.BOLD, 14));
    header.setBackground(new Color(240, 240, 240));
    header.setForeground(Color.BLACK);
    header.setReorderingAllowed(false);

    // Striped rows
    jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public java.awt.Component getTableCellRendererComponent(JTable table, Object value,
                                                                boolean isSelected, boolean hasFocus,
                                                                int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (!isSelected) {
                setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
            }
            return this;
        }
    });
}


   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1200, 800));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "No", "User", "gmail", "Tell"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(331, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 623, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(246, 246, 246))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 585, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(137, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
