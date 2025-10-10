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
import java.util.Locale;
import java.util.Map;


public class BookingPage extends javax.swing.JFrame  {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(BookingPage.class.getName());
    private HashMap<LocalDate, Integer> bookingCountMap = new HashMap<>();
     private ArrayList<LocalDate> closedDays = new ArrayList<>();
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

        setSize(1210, 830);        
        setLocationRelativeTo(null); 
        setVisible(true);
        
        username.setHorizontalAlignment(JLabel.RIGHT);
        username.setText(user);
        
        LocalDate today = LocalDate.now();
        int monthValue = today.getMonthValue();
        int yearValue = today.getYear();
        int dayValue = today.getDayOfMonth();

        monthComboBox1.setSelectedIndex(monthValue - 1);
        yearComboBox.setSelectedItem(String.valueOf(yearValue));

     
        calendarPanel.setLayout(new java.awt.GridLayout(0, 7,5, 5));
       // loadBookingCount();
       loadClosedDays();
        updateCalendar(); 
        
    }
    
      private void SetupUi() {
       
        adminBtn.setContentAreaFilled(false);
        adminBtn.setBorderPainted(false);

        historyBtn.setContentAreaFilled(false);
        historyBtn.setBorderPainted(false);

        profileBtn.setContentAreaFilled(false);
        profileBtn.setBorderPainted(false);

        homeBtn.setContentAreaFilled(false);
        homeBtn.setBorderPainted(false);
        
        
         if(role.equals("admin")){
             System.out.println("Admin !!!!");
        }else{
             System.out.println("User !!!!");
             adminBtn.setVisible(false);  // user ซ่อนปุ่ม
             iconAdmin.setVisible(false);
             
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
                   
                }
    
    }
    
    private void loadBookingCount() {
        CSVHandler csvHandler = new CSVHandler("src/main/data/history_user.csv");
        ArrayList<String[]> data = csvHandler.readCSV();

        // ใช้ format ที่ตรงกับข้อมูลในไฟล์ CSV
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH);
       

        for (String[] row : data) {
            try {
                String dateStr = row[3].trim(); // คอลัมน์ Date
                LocalDate date = LocalDate.parse(dateStr, formatter); 
                bookingCountMap.put(date, bookingCountMap.getOrDefault(date, 0) + 1);
            } catch (Exception e) {
                e.printStackTrace(); // debug ถ้า parse ไม่ได้
            }
        }
    }

      
private Map<LocalDate, Integer> calculateDailyBooking() {
    CSVHandler csvHandler = new CSVHandler("src/main/data/history_user.csv");
    ArrayList<String[]> data = csvHandler.readCSV();
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    Map<LocalDate, Integer> dailyBooking = new HashMap<>();

     boolean firstLine = true; // ข้าม header
    for (String[] parts : data) {
        if (firstLine) {
            firstLine = false;
            continue;
        }
        if (parts.length >= 5) {
            String dateStr = parts[3].trim();
            String timeRange = parts[4].trim();

            try {
                // ใช้ Locale อังกฤษ
                LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH));

                // แยกเวลา
                String[] times = timeRange.split("-");
                if (times.length != 2) continue;

                LocalTime start = LocalTime.parse(times[0].trim(), timeFormatter);
                LocalTime end = LocalTime.parse(times[1].trim(), timeFormatter);

                // นับจำนวนชั่วโมงแบบ slot
                int slots = 0;
                LocalTime t = start;
                while (t.isBefore(end)) {
                    slots++;
                    t = t.plusHours(1);
                }

                dailyBooking.put(date, dailyBooking.getOrDefault(date, 0) + slots);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    return dailyBooking;
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

     Map<LocalDate, Integer> dailyBookingMap = calculateDailyBooking();

    
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
           int booked = dailyBookingMap.getOrDefault(buttonDate, 0);
 
            dayButton.setPreferredSize(new Dimension(20, 20));
            dayButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
            dayButton.setFocusPainted(false);
            dayButton.setBorderPainted(false);
            dayButton.setContentAreaFilled(true);
            dayButton.setOpaque(true);
            System.out.println("วันที่ " + buttonDate + " จำนวน " + booked);

           
            // ถ้าวันนี้ผ่านมาแล้ว
           if (closedDays.contains(buttonDate)) {
                dayButton.setBackground(Color.GRAY);
                dayButton.setForeground(Color.WHITE);
                 dayButton.addActionListener(e -> {
                    JOptionPane.showMessageDialog(this, "วันนี้ร้านปิด");
                });
            } else if (buttonDate.isBefore(today)) {
                dayButton.setBackground(Color.LIGHT_GRAY);
                dayButton.setForeground(Color.BLACK);
                dayButton.addActionListener(e -> {
                    JOptionPane.showMessageDialog(this, "ไม่สามารถจองวันผ่านมาแล้วได้");
                });
            }  else if (booked >= 40) { // เต็ม
                dayButton.setBackground(new Color(244, 67, 54));
                dayButton.setForeground(Color.WHITE);
                 dayButton.addActionListener(e -> {
                    JOptionPane.showMessageDialog(this, "เต็ม");
                    // ไม่เปลี่ยนสี
                });
            } else  if (booked >= 30) { // เยอะ
                dayButton.setBackground(new Color(255, 152, 0));
                dayButton.setForeground(Color.BLACK);
            } else if (booked >= 20) { // กลาง
                dayButton.setBackground(new Color(255, 213, 79));
                dayButton.setForeground(Color.BLACK);
            } else if (booked >= 10) { // น้อย
                dayButton.setBackground(new Color(3, 169, 244));
                dayButton.setForeground(Color.WHITE);
            } else { // ว่าง
                dayButton.setBackground(new Color(76, 175, 80));
                dayButton.setForeground(Color.WHITE);
            } 

            // เพิ่ม ActionListener สำหรับวันที่เลือกได้เท่านั้น
            if (!buttonDate.isBefore(today) && booked < 40  && !closedDays.contains(buttonDate) ) {
                dayButton.addActionListener(e -> {
                    selectedDay = day;

                   if (lastSelectedButton != null) {
                    // คืนค่าสีปุ่มเก่า
                    int lastBooked = dailyBookingMap.getOrDefault(
                        LocalDate.of(year, monthIndex, Integer.parseInt(lastSelectedButton.getText())), 0
                    );
                    if (lastBooked >= 40) {
                        lastSelectedButton.setBackground(new Color(244, 67, 54));
                    } else if (lastBooked >= 30) {
                        lastSelectedButton.setBackground(new Color(255, 152, 0));
                    } else if (lastBooked >= 20) {
                        lastSelectedButton.setBackground(new Color(255, 213, 79));
                    } else if (lastBooked >= 10) {
                        lastSelectedButton.setBackground(new Color(3, 169, 244));
                    } else {
                        lastSelectedButton.setBackground(new Color(76, 175, 80));
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
    private void loadClosedDays() {
        closedDays.clear(); // เคลียร์ข้อมูลเก่า

        CSVHandler closeCsv = new CSVHandler("src/main/data/close_day.csv");
        ArrayList<String[]> closeData = closeCsv.readCSV();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // ตามรูปแบบไฟล์

        for (String[] row : closeData) {
            if (row.length > 0 && !row[0].equalsIgnoreCase("Date")) {
                try {
                    closedDays.add(LocalDate.parse(row[0].trim(), formatter));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
}



   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
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
        jPanel1 = new RoundedPanel(30); // 30 radius;
        calendarPanel = new javax.swing.JPanel();
        afterMonth = new javax.swing.JButton();
        beforeMonth = new javax.swing.JButton();
        jPanel6 = new RoundedPanel(30); // 30 radius;
        jPanel7 = new RoundedPanel(30); // 30 radius;
        jPanel8 = new RoundedPanel(30); // 30 radius;
        jPanel9 = new RoundedPanel(30); // 30 radius;
        jPanel10 = new RoundedPanel(30); // 30 radius;
        jLabel1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        username = new javax.swing.JLabel();
        iconExit = new javax.swing.JLabel();
        close = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jPanel11 = new RoundedPanel(30); // 30 radius;

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setPreferredSize(new java.awt.Dimension(1200, 800));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        monthLabel.setBackground(new java.awt.Color(255, 255, 255));
        monthLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        monthLabel.setText("Year :");
        getContentPane().add(monthLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(541, 118, 56, 35));

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

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(258, 178, 928, -1));

        jPanel4.setBackground(new java.awt.Color(51, 255, 0));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 800, -1, -1));

        monthComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        monthComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthComboBox1ActionPerformed(evt);
            }
        });
        getContentPane().add(monthComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(364, 120, 133, 35));

        monthLabel3.setBackground(new java.awt.Color(255, 255, 255));
        monthLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        monthLabel3.setText("   Month :");
        getContentPane().add(monthLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(272, 118, -1, 35));

        yearComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2025", "2026", "2027", "2028" }));
        yearComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearComboBoxActionPerformed(evt);
            }
        });
        getContentPane().add(yearComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(603, 120, 133, 35));

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

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 800));

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
                .addContainerGap(10, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(258, 241, -1, -1));

        afterMonth.setText(">");
        afterMonth.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                afterMonthMouseClicked(evt);
            }
        });
        getContentPane().add(afterMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 120, 42, 33));

        beforeMonth.setText("<");
        beforeMonth.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                beforeMonthMouseClicked(evt);
            }
        });
        getContentPane().add(beforeMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 120, 47, 33));

        jPanel6.setBackground(new java.awt.Color(244, 67, 54));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 760, 20, 20));

        jPanel7.setBackground(new java.awt.Color(255, 152, 0));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 760, -1, 20));

        jPanel8.setBackground(new java.awt.Color(255, 213, 79));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 760, -1, 20));

        jPanel9.setBackground(new java.awt.Color(3, 169, 244));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 760, -1, -1));

        jPanel10.setBackground(new java.awt.Color(76, 175, 80));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 760, -1, 20));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("เต็ม");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 760, 30, 20));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("เยอะ");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 760, 40, 20));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("กลาง");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 760, 40, 20));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("น้อย");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 760, 40, 20));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("ว่าง");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 760, 30, 20));

        username.setBackground(new java.awt.Color(0, 0, 0));
        username.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        username.setText("..");
        getContentPane().add(username, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 40, 660, 40));

        iconExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconExitMouseClicked(evt);
            }
        });
        getContentPane().add(iconExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 40, 40, 40));

        close.setText("jButton1");
        close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeActionPerformed(evt);
            }
        });
        getContentPane().add(close, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 750, 60, 40));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("ปิด");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 760, 30, 20));

        jPanel11.setBackground(java.awt.Color.gray);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 21, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 760, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void monthComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthComboBox1ActionPerformed
             updateCalendar();
             // ยังไม่ได้เลือกวัน -> ส่ง -1
           
    }//GEN-LAST:event_monthComboBox1ActionPerformed

    private void yearComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearComboBoxActionPerformed
       updateCalendar();
     
    }//GEN-LAST:event_yearComboBoxActionPerformed

    private void homeBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeBtnMouseEntered
        homeBtn.setForeground(Color.WHITE);
    }//GEN-LAST:event_homeBtnMouseEntered

    private void homeBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeBtnMouseExited
        homeBtn.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_homeBtnMouseExited

    private void homeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeBtnActionPerformed
        dispose();
        new HomePage(userName,role);
    }//GEN-LAST:event_homeBtnActionPerformed

    private void profileBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profileBtnMouseClicked
        dispose();
        new Profile(userName,role);
    }//GEN-LAST:event_profileBtnMouseClicked

    private void profileBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profileBtnMouseEntered
         profileBtn.setForeground(Color.WHITE);
    }//GEN-LAST:event_profileBtnMouseEntered

    private void profileBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_profileBtnMouseExited
        profileBtn.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_profileBtnMouseExited

    private void historyBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_historyBtnMouseClicked
        dispose();
        new History(userName,role);
    }//GEN-LAST:event_historyBtnMouseClicked

    private void historyBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_historyBtnMouseEntered
       historyBtn.setForeground(Color.WHITE);
    }//GEN-LAST:event_historyBtnMouseEntered

    private void historyBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_historyBtnMouseExited
        historyBtn.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_historyBtnMouseExited

    private void historyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historyBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_historyBtnActionPerformed

    private void bookingBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookingBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bookingBtnActionPerformed

    private void adminBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminBtnMouseEntered
         adminBtn.setForeground(Color.WHITE);
    }//GEN-LAST:event_adminBtnMouseEntered

    private void adminBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminBtnMouseExited
         adminBtn.setForeground(new Color(204,204,204));
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
        loadClosedDays();
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
    loadClosedDays();
    updateCalendar();
    }//GEN-LAST:event_beforeMonthMouseClicked

    private void iconExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconExitMouseClicked
        dispose();
        new Login();
    }//GEN-LAST:event_iconExitMouseClicked

    private void closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeActionPerformed
         dispose();
        new CloseDay(userName,role);
       
    }//GEN-LAST:event_closeActionPerformed

    
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton adminBtn;
    private javax.swing.JButton afterMonth;
    private javax.swing.JButton beforeMonth;
    private javax.swing.JButton bookingBtn;
    private javax.swing.JPanel calendarPanel;
    private javax.swing.JButton close;
    private javax.swing.JButton historyBtn;
    private javax.swing.JButton homeBtn;
    private javax.swing.JLabel iconAdmin;
    private javax.swing.JLabel iconBooking;
    private javax.swing.JLabel iconExit;
    private javax.swing.JLabel iconHistory;
    private javax.swing.JLabel iconHome;
    private javax.swing.JLabel iconProfile;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel logo;
    private javax.swing.JComboBox<String> monthComboBox1;
    private javax.swing.JLabel monthLabel;
    private javax.swing.JLabel monthLabel3;
    private javax.swing.JButton profileBtn;
    private javax.swing.JLabel username;
    private javax.swing.JComboBox<String> yearComboBox;
    // End of variables declaration//GEN-END:variables
}
