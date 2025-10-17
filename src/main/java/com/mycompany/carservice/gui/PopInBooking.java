/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.carservice.gui;

import com.mycompany.carservice.entity.RoundedPanel;
import com.mycompany.carservice.entity.CSVHandler;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author nannapat
 */
public class PopInBooking extends javax.swing.JDialog {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PopInBooking.class.getName());
    private String name;
    private String userId;
    private String timeBooking;
      private String endTime;
    private String service;
    private String day;
    private String month;
    private String year;
    private ArrayList<String[]> bookingDetail = new ArrayList<>();
     ArrayList<String[]> users ;
     
    CSVHandler csvHandler ;
    /**
     * Creates new form popInBooking   
     */
    public PopInBooking(java.awt.Frame parent, boolean modal,String name,String timeBooking,String endTime,String service,String day,String month , String year) {// java.awt.Frame parent เอาไว้ให้ class นี้รู้ว่าจะต้อง pop หน้าจอตัวเอาที่ใคร
        super(parent, modal);
        this.name = name;
        this.timeBooking = timeBooking;
        this.service = service;
        this.day = day;
        this.month = month;
        this.endTime = endTime;
        this.year = year;
         csvHandler = new CSVHandler("src/main/data/user.csv");
       
        initComponents();
        setText();
        this.users = csvHandler.readCSV();
        System.out.println("Time: " + timeBooking);
        System.out.println("Service: " + service);
         cheackuser();
           System.out.println("found service:  "+ getServiceTime(service)); 
       setLocationRelativeTo(null);
        setVisible(true);  
        
        
    }
    public void setText() {
       
       // finishtimeLabel.setText(finishTime);
         dayLabel.setText(day +" "+ month + " " + year);
        timestartLabel.setText(timeBooking);
        userLabel.setText(name);
        serviceLabel.setText(service);
        finishtimeLabel.setText(endTime);
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
    private String getServiceTime(String serviceName) {
        
    csvHandler  = new CSVHandler("src/main/data/service.csv");
       
        ArrayList<String[]> services = csvHandler.readCSV();
        for(String[] s : services){
            if(s[0].equalsIgnoreCase(serviceName.trim())){
                return s[3]; // column Time
                 
            }
        }
        return "Unknown"; // ถ้าไม่เจอ
    }

   
    private void saveBooking(String[] userData) {
    // 1. อ่านข้อมูลเก่าจาก CSV
    csvHandler = new CSVHandler("src/main/data/history_user.csv");
    
 
     csvHandler.appendCSV(userData);
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        backBtn = new javax.swing.JButton();
        okBtn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new RoundedPanel(30) // 30 radius
        ;
        jScrollPane1 = new javax.swing.JScrollPane();
        textDetail = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        userLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        dayLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        textFieldCar = new javax.swing.JTextField();
        timestartLabel = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        finishtimeLabel = new javax.swing.JLabel();
        serviceLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        backBtn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        backBtn.setText("Back");
        backBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backBtnMouseClicked(evt);
            }
        });

        okBtn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        okBtn.setText("Ok");
        okBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                okBtnMouseClicked(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(48, 47, 46));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Booking");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(240, 240, 240));

        textDetail.setColumns(20);
        textDetail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        textDetail.setRows(5);
        jScrollPane1.setViewportView(textDetail);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Detail :");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("User :");

        userLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        userLabel.setText("....");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Day :");

        dayLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dayLabel.setText("....");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Service :");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Time :");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Car registration :");

        textFieldCar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        timestartLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        timestartLabel.setText("jLabel8");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("-");

        finishtimeLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        finishtimeLabel.setText("jLabel9");

        serviceLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        serviceLabel.setText("jLabel9");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(serviceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(timestartLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(finishtimeLabel))
                    .addComponent(textFieldCar, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(userLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(dayLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(serviceLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(timestartLabel)
                    .addComponent(jLabel8)
                    .addComponent(finishtimeLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(textFieldCar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(94, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(backBtn)
                        .addGap(18, 18, 18)
                        .addComponent(okBtn)
                        .addGap(27, 27, 27))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(87, 87, 87))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backBtn)
                    .addComponent(okBtn))
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backBtnMouseClicked
        dispose(); 
    }//GEN-LAST:event_backBtnMouseClicked

    private void okBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_okBtnMouseClicked
       System.out.println("โหลลลลล: ");
       
        csvHandler = new CSVHandler("src/main/data/user.csv");
       ArrayList<String[]> userscheck = csvHandler.readCSV();
        for(String[] data : userscheck){
            
            if(data[1].trim().equalsIgnoreCase(name.trim())){
                System.out.println("User found: " + String.join(", ", data));
                userLabel.setText(data[1]);

                userId = data[0];
                System.out.println("UserID: " + userId);

                // ดึงข้อมูลจาก GUI

               
                String date = dayLabel.getText().trim();
                String time = timestartLabel.getText() + "-" +finishtimeLabel.getText();
                 System.out.println("เวลาที่เลือก : " + time);
                 String note = textDetail.getText().trim();
                String carReg = textFieldCar.getText().trim();
                String status = "booked"; // หรือค่าอื่นตามต้องการ

                
                String[] userData = {userId, data[1], service, date , time, note, carReg, status};

                // เขียนข้อมูลต่อจาก CSV เดิม
               saveBooking(userData);
                System.out.println("Booking saved: " + String.join(", ", userData));

                break;
            }
    }
      dispose(); 
      new PopSuccess(null,true,"Booking Success");
    }//GEN-LAST:event_okBtnMouseClicked

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBtn;
    private javax.swing.JLabel dayLabel;
    private javax.swing.JLabel finishtimeLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton okBtn;
    private javax.swing.JLabel serviceLabel;
    private javax.swing.JTextArea textDetail;
    private javax.swing.JTextField textFieldCar;
    private javax.swing.JLabel timestartLabel;
    private javax.swing.JLabel userLabel;
    // End of variables declaration//GEN-END:variables
}
