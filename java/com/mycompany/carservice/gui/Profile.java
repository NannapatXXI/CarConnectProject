/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.carservice.gui;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.util.*;
import javax.swing.text.*;
import com.formdev.flatlaf.FlatLightLaf;
import com.mycompany.carservice.gui.Changepassword;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import com.mycompany.carservice.entity.RoundedPanel;
/**
 *
 * @author User
 */
public class Profile extends javax.swing.JFrame {
    private final String userName;
    private final String role;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Profile.class.getName());
    private String user;
    private String Tell;
    private String experience;
    private String oldName; // เก็บชื่อเก่าก่อนแก้
    private String currentUserName = "สมหญิง สวยงาม";
    
    
    /**
     * Creates new form Profile
     */
    public Profile(String userName,String role) {
        initComponents();
        this.userName = userName ;
        this.role = role;
        username.setHorizontalAlignment(JLabel.RIGHT);
        username.setText(userName);
        
        ((AbstractDocument) phoneText.getDocument()).setDocumentFilter(new NumberDocumentFilter(10));
        setupui();
        /*homebutton.putClientProperty("JButton.buttonType", "roundRect");
        homebutton.putClientProperty("arc",5);
        historybutton.putClientProperty("JButton.buttonType", "roundRect");
        historybutton.putClientProperty("arc",5);
        bookingbutton.putClientProperty("JButton.buttonType", "roundRect");
        bookingbutton.putClientProperty("arc", 5);*/
        setSize(1200, 800);        
        setLocationRelativeTo(null); // จัดกลางหน้าจอ
        setVisible(true);
        loadUserProfile(currentUserName);
         
         if ("admin".equalsIgnoreCase(role)) {   
            adminBtn.setVisible(true);
            iconAdmin.setVisible(true); // แสดงเฉพาะ Admin
        } else {
            adminBtn.setVisible(false);
            iconAdmin.setVisible(false);// ซ่อนสำหรับ User ปกติ
        }
       
         try {
                URL logoIconURL = new File("src/main/image/logoCarConnect.png").toURI().toURL();
                URL homeIconURL = new File("src/main/image/home.png").toURI().toURL();
                URL bookingIconURL = new File("src/main/image/booking.png").toURI().toURL();
                URL historyIconURL = new File("src/main/image/history.png").toURI().toURL();
                URL profileIconURL = new File("src/main/image/profile.png").toURI().toURL();
                URL adminIconURL = new File("src/main/image/admin.png").toURI().toURL();
                URL changepasswordIconURL = new File("src/main/image/Pofilepassword.png").toURI().toURL();
                URL exitIconURL = new File("src/main/image/logout.png").toURI().toURL();
                
                changepasswordicon.setIcon(new ImageIcon(changepasswordIconURL));
                logo.setIcon(new ImageIcon(logoIconURL));
                iconHome.setIcon(new ImageIcon(homeIconURL));
                iconBooking.setIcon(new ImageIcon(bookingIconURL));
                iconHistory.setIcon(new ImageIcon(historyIconURL));
                iconProfile.setIcon(new ImageIcon(profileIconURL));
                iconAdmin.setIcon(new ImageIcon(adminIconURL));
                iconExit.setIcon(new ImageIcon(exitIconURL));

            } catch (MalformedURLException e) {
                System.out.println(e);
                logger.severe("Cannot load icon images");
            }
    }

    private void setupui(){
        homeBtn.setContentAreaFilled(false);
        homeBtn.setBorderPainted(false);
        
        bookingBtn.setContentAreaFilled(false);
        bookingBtn.setBorderPainted(false);

        historyBtn.setContentAreaFilled(false);
        historyBtn.setBorderPainted(false);

        adminBtn.setContentAreaFilled(false);
        adminBtn.setBorderPainted(false);

    }
    
    
    private void loadFromCSV() {
    String filePath = "src/main/data/user.csv"; // ตำแหน่งไฟล์
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;
        boolean isFirstLine = true;
        while ((line = br.readLine()) != null) {
            if (isFirstLine) { 
                isFirstLine = false; // ข้ามบรรทัด header
                continue;
            }

            String[] values = line.split(",");

            // สมมติเลือกบรรทัดแรกสุด
            if (values.length >= 4) {
                usernameText.setText(values[1]);       // Name
                passwordText.setText(values[3]);       // Phone
                phoneText.setText(values[2]); // Email (หรือเปลี่ยนเป็น exp ถ้ามี)
            }
            break; // โหลดแค่แถวแรกพอ
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    
}
// โหลดข้อมูลโปรไฟล์ของผู้ใช้จากไฟล์ user.csv
private void loadUserProfile(String name) {
    // แสดงชื่อผู้ใช้ปัจจุบันบน label
    //username.setText(userName);
    String filePath = "src/main/data/user.csv";
    // ใช้ BufferedReader อ่านไฟล์ทีละบรรทัด
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;
        boolean isFirstLine = true;
        while ((line = br.readLine()) != null) {
            if (isFirstLine) {
                isFirstLine = false; // เอาไว้ข้ามบรรทัดแรก (หัวตาราง)
                continue;
            }
            // ถ้าชื่อ (values[1]) ตรงกับ name ที่ส่งมา
            String[] values = line.split(",");
            if (values[1].trim().equals(name.trim())) {
                usernameText.setText(values[1]);   // Name
                passwordText.setText(values[2]);   // ✅ Password
                emailText.setText(values[3]);      // Email
                phoneText.setText(values[4]);      // Phone
                // เก็บชื่อเก่าไว้ใช้ตอนแก้ไข/อัพเดทข้อมูล
                oldName = values[1];
                break;
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
// ใช้ค้นหาจาก "name" (ชื่อเดิม) แล้วแก้ไขเป็นค่าใหม่
private void updateFirstRowCSV(String name, String newName, String newPassword, String newEmail, String newPhone) {
    String filePath = "src/main/data/user.csv";
    List<String> lines = new ArrayList<>();
    // อ่านไฟล์ทั้งหมดแล้วเก็บใน list "lines"
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    
    for (int i = 1; i < lines.size(); i++) {
        String[] values = lines.get(i).split(",");
        // ถ้าเจอชื่อเดิมตรงกับ "name"
        if (values[1].trim().equals(name)) {
            String userID = values[0]; // เก็บ userID เดิมไว้
            String role   = values[5]; // เก็บ role เดิมไว้

            // ✅ สร้างบรรทัดใหม่โดยแทนที่ Name, Password, Email, Phone ด้วยค่าที่แก้ไข
            // รูปแบบ: userID, newName, newPassword, newEmail, newPhone, role
            lines.set(i, userID + "," + newName + "," + newPassword + "," + newEmail + "," + newPhone + "," + role);
            break;
        }
    }
    //  เขียนข้อมูลทั้งหมด  กลับลงไฟล์
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
        for (String l : lines) {
            bw.write(l);
            bw.newLine(); // ขึ้นบรรทัดใหม่
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    }
// ✅ DocumentFilter สำหรับกรอกเฉพาะตัวเลข และจำกัดจำนวนหลัก
class NumberDocumentFilter extends DocumentFilter {
    private int maxLength; // ความยาวสูงสุดของ input

    public NumberDocumentFilter(int maxLength) {
        this.maxLength = maxLength; 
    }

    @Override
    // เรียกเมื่อมีการ "insert" ตัวอักษรใหม่เข้ามา
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (string == null) return; // ถ้าไม่มีค่า → ไม่ทำอะไร
        // เช็คว่าเป็นตัวเลขล้วน และความยาวไม่เกินที่กำหนด
        if (isNumeric(string) && (fb.getDocument().getLength() + string.length()) <= maxLength) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    // เรียกเมื่อมีการ "แทนที่" ข้อความ เช่น paste หรือ select + พิมพ์ทับ
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text == null) return;
        // เช็คว่าเป็นตัวเลข และความยาวหลังแทนที่ไม่เกินที่กำหนด
        if (isNumeric(text) && (fb.getDocument().getLength() - length + text.length()) <= maxLength) {
            super.replace(fb, offset, length, text, attrs);
        }
    }
    // ฟังก์ชันช่วยเช็คว่า input เป็นตัวเลขหรือไม่
    private boolean isNumeric(String str) {
        return str.matches("\\d*"); //  แก้เป็น \\d* ให้รองรับ empty string (เวลาลบ)
    }
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel6 = new javax.swing.JPanel();
        jPanel4 = new RoundedPanel(30); // 30 radius;
        usernameText = new javax.swing.JTextField();
        changepasswordicon = new javax.swing.JLabel();
        passwordText = new javax.swing.JTextField();
        phoneText = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        texttInbtnEdittext = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        emailText = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        iconHome = new javax.swing.JLabel();
        iconBooking = new javax.swing.JLabel();
        iconHistory = new javax.swing.JLabel();
        iconProfile = new javax.swing.JLabel();
        iconAdmin = new javax.swing.JLabel();
        homeBtn = new javax.swing.JButton();
        adminBtn = new javax.swing.JButton();
        profileBtn = new javax.swing.JButton();
        historyBtn = new javax.swing.JButton();
        bookingBtn = new javax.swing.JButton();
        logo = new javax.swing.JLabel();
        username = new javax.swing.JLabel();
        iconExit = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        usernameText.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        usernameText.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        usernameText.setActionCommand("<Not Set>");
        usernameText.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jPanel4.add(usernameText, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, 646, 63));

        changepasswordicon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changepasswordiconMouseClicked(evt);
            }
        });
        jPanel4.add(changepasswordicon, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 230, 60, 40));

        passwordText.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        passwordText.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        passwordText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordTextActionPerformed(evt);
            }
        });
        jPanel4.add(passwordText, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 223, 646, 60));

        phoneText.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        phoneText.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        phoneText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phoneTextActionPerformed(evt);
            }
        });
        jPanel4.add(phoneText, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 400, 646, 60));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Username :");
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 110, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("password :");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 240, 100, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("phone :");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 410, 60, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Profile");
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, -1, -1));

        texttInbtnEdittext.setBackground(new java.awt.Color(0, 0, 0));
        texttInbtnEdittext.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        texttInbtnEdittext.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        texttInbtnEdittext.setText("Save");
        texttInbtnEdittext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                texttInbtnEdittextMouseClicked(evt);
            }
        });
        jPanel4.add(texttInbtnEdittext, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 552, 60, 40));

        jButton1.setBackground(new java.awt.Color(255, 149, 0));
        jPanel4.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 550, 140, 50));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Email :");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 330, -1, -1));

        emailText.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        emailText.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        emailText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailTextActionPerformed(evt);
            }
        });
        jPanel4.add(emailText, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 310, 646, 63));

        jLabel8.setText("jLabel8");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 240, -1, -1));

        jPanel6.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 130, 900, 610));

        jPanel2.setBackground(new java.awt.Color(43, 43, 43));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        iconHome.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(iconHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 220, 40, 30));

        iconBooking.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(iconBooking, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 290, 40, 30));

        iconHistory.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(iconHistory, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 360, 40, 30));

        iconProfile.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(iconProfile, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 430, 40, 30));

        iconAdmin.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(iconAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 500, 40, 30));

        homeBtn.setBackground(new java.awt.Color(43, 43, 43));
        homeBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        homeBtn.setForeground(new java.awt.Color(204, 204, 204));
        homeBtn.setText(" Home    ");
        homeBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
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
        jPanel2.add(homeBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 260, 70));

        adminBtn.setBackground(new java.awt.Color(43, 43, 43));
        adminBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        adminBtn.setForeground(new java.awt.Color(204, 204, 204));
        adminBtn.setText("Admin   ");
        adminBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        adminBtn.setPreferredSize(new java.awt.Dimension(164, 90));
        adminBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                adminBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                adminBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                adminBtnMouseExited(evt);
            }
        });
        adminBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminBtnActionPerformed(evt);
            }
        });
        jPanel2.add(adminBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 480, 270, 70));

        profileBtn.setBackground(new java.awt.Color(255, 157, 0));
        profileBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        profileBtn.setForeground(new java.awt.Color(255, 255, 255));
        profileBtn.setText("Profile  ");
        profileBtn.setPreferredSize(new java.awt.Dimension(164, 90));
        jPanel2.add(profileBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 260, 70));

        historyBtn.setBackground(new java.awt.Color(43, 43, 43));
        historyBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        historyBtn.setForeground(new java.awt.Color(204, 204, 204));
        historyBtn.setText("History ");
        historyBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
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
        historyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                historyBtnActionPerformed(evt);
            }
        });
        jPanel2.add(historyBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 260, 70));

        bookingBtn.setBackground(new java.awt.Color(43, 43, 43));
        bookingBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        bookingBtn.setForeground(new java.awt.Color(204, 204, 204));
        bookingBtn.setText("Booking");
        bookingBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
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
        jPanel2.add(bookingBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 260, 70));

        logo.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 240, 140));

        jPanel6.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 800));

        username.setBackground(new java.awt.Color(0, 0, 0));
        username.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        username.setText("..");
        jPanel6.add(username, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 40, 660, 40));

        iconExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconExitMouseClicked(evt);
            }
        });
        jPanel6.add(iconExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 40, 40, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 1200, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void phoneTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phoneTextActionPerformed
        

    }//GEN-LAST:event_phoneTextActionPerformed

    private void texttInbtnEdittextMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_texttInbtnEdittextMouseClicked
        String newName = usernameText.getText();
    String newPassword = passwordText.getText();
    String newEmail = emailText.getText();
    String newPhone = phoneText.getText();

    updateFirstRowCSV(oldName, newName, newPassword, newEmail, newPhone);
    oldName = newName; 
    System.out.println("Name : " + newName);
    System.out.println("Password : " + newPassword);     
    System.out.println("Email : " + newEmail);
    System.out.println("Phone : " + newPhone);

    // อัปเดต oldName เป็นค่าล่าสุด (เพื่อใช้รอบต่อไป)
    oldName = usernameText.getText(); 
        user = usernameText.getText();
            System.out.println("User : "+ user);
        Tell = passwordText.getText();
            System.out.println("Tell : "+ Tell);     
        experience = phoneText.getText();
            System.out.println("experience : "+ experience);
    }//GEN-LAST:event_texttInbtnEdittextMouseClicked

    private void passwordTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordTextActionPerformed

    private void emailTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailTextActionPerformed

    private void changepasswordiconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_changepasswordiconMouseClicked
        new Changepassword(userName,role);
    }//GEN-LAST:event_changepasswordiconMouseClicked

    private void adminBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminBtnMouseClicked
        dispose();
        new AdminPage(userName,role);
    }//GEN-LAST:event_adminBtnMouseClicked

    private void adminBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminBtnMouseEntered
        //adminBtn.setBackground(Color.GRAY);
        adminBtn.setForeground(Color.WHITE);
    }//GEN-LAST:event_adminBtnMouseEntered

    private void adminBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminBtnMouseExited
        //adminBtn.setBackground(new Color(28,24,24));
        adminBtn.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_adminBtnMouseExited

    private void adminBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_adminBtnActionPerformed

    private void historyBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_historyBtnMouseClicked
        dispose();
        new History(userName,role);
    }//GEN-LAST:event_historyBtnMouseClicked

    private void historyBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_historyBtnMouseEntered
        //historyBtn.setBackground(Color.GRAY);
        historyBtn.setForeground(Color.WHITE);
    }//GEN-LAST:event_historyBtnMouseEntered

    private void historyBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_historyBtnMouseExited
        //historyBtn.setBackground(new Color(204,204,204));
        historyBtn.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_historyBtnMouseExited

    private void historyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historyBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_historyBtnActionPerformed

    private void bookingBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bookingBtnMouseClicked
        dispose();
        new BookingPage(userName,role);
    }//GEN-LAST:event_bookingBtnMouseClicked

    private void bookingBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bookingBtnMouseEntered
        //bookingBtn.setBackground(Color.GRAY);
        bookingBtn.setForeground(Color.WHITE);
    }//GEN-LAST:event_bookingBtnMouseEntered

    private void bookingBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bookingBtnMouseExited
        //bookingBtn.setBackground(new Color(28,24,24));
        bookingBtn.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_bookingBtnMouseExited

    private void homeBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeBtnMouseClicked
        dispose();
        new HomePage(userName,role);
    }//GEN-LAST:event_homeBtnMouseClicked

    private void homeBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeBtnMouseEntered
        //profileBtn.setBackground(Color.GRAY);
        homeBtn.setForeground(Color.WHITE);
    }//GEN-LAST:event_homeBtnMouseEntered

    private void homeBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeBtnMouseExited
        //profileBtn.setBackground(new Color(28,24,24));
        homeBtn.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_homeBtnMouseExited

    private void iconExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconExitMouseClicked
        dispose();
        new Login();
    }//GEN-LAST:event_iconExitMouseClicked

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton adminBtn;
    private javax.swing.JButton bookingBtn;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel changepasswordicon;
    private javax.swing.JTextField emailText;
    private javax.swing.JButton historyBtn;
    private javax.swing.JButton homeBtn;
    private javax.swing.JLabel iconAdmin;
    private javax.swing.JLabel iconBooking;
    private javax.swing.JLabel iconExit;
    private javax.swing.JLabel iconHistory;
    private javax.swing.JLabel iconHome;
    private javax.swing.JLabel iconProfile;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel logo;
    private javax.swing.JTextField passwordText;
    private javax.swing.JTextField phoneText;
    private javax.swing.JButton profileBtn;
    private javax.swing.JLabel texttInbtnEdittext;
    private javax.swing.JLabel username;
    private javax.swing.JTextField usernameText;
    // End of variables declaration//GEN-END:variables
}
