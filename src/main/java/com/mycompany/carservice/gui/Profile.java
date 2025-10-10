/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.carservice.gui;

import java.awt.Color;

import java.io.*;
import java.util.*;
import javax.swing.JTextField;
import javax.swing.text.*;
import com.formdev.flatlaf.FlatLightLaf;
import com.mycompany.carservice.entity.CSVHandler;
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
    private  String userName;
    private String role;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Profile.class.getName());
    private String user;
    private String Tell;
    private String experience;
    private String oldName; // เก็บชื่อเก่าก่อนแก้
    private String currentUserName ;
    
    
    
    /**
     * Creates new form Profile
     */
    public Profile(String userName,String role) {
        initComponents();
        
        passwordText.setEditable(false);     // ปิดการแก้ password
        emailtext.setEditable(false);   // ปิดการแก้ email
        
        this.userName = userName ;
        this.role = role;
        usernamelabel.setHorizontalAlignment(JLabel.RIGHT);
        usernamelabel.setText(userName);
        this.currentUserName = userName;
        profile.setHorizontalAlignment(JLabel.RIGHT);
        loadUserProfile(this.userName);
        ((AbstractDocument) phoneText.getDocument()).setDocumentFilter(new NumberDocumentFilter(10));
        setupui();
        
        setSize(1200, 800);        
        setLocationRelativeTo(null); // จัดกลางหน้าจอ
        setVisible(true);
        
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
                URL usernameIconURL = new File("src/main/image/user.png").toURI().toURL();
                URL passwordIconURL = new File("src/main/image/padlock.png").toURI().toURL();
                URL emailIconURL = new File("src/main/image/email.png").toURI().toURL();
                URL phoneIconURL = new File("src/main/image/phone-call.png").toURI().toURL();
                URL profiletextIconURL = new File("src/main/image/profilemain.png").toURI().toURL();
                
                //profileicon.setIcon(new ImageIcon(profiletextIconURL));
                phoneicon.setIcon(new ImageIcon(phoneIconURL));
                emailicon.setIcon(new ImageIcon(emailIconURL));
                usernameicon.setIcon(new ImageIcon(usernameIconURL));
                passwordicon.setIcon(new ImageIcon(passwordIconURL));
                changepasswordicon.setIcon(new ImageIcon(changepasswordIconURL));
                logo.setIcon(new ImageIcon(logoIconURL));
                iconHome.setIcon(new ImageIcon(homeIconURL));
                iconBooking.setIcon(new ImageIcon(bookingIconURL));
                iconHistory.setIcon(new ImageIcon(historyIconURL));
                iconProfile.setIcon(new ImageIcon(profileIconURL));
                iconAdmin.setIcon(new ImageIcon(adminIconURL));
                iconExit.setIcon(new ImageIcon(exitIconURL));
                
                profile.setIcon(new ImageIcon(profiletextIconURL));

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
// โหลดข้อมูลโปรไฟล์ของผู้ใช้จากไฟล์ user.csv
private void loadUserProfile(String username) {
    CSVHandler csv = new CSVHandler("src/main/data/user.csv");
        ArrayList<String[]> users = csv.readCSV();

        for (String[] row : users) { //วนลูปไปทีละแถว
            if (row.length > 1 && row[1].equalsIgnoreCase(username)) {
                oldName = row[1];
                usernameText.setText(row[1]);
                passwordText.setText("•".repeat(row[2].length())); // ซ่อน password ด้วยจุด
                emailtext.setText(row[3]);
                phoneText.setText(row[4]);
                break;
            }
        }

        usernamelabel.setText(username);
    }
private void updateFirstRowCSV(String name, String newName,  String newPhone) {
    CSVHandler csv = new CSVHandler("src/main/data/user.csv");
        ArrayList<String[]> users = csv.readCSV();

        for (String[] row : users) {
            if (row[1].equals(oldName)) {
                row[1] = newName;
                row[4] = newPhone;
                break;
            }
        }

        csv.writeCSV(users);
        oldName = newName;
        logger.info("Profile updated for " + newName);
}

class NumberDocumentFilter extends DocumentFilter {
   private final int maxLength;

        public NumberDocumentFilter(int maxLength) {
            this.maxLength = maxLength;
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            //ถ้า text ไม่ว่าง, มีแต่ตัวเลข, และความยาวรวมไม่เกิน maxLength
            if (text != null && text.matches("\\d+") &&
                fb.getDocument().getLength() - length + text.length() <= maxLength) {
                super.replace(fb, offset, length, text, attrs);     // ยอมให้แทนที่ข้อความ
            }
        }
    
}
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel6 = new javax.swing.JPanel();
        emaillabel = new RoundedPanel(30); // 30 radius;
        passwordicon = new javax.swing.JLabel();
        usernameicon = new javax.swing.JLabel();
        usernameText = new javax.swing.JTextField();
        emailicon = new javax.swing.JLabel();
        changepasswordicon = new javax.swing.JLabel();
        emailtext = new javax.swing.JTextField();
        phoneicon = new javax.swing.JLabel();
        phoneText = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        savebutton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        passwordText = new javax.swing.JTextField();
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
        usernamelabel = new javax.swing.JLabel();
        iconExit = new javax.swing.JLabel();
        profile = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        emaillabel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        emaillabel.add(passwordicon, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 150, 70, 60));
        emaillabel.add(usernameicon, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 70, 70, 60));

        usernameText.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        usernameText.setActionCommand("<Not Set>");
        usernameText.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        usernameText.setMargin(new java.awt.Insets(2, 55, 5, 6));
        usernameText.setName(""); // NOI18N
        emaillabel.add(usernameText, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 70, 650, 63));
        emaillabel.add(emailicon, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 230, 70, 60));

        changepasswordicon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changepasswordiconMouseClicked(evt);
            }
        });
        emaillabel.add(changepasswordicon, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 160, 60, 40));

        emailtext.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        emailtext.setMargin(new java.awt.Insets(2, 55, 2, 6));
        emaillabel.add(emailtext, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 230, 650, 60));
        emaillabel.add(phoneicon, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 310, 80, 60));

        phoneText.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        phoneText.setMargin(new java.awt.Insets(2, 55, 2, 6));
        emaillabel.add(phoneText, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 310, 650, 60));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Username :");
        emaillabel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 190, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("password :");
        emaillabel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 200, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("phone :");
        emaillabel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 320, 130, -1));

        savebutton.setBackground(new java.awt.Color(255, 157, 0));
        savebutton.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        savebutton.setForeground(new java.awt.Color(255, 255, 255));
        savebutton.setText("Save");
        savebutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                savebuttonMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                savebuttonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                savebuttonMouseExited(evt);
            }
        });
        emaillabel.add(savebutton, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 390, 110, 40));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Email :");
        emaillabel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 240, 120, -1));

        passwordText.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        passwordText.setMargin(new java.awt.Insets(2, 55, 2, 6));
        emaillabel.add(passwordText, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, 650, 60));

        jPanel6.add(emaillabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 210, 880, 460));

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
        logo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoMouseClicked(evt);
            }
        });
        jPanel2.add(logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 240, 140));

        jPanel6.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 800));

        usernamelabel.setBackground(new java.awt.Color(0, 0, 0));
        usernamelabel.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        usernamelabel.setText("..");
        jPanel6.add(usernamelabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 40, 660, 40));

        iconExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconExitMouseClicked(evt);
            }
        });
        jPanel6.add(iconExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 40, 40, 40));

        profile.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        profile.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        profile.setText("PROFILE");
        profile.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel6.add(profile, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 110, 290, 80));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 1200, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
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

    private void savebuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_savebuttonMouseClicked
    String newName = usernameText.getText();
    String newPassword = emailtext.getText();
    String newPhone = phoneText.getText();

    updateFirstRowCSV(oldName, newName, newPhone);

    // อัปเดตตัวแปรสำหรับรอบต่อไป
    oldName = newName; 
    currentUserName = newName;

    System.out.println("Name : " + newName);
    System.out.println("Password : " + newPassword);     
    System.out.println("Phone : " + newPhone);
    System.out.println("User : " + currentUserName);
    new PopSuccess(null,true," Success");

    // เปิดหน้า Profile ใหม่
    new Profile(currentUserName, role);
    this.dispose(); // ปิดหน้าปัจจุบัน
    }//GEN-LAST:event_savebuttonMouseClicked

    private void savebuttonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_savebuttonMouseEntered
        savebutton.setBackground(Color.GRAY);
        savebutton.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_savebuttonMouseEntered

    private void savebuttonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_savebuttonMouseExited
        savebutton.setBackground(new Color(255,157,0));
        savebutton.setForeground(new Color(255,255,255));
    }//GEN-LAST:event_savebuttonMouseExited

    private void changepasswordiconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_changepasswordiconMouseClicked
        new Changepassword(userName, role);
    }//GEN-LAST:event_changepasswordiconMouseClicked

    private void logoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoMouseClicked
        dispose();
        new HomePage(userName,role);
    }//GEN-LAST:event_logoMouseClicked

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton adminBtn;
    private javax.swing.JButton bookingBtn;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel changepasswordicon;
    private javax.swing.JLabel emailicon;
    private javax.swing.JPanel emaillabel;
    private javax.swing.JTextField emailtext;
    private javax.swing.JButton historyBtn;
    private javax.swing.JButton homeBtn;
    private javax.swing.JLabel iconAdmin;
    private javax.swing.JLabel iconBooking;
    private javax.swing.JLabel iconExit;
    private javax.swing.JLabel iconHistory;
    private javax.swing.JLabel iconHome;
    private javax.swing.JLabel iconProfile;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel logo;
    private javax.swing.JTextField passwordText;
    private javax.swing.JLabel passwordicon;
    private javax.swing.JTextField phoneText;
    private javax.swing.JLabel phoneicon;
    private javax.swing.JLabel profile;
    private javax.swing.JButton profileBtn;
    private javax.swing.JButton savebutton;
    private javax.swing.JTextField usernameText;
    private javax.swing.JLabel usernameicon;
    private javax.swing.JLabel usernamelabel;
    // End of variables declaration//GEN-END:variables
}
