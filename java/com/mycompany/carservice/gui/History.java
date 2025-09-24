/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.carservice.gui;
import java.awt.Color;
import javax.swing.*;
import com.formdev.flatlaf.FlatLightLaf;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import com.mycompany.carservice.entity.RoundedPanel;




/**
 *
 * @author User
 */
public class History extends javax.swing.JFrame {
    private final String userName;
    private final String role;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(History.class.getName());
    /**
     * Creates new form History
     */
    public History(String userName,String role) {
        initComponents();
        this.userName = userName ;
        this.role = role;
        SetupIcon();
        setupUi();
        loadHistoryData();
        username.setHorizontalAlignment(JLabel.RIGHT);
        username.setText(userName);
        if ("admin".equalsIgnoreCase(role)) {   
            adminBtn.setVisible(true);
            iconAdmin.setVisible(true); // แสดงเฉพาะ Admin
        } else {
            adminBtn.setVisible(false);
            iconAdmin.setVisible(false);// ซ่อนสำหรับ User ปกติ
        }
        
         //จัดข้อความให้อยู่ตรงกลาง
        ((DefaultTableCellRenderer) jTable1.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);
         // จัดกลางข้อมูลทุกคอลัมน์
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < jTable1.getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        setSize(1200, 800);        
        setLocationRelativeTo(null); // จัดกลางหน้าจอ
        setVisible(true);
        jTable1.setRowHeight(50);

    }
    
    private void setupUi(){
        UIManager.put("Table.selectionBackground", new Color(0, 0, 0));
        UIManager.put("Table.selectionForeground", Color.WHITE);
        UIManager.put("Table.alternateRowColor", Color.GRAY);
        
        homeBtn.setContentAreaFilled(false);
        homeBtn.setBorderPainted(false);
        
        bookingBtn.setContentAreaFilled(false);
        bookingBtn.setBorderPainted(false);

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
                    URL exitIconURL = new File("src/main/image/logout.png").toURI().toURL();
                    
                    logo.setIcon(new ImageIcon(logoIconURL));
                    iconHome.setIcon(new ImageIcon(homeIconURL));
                    iconBooking.setIcon(new ImageIcon(bookingIconURL));
                    iconHistory.setIcon(new ImageIcon(historyIconURL));
                    iconProfile.setIcon(new ImageIcon(profileIconURL));
                    iconAdmin.setIcon(new ImageIcon(adminIconURL));
                    iconExit.setIcon(new ImageIcon(exitIconURL));

                } catch (Exception e) {
                    System.out.println(e);
                    logger.severe("Cannot load icon images");
                }
    
    }
    
    
  private void loadHistoryData() {
    String filePath = "src/main/data/history_user.csv";
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.setRowCount(0);

    String currentUser = username.getText().trim();  // ชื่อผู้ใช้ปัจจุบัน
    int rowNumber = 1;

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;
        boolean firstLine = true;

        while ((line = br.readLine()) != null) {
            if (firstLine) { // ข้าม header
                firstLine = false;
                continue;
            }

            String[] data = line.split(",");

            if (data.length >= 8) {
                String nameInCsv = data[1].replace("\uFEFF", "").trim(); // ใช้ column 1 = Name

                System.out.println("CSV Name: [" + nameInCsv + "]");
                System.out.println("Current User: [" + currentUser + "]");

                if (nameInCsv.equalsIgnoreCase(currentUser)) {
                    Object[] newRow = {
                    rowNumber,        // No (ลำดับในตาราง)
                    nameInCsv,        // Name
                    data[2].trim(),   // Service
                    data[3].trim(),   // Date
                    data[4].trim(),   // Time
                    data[5].trim(),   // Note
                    data[6].trim(),   // Car registration
                    data[7].trim()    // Status
            };
                    model.addRow(newRow);
                    rowNumber++;
                }
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error loading history data");
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel9 = new RoundedPanel(30); // 30 radius;
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        profileuser = new javax.swing.JLabel();
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
        jLabel1 = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1200, 800));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1200, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 107, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(2149, 0, -1, 207));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setForeground(new java.awt.Color(255, 255, 255));
        jPanel7.setPreferredSize(new java.awt.Dimension(1200, 800));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(28, 24, 24));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel7.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 744, -1, 1137));

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Name", "Service", "Date", "Time", "Note", "Car registration", "Status"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 556, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 140, 930, 610));
        jPanel7.add(profileuser, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 10, -1, -1));

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

        profileBtn.setBackground(new java.awt.Color(43, 43, 43));
        profileBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        profileBtn.setForeground(new java.awt.Color(204, 204, 204));
        profileBtn.setText("Profile  ");
        profileBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
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

        historyBtn.setBackground(new java.awt.Color(255, 157, 0));
        historyBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        historyBtn.setForeground(new java.awt.Color(255, 255, 255));
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

        jPanel7.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 800));

        username.setBackground(new java.awt.Color(0, 0, 0));
        username.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        username.setText("..");
        jPanel7.add(username, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 40, 660, 40));

        iconExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconExitMouseClicked(evt);
            }
        });
        jPanel7.add(iconExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 40, 40, 40));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel1.setText("History");
        jPanel7.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 40, -1, -1));

        getContentPane().add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 800));

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

    private void adminBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_adminBtnActionPerformed

    private void profileBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profileBtnMouseClicked
        dispose();
        new Profile(userName,role);
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
      
    }//GEN-LAST:event_historyBtnMouseClicked

    private void historyBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_historyBtnMouseEntered

    }//GEN-LAST:event_historyBtnMouseEntered

    private void historyBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_historyBtnMouseExited

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
        homeBtn.setForeground(Color.WHITE);
    }//GEN-LAST:event_homeBtnMouseEntered

    private void homeBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeBtnMouseExited
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
    private javax.swing.JButton historyBtn;
    private javax.swing.JButton homeBtn;
    private javax.swing.JLabel iconAdmin;
    private javax.swing.JLabel iconBooking;
    private javax.swing.JLabel iconExit;
    private javax.swing.JLabel iconHistory;
    private javax.swing.JLabel iconHome;
    private javax.swing.JLabel iconProfile;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel logo;
    private javax.swing.JButton profileBtn;
    private javax.swing.JLabel profileuser;
    private javax.swing.JLabel username;
    // End of variables declaration//GEN-END:variables
}
