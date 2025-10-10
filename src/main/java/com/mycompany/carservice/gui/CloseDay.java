/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.carservice.gui;

import com.mycompany.carservice.entity.CSVHandler;
import java.text.SimpleDateFormat;
import com.mycompany.carservice.entity.RoundedPanel;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author nannapat
 */
public class CloseDay extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CloseDay.class.getName());
    CSVHandler csvHandler = new CSVHandler("src/main/data/close_day.csv");
    private String userName;
     private String role;


 
    public CloseDay(String userName,String role) {
        initComponents();
        setLocationRelativeTo(null);
        setVisible(true);
        loadCsv();
        this.userName = userName;
        this.role = role;
    }

    private void loadCsv(){
         
    
         ArrayList<String[]> day = new ArrayList<>(csvHandler.readCSV());
           for (int i = 1; i < day.size(); i++) {
            String[] row = day.get(i);
            if (row.length > 0) {
                // สมมติว่าคอลัมน์แรกคือวันที่
                String date = row[0];
                jComboBox1.addItem(date);
            }
    }
         
    }
     private void deleteRow(int row) {
       
            csvHandler.deleteRow(row); 
         
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new RoundedPanel(30); // 30 radius;
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        saveDayOpen = new javax.swing.JButton();
        saveDayClose = new javax.swing.JButton();
        dateSpinner1 = new javax.swing.JSpinner();
        jComboBox1 = new javax.swing.JComboBox<>();
        backBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(43, 43, 43));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Close ");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(500, 400));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setMinimumSize(new java.awt.Dimension(458, 237));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("ปิดวัน :");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, -1, -1));

        jLabel3.setText("เปิดวัน :");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, -1, -1));

        saveDayOpen.setText("Save");
        saveDayOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveDayOpenActionPerformed(evt);
            }
        });
        jPanel3.add(saveDayOpen, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 70, -1, -1));

        saveDayClose.setText("Save");
        saveDayClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveDayCloseActionPerformed(evt);
            }
        });
        jPanel3.add(saveDayClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 140, -1, -1));

        dateSpinner1.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.DAY_OF_MONTH)
        );
        dateSpinner1.setEditor(new javax.swing.JSpinner.DateEditor(dateSpinner1, "dd-MM-yyyy")
        );
        jPanel3.add(dateSpinner1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, 160, -1));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-" }));
        jPanel3.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 160, -1));

        jPanel4.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 460, 280));

        backBtn.setText("Close");
        backBtn.setActionCommand("close");
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });
        jPanel4.add(backBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 320, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 460, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, 0)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed
        dispose();
        new BookingPage(userName, role);
    }//GEN-LAST:event_backBtnActionPerformed

    private void saveDayOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveDayOpenActionPerformed
            int selectedIndex = jComboBox1.getSelectedIndex() + 2  ;
             System.out.println(": " + selectedIndex);
             deleteRow(selectedIndex);
             loadCsv();
            
    }//GEN-LAST:event_saveDayOpenActionPerformed

    private void saveDayCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveDayCloseActionPerformed
            // อ่านค่าวันจาก spinner
        Date selectedDate = (Date) dateSpinner1.getValue();
        String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(selectedDate);

          

            System.out.println("วันที่ที่เลือกคือ: " + formattedDate);
            
             // 1. อ่านข้อมูลเก่าจาก CSV
           

             csvHandler.appendCSV(new String[]{formattedDate});
             loadCsv();
    }//GEN-LAST:event_saveDayCloseActionPerformed

 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBtn;
    private javax.swing.JSpinner dateSpinner1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JButton saveDayClose;
    private javax.swing.JButton saveDayOpen;
    // End of variables declaration//GEN-END:variables
}
