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
import java.awt.Font;
import java.io.*;
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
        bookingAn.setHorizontalTextPosition(JLabel.LEFT); // ข้อความอยู่ซ้าย, icon อยู่ขวา
        completedAn.setHorizontalTextPosition(JLabel.LEFT); // ข้อความอยู่ซ้าย, icon อยู่ขวา
        pendingAn.setHorizontalTextPosition(JLabel.LEFT); // ข้อความอยู่ซ้าย, icon อยู่ขวา
        announcements.setHorizontalTextPosition(JLabel.LEFT); // ข้อความอยู่ซ้าย, icon อยู่ขวา
        shortcuts.setHorizontalTextPosition(JLabel.LEFT); // ข้อความอยู่ซ้าย, icon อยู่ขวา
        username.setText("User : "+userName);
        welcomeUser.setText(userName+" ");
        SetupIcon();
        
        String announcement = loadAnnouncementForLabel();   // เพิ่ม
        announcementsLabel.setText(announcement);   // เพิ่ม
        announcementsLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));   // เดิม
        announcementsLabel.setForeground(Color.BLACK);
        announcementsLabel.setOpaque(false);

        if ("admin".equalsIgnoreCase(role)) {   
            fixAnnouncement.setVisible(true); 
            adminBtn.setVisible(true);
            iconAdmin.setVisible(true); // แสดงเฉพาะ Admin
        } else {
            fixAnnouncement.setVisible(false);
            adminBtn.setVisible(false);
            iconAdmin.setVisible(false);// ซ่อนสำหรับ User ปกติ
        }
    }
    

    
    private void SetupUi() {
        bookingBtn.setContentAreaFilled(false);
        bookingBtn.setBorderPainted(false);

        historyBtn.setContentAreaFilled(false);
        historyBtn.setBorderPainted(false);

        profileBtn.setContentAreaFilled(false);
        profileBtn.setBorderPainted(false);

        adminBtn.setContentAreaFilled(false);
        adminBtn.setBorderPainted(false);

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
                    URL fixIconURL = new File("src/main/image/settings.png").toURI().toURL();
                    URL bookingannIconURL = new File("src/main/image/bookingann.png").toURI().toURL();
                    URL completedURL = new File("src/main/image/checked.png").toURI().toURL();
                    URL pendingIconURL = new File("src/main/image/file.png").toURI().toURL();
                    URL announcementIconURL = new File("src/main/image/megaphone.png").toURI().toURL();
                    URL shortcutsIconURL = new File("src/main/image/export.png").toURI().toURL();
                    logo.setIcon(new ImageIcon(logoIconURL));
                    iconHome.setIcon(new ImageIcon(homeIconURL));
                    iconBooking.setIcon(new ImageIcon(bookingIconURL));
                    iconHistory.setIcon(new ImageIcon(historyIconURL));
                    iconProfile.setIcon(new ImageIcon(profileIconURL));
                    iconAdmin.setIcon(new ImageIcon(adminIconURL));
                    iconUserProfile.setIcon(new ImageIcon(profileUserIconURL));
                    welcomeUser.setIcon(new ImageIcon(welcomeIconURL));
                    fixAnnouncement.setIcon(new ImageIcon(fixIconURL));
                    bookingAn.setIcon(new ImageIcon(bookingannIconURL));
                    completedAn.setIcon(new ImageIcon(completedURL));
                    pendingAn.setIcon(new ImageIcon(pendingIconURL));
                    announcements.setIcon(new ImageIcon(announcementIconURL));
                    shortcuts.setIcon(new ImageIcon(shortcutsIconURL));
                    
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

        }
        private String loadAnnouncementForLabel() {
                StringBuilder announcement = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new FileReader("src/main/data/announcement.csv"))) {
                    String line = br.readLine(); // ข้าม header
                    while ((line = br.readLine()) != null) {
                        if (announcement.length() > 0) {
                            announcement.append("<br>"); // ใช้ <br> แทน \n
                        }
                        announcement.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String result = announcement.length() > 0 ? announcement.toString() : "ยังไม่มีประกาศ";
                return "<html>" + result + "</html>"; // เพิ่ม <html> ให้ JLabel แสดงหลายบรรทัด
        }


    // ✅ เซฟข้อความใหม่ลง CSV
    private void saveAnnouncement(String newMessage) {   // เพิ่ม
        try (PrintWriter pw = new PrintWriter(new FileWriter("src/main/data/announcement.csv"))) {
            pw.println("message");
            pw.println(newMessage);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // ✅ Dialog สำหรับแก้ไขประกาศ

        private void openEditAnnouncementDialog() {
    // ลบ <html> และ <br> ออก เพื่อได้ข้อความดิบ
        String currentMessage = announcementsLabel.getText()
                .replaceAll("(?i)<html>", "")
                .replaceAll("(?i)</html>", "")
                .replaceAll("(?i)<br>", "\n");

        JTextArea textArea = new JTextArea(currentMessage, 5, 30);
        textArea.setFont(new Font("Tahoma", Font.PLAIN, 16));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);

        int option = JOptionPane.showConfirmDialog(
                this,
                scrollPane,
                "Edit announcement",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option == JOptionPane.OK_OPTION) {
                String newMessage = textArea.getText();
                if (newMessage != null && !newMessage.trim().isEmpty()) {
                    // แสดงใน JLabel แบบหลายบรรทัด
                    announcementsLabel.setText("<html>" + newMessage.replaceAll("\n", "<br>") + "</html>");
                    saveAnnouncement(newMessage);
                }
        }
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
        bookingAn = new javax.swing.JLabel();
        userBookingInfo = new javax.swing.JLabel();
        jPanel4 = new RoundedPanel(30);// 30 radius;
        completedAn = new javax.swing.JLabel();
        completedinfo = new javax.swing.JLabel();
        jPanel5 = new RoundedPanel(30); // 30 radius;
        fixAnnouncement = new javax.swing.JLabel();
        announcements = new javax.swing.JLabel();
        announcementsLabel = new javax.swing.JLabel();
        jPanel6 = new RoundedPanel(30); // 30 radius;
        pendingAn = new javax.swing.JLabel();
        pendinginfo = new javax.swing.JLabel();
        jPanel7 = new RoundedPanel(30); // 30 radius;
        shortcuts = new javax.swing.JLabel();
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
        jPanel3.add(iconUserProfile, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 40, 40, 40));

        username.setBackground(new java.awt.Color(0, 0, 0));
        username.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        username.setText("..");
        jPanel3.add(username, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 820, 40));

        welcomeUser.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        welcomeUser.setText("\"\"");
        welcomeUser.setToolTipText("");
        jPanel3.add(welcomeUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 110, -1, -1));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        bookingAn.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        bookingAn.setText("Bookings ");
        jPanel1.add(bookingAn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        userBookingInfo.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        userBookingInfo.setText("2");
        jPanel1.add(userBookingInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 60, 60));

        jPanel3.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 219, 263, 150));

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        completedAn.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        completedAn.setText("Completed ");
        jPanel4.add(completedAn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        completedinfo.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        completedinfo.setText("2");
        jPanel4.add(completedinfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 60, 60));

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 387, 263, 150));

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        fixAnnouncement.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                fixAnnouncementMouseClicked(evt);
            }
        });
        jPanel5.add(fixAnnouncement, new org.netbeans.lib.awtextra.AbsoluteConstraints(467, 10, 40, 40));

        announcements.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        announcements.setText("Announcements");
        jPanel5.add(announcements, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        announcementsLabel.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        announcementsLabel.setText(".");
        announcementsLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel5.add(announcementsLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 450, 170));

        jPanel3.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 220, 510, 260));

        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pendingAn.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        pendingAn.setText("Pending ");
        jPanel6.add(pendingAn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        pendinginfo.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        pendinginfo.setText("2");
        jPanel6.add(pendinginfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 60, 60));

        jPanel3.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 556, 263, 160));

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        shortcuts.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        shortcuts.setText("Shortcuts ");
        jPanel7.add(shortcuts, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

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
        //profileBtn.setBackground(Color.GRAY);
        profileBtn.setForeground(Color.WHITE);
    }//GEN-LAST:event_profileBtnMouseEntered

    private void profileBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profileBtnMouseExited
        //profileBtn.setBackground(new Color(28,24,24));
        profileBtn.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_profileBtnMouseExited

    private void historyBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_historyBtnMouseClicked
        dispose();
        new History();
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
        dispose();
        new Profile();
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

    private void fixAnnouncementMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fixAnnouncementMouseClicked
        openEditAnnouncementDialog();
    }//GEN-LAST:event_fixAnnouncementMouseClicked

   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton adminBtn;
    private javax.swing.JLabel announcements;
    private javax.swing.JLabel announcementsLabel;
    private javax.swing.JLabel bookingAn;
    private javax.swing.JButton bookingBtn;
    private javax.swing.JLabel completedAn;
    private javax.swing.JLabel completedinfo;
    private javax.swing.JLabel fixAnnouncement;
    private javax.swing.JButton historyBtn;
    private javax.swing.JButton homeBtn;
    private javax.swing.JLabel iconAdmin;
    private javax.swing.JLabel iconBooking;
    private javax.swing.JLabel iconHistory;
    private javax.swing.JLabel iconHome;
    private javax.swing.JLabel iconProfile;
    private javax.swing.JLabel iconUserProfile;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JLabel pendingAn;
    private javax.swing.JLabel pendinginfo;
    private javax.swing.JButton profileBtn;
    private javax.swing.JLabel shortcuts;
    private javax.swing.JLabel userBookingInfo;
    private javax.swing.JLabel username;
    private javax.swing.JButton viewHistory;
    private javax.swing.JLabel welcomeUser;
    // End of variables declaration//GEN-END:variables
}
