/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.carservice.gui;

import com.mycompany.carservice.entity.CSVHandler;
import java.awt.*;
import java.net.URL;
import javax.swing.*;
import java.io.*;
import java.util.*;
import com.mycompany.carservice.entity.RoundedPanel;

/**
 * คลาส Register ใช้สำหรับสร้างหน้าต่างสมัครสมาชิก (Register Page)
 * โดยผู้ใช้สามารถกรอกชื่อผู้ใช้ รหัสผ่าน ยืนยันรหัสผ่าน เบอร์โทรศัพท์ และอีเมล
 * เมื่อบันทึกแล้ว ข้อมูลจะถูกเก็บลงในไฟล์ CSV (user.csv)
 * 
 * หน้าที่หลักของคลาส:
 * - แสดง UI สำหรับสมัครสมาชิก
 * - โหลดไอคอนให้กับช่องกรอกข้อมูล
 * - ตรวจสอบความถูกต้องของข้อมูลที่กรอก
 * - สร้างรหัสผู้ใช้ (UserID) ใหม่อัตโนมัติจากไฟล์ CSV
 * 
 */
public class Register extends javax.swing.JFrame {
    /**
     * Logger สำหรับบันทึกข้อผิดพลาดหรือข้อความสำคัญในระบบ 
     */
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Register.class.getName());
    
    /**
     * คอนสตรัคเตอร์ของคลาส Register
     * ใช้สำหรับตั้งค่าหน้าต่างสมัครสมาชิกและกำหนดค่าเริ่มต้นขององค์ประกอบต่าง ๆ
     */
        public Register() {
                // เรียกใช้เมธอดที่ NetBeans สร้างขึ้นเพื่อจัดการส่วนประกอบในหน้า
                initComponents();

                // กำหนดขนาดหน้าต่าง (width=1200, height=800)
                setSize(1200, 800);

                // จัดตำแหน่งหน้าต่างให้อยู่ตรงกลางหน้าจอ
                setLocationRelativeTo(null);

                // แสดงหน้าต่าง Register
                setVisible(true);

                // ซ่อนข้อความเตือนทั้งหมดไว้ก่อน (จะโชว์เมื่อข้อมูลไม่ถูกต้อง)
                cautionusername.setVisible(false); 
                cautionpass.setVisible(false); 
                cautionconfirmpass.setVisible(false); 
                cautiontel.setVisible(false); 
                cautionemail.setVisible(false);

                try {
                    // โหลดภาพไอคอนจากโฟลเดอร์ src/main/image/
                    URL userIconURL = new File("src/main/image/user.png").toURI().toURL();
                    URL passIconURL = new File("src/main/image/padlock.png").toURI().toURL();
                    URL emailIconURL = new File("src/main/image/email.png").toURI().toURL();
                    URL telIconURL = new File("src/main/image/phone-call.png").toURI().toURL();

                    // ตั้งค่าไอคอนให้กับ JLabel ที่อยู่ข้างช่องกรอกข้อมูล
                    iconusername.setIcon(new ImageIcon(userIconURL));
                    iconpassword.setIcon(new ImageIcon(passIconURL));
                    iconconfirmpassword.setIcon(new ImageIcon(passIconURL));
                    iconemail.setIcon(new ImageIcon(emailIconURL));
                    icontel.setIcon(new ImageIcon(telIconURL));
                } catch (Exception e) {
                    // ถ้ามีปัญหาในการโหลดไฟล์ภาพ
                    System.out.println(e);
                    logger.severe("Cannot load icon images");
                }       
        }
        
        /**
        * เมธอด generateNewUserID()
        * ใช้สำหรับสร้างรหัสผู้ใช้ (User ID) ใหม่โดยอัตโนมัติ
        * โดยจะอ่านไฟล์ user.csv เพื่อตรวจสอบ ID สูงสุดในปัจจุบัน
        * แล้วเพิ่มขึ้น 1 จากนั้นแปลงเป็นรูปแบบเลข 4 หลัก (เช่น 0001, 0002, 0003)
        *
        * @return รหัสผู้ใช้ใหม่ในรูปแบบ String (เช่น "0005")
        */
        private String generateNewUserID() {
            // สร้างออบเจ็กต์ CSVHandler เพื่ออ่านไฟล์ข้อมูลผู้ใช้
            CSVHandler csvHandler = new CSVHandler("src/main/data/user.csv");
            ArrayList<String[]> data = csvHandler.readCSV();
            
            // ตัวแปรw;hเก็บค่า ID สูงสุดจากไฟล์
            int maxID = 0;
            
            // วนลูปอ่านแต่ละแถวของข้อมูล CSV
            for (String[] row : data) {
                if (row.length > 0) {
                    String idStr = row[0];
                    try {
                        // แปลงค่าเป็น int เพื่อตรวจสอบ ID ที่มากที่สุด
                        int id = Integer.parseInt(idStr);
                        if (id > maxID) maxID = id;
                        
                    } catch (NumberFormatException e) {
                        // ถ้าค่า ID ไม่ใช่ตัวเลข (ข้ามแถวนี้)
                    }
                }
            }
            // เพิ่มค่า ID สูงสุดขึ้น 1 เพื่อเป็น ID ใหม่
            maxID++;
            // คืนค่าในรูปแบบตัวเลข 4 หลัก เช่น "0005"
            return String.format("%04d", maxID);
        }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        registerButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        logButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        cautionconfirmpass = new javax.swing.JLabel();
        iconusername = new javax.swing.JLabel();
        iconpassword = new javax.swing.JLabel();
        icontel = new javax.swing.JLabel();
        iconemail = new javax.swing.JLabel();
        iconconfirmpassword = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        username = new javax.swing.JTextField();
        password = new javax.swing.JPasswordField();
        confirmPassword = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        cautionpass = new javax.swing.JLabel();
        cautionusername = new javax.swing.JLabel();
        cautionemail = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cautiontel = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tel = new javax.swing.JTextField();
        email = new javax.swing.JTextField();
        jPanel2 = new RoundedPanel(30); // 30 radius;
        registerFinish = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(43, 43, 43));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        registerButton.setBackground(new java.awt.Color(255, 149, 0));
        registerButton.setFont(new java.awt.Font("Gadugi", 1, 24)); // NOI18N
        registerButton.setText("Register");
        registerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registerButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                registerButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                registerButtonMouseExited(evt);
            }
        });
        jPanel4.add(registerButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(72, 401, 237, 60));

        jLabel2.setFont(new java.awt.Font("Gadugi", 1, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("WELCOME");
        jLabel2.setToolTipText("");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(72, 161, -1, 62));

        logButton.setFont(new java.awt.Font("Gadugi", 1, 24)); // NOI18N
        logButton.setText("Login");
        logButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logButtonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                logButtonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                logButtonMouseExited(evt);
            }
        });
        jPanel4.add(logButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(72, 305, 237, 60));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 370, 800));

        jLabel4.setFont(new java.awt.Font("Gadugi", 1, 48)); // NOI18N
        jLabel4.setText("Register");
        jLabel4.setToolTipText("");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 70, -1, -1));

        cautionconfirmpass.setForeground(new java.awt.Color(255, 0, 0));
        cautionconfirmpass.setText("Passwords don't match !!! please try again");
        cautionconfirmpass.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel1.add(cautionconfirmpass, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 420, -1, -1));
        jPanel1.add(iconusername, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 160, 50, 60));
        jPanel1.add(iconpassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 260, 50, 60));
        jPanel1.add(icontel, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 560, 50, 60));
        jPanel1.add(iconemail, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 460, 50, 60));
        jPanel1.add(iconconfirmpassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 360, 50, 60));

        jLabel3.setText("Username");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 140, -1, -1));

        jLabel5.setText("Password");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 240, -1, -1));

        username.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        username.setMargin(new java.awt.Insets(2, 55, 2, 6));
        username.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                usernameKeyTyped(evt);
            }
        });
        jPanel1.add(username, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 160, 510, 60));

        password.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        password.setMargin(new java.awt.Insets(2, 55, 2, 6));
        password.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                passwordKeyTyped(evt);
            }
        });
        jPanel1.add(password, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 260, 510, 60));

        confirmPassword.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        confirmPassword.setMargin(new java.awt.Insets(2, 55, 2, 6));
        confirmPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmPasswordActionPerformed(evt);
            }
        });
        confirmPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                confirmPasswordKeyTyped(evt);
            }
        });
        jPanel1.add(confirmPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 360, 510, 60));

        jLabel6.setText("Confirm Password");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 340, -1, -1));

        cautionpass.setForeground(new java.awt.Color(255, 0, 0));
        cautionpass.setText("Passwords don't match !!! please try again");
        cautionpass.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel1.add(cautionpass, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 320, -1, -1));

        cautionusername.setForeground(new java.awt.Color(255, 0, 0));
        cautionusername.setText("Passwords don't match !!! please try again");
        cautionusername.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel1.add(cautionusername, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 220, -1, -1));

        cautionemail.setForeground(new java.awt.Color(255, 0, 0));
        cautionemail.setText("Passwords don't match !!! please try again");
        cautionemail.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel1.add(cautionemail, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 520, -1, -1));

        jLabel7.setText("Email");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 440, -1, -1));

        cautiontel.setForeground(new java.awt.Color(255, 0, 0));
        cautiontel.setText("Passwords don't match !!! please try again");
        cautiontel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel1.add(cautiontel, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 620, -1, -1));

        jLabel8.setText("Phone");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 540, -1, -1));

        tel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tel.setMargin(new java.awt.Insets(2, 55, 2, 6));
        tel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                telKeyTyped(evt);
            }
        });
        jPanel1.add(tel, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 560, 510, 60));

        email.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        email.setMargin(new java.awt.Insets(2, 55, 2, 6));
        email.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                emailKeyTyped(evt);
            }
        });
        jPanel1.add(email, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 460, 510, 60));

        registerFinish.setBackground(new java.awt.Color(255, 149, 0));
        registerFinish.setFont(new java.awt.Font("Gadugi", 1, 24)); // NOI18N
        registerFinish.setText("Register");
        registerFinish.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registerFinishMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                registerFinishMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                registerFinishMouseExited(evt);
            }
        });
        registerFinish.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerFinishActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(119, Short.MAX_VALUE)
                .addComponent(registerFinish, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(617, Short.MAX_VALUE)
                .addComponent(registerFinish, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 40, 730, 710));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1200, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void registerFinishActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerFinishActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_registerFinishActionPerformed

    private void registerFinishMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerFinishMouseExited
        registerFinish.setForeground(Color.BLACK);
    }//GEN-LAST:event_registerFinishMouseExited

    private void registerFinishMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerFinishMouseEntered
        registerFinish.setForeground(Color.WHITE);
    }//GEN-LAST:event_registerFinishMouseEntered

    private void registerFinishMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerFinishMouseClicked
        /**
        * ตอนคลิกปุ่ม Register
        * เมธอดนี้จะตรวจสอบว่าถูกต้องตามเงื่อนไขที่ได้ตั้งไว้ในเเต่ล่ะส่วนหรือไม่ของ
        * ีusername ,password,confirm password, email, phone number
        * มี
        * - ตรวจสอบว่าช่องว่างหรือไม่
        * - ตรวจสอบความซับซ้อนของรหัสผ่าน
        * - ตรวจสอบการยืนยันรหัสผ่าน
        * - ตรวจสอบเบอร์โทรและอีเมล
        * 
        * ถ้าข้อมูลถูกต้องทั้งหมด → เขียนข้อมูลใหม่ลงไฟล์ user.csv
        * จากนั้นจะปิดหน้าต่างสมัครสมาชิกและเปิดหน้า Login ใหม่
        * 
        *.@param evt เหตุการณ์ MouseEvent เมื่อผู้ใช้คลิกปุ่มสมัคร
        */
        
        // แปลงรหัสผ่านและยืนยันรหัสผ่านจากช่อง Password Field เป็น String
        String pass = new String(password.getPassword()).trim();
        String confirmpass = new String(confirmPassword.getPassword()).trim();
        // ตัวแปรใช้เก็บสถานะการตรวจสอบแต่ละช่อง (0 = ผ่าน, 1 = ไม่ผ่าน)
        int sumuser = 0;
        int sumpass = 0;
        int sumconfirm = 0;
        int sumtel = 0;
        int sumemail = 0;
        
        //ตรวจสอบช่อง Username ว่าตรงกับเงื่อนไขที่ตั้งไว้หรือไม่
        if (username.getText().trim().isEmpty()) {
            cautionusername.setText("Need to input username");
            cautionusername.setVisible(true);
            sumuser=1;
        }else{
            cautionusername.setText("");
            cautionusername.setVisible(false);
            sumuser=0;
        }
        //ตรวจสอบช่อง Password ว่าตรงกับเงื่อนไขที่ตั้งไว้หรือไม่
        if (pass.isEmpty()) {
            cautionpass.setText("Need to input password");
            cautionpass.setVisible(true);
            sumpass=1;
        }else if (pass.length() < 6) {
            cautionpass.setText("Password need at least 6 characters");
            cautionpass.setVisible(true);
            sumpass=1;
        }else if (!pass.matches(".*\\d.*")) {
            cautionpass.setText("Password must have at least one number");
            cautionpass.setVisible(true);
            sumpass=1;
        }else if (!pass.matches(".*[a-z].*")) {
            cautionpass.setText("Password must have at least one lowercase letter");
            cautionpass.setVisible(true);
            sumpass=1;
        }else if (!pass.matches(".*[A-Z].*")) {
            cautionpass.setText("Password must have at least one uppercase letter");
            cautionpass.setVisible(true);
            sumpass=1;
        }else if (!pass.matches(".*[!@#$%^&*()].*")) {
            cautionpass.setText("Password must have at least one special character");
            cautionpass.setVisible(true);
            sumpass=1;
        }else{
            cautionpass.setText("");
            cautionpass.setVisible(false);
            sumpass=0;
        }
        //ตรวจสอบช่อง Confirm Password ว่าตรงกับ Password หรือไม่
        if (confirmpass.isEmpty()) {
            cautionconfirmpass.setText("Need to confirm password");
            cautionconfirmpass.setVisible(true);
            sumconfirm=1;
            
        // ถ้ารหัสผ่านและยืนยันรหัสผ่านไม่ตรงกัน
        }else if (!confirmpass.equals(pass)) {
            cautionconfirmpass.setText("Nah");
            cautionconfirmpass.setVisible(true);
            sumpass=1;
        }else{
            cautionconfirmpass.setText("");
            cautionconfirmpass.setVisible(false);
            sumconfirm=0;
        }
        //ตรวจสอบช่อง tel หรือ phone number ว่าตรงกับเงื่อนไขที่ตั้งไว้หรือไม่
        if (tel.getText().trim().isEmpty()) {
            cautiontel.setText("Need to input your Phone number");
            cautiontel.setVisible(true);
            sumtel=1;
        }else if (tel.getText().length() < 10 || tel.getText().length() > 10 ){
            cautiontel.setText("Wrong phone number !!");
            cautiontel.setVisible(true);
            sumtel=1;
        }else{
            cautiontel.setText("");
            cautiontel.setVisible(false);
            sumtel=0;
        }
        //ตรวจสอบช่อง Email ว่าตรงกับ pattern ที่ตั้งไว้หรือไม่
        String emailText = email.getText().trim();
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.(com|co\\.th)$";

        if (emailText.isEmpty()) {
            cautionemail.setText("Need to input your email");
            cautionemail.setVisible(true);
            sumemail = 1;
        } else if (!emailText.matches(emailPattern)) {
            cautionemail.setText("Invalid email format (must end with .com, or .co.th)");
            cautionemail.setVisible(true);
            sumemail = 1;
        } else {
            cautionemail.setText("");
            cautionemail.setVisible(false);
            sumemail = 0;
        }
        //ถ้าทุกช่องผ่านการตรวจสอบทั้งหมดเเล้ว ทำการปิดคำเตือนทั้งหมด 
        if (sumpass == 0 && sumuser == 0 && sumconfirm==0 &&  sumtel == 0 &&  sumtel == 0){
            cautionusername.setVisible(false);
            cautionpass.setVisible(false);
            cautionconfirmpass.setVisible(false);
            cautiontel.setVisible(false);
            cautionemail.setVisible(false);

            // อ่านค่าจากทุกช่องทั้งหมดเเล้วเก็บไว้
            String usernameget = username.getText().trim();
            String passwordget = new String(password.getPassword()).trim();
            String telget = tel.getText().trim();
            String emailget = email.getText().trim();
            // นำมาเขียนลง user.csv
            try {
                File f = new File("src/main/data/user.csv");
                FileWriter fw = new FileWriter(f,true);
                BufferedWriter bw = new BufferedWriter(fw);
                System.out.println("Saved successfully!");
                // เขียนข้อมูลลงไฟล์ CSV ในรูปแบบ: id,username,password,email,tel,role
                bw.write("\n"+ generateNewUserID() +","+usernameget+","+passwordget+","+emailget+","+telget+",user");
                bw.close();
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.print(username.getText() + "\n" + pass);
            // ปิดหน้าต่าง Register เปิดหน้า Login ใหม่
            dispose();
            new Login();

        }
        
    }//GEN-LAST:event_registerFinishMouseClicked

    private void confirmPasswordKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_confirmPasswordKeyTyped
        char c = evt.getKeyChar();
        if (Character.isWhitespace(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_confirmPasswordKeyTyped

    private void passwordKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordKeyTyped
        char c = evt.getKeyChar();
        if (Character.isWhitespace(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_passwordKeyTyped

    private void usernameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usernameKeyTyped
        char c = evt.getKeyChar();
        if (!Character.isLetter(c)&!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_usernameKeyTyped

    private void logButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logButtonMouseExited
        logButton.setBackground(Color.WHITE);
        logButton.setForeground(Color.BLACK);
    }//GEN-LAST:event_logButtonMouseExited

    private void logButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logButtonMouseEntered
        logButton.setBackground(Color.GRAY);
        logButton.setForeground(Color.WHITE);
    }//GEN-LAST:event_logButtonMouseEntered

    private void logButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logButtonMouseClicked
        dispose();
        new Login();
    }//GEN-LAST:event_logButtonMouseClicked

    private void registerButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerButtonMouseExited

        registerButton.setForeground(Color.BLACK);
    }//GEN-LAST:event_registerButtonMouseExited

    private void registerButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerButtonMouseEntered

        registerButton.setForeground(Color.WHITE);
    }//GEN-LAST:event_registerButtonMouseEntered

    private void registerButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerButtonMouseClicked
        dispose();
        new Register();
    }//GEN-LAST:event_registerButtonMouseClicked

    private void telKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_telKeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
        if (tel.getText().length() >= 10) {
            evt.consume();
        }
    }//GEN-LAST:event_telKeyTyped

    private void emailKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_emailKeyTyped
        char c = evt.getKeyChar();
        if (c == ' ') {
            evt.consume();
        }
    }//GEN-LAST:event_emailKeyTyped

    private void confirmPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_confirmPasswordActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel cautionconfirmpass;
    private javax.swing.JLabel cautionemail;
    private javax.swing.JLabel cautionpass;
    private javax.swing.JLabel cautiontel;
    private javax.swing.JLabel cautionusername;
    private javax.swing.JPasswordField confirmPassword;
    private javax.swing.JTextField email;
    private javax.swing.JLabel iconconfirmpassword;
    private javax.swing.JLabel iconemail;
    private javax.swing.JLabel iconpassword;
    private javax.swing.JLabel icontel;
    private javax.swing.JLabel iconusername;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JButton logButton;
    private javax.swing.JPasswordField password;
    private javax.swing.JButton registerButton;
    private javax.swing.JButton registerFinish;
    private javax.swing.JTextField tel;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables
}
