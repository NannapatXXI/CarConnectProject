
package com.mycompany.carservice.gui;

import java.awt.*;
import java.io.File;
import javax.swing.*;
import java.net.URL;
import com.mycompany.carservice.entity.CSVHandler;
import com.mycompany.carservice.entity.RoundedPanel;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * คลาส Login 
 * โดยมีการตรวจสอบชื่อผู้ใช้และรหัสผ่านจากไฟล์ CSV
 * หากเข้าสู่ระบบสำเร็จจะไปยังหน้า HomePage 
 * 
 */
public class Login extends javax.swing.JFrame {
    /**
     *  Login ใช้สำหรับสร้างหน้าต่างล็อกอินของโปรแกรม
     */
    public Login() {
            
            initComponents(); // เรียกใช้ method ที่สร้างและจัดวาง components ทั้งหมดในหน้า
            setSize(1200, 800);   // กำหนดขนาดหน้าต่าง
            setLocationRelativeTo(null); // จัดให้อยู่กึ่งกลางหน้าจอ
            setVisible(true); // แสดงหน้าต่าง Login
            cautionSetup();
            setupUI();
    }
        /**
        * ซ่อนข้อความเตือนทั้งหมดที่เกี่ยวกับการกรอกข้อมูลไม่ถูกต้อง
        */
    private void cautionSetup(){
        // ซ่อนข้อความเตือนของ username และ password ตอนเริ่มต้น
            cautionusername.setVisible(false);
            cautionpass.setVisible(false);
    }
        /**
        * ตั้งค่าไอคอนสำหรับ JLabel ที่เกี่ยวข้องกับช่องกรอกข้อมูลต่างๆ
        */
    private void setupUI(){
        try {
                    // โหลดภาพไอคอนของ username และ password จากโฟลเดอร์
                    URL userIconURL = new File("src/main/image/user.png").toURI().toURL();
                    URL passIconURL = new File("src/main/image/padlock.png").toURI().toURL();
                    URL viewIconURL = new File("src/main/image/view.png").toURI().toURL();
                    // ตั้งค่าไอคอนให้กับ JLabel
                    iconusername.setIcon(new ImageIcon(userIconURL));
                    iconpassword.setIcon(new ImageIcon(passIconURL));
                    eye.setIcon(new ImageIcon(viewIconURL));

                } catch (Exception e) {
                    System.out.println(e);
                }
    }
    /**
     * method CompleteLogin ไว้ตรวจสอบข้อมูลก่อนเข้าสู่ระบบจากไฟล์ CSV
     * ถ้าตรงกันจะเข้าสู่หน้า HomePage ตาม role ของผู้ใช้
     */
    private void CompleteLogin(){
                // ดึงค่าจากช่อง username และ password
                String getUsername = username.getText().trim();
                String getPass = new String(password.getPassword()).trim();
                String getBoth = getUsername + "," + getPass;
                String role = null;
                
                // สร้างออบเจ็กต์ CSVHandler เพื่ออ่านข้อมูลจากไฟล์ user.csv
                CSVHandler csvHandler = new CSVHandler("src/main/data/user.csv");
                ArrayList<String[]> users = csvHandler.readCSV();
                
                //ตัวตรวจสอบการเข้าสู่ระบบ
                boolean loginSuccess = false;
                boolean checkedUsername = false;
                boolean checkedPassword = false;
                
                //ดึงและตรวจสอบข้อมูลในไฟล์ user.csv 
                for (String[] parts : users) {
                    if (parts.length >= 6) {
                        String fileUsername = parts[1];
                        String filePassword = parts[2];
                        role = parts[5];
                        String confirmlogin = fileUsername + "," + filePassword;
                        
                        // ตรวจสอบ username
                        if (getUsername.equals(fileUsername)) {
                            checkedUsername = true;

                            // ตรวจสอบ password เฉพาะเมื่อ username ตรง
                            if (getBoth.equals(confirmlogin)) {
                                checkedPassword = true;
                                loginSuccess = true;
                                break;
                            }
                        }
                    }
                }
                // เช็คว่าผู้ใช้ป้อนชื่อผู้ใช้หรือไม่ และห้ามเป็นช่องว่าง
                if (getUsername.trim().isEmpty()) {
                    cautionusername.setText("Need to input username");
                    cautionusername.setVisible(true);
                }else if(!checkedUsername) {
                    cautionusername.setText("Username not found. Please register.");
                    cautionusername.setVisible(true);
                }else{
                    cautionusername.setText("");
                    cautionusername.setVisible(false);
                }
                
                //เช็ค password ที่ผู้ใช้ป้อนเข้ามาว่าตรงกับ user.csv ไหม
                if (getPass.trim().isEmpty()) {
                    cautionpass.setText("Need to input password");
                    cautionpass.setVisible(true);
                }else if(!checkedPassword) {
                    cautionpass.setText("Incorrect password. Please try again.");
                    cautionpass.setVisible(true);
                }else{
                    cautionpass.setText("");
                    cautionpass.setVisible(false);
                }
                
                //ถ้า loginSuccess เป็น true จะทำให้ คำเตือนไม่โชว์ ปิดหน้าต่าง Login เเล้วส่งข้อมูล username กับ role ไปหน้า Homepage
                if (loginSuccess && checkedUsername && checkedPassword ) {
                    cautionusername.setVisible(false);
                    cautionpass.setVisible(false);
                    password.setText(""); 
                    dispose();
                    new HomePage(getUsername,role);
                    //new BookingPage(getUsername);
                } else {
                    System.out.println("false");
                }  
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jFrame1 = new javax.swing.JFrame();
        jFrame2 = new javax.swing.JFrame();
        jFrame3 = new javax.swing.JFrame();
        jDialog1 = new javax.swing.JDialog();
        jFrame4 = new javax.swing.JFrame();
        jFrame5 = new javax.swing.JFrame();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        logButton = new javax.swing.JButton();
        registerButton = new javax.swing.JButton();
        jPanel2 = new RoundedPanel(30); // 30 radius;
        eye = new javax.swing.JLabel();
        iconpassword = new javax.swing.JLabel();
        iconusername = new javax.swing.JLabel();
        loginFinish = new javax.swing.JButton();
        password = new javax.swing.JPasswordField();
        cautionpass = new javax.swing.JLabel();
        anotherRegister = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cautionusername = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        username = new javax.swing.JTextField();
        repassword = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 394, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 147, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame3Layout = new javax.swing.GroupLayout(jFrame3.getContentPane());
        jFrame3.getContentPane().setLayout(jFrame3Layout);
        jFrame3Layout.setHorizontalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame3Layout.setVerticalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame4Layout = new javax.swing.GroupLayout(jFrame4.getContentPane());
        jFrame4.getContentPane().setLayout(jFrame4Layout);
        jFrame4Layout.setHorizontalGroup(
            jFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame4Layout.setVerticalGroup(
            jFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame5Layout = new javax.swing.GroupLayout(jFrame5.getContentPane());
        jFrame5.getContentPane().setLayout(jFrame5Layout);
        jFrame5Layout.setHorizontalGroup(
            jFrame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame5Layout.setVerticalGroup(
            jFrame5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(43, 43, 43));

        jLabel2.setFont(new java.awt.Font("Gadugi", 1, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("WELCOME");
        jLabel2.setToolTipText("");

        logButton.setBackground(new java.awt.Color(255, 149, 0));
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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(registerButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(logButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(161, 161, 161)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(82, 82, 82)
                .addComponent(logButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(registerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 368, 800));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        eye.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                eyeMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                eyeMouseReleased(evt);
            }
        });
        jPanel2.add(eye, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 300, 50, 40));
        jPanel2.add(iconpassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 290, 50, 60));
        jPanel2.add(iconusername, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, 50, 60));

        loginFinish.setBackground(new java.awt.Color(255, 149, 0));
        loginFinish.setFont(new java.awt.Font("Gadugi", 1, 24)); // NOI18N
        loginFinish.setText("Login");
        loginFinish.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginFinishMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginFinishMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginFinishMouseExited(evt);
            }
        });
        jPanel2.add(loginFinish, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 430, 530, 60));

        password.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        password.setMargin(new java.awt.Insets(2, 55, 2, 6));
        password.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passwordKeyPressed(evt);
            }
        });
        jPanel2.add(password, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 290, 530, 60));

        cautionpass.setForeground(new java.awt.Color(255, 0, 51));
        cautionpass.setText("can't use this password it's less than 8 charactor");
        cautionpass.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel2.add(cautionpass, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 350, -1, -1));

        anotherRegister.setText("have an account? Register");
        anotherRegister.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                anotherRegisterMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                anotherRegisterMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                anotherRegisterMouseExited(evt);
            }
        });
        jPanel2.add(anotherRegister, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 490, -1, -1));

        jLabel5.setText("Password");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, -1, -1));

        cautionusername.setForeground(new java.awt.Color(255, 0, 51));
        cautionusername.setText("can't use this password it's less than 8 charactor");
        cautionusername.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel2.add(cautionusername, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 240, -1, -1));

        jLabel3.setText("Username");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 150, -1, -1));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Gadugi", 1, 48)); // NOI18N
        jLabel1.setText("Login");
        jLabel1.setToolTipText("");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 50, -1, 62));

        username.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        username.setMargin(new java.awt.Insets(2, 55, 2, 6));
        username.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                usernameKeyPressed(evt);
            }
        });
        jPanel2.add(username, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 180, 530, 60));

        repassword.setText("Forgot password?");
        repassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                repasswordMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                repasswordMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                repasswordMouseExited(evt);
            }
        });
        jPanel2.add(repassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 350, -1, -1));

        jPanel3.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 110, 680, 580));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 800));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void anotherRegisterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_anotherRegisterMouseClicked
        dispose();
        new Register();
    }//GEN-LAST:event_anotherRegisterMouseClicked

    private void anotherRegisterMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_anotherRegisterMouseEntered
        anotherRegister.setForeground(Color.GRAY);
    }//GEN-LAST:event_anotherRegisterMouseEntered

    private void anotherRegisterMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_anotherRegisterMouseExited
        anotherRegister.setForeground(Color.BLACK);
    }//GEN-LAST:event_anotherRegisterMouseExited

    private void logButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logButtonMouseClicked
        dispose();
        new Login();
    }//GEN-LAST:event_logButtonMouseClicked

    private void logButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logButtonMouseEntered
        logButton.setForeground(Color.WHITE);
    }//GEN-LAST:event_logButtonMouseEntered

    private void logButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logButtonMouseExited
        logButton.setForeground(Color.BLACK);
    }//GEN-LAST:event_logButtonMouseExited

    private void registerButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerButtonMouseClicked
        dispose();
        new Register();
    }//GEN-LAST:event_registerButtonMouseClicked

    private void registerButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerButtonMouseEntered
        registerButton.setBackground(Color.GRAY);
        registerButton.setForeground(Color.WHITE);
    }//GEN-LAST:event_registerButtonMouseEntered

    private void registerButtonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registerButtonMouseExited
        registerButton.setBackground(Color.WHITE);
        registerButton.setForeground(Color.BLACK);
    }//GEN-LAST:event_registerButtonMouseExited

    private void loginFinishMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginFinishMouseExited
        loginFinish.setForeground(Color.BLACK);
    }//GEN-LAST:event_loginFinishMouseExited

    private void loginFinishMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginFinishMouseEntered
        loginFinish.setForeground(Color.WHITE);
    }//GEN-LAST:event_loginFinishMouseEntered

    private void loginFinishMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_loginFinishMouseClicked
        CompleteLogin();
    }//GEN-LAST:event_loginFinishMouseClicked

    private void repasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repasswordMouseClicked
        new PopConfirm();
    }//GEN-LAST:event_repasswordMouseClicked

    private void repasswordMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repasswordMouseEntered
        repassword.setForeground(Color.GRAY);
    }//GEN-LAST:event_repasswordMouseEntered

    private void repasswordMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repasswordMouseExited
        repassword.setForeground(Color.BLACK);
    }//GEN-LAST:event_repasswordMouseExited

    private void eyeMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eyeMouseReleased
        password.setEchoChar('•');
    }//GEN-LAST:event_eyeMouseReleased

    private void eyeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eyeMousePressed
        password.setEchoChar((char) 0);
    }//GEN-LAST:event_eyeMousePressed

    private void usernameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usernameKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            CompleteLogin();
        }
    }//GEN-LAST:event_usernameKeyPressed

    private void passwordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            CompleteLogin();
        }
    }//GEN-LAST:event_passwordKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel anotherRegister;
    private javax.swing.JLabel cautionpass;
    private javax.swing.JLabel cautionusername;
    private javax.swing.JLabel eye;
    private javax.swing.JLabel iconpassword;
    private javax.swing.JLabel iconusername;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JFrame jFrame3;
    private javax.swing.JFrame jFrame4;
    private javax.swing.JFrame jFrame5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JButton logButton;
    private javax.swing.JButton loginFinish;
    private javax.swing.JPasswordField password;
    private javax.swing.JButton registerButton;
    private javax.swing.JLabel repassword;
    private javax.swing.JTextField username;
    // End of variables declaration//GEN-END:variables
}
