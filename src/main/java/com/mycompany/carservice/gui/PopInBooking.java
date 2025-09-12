/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.carservice.gui;

import com.mycompany.carservice.entity.CSVHandler;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author nannapat
 */
public class PopInBooking extends javax.swing.JDialog {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PopInBooking.class.getName());
    private String name;
     private String userId;
    private ArrayList<String[]> bookingDetail = new ArrayList<>();
     ArrayList<String[]> users ;
     
    CSVHandler csvHandler ;
    /**
     * Creates new form popInBooking   
     */
    public PopInBooking(java.awt.Frame parent, boolean modal,String name) {// java.awt.Frame parent เอาไว้ให้ class นี้รู้ว่าจะต้อง pop หน้าจอตัวเอาที่ใคร
        super(parent, modal);
        this.name = name;
         csvHandler = new CSVHandler("src/main/data/user.csv");
       
        initComponents();
        setLocationRelativeTo(null);
        this.users = csvHandler.readCSV();

         cheackuser();
        
    }
    public void setDayLabel(String text) {
        dayLabel.setText(text);
    }

    public void setTimeLabel(String text) {
        timeLabel1.setText(text);
    }

    public void setServiceLabel(String text) {
     serviceLabel.setText(text);
    }
    public void cheackuser(){
       
       for(String[] data : users){
        if(data[1].trim().equalsIgnoreCase(name.trim())){
            System.out.println("User found: " + String.join(", ", data));
            userLabel.setText(data[1]);
            break;
        }
    }
      
      
    }
    private void saveBooking(String[] newBooking) {
    // 1. อ่านข้อมูลเก่าจาก CSV
    csvHandler = new CSVHandler("src/main/data/history_user.csv");
       
    ArrayList<String[]> allBookings = csvHandler.readCSV();

    // 2. เพิ่มข้อมูลใหม่
    allBookings.add(newBooking);

    // 3. เขียนทั้งหมดกลับไปไฟล์
    csvHandler.writeCSV(allBookings);
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        textFieldCar = new javax.swing.JTextField();
        dayLabel = new javax.swing.JLabel();
        timeLabel1 = new javax.swing.JLabel();
        serviceLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textDetail = new javax.swing.JTextArea();
        backBtn = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        userLabel = new javax.swing.JLabel();
        okBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Booking");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Day :");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Time :");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Service :");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("ทะเบียนรถ :");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Detail :");

        dayLabel.setForeground(new java.awt.Color(255, 255, 255));
        dayLabel.setText("....");

        timeLabel1.setForeground(new java.awt.Color(255, 255, 255));
        timeLabel1.setText("....");

        serviceLabel.setForeground(new java.awt.Color(255, 255, 255));
        serviceLabel.setText("....");

        textDetail.setColumns(20);
        textDetail.setRows(5);
        jScrollPane1.setViewportView(textDetail);

        backBtn.setText("Back");
        backBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backBtnMouseClicked(evt);
            }
        });

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("User :");

        userLabel.setForeground(new java.awt.Color(255, 255, 255));
        userLabel.setText("....");

        okBtn.setText("Ok");
        okBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                okBtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(backBtn)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(okBtn))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel4)
                                .addComponent(jLabel2)
                                .addComponent(jLabel7))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(userLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(timeLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(dayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(textFieldCar, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(serviceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(31, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(216, 216, 216))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(timeLabel1)
                    .addComponent(jLabel7)
                    .addComponent(userLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(textFieldCar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(serviceLabel)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(dayLabel)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backBtn)
                    .addComponent(okBtn))
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backBtnMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_backBtnMouseClicked

    private void okBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_okBtnMouseClicked
      for(String[] data : users){
        if(data[1].trim().equalsIgnoreCase(name.trim())){
            System.out.println("User found: " + String.join(", ", data));
            userLabel.setText(data[1]);

            userId = data[0];
            System.out.println("UserID: " + userId);

            // ดึงข้อมูลจาก GUI
            String service = serviceLabel.getText().trim();
            String date = dayLabel.getText().trim();
            String time = timeLabel1.getText().trim();
            String note = textDetail.getText().trim();
            String carReg = textFieldCar.getText().trim();
            String status = "process"; // หรือค่าอื่นตามต้องการ

            // สร้าง array ตามลำดับที่ต้องการ
            String[] userData = {userId, data[1], service, date, time, note, carReg, status};

            // เขียนข้อมูลต่อจาก CSV เดิม
            saveBooking(userData); // ต้องสร้างฟังก์ชัน appendCSV ใน CSVHandler
            System.out.println("Booking saved: " + String.join(", ", userData));

            break;
        }
    }
      dispose(); 
    }//GEN-LAST:event_okBtnMouseClicked

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBtn;
    private javax.swing.JLabel dayLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton okBtn;
    private javax.swing.JLabel serviceLabel;
    private javax.swing.JTextArea textDetail;
    private javax.swing.JTextField textFieldCar;
    private javax.swing.JLabel timeLabel1;
    private javax.swing.JLabel userLabel;
    // End of variables declaration//GEN-END:variables
}
