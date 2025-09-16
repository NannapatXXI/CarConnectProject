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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author nannapat
 */
public class BookingPage extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(BookingPage.class.getName());
    private HashMap<LocalDate, Integer> bookingCountMap = new HashMap<>();
    private int selectedDay = -1;
    private int selectedTime = -1;
    private int selectedService = -1;
    private String user ;
     private String role ;
     private int month;
    // เพิ่มตัวแปรไว้เก็บปุ่มล่าสุดที่เลือก
    private JButton lastSelectedButton = null;
   
 
    public BookingPage(String user,String role) {
        this.user = user;
         this.role = role;
        
        initComponents();
        SetupUi();
       getContentPane().setBackground(java.awt.Color.BLACK);

        setSize(1200, 800);        
        setLocationRelativeTo(null); 
        setVisible(true);
        
        userName.setText(user);
        
        LocalDate today = LocalDate.now();
        int monthValue = today.getMonthValue();
        int yearValue = today.getYear();
        int dayValue = today.getDayOfMonth();

        monthComboBox1.setSelectedIndex(monthValue - 1);
        yearComboBox.setSelectedItem(String.valueOf(yearValue));

        // อัปเดตเวลาตามวันปัจจุบัน
        updateTimeComboBox(dayValue, monthValue, yearValue);
       
       
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
      private void loadBookingCount() {
    CSVHandler handler = new CSVHandler("src/main/data/booking_count.csv");
    ArrayList<String[]> data = handler.readCSV();

    bookingCountMap.clear();
    for(int i=1; i<data.size(); i++){ // ข้าม header
        String[] row = data.get(i);
        if(row.length < 2) continue;

        String dateStr = row[0].trim();
        String countStr = row[1].trim();

            try {
                    LocalDate date = LocalDate.parse(dateStr); // YYYY-MM-DD เท่านั้น
                    int count = Integer.parseInt(countStr);
                    bookingCountMap.put(date, count);
            } catch(Exception e){
            // ข้ามวันที่ไม่ใช่รูปแบบ YYYY-MM-DD
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

        LocalDate today = LocalDate.now(); // วันปัจจุบัน
    
        // ดึงเดือนจาก monthComboBox (1-12)
        final int monthIndex = monthComboBox1.getSelectedIndex() + 1; // ทำให้ final

        // ดึงปีจาก yearComboBox
        int yearTemp = 0;
        try {
            String yearStr = (String) yearComboBox.getSelectedItem(); // เช่น "2025"
            yearTemp = Integer.parseInt(yearStr);
        } catch (Exception e) {
            yearTemp = today.getYear(); // fallback
        }
        final int year = yearTemp; // copy เป็น final

        // ใช้ YearMonth หาเดือน/จำนวนวัน
        YearMonth yearMonth = YearMonth.of(year, monthIndex);
        int daysInMonth = yearMonth.lengthOfMonth();

        for (int i = 1; i <= daysInMonth; i++) {
            final int day = i; // copy เป็น final
            JButton dayButton = new JButton(String.valueOf(day));
            
            dayButton.setPreferredSize(new Dimension(20, 20));
            dayButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
            dayButton.setFocusPainted(false);
            dayButton.setBorderPainted(false);
            dayButton.setContentAreaFilled(true);
            dayButton.setOpaque(true);

            LocalDate buttonDate = LocalDate.of(year, monthIndex, day);
            int booked = bookingCountMap.getOrDefault(buttonDate, 0); // จำนวนจองวันนั้น

            // ถ้าวันนี้ผ่านมาแล้ว
            if (buttonDate.isBefore(today)) {
   
                dayButton.setBackground(Color.LIGHT_GRAY);
                dayButton.setForeground(Color.BLACK);

                dayButton.addActionListener(e -> {
                // ป้องกันไม่ให้เลือกวันเก่า
                JOptionPane.showMessageDialog(this, "ไม่สามารถจองวันผ่านมาแล้วได้");
                });
            } else if (booked >= 10) {
                // dayButton.setEnabled(false); // ลบออก
                dayButton.setBackground(Color.RED);
                dayButton.setForeground(Color.WHITE);

                dayButton.addActionListener(e -> {
                JOptionPane.showMessageDialog(this, "วันนี้จองเต็มแล้ว");
                 });
            } else {
                dayButton.setBackground(Color.WHITE);
                dayButton.setForeground(Color.BLACK);

                dayButton.addActionListener(e -> {
                    selectedDay = day;
                    if (lastSelectedButton != null) {
                        lastSelectedButton.setBackground(Color.WHITE);
                        lastSelectedButton.setForeground(Color.BLACK);
                    }
                
                dayButton.setBackground(Color.GREEN);
                lastSelectedButton = dayButton;

                updateTimeComboBox(selectedDay, monthIndex, year);
                });
            }

       
            calendarPanel.add(dayButton);
        
        }

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }
    private void updateTimeComboBox(int selectedDay, int selectedMonth, int selectedYear){
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("----"); // ค่าเริ่มต้น
         selectedTime = 0;
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
    
        for(int h=10; h<=17; h++){
            if(selectedDay==today.getDayOfMonth() && selectedMonth==today.getMonthValue() && selectedYear==today.getYear()){
                if(h>now.getHour()){ // เพิ่มเฉพาะเวลาหลังจากตอนนี้
                    model.addElement(h + ".00");
                }
            } else {
                model.addElement(h + ".00"); // วันอื่น ๆ เพิ่มหมด
            }
        }

        timeComboBox.setModel(model);
    }


   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        calendarPanel = new javax.swing.JPanel();
        confirmBtn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        homeBtn = new javax.swing.JButton();
        adminBtn = new javax.swing.JButton();
        profileBtn = new javax.swing.JButton();
        historyBtn = new javax.swing.JButton();
        bookingBtn = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        userName = new javax.swing.JLabel();
        monthLabel = new javax.swing.JLabel();
        timeComboBox = new javax.swing.JComboBox<>();
        jPanel3 = new RoundedPanel(30); // 30 radius;
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        monthLabel1 = new javax.swing.JLabel();
        monthComboBox1 = new javax.swing.JComboBox<>();
        monthLabel2 = new javax.swing.JLabel();
        serviceComboBox1 = new javax.swing.JComboBox<>();
        monthLabel3 = new javax.swing.JLabel();
        yearComboBox = new javax.swing.JComboBox<>();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));

        calendarPanel.setBackground(new java.awt.Color(0, 0, 0));
        calendarPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 10, true));
        calendarPanel.setForeground(new java.awt.Color(51, 51, 0));
        calendarPanel.setLayout(new java.awt.GridLayout(1, 0));

        confirmBtn.setText("Confirm");
        confirmBtn.setToolTipText("");
        confirmBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmBtnActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(28, 24, 24));
        jPanel2.setForeground(new java.awt.Color(255, 153, 0));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        homeBtn.setBackground(new java.awt.Color(28, 24, 24));
        homeBtn.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        homeBtn.setForeground(new java.awt.Color(255, 255, 255));
        homeBtn.setText("Home");
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
        jPanel2.add(homeBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 164, 90));

        adminBtn.setBackground(new java.awt.Color(28, 24, 24));
        adminBtn.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        adminBtn.setForeground(new java.awt.Color(255, 255, 255));
        adminBtn.setText("Admin");
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
        jPanel2.add(adminBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 560, 164, 90));

        profileBtn.setBackground(new java.awt.Color(28, 24, 24));
        profileBtn.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        profileBtn.setForeground(new java.awt.Color(255, 255, 255));
        profileBtn.setText("Profile");
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
        jPanel2.add(profileBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 430, 164, 90));

        historyBtn.setBackground(new java.awt.Color(28, 24, 24));
        historyBtn.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        historyBtn.setForeground(new java.awt.Color(255, 255, 255));
        historyBtn.setText("History");
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
        jPanel2.add(historyBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, 164, 90));

        bookingBtn.setBackground(new java.awt.Color(255, 157, 0));
        bookingBtn.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        bookingBtn.setText("Booking");
        bookingBtn.setPreferredSize(new java.awt.Dimension(164, 90));
        jPanel2.add(bookingBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 164, 90));

        jPanel1.setBackground(new java.awt.Color(58, 58, 58));
        jPanel1.setPreferredSize(new java.awt.Dimension(1200, 104));

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("User :");

        userName.setFont(new java.awt.Font("Helvetica Neue", 0, 24)); // NOI18N
        userName.setForeground(new java.awt.Color(255, 255, 255));
        userName.setText("....");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(userName, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userName, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        monthLabel.setBackground(new java.awt.Color(255, 255, 255));
        monthLabel.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        monthLabel.setForeground(new java.awt.Color(255, 255, 255));
        monthLabel.setText("Year :");

        timeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeComboBoxActionPerformed(evt);
            }
        });

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

        monthLabel1.setBackground(new java.awt.Color(255, 255, 255));
        monthLabel1.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        monthLabel1.setForeground(new java.awt.Color(255, 255, 255));
        monthLabel1.setText("Time :");

        monthComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        monthComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthComboBox1ActionPerformed(evt);
            }
        });

        monthLabel2.setBackground(new java.awt.Color(255, 255, 255));
        monthLabel2.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        monthLabel2.setForeground(new java.awt.Color(255, 255, 255));
        monthLabel2.setText("Service :");

        serviceComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "---- ", "Wash a car", "Repair and Check" }));
        serviceComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serviceComboBox1ActionPerformed(evt);
            }
        });

        monthLabel3.setBackground(new java.awt.Color(255, 255, 255));
        monthLabel3.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        monthLabel3.setForeground(new java.awt.Color(255, 255, 255));
        monthLabel3.setText("   Month :");

        yearComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2025", "2026", "2027", "2028" }));
        yearComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1222, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(monthLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(monthComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(monthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(yearComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(monthLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(timeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(monthLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(serviceComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(69, 69, 69))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(calendarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 937, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(846, 846, 846)
                                .addComponent(confirmBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 937, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(timeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(monthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(monthLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(monthComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(monthLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(serviceComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(monthLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(yearComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(26, 26, 26)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(calendarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(confirmBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 734, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void confirmBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmBtnActionPerformed
            
        
            if (selectedDay > 0) {
                if(selectedTime > 0){
                   if(selectedService > 0){
                        String monthSelected = (String) monthComboBox1.getSelectedItem();
                        String timeSelected = (String) timeComboBox.getSelectedItem();
                        int daySelected = selectedDay;
                         String service = (String) serviceComboBox1.getSelectedItem();
                        System.out.println("คุณเลือกวันที่: " + selectedDay + "/" + (month + 1) +"   "+ timeSelected + " บริการ: " + service);

                        // สร้าง Dialog ของเราขึ้นมา
                        PopInBooking dialog = new PopInBooking(this, true,user);

                         // ใส่ค่าที่เลือกลงใน Label ของ Dialog
                        dialog.setDayLabel(daySelected + " " + monthSelected);
                        dialog.setTimeLabel(timeSelected);
                        dialog.setServiceLabel(service);
                        dialog.setVisible(true);
                         LocalDate selectedDate = LocalDate.of(
                             Integer.parseInt(yearComboBox.getSelectedItem().toString()),
                             monthComboBox1.getSelectedIndex() + 1,
                             selectedDay
                        );

                         // เพิ่มจำนวนจอง
                        int currentCount = bookingCountMap.getOrDefault(selectedDate, 0);
                         bookingCountMap.put(selectedDate, currentCount + 1);

                        // บันทึกลงไฟล์ CSV
                        saveBookingCount();

                        // รีเฟรชปฏิทินให้สีปุ่มอัปเดต
                        updateCalendar();
                        
                   }else{
                        System.out.println("ยังไม่ได้เลือกบริการ");
                        new PopAlert(this,true,"ยังไม่ได้เลือกบริการ");
                   }
                     
                }else{
                     System.out.println("ยังไม่ได้เลือกเวลา");
                      new PopAlert(this,true,"ยังไม่ได้เลือกเวลา");
                }
              
            } else {
                System.out.println("ยังไม่ได้เลือกวัน");
                 new PopAlert(this,true,"ยังไม่ได้เลือกวัน");
            }
    }//GEN-LAST:event_confirmBtnActionPerformed

    private void timeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeComboBoxActionPerformed
      
        if( !timeComboBox.getSelectedItem().toString().trim().equals("----")){
            System.out.println("เวลาที่เลือก"+  timeComboBox.getSelectedItem());
            selectedTime = 1;
           
        }else{
             selectedTime = 0;
        }
        
    }//GEN-LAST:event_timeComboBoxActionPerformed

    private void monthComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthComboBox1ActionPerformed
             updateCalendar();
             // ยังไม่ได้เลือกวัน -> ส่ง -1
            updateTimeComboBox(-1, monthComboBox1.getSelectedIndex() + 1,Integer.parseInt(yearComboBox.getSelectedItem().toString()));
    }//GEN-LAST:event_monthComboBox1ActionPerformed

    private void serviceComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serviceComboBox1ActionPerformed
         if( !serviceComboBox1.getSelectedItem().toString().trim().equals("----")){
              System.out.println("บริการที่เลือก"+  serviceComboBox1.getSelectedItem());
            selectedService = 1;
        }else{
              selectedService = 0;
         }
    }//GEN-LAST:event_serviceComboBox1ActionPerformed

    private void homeBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeBtnMouseExited
        homeBtn.setBackground(new Color(28,24,24));
    }//GEN-LAST:event_homeBtnMouseExited

    private void homeBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeBtnMouseEntered
        homeBtn.setBackground(Color.GRAY);
    }//GEN-LAST:event_homeBtnMouseEntered

    private void homeBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeBtnMouseClicked
        dispose();
        new HomePage(user,role);
    }//GEN-LAST:event_homeBtnMouseClicked

    private void adminBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminBtnMouseClicked
        dispose();
        new AdminPage(user,role);
    }//GEN-LAST:event_adminBtnMouseClicked

    private void adminBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminBtnMouseEntered
        
         adminBtn.setBackground(Color.GRAY);
    }//GEN-LAST:event_adminBtnMouseEntered

    private void adminBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminBtnMouseExited
       adminBtn.setBackground(new Color(28,24,24));
    }//GEN-LAST:event_adminBtnMouseExited

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

    private void yearComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearComboBoxActionPerformed
       updateCalendar();
       // ยังไม่ได้เลือกวัน -> ส่ง -1
        updateTimeComboBox(-1, monthComboBox1.getSelectedIndex() + 1,Integer.parseInt(yearComboBox.getSelectedItem().toString()));
    }//GEN-LAST:event_yearComboBoxActionPerformed

    
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton adminBtn;
    private javax.swing.JButton bookingBtn;
    private javax.swing.JPanel calendarPanel;
    private javax.swing.JButton confirmBtn;
    private javax.swing.JButton historyBtn;
    private javax.swing.JButton homeBtn;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JComboBox<String> monthComboBox1;
    private javax.swing.JLabel monthLabel;
    private javax.swing.JLabel monthLabel1;
    private javax.swing.JLabel monthLabel2;
    private javax.swing.JLabel monthLabel3;
    private javax.swing.JButton profileBtn;
    private javax.swing.JComboBox<String> serviceComboBox1;
    private javax.swing.JComboBox<String> timeComboBox;
    private javax.swing.JLabel userName;
    private javax.swing.JComboBox<String> yearComboBox;
    // End of variables declaration//GEN-END:variables
}
