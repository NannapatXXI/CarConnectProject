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
/**
 *
 * @author User
 */
public class Profile extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Profile.class.getName());
    private String user;
    private String Tell;
    private String experience;
    private String oldName; // เก็บชื่อเก่าก่อนแก้
    private String currentUserName = "สมหญิง สวยงาม";
    
    
    /**
     * Creates new form Profile
     */
    public Profile() {
        
        initComponents();
        ((AbstractDocument) phoneText.getDocument()).setDocumentFilter(new NumberDocumentFilter(10));
        setupui();
        /*homebutton.putClientProperty("JButton.buttonType", "roundRect");
        homebutton.putClientProperty("arc",5);
        historybutton.putClientProperty("JButton.buttonType", "roundRect");
        historybutton.putClientProperty("arc",5);
        bookingbutton.putClientProperty("JButton.buttonType", "roundRect");
        bookingbutton.putClientProperty("arc", 5);*/
         setSize(1280, 800);  
         setLocationRelativeTo(null); // จัดตรงกลางจอ
         setVisible(true);
         loadUserProfile(currentUserName);
       
         try {
                URL homeIconURL = new File("src/main/image/home.png").toURI().toURL();
                URL bookingIconURL = new File("src/main/image/booking.png").toURI().toURL();
                URL historyIconURL = new File("src/main/image/history.png").toURI().toURL();
                URL profileIconURL = new File("src/main/image/profile.png").toURI().toURL();
                URL carconnectIconURL = new File("src/main/image/logoCarConnect.png").toURI().toURL();
                URL changepasswordIconURL = new File("src/main/image/Pofilepassword.png").toURI().toURL();
                
                changepasswordicon.setIcon(new ImageIcon(changepasswordIconURL));
                homeicon.setIcon(new ImageIcon(homeIconURL));
                bookingicon.setIcon(new ImageIcon(bookingIconURL));
                historyicon.setIcon(new ImageIcon(historyIconURL));
                profileicon.setIcon(new ImageIcon(profileIconURL));
                carconnecticon.setIcon(new ImageIcon(carconnectIconURL));
                
                changepasswordicon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose();
                new Changepassword(currentUserName); // ส่งค่า currentUserName ไป
            }
        });
                
                
                
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
    }
    private void setupui(){
        homebutton.setBorderPainted(false);
        historybutton.setBorderPainted(false);
        bookingbutton.setBorderPainted(false);
        profilebutton.setBorderPainted(false);
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
    userlabel.setText("" + currentUserName);
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
        jPanel4 = new javax.swing.JPanel();
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
        homeicon = new javax.swing.JLabel();
        bookingicon = new javax.swing.JLabel();
        historyicon = new javax.swing.JLabel();
        profileicon = new javax.swing.JLabel();
        profilebutton = new javax.swing.JButton();
        homebutton = new javax.swing.JButton();
        historybutton = new javax.swing.JButton();
        bookingbutton = new javax.swing.JButton();
        homeicon1 = new javax.swing.JLabel();
        carconnecticon = new javax.swing.JLabel();
        userlabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(228, 224, 224));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        usernameText.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        usernameText.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        usernameText.setActionCommand("<Not Set>");
        usernameText.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jPanel4.add(usernameText, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, 646, 63));

        changepasswordicon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changepasswordiconMouseClicked(evt);
            }
        });
        jPanel4.add(changepasswordicon, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 280, 80, 60));

        passwordText.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        passwordText.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        passwordText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordTextActionPerformed(evt);
            }
        });
        jPanel4.add(passwordText, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 280, 646, 63));

        phoneText.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        phoneText.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        phoneText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phoneTextActionPerformed(evt);
            }
        });
        jPanel4.add(phoneText, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 490, 646, 63));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Username :");
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 210, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("password :");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 300, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("phone :");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 510, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Profile");
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel4.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 100, -1, -1));

        texttInbtnEdittext.setBackground(new java.awt.Color(0, 0, 0));
        texttInbtnEdittext.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        texttInbtnEdittext.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        texttInbtnEdittext.setText("Save");
        texttInbtnEdittext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                texttInbtnEdittextMouseClicked(evt);
            }
        });
        jPanel4.add(texttInbtnEdittext, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 620, -1, -1));

        jButton1.setBackground(new java.awt.Color(255, 149, 0));
        jPanel4.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 610, 140, 50));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Email :");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 410, -1, -1));

        emailText.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        emailText.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        emailText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailTextActionPerformed(evt);
            }
        });
        jPanel4.add(emailText, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 380, 646, 63));

        jLabel8.setText("jLabel8");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 300, -1, -1));

        jPanel6.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 80, 970, 680));

        jPanel2.setBackground(new java.awt.Color(43, 43, 43));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(homeicon, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 230, 40, 50));
        jPanel2.add(bookingicon, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 320, 40, 50));
        jPanel2.add(historyicon, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 410, 40, 50));
        jPanel2.add(profileicon, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 500, 50, 50));

        profilebutton.setBackground(new java.awt.Color(255, 149, 0));
        profilebutton.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        profilebutton.setText("Profile");
        profilebutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                profilebuttonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                profilebuttonMouseExited(evt);
            }
        });
        jPanel2.add(profilebutton, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 480, 210, 90));

        homebutton.setBackground(new java.awt.Color(43, 43, 43));
        homebutton.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        homebutton.setForeground(new java.awt.Color(255, 255, 255));
        homebutton.setText("Home");
        homebutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                homebuttonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                homebuttonMouseExited(evt);
            }
        });
        jPanel2.add(homebutton, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 210, 200, 90));

        historybutton.setBackground(new java.awt.Color(28, 24, 24));
        historybutton.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        historybutton.setForeground(new java.awt.Color(255, 255, 255));
        historybutton.setText("History");
        historybutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                historybuttonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                historybuttonMouseExited(evt);
            }
        });
        jPanel2.add(historybutton, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 390, 190, 90));

        bookingbutton.setBackground(new java.awt.Color(28, 24, 24));
        bookingbutton.setFont(new java.awt.Font("Helvetica Neue", 1, 24)); // NOI18N
        bookingbutton.setForeground(new java.awt.Color(255, 255, 255));
        bookingbutton.setText("Booking");
        bookingbutton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        bookingbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bookingbuttonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bookingbuttonMouseExited(evt);
            }
        });
        bookingbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookingbuttonActionPerformed(evt);
            }
        });
        jPanel2.add(bookingbutton, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 300, 210, 90));
        jPanel2.add(homeicon1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 230, 40, 50));
        jPanel2.add(carconnecticon, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 210, 180));

        jPanel6.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 225, 800));

        userlabel.setFont(new java.awt.Font("Helvetica Neue", 0, 36)); // NOI18N
        jPanel6.add(userlabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 20, 310, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void homebuttonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homebuttonMouseEntered
        homebutton.setBackground(Color.GRAY);
        //texttInbtnHome.setForeground(Color.WHITE);
    }//GEN-LAST:event_homebuttonMouseEntered

    private void homebuttonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homebuttonMouseExited
       homebutton.setBackground(new Color(28,24,24));
       //texttInbtnHome.setForeground(Color.WHITE);
    }//GEN-LAST:event_homebuttonMouseExited

    private void bookingbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookingbuttonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bookingbuttonActionPerformed

    private void bookingbuttonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bookingbuttonMouseEntered
        bookingbutton.setBackground(Color.GRAY);
        
    }//GEN-LAST:event_bookingbuttonMouseEntered

    private void bookingbuttonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bookingbuttonMouseExited
        bookingbutton.setBackground(new Color(28,24,24));
        
    }//GEN-LAST:event_bookingbuttonMouseExited

    private void passwordTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordTextActionPerformed

    private void emailTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailTextActionPerformed

    private void historybuttonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_historybuttonMouseExited
        historybutton.setBackground(new Color(28,24,24));
        //historybutton.setForeground(Color.WHITE);
    }//GEN-LAST:event_historybuttonMouseExited

    private void historybuttonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_historybuttonMouseEntered
        historybutton.setBackground(Color.GRAY);
        //historybutton.setForeground(Color.BLACK);
    }//GEN-LAST:event_historybuttonMouseEntered

    private void profilebuttonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profilebuttonMouseEntered
        profilebutton.setForeground(Color.WHITE);
    }//GEN-LAST:event_profilebuttonMouseEntered

    private void profilebuttonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profilebuttonMouseExited
        profilebutton.setForeground(Color.BLACK);
    }//GEN-LAST:event_profilebuttonMouseExited

    private void changepasswordiconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_changepasswordiconMouseClicked
        dispose();
    new Changepassword(currentUserName);
    }//GEN-LAST:event_changepasswordiconMouseClicked

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bookingbutton;
    private javax.swing.JLabel bookingicon;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel carconnecticon;
    private javax.swing.JLabel changepasswordicon;
    private javax.swing.JTextField emailText;
    private javax.swing.JButton historybutton;
    private javax.swing.JLabel historyicon;
    private javax.swing.JButton homebutton;
    private javax.swing.JLabel homeicon;
    private javax.swing.JLabel homeicon1;
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
    private javax.swing.JTextField passwordText;
    private javax.swing.JTextField phoneText;
    private javax.swing.JButton profilebutton;
    private javax.swing.JLabel profileicon;
    private javax.swing.JLabel texttInbtnEdittext;
    private javax.swing.JLabel userlabel;
    private javax.swing.JTextField usernameText;
    // End of variables declaration//GEN-END:variables
}
