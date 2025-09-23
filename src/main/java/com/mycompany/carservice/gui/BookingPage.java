/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.carservice.gui;

import com.mycompany.carservice.entity.RoundedPanel;
import com.mycompany.carservice.entity.CSVHandler;
import java.awt.Color;
import java.awt.event.*;
import java.util.Calendar;
import javax.swing.*;
import java.awt.Font;

import java.time.*;

import java.awt.Dimension;
import java.io.File;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class BookingPage extends javax.swing.JFrame  {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(BookingPage.class.getName());
    private HashMap<LocalDate, Integer> bookingCountMap = new HashMap<>();
    private int selectedDay = -1;
    private int selectedTime = -1;
    private int selectedService = -1;
    private String userName ;
     private String role ;
     private int month;
    // เพิ่มตัวแปรไว้เก็บปุ่มล่าสุดที่เลือก
    private JButton lastSelectedButton = null;
   
 
    public BookingPage(String user,String role) {
        this.userName = user;
         this.role = role;
        
        initComponents();
        SetupUi();
        SetupIcon();
       getContentPane().setBackground(java.awt.Color.WHITE);

        setSize(1200, 825);        
        setLocationRelativeTo(null); 
        setVisible(true);
        
        username.setText(user);
        
        LocalDate today = LocalDate.now();
        int monthValue = today.getMonthValue();
        int yearValue = today.getYear();
        int dayValue = today.getDayOfMonth();

        monthComboBox1.setSelectedIndex(monthValue - 1);
        yearComboBox.setSelectedItem(String.valueOf(yearValue));

     
        calendarPanel.setLayout(new java.awt.GridLayout(0, 7,5, 5));
        loadBookingCount();
        updateCalendar(); 
    }
    
      private void SetupUi() {
       
        homeBtn.setBorderPainted(false); 
        adminBtn.setBorderPainted(false); 
        historyBtn.setBorderPainted(false); 
        profileBtn.setBorderPainted(false); 
         if(role.equals("admin")){
             System.out.println("Admin !!!!");
        }else{
             System.out.println("User !!!!");
             adminBtn.setVisible(false);  // user ซ่อนปุ่ม

        }
        
        
    
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
                   
                    logo.setIcon(new ImageIcon(logoIconURL));
                    iconHome.setIcon(new ImageIcon(homeIconURL));
                    iconBooking.setIcon(new ImageIcon(bookingIconURL));
                    iconHistory.setIcon(new ImageIcon(historyIconURL));
                    iconProfile.setIcon(new ImageIcon(profileIconURL));
                    iconAdmin.setIcon(new ImageIcon(adminIconURL));
                    iconUserProfile.setIcon(new ImageIcon(profileUserIconURL));
                   
                    
                } catch (Exception e) {
                    System.out.println(e);
                   
                }
    
    }
    
    
 
    
public void loadBookingCount() {
        CSVHandler handler = new CSVHandler("src/main/data/history_user.csv");
        ArrayList<String[]> data = handler.readCSV();

        bookingCountMap.clear();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy"); // "1 September 2025"

        for (int i = 0; i < data.size(); i++) {
            String[] row = data.get(i);
            if (row.length < 4) continue; // เช็คว่ามีวันที่

            String dateStr = row[3].trim(); // column Date
            try {
                LocalDate date = LocalDate.parse(dateStr, formatter);
                bookingCountMap.put(date, bookingCountMap.getOrDefault(date, 0) + 1);
            } catch (Exception e) {
                // ข้ามวันที่ไม่ถูกต้อง
            }
        }
    }

      
    private void saveBookingCount() {
    CSVHandler handler = new CSVHandler("src/main/data/booking_count.csv");
    ArrayList<String[]> data = new ArrayList<>();

    // เพิ่ม header
    data.add(new String[] { "date", "count" });

    // เพิ่มข้อมูลทั้งหมดจาก bookingCountMap
    for (LocalDate date : bookingCountMap.keySet()) {
        int count = bookingCountMap.get(date);
        data.add(new String[] { date.toString(), String.valueOf(count) });
    }

    handler.writeCSV(data); // สมมติว่า CSVHandler มี method writeCSV
}
    
    
    private void updateCalendar() {
        
        calendarPanel.removeAll(); 
    selectedDay = -1;

    LocalDate today = LocalDate.now(); 
    
    // ดึงเดือน/ปีจาก combobox
    int monthIndex = monthComboBox1.getSelectedIndex() + 1;
    int year = Integer.parseInt(yearComboBox.getSelectedItem().toString());

    YearMonth yearMonth = YearMonth.of(year, monthIndex);//ดึงปีและวันที่เลือกใน combobox
    int daysInMonth = yearMonth.lengthOfMonth();

    // หาว่าวันที่ 1 ตรงกับวันอะไร
    LocalDate firstDayOfMonth = yearMonth.atDay(1);//สร้างวันที่ 1 ของเดือนนั้น
    DayOfWeek firstDayOfWeek = firstDayOfMonth.getDayOfWeek();//เป็นวันอะไรในสัปดาห์

    
    int shift = firstDayOfWeek.getValue(); //เอาไว้บอกว่าเดือนนั้นวันที่ 1 ของเดือนเริ่มตรงไหน
    System.out.println("shift : "+ shift);
 
    // เติมช่องว่างก่อนวันจริง
    for (int i = 1; i < shift; i++) {
        calendarPanel.add(new JLabel("")); // ช่องว่าง
    }

    // เติมปุ่มวัน
    for (int i = 1; i <= daysInMonth; i++) {
            final int day = i;
            JButton dayButton = new JButton(String.valueOf(day));

            LocalDate buttonDate = LocalDate.of(year, monthIndex, day);
            int booked = bookingCountMap.getOrDefault(buttonDate, 0);

            dayButton.setPreferredSize(new Dimension(20, 20));
            dayButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
            dayButton.setFocusPainted(false);
            dayButton.setBorderPainted(false);
            dayButton.setContentAreaFilled(true);
            dayButton.setOpaque(true);
             System.out.println("จำนวน "+ booked);
           
            // ถ้าวันนี้ผ่านมาแล้ว
            if (buttonDate.isBefore(today)) {
                dayButton.setBackground(Color.LIGHT_GRAY);
                dayButton.setForeground(Color.BLACK);

                // ป้องกันไม่ให้เลือกวันเก่า
                dayButton.addActionListener(e -> {
                    JOptionPane.showMessageDialog(this, "ไม่สามารถจองวันผ่านมาแล้วได้");
                    // ไม่เปลี่ยนสี
                });
            } else if (booked >= 20) {
                dayButton.setBackground(Color.RED);
                dayButton.setForeground(Color.WHITE);

                dayButton.addActionListener(e -> {
                    JOptionPane.showMessageDialog(this, "วันนี้จองเต็มแล้ว");
                });
            } else if (booked >= 15) {
                dayButton.setBackground(Color.ORANGE);
                dayButton.setForeground(Color.BLACK);
            } else if (booked >= 10) {
                dayButton.setBackground(Color.YELLOW);
                dayButton.setForeground(Color.BLACK);
            } else if (booked >= 5) {
                dayButton.setBackground(Color.CYAN);
                dayButton.setForeground(Color.BLACK);
            } else {
                dayButton.setBackground(Color.WHITE);
                dayButton.setForeground(Color.BLACK);
            }

            // เพิ่ม ActionListener สำหรับวันที่เลือกได้เท่านั้น
            if (!buttonDate.isBefore(today) && booked < 20) {
                dayButton.addActionListener(e -> {
                    selectedDay = day;

                    if (lastSelectedButton != null) {
                        // คืนค่าสีปุ่มเก่า
                        int lastBooked = bookingCountMap.getOrDefault(
                            LocalDate.of(year, monthIndex, Integer.parseInt(lastSelectedButton.getText())), 0
                        );
                        if (lastBooked >= 20) {
                            lastSelectedButton.setBackground(Color.RED);
                        } else if (lastBooked >= 15) {
                            lastSelectedButton.setBackground(Color.ORANGE);
                        } else if (lastBooked >= 10) {
                            lastSelectedButton.setBackground(Color.YELLOW);
                        } else if (lastBooked >= 5) {
                            lastSelectedButton.setBackground(Color.CYAN);
                        } else {
                            lastSelectedButton.setBackground(Color.WHITE);
                        }
                        lastSelectedButton.setForeground(Color.BLACK);
                    }

                    dayButton.setBackground(Color.GREEN); // ปุ่มที่เลือกตอนนี้
                    dayButton.setForeground(Color.BLACK);
                    lastSelectedButton = dayButton;

                    String monthSelected = (String) monthComboBox1.getSelectedItem();
                    String yearSelected = (String) yearComboBox.getSelectedItem();
                    String dayString = String.valueOf(selectedDay);

                    new ShowDeteilDay(this, true, dayString, monthSelected, yearSelected,userName);

                });
            }

       
            calendarPanel.add(dayButton);
        
        }

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }


   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        confirmBtn = new javax.swing.JButton();
        monthLabel = new javax.swing.JLabel();
        jPanel3 = new RoundedPanel(30); // 30 radius;
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        monthComboBox1 = new javax.swing.JComboBox<>();
        monthLabel3 = new javax.swing.JLabel();
        yearComboBox = new javax.swing.JComboBox<>();
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
        iconUserProfile = new javax.swing.JLabel();
        jPanel1 = new RoundedPanel(30); // 30 radius;
        calendarPanel = new javax.swing.JPanel();
        afterMonth = new javax.swing.JButton();
        beforeMonth = new javax.swing.JButton();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setPreferredSize(new java.awt.Dimension(1200, 800));

        confirmBtn.setText("Confirm");
        confirmBtn.setToolTipText("");
        confirmBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmBtnActionPerformed(evt);
            }
        });

        monthLabel.setBackground(new java.awt.Color(255, 255, 255));
        monthLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        monthLabel.setText("Year :");

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Monday");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 70, 40));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Tuesday");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Wednesday");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Thursday");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Friday");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 10, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Saturday");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 10, -1, -1));

        jLabel8.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel8.setText("Sunday");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 10, -1, -1));

        jPanel4.setBackground(new java.awt.Color(51, 255, 0));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        monthComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        monthComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthComboBox1ActionPerformed(evt);
            }
        });

        monthLabel3.setBackground(new java.awt.Color(255, 255, 255));
        monthLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        monthLabel3.setText("   Month :");

        yearComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2025", "2026", "2027", "2028" }));
        yearComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearComboBoxActionPerformed(evt);
            }
        });

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
        homeBtn.setForeground(new java.awt.Color(255, 255, 255));
        homeBtn.setText(" Home    ");
        homeBtn.setPreferredSize(new java.awt.Dimension(164, 90));
        homeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                homeBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                homeBtnMouseExited(evt);
            }
        });
        homeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeBtnActionPerformed(evt);
            }
        });
        jPanel2.add(homeBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 260, 70));

        adminBtn.setBackground(new java.awt.Color(43, 43, 43));
        adminBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        adminBtn.setForeground(new java.awt.Color(255, 255, 255));
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
        jPanel2.add(adminBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 480, 270, 70));

        profileBtn.setBackground(new java.awt.Color(43, 43, 43));
        profileBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        profileBtn.setForeground(new java.awt.Color(255, 255, 255));
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

        historyBtn.setBackground(new java.awt.Color(43, 43, 43));
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

        bookingBtn.setBackground(new java.awt.Color(255, 157, 0));
        bookingBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        bookingBtn.setForeground(new java.awt.Color(255, 255, 255));
        bookingBtn.setText("Booking");
        bookingBtn.setPreferredSize(new java.awt.Dimension(164, 90));
        bookingBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookingBtnActionPerformed(evt);
            }
        });
        jPanel2.add(bookingBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 260, 70));

        logo.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 240, 140));

        username.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        username.setText("....");

        jPanel1.setBackground(new java.awt.Color(240, 240, 240));

        calendarPanel.setBackground(new java.awt.Color(240, 240, 240));
        calendarPanel.setForeground(new java.awt.Color(51, 51, 0));
        calendarPanel.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(calendarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 913, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(calendarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        afterMonth.setText(">");
        afterMonth.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                afterMonthMouseClicked(evt);
            }
        });

        beforeMonth.setText("<");
        beforeMonth.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                beforeMonthMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(260, 260, 260)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(confirmBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                        .addContainerGap(21, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(monthLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(monthComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(monthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(yearComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(253, 253, 253)
                        .addComponent(iconUserProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(beforeMonth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(afterMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21))
                            .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(iconUserProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(monthLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(monthComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(monthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yearComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(afterMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(beforeMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(confirmBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void confirmBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmBtnActionPerformed
      /* 
        
        AlertManager manager = new AlertManager();
        PopAlert alert = new PopAlert(this, true); // ใช้ dialog เดิม
        manager.registerObserver(alert);

        // ตรวจสอบวัน/เวลา/บริการ
        manager.checkBooking(selectedDay, selectedTime, selectedService);

        // ถ้าเลือกครบก็ทำขั้นตอนจองปกติ
        if (selectedDay > 0 && selectedTime > 0 && selectedService > 0) {
            String monthSelected = (String) monthComboBox1.getSelectedItem();
             String yearSelected = (String) yearComboBox.getSelectedItem();
            String timeSelected = (String) timeComboBox.getSelectedItem();
           
            int daySelected = selectedDay;
            String service = (String) serviceComboBox1.getSelectedItem();

            PopInBooking dialog = new PopInBooking(this, true, userName);
            dialog.setDayLabel(daySelected + " " + monthSelected);
            dialog.setTimeLabel(timeSelected);
            dialog.setServiceLabel(service);
            dialog.setYearLabel(yearSelected);
            dialog.setVisible(true);

            LocalDate selectedDate = LocalDate.of(
                Integer.parseInt(yearComboBox.getSelectedItem().toString()),
                monthComboBox1.getSelectedIndex() + 1,
                selectedDay
            );

            // เพิ่มจำนวนจอง
            int currentCount = bookingCountMap.getOrDefault(selectedDate, 0);
            bookingCountMap.put(selectedDate, currentCount + 1);

            saveBookingCount();
            updateCalendar();
        }
*/
    }//GEN-LAST:event_confirmBtnActionPerformed

    private void monthComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthComboBox1ActionPerformed
             updateCalendar();
             // ยังไม่ได้เลือกวัน -> ส่ง -1
           
    }//GEN-LAST:event_monthComboBox1ActionPerformed

    private void yearComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearComboBoxActionPerformed
       updateCalendar();
     
    }//GEN-LAST:event_yearComboBoxActionPerformed

    private void homeBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeBtnMouseEntered
        homeBtn.setBackground(Color.GRAY);
    }//GEN-LAST:event_homeBtnMouseEntered

    private void homeBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeBtnMouseExited
        homeBtn.setBackground(new Color(43,43,43));
    }//GEN-LAST:event_homeBtnMouseExited

    private void homeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeBtnActionPerformed
        dispose();
        new HomePage(userName,role);
    }//GEN-LAST:event_homeBtnActionPerformed

    private void profileBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profileBtnMouseClicked
        dispose();
        new Profile();
    }//GEN-LAST:event_profileBtnMouseClicked

    private void profileBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profileBtnMouseEntered
        profileBtn.setBackground(Color.GRAY);
    }//GEN-LAST:event_profileBtnMouseEntered

    private void profileBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profileBtnMouseExited
        profileBtn.setBackground(new Color(43,43,43));
    }//GEN-LAST:event_profileBtnMouseExited

    private void historyBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_historyBtnMouseClicked
        dispose();
        new History();
    }//GEN-LAST:event_historyBtnMouseClicked

    private void historyBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_historyBtnMouseEntered
        historyBtn.setBackground(Color.GRAY);
    }//GEN-LAST:event_historyBtnMouseEntered

    private void historyBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_historyBtnMouseExited
        historyBtn.setBackground(new Color(43,43,43));
    }//GEN-LAST:event_historyBtnMouseExited

    private void historyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historyBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_historyBtnActionPerformed

    private void bookingBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookingBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bookingBtnActionPerformed

    private void adminBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminBtnMouseEntered
         adminBtn.setBackground(Color.GRAY);
    }//GEN-LAST:event_adminBtnMouseEntered

    private void adminBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminBtnMouseExited
        adminBtn.setBackground(new Color(43,43,43));
    }//GEN-LAST:event_adminBtnMouseExited

    private void adminBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminBtnMouseClicked
        dispose();
        new AdminPage(userName,role);
    }//GEN-LAST:event_adminBtnMouseClicked

    private void afterMonthMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_afterMonthMouseClicked
        System.out.println("After ");
         int currentIndex = monthComboBox1.getSelectedIndex();
        int yearIndex = yearComboBox.getSelectedIndex();

        // ถ้าเลยธันวาคม → ข้ามไปปีถัดไป
        if (currentIndex == 11) { // index เริ่มจาก 0 → 11 = ธันวาคม
            monthComboBox1.setSelectedIndex(0);
            yearComboBox.setSelectedIndex(yearIndex + 1);
        } else {
            monthComboBox1.setSelectedIndex(currentIndex + 1);
        }

        updateCalendar();
                                      
    }//GEN-LAST:event_afterMonthMouseClicked

    private void beforeMonthMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_beforeMonthMouseClicked
        System.out.println("After ");
        int currentIndex = monthComboBox1.getSelectedIndex();
    int yearIndex = yearComboBox.getSelectedIndex();

    // ถ้าเลยมกราคม → ย้อนกลับไปปีที่แล้ว
    if (currentIndex == 0) { // index 0 = มกราคม
        monthComboBox1.setSelectedIndex(11);
        yearComboBox.setSelectedIndex(yearIndex - 1);
    } else {
        monthComboBox1.setSelectedIndex(currentIndex - 1);
    }

    updateCalendar();
    }//GEN-LAST:event_beforeMonthMouseClicked

    
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton adminBtn;
    private javax.swing.JButton afterMonth;
    private javax.swing.JButton beforeMonth;
    private javax.swing.JButton bookingBtn;
    private javax.swing.JPanel calendarPanel;
    private javax.swing.JButton confirmBtn;
    private javax.swing.JButton historyBtn;
    private javax.swing.JButton homeBtn;
    private javax.swing.JLabel iconAdmin;
    private javax.swing.JLabel iconBooking;
    private javax.swing.JLabel iconHistory;
    private javax.swing.JLabel iconHome;
    private javax.swing.JLabel iconProfile;
    private javax.swing.JLabel iconUserProfile;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel logo;
    private javax.swing.JComboBox<String> monthComboBox1;
    private javax.swing.JLabel monthLabel;
    private javax.swing.JLabel monthLabel3;
    private javax.swing.JButton profileBtn;
    private javax.swing.JLabel username;
    private javax.swing.JComboBox<String> yearComboBox;
    // End of variables declaration//GEN-END:variables
}
