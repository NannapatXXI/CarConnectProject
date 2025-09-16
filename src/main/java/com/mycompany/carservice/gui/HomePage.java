/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.carservice.gui;

import java.awt.Color;
import java.io.File;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.*;
import com.formdev.flatlaf.FlatLightLaf;
import com.mycompany.carservice.entity.CSVHandler;
import com.mycompany.carservice.entity.RoundedPanel;
import java.util.ArrayList;

/**
 *
 * @author nannapat
 */
public class HomePage extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(HomePage.class.getName());
    private final String userName;
    private final String role;

    /**
     * Creates new form mainPage
     * @param userName
     */
    public HomePage(String userName,String role) {
        this.userName = userName ;
        this.role = role;
        initComponents();
        username.setHorizontalAlignment(JLabel.RIGHT);
        SetupUi();
        Announcements();
        setSize(1200, 800);        
        setLocationRelativeTo(null); // จัดกลางหน้าจอ
        setVisible(true);
        welcomeUser.setHorizontalTextPosition(JLabel.LEFT); // ข้อความอยู่ซ้าย, icon อยู่ขวา
        username.setText("User : "+userName);
        welcomeUser.setText(userName+" ");
        SetupIcon();
        
    }
    

    
    private void SetupUi() {
       
        bookingBtn.setContentAreaFilled(false);
        historyBtn.setContentAreaFilled(false);
        profileBtn.setContentAreaFilled(false);
        adminBtn.setContentAreaFilled(false);
    
    }
    private void SetupIcon() {
        try {
                    URL logoIconURL = new File("src/main/image/logoCarConnect.png").toURI().toURL();
                    URL homeIconURL = new File("src/main/image/home.png").toURI().toURL();
                    URL bookingIconURL = new File("src/main/image/booking.png").toURI().toURL();
                    URL historyIconURL = new File("src/main/image/history.png").toURI().toURL();
                    URL profileIconURL = new File("src/main/image/profile.png").toURI().toURL();
                    URL adminIconURL = new File("src/main/image/admin.png").toURI().toURL();
                    URL profileUserIconURL = new File("src/main/image/profileuser.png").toURI().toURL();
                    URL welcomeIconURL = new File("src/main/image/wavinghand.png").toURI().toURL();
                    logo.setIcon(new ImageIcon(logoIconURL));
                    iconHome.setIcon(new ImageIcon(homeIconURL));
                    iconBooking.setIcon(new ImageIcon(bookingIconURL));
                    iconHistory.setIcon(new ImageIcon(historyIconURL));
                    iconProfile.setIcon(new ImageIcon(profileIconURL));
                    iconAdmin.setIcon(new ImageIcon(adminIconURL));
                    iconUserProfile.setIcon(new ImageIcon(profileUserIconURL));
                    welcomeUser.setIcon(new ImageIcon(welcomeIconURL));
                    
                } catch (Exception e) {
                    System.out.println(e);
                    logger.severe("Cannot load icon images");
                }
    
    }
        private void Announcements() {
                // สร้าง object CSVHandler
                CSVHandler csvHandler = new CSVHandler("src/main/data/history_user.csv");
                // อ่านข้อมูลทั้งหมดจาก CSV
                ArrayList<String[]> users = csvHandler.readCSV();
                int processCount = 0, completedCount = 0, userBookingCount =0; // ตัวนับ
                for (String[] parts : users) {
                        // ตรวจสอบว่ามี index หรือไม่
                        if (parts.length >= 8) {
                                String fileProcess = parts[7];
                                String fileUsername = parts[1];
                                System.out.println(fileUsername);
                                if (fileProcess.toLowerCase().contains("process")) {
                                        processCount++;
                                }
                                else if (fileProcess.toLowerCase().contains("completed")) {
                                        completedCount++;
                                }
                                
                                if (fileUsername.toLowerCase().contains(userName.toLowerCase())) {
                                        userBookingCount++;
                                }
                        }
                }
                System.out.println(userName);
                pendinginfo.setText(processCount+"");
                completedinfo.setText(completedCount+"");
                userBookingInfo.setText(userBookingCount+"");
                
//                JTextArea announcementsArea = new JTextArea();
//                announcementsArea.setOpaque(false);
//                announcementsArea.setLineWrap(true);
//                announcementsArea.setWrapStyleWord(true);
//
//                String role = currentUserRole; // Admin / User
//                announcementsArea.setEditable(role.equals("Admin"));
//
//                JScrollPane scrollPane = new JScrollPane(announcementsArea);
//                scrollPane.setOpaque(false);
//                scrollPane.getViewport().setOpaque(false);
        }
    
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
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
        jPanel3 = new javax.swing.JPanel();
        iconUserProfile = new javax.swing.JLabel();
        username = new javax.swing.JLabel();
        welcomeUser = new javax.swing.JLabel();
        jPanel1 = new RoundedPanel(30);
        // 30 radius;
        jLabel6 = new javax.swing.JLabel();
        userBookingInfo = new javax.swing.JLabel();
        jPanel4 = new RoundedPanel(30);// 30 radius;
        jLabel5 = new javax.swing.JLabel();
        completedinfo = new javax.swing.JLabel();
        jPanel5 = new RoundedPanel(30); // 30 radius;
        jLabel7 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel6 = new RoundedPanel(30); // 30 radius;
        jLabel4 = new javax.swing.JLabel();
        pendinginfo = new javax.swing.JLabel();
        jPanel7 = new RoundedPanel(30); // 30 radius;
        jLabel8 = new javax.swing.JLabel();
        newBooking = new javax.swing.JButton();
        viewHistory = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(java.awt.Color.white);
        setLocation(new java.awt.Point(0, 0));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        homeBtn.setBackground(new java.awt.Color(255, 157, 0));
        homeBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        homeBtn.setForeground(new java.awt.Color(255, 255, 255));
        homeBtn.setText(" Home    ");
        homeBtn.setPreferredSize(new java.awt.Dimension(164, 90));
        homeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeBtnActionPerformed(evt);
            }
        });
        jPanel2.add(homeBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 260, 70));

        adminBtn.setBackground(new java.awt.Color(28, 24, 24));
        adminBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        adminBtn.setForeground(new java.awt.Color(204, 204, 204));
        adminBtn.setText("Admin   ");
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

        profileBtn.setBackground(new java.awt.Color(28, 24, 24));
        profileBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        profileBtn.setForeground(new java.awt.Color(204, 204, 204));
        profileBtn.setText("Profile  ");
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
        jPanel2.add(profileBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 260, 70));

        historyBtn.setBackground(new java.awt.Color(28, 24, 24));
        historyBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        historyBtn.setForeground(new java.awt.Color(204, 204, 204));
        historyBtn.setText("History ");
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

        bookingBtn.setBackground(new java.awt.Color(28, 24, 24));
        bookingBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        bookingBtn.setForeground(new java.awt.Color(204, 204, 204));
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
        jPanel2.add(bookingBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 260, 70));

        logo.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 240, 140));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 800));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        iconUserProfile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconUserProfileMouseClicked(evt);
            }
        });
        jPanel3.add(iconUserProfile, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 30, 40, 50));

        username.setBackground(new java.awt.Color(0, 0, 0));
        username.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        username.setText("..");
        jPanel3.add(username, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 40, 180, 40));

        welcomeUser.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        welcomeUser.setText("\"\"");
        welcomeUser.setToolTipText("");
        jPanel3.add(welcomeUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 110, -1, -1));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel6.setText("Bookings");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        userBookingInfo.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        userBookingInfo.setText("2");
        jPanel1.add(userBookingInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 60, 60));

        jPanel3.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 219, 263, 150));

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setText("Completed");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        completedinfo.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        completedinfo.setText("2");
        jPanel4.add(completedinfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 60, 60));

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 387, 263, 150));

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel7.setText("Announcements");
        jPanel5.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));
        jPanel5.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 450, 20));

        jPanel3.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 220, 510, 260));

        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setText("Pending");
        jPanel6.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        pendinginfo.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        pendinginfo.setText("2");
        jPanel6.add(pendinginfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 60, 60));

        jPanel3.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 556, 263, 160));

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel8.setText("Shortcuts");
        jPanel7.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        newBooking.setBackground(new java.awt.Color(255, 157, 0));
        newBooking.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        newBooking.setForeground(new java.awt.Color(255, 255, 255));
        newBooking.setText("New Booking");
        newBooking.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                newBookingMouseClicked(evt);
            }
        });
        newBooking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newBookingActionPerformed(evt);
            }
        });
        jPanel7.add(newBooking, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 440, 40));

        viewHistory.setBackground(new java.awt.Color(255, 157, 0));
        viewHistory.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        viewHistory.setForeground(new java.awt.Color(255, 255, 255));
        viewHistory.setText("View History");
        viewHistory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewHistoryMouseClicked(evt);
            }
        });
        jPanel7.add(viewHistory, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 440, 40));

        jPanel3.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 500, 509, 220));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel9.setText("WELCOME,");
        jLabel9.setToolTipText("");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, -1, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, 980, 800));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void profileBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profileBtnMouseClicked
        dispose();
        new Profile();
    }//GEN-LAST:event_profileBtnMouseClicked

    private void profileBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profileBtnMouseEntered
        profileBtn.setBackground(Color.GRAY);
    }//GEN-LAST:event_profileBtnMouseEntered

    private void profileBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profileBtnMouseExited
        profileBtn.setBackground(new Color(28,24,24));
    }//GEN-LAST:event_profileBtnMouseExited

    private void historyBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_historyBtnMouseClicked
        dispose();
        new History();
    }//GEN-LAST:event_historyBtnMouseClicked

    private void historyBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_historyBtnMouseEntered
        historyBtn.setBackground(Color.GRAY);
    }//GEN-LAST:event_historyBtnMouseEntered

    private void historyBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_historyBtnMouseExited
        historyBtn.setBackground(new Color(28,24,24));
    }//GEN-LAST:event_historyBtnMouseExited

    private void bookingBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bookingBtnMouseClicked
        dispose();
        new BookingPage(userName,role);
    }//GEN-LAST:event_bookingBtnMouseClicked

    private void bookingBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bookingBtnMouseEntered
        bookingBtn.setBackground(Color.GRAY);
    }//GEN-LAST:event_bookingBtnMouseEntered

    private void bookingBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bookingBtnMouseExited
        bookingBtn.setBackground(new Color(28,24,24));
    }//GEN-LAST:event_bookingBtnMouseExited

    private void adminBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminBtnMouseClicked
        dispose();
        new AdminPage(userName,role);
    }//GEN-LAST:event_adminBtnMouseClicked

    private void adminBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminBtnMouseEntered
        adminBtn.setBackground(Color.GRAY);
    }//GEN-LAST:event_adminBtnMouseEntered

    private void adminBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminBtnMouseExited
        adminBtn.setBackground(new Color(28,24,24));
    }//GEN-LAST:event_adminBtnMouseExited

    private void homeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_homeBtnActionPerformed

    private void historyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historyBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_historyBtnActionPerformed

    private void adminBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_adminBtnActionPerformed

    private void iconUserProfileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconUserProfileMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_iconUserProfileMouseClicked

    private void newBookingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newBookingMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_newBookingMouseClicked

    private void viewHistoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewHistoryMouseClicked
        dispose();
        new History();
    }//GEN-LAST:event_viewHistoryMouseClicked

    private void newBookingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newBookingActionPerformed
        dispose();
        new BookingPage(userName,role);
    }//GEN-LAST:event_newBookingActionPerformed

   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton adminBtn;
    private javax.swing.JButton bookingBtn;
    private javax.swing.JLabel completedinfo;
    private javax.swing.JButton historyBtn;
    private javax.swing.JButton homeBtn;
    private javax.swing.JLabel iconAdmin;
    private javax.swing.JLabel iconBooking;
    private javax.swing.JLabel iconHistory;
    private javax.swing.JLabel iconHome;
    private javax.swing.JLabel iconProfile;
    private javax.swing.JLabel iconUserProfile;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JLabel logo;
    private javax.swing.JButton newBooking;
    private javax.swing.JLabel pendinginfo;
    private javax.swing.JButton profileBtn;
    private javax.swing.JLabel userBookingInfo;
    private javax.swing.JLabel username;
    private javax.swing.JButton viewHistory;
    private javax.swing.JLabel welcomeUser;
    // End of variables declaration//GEN-END:variables
}
