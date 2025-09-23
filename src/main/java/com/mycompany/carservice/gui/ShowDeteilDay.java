/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.carservice.gui;

import com.mycompany.carservice.entity.CSVHandler;
import com.mycompany.carservice.entity.RoundedPanel;
import java.awt.Color;
import java.awt.Frame;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 *
 * @author nannapat
 */
public class ShowDeteilDay extends javax.swing.JDialog {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ShowDeteilDay.class.getName());
    private String day;
    private String month;
    private String year;
    private String username;
    private javax.swing.JPanel selectedPanel = null; // Panel ที่ถูกเลือกล่าสุด
    Frame parentFrame;
   
     
    
    /**
     * Creates new form ShowDeteilDay
     */
    public ShowDeteilDay(java.awt.Frame parent, boolean modal,String day,String month , String year , String name) {
        super(parent, modal);
        this.username = name;
        this.day = day;
        this.month = month;
        this.year = year;
        this.parentFrame = parent;
       
        initComponents();
         
          changeStatus();
           loadService();
          dayLabel.setText("วันที่ "+day +" "+ month + " " + year);
        
        setLocationRelativeTo(null);
        setVisible(true);  
        
        
      
        
    }
    private boolean isPastDateTime(String day, String month, String year, String timeText) {
            try {
            // แปลงเป็น LocalDate ของวันจอง
            String dateStr = day + " " + month + " " + year;
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH);
            LocalDate bookingDate = LocalDate.parse(dateStr, dateFormatter);

            LocalDate today = LocalDate.now();

            // 📌 ถ้าวันจอง < วันนี้ → ทั้งวันเป็นอดีต
            if (bookingDate.isBefore(today)) {
                return true;
            }

            // 📌 ถ้าวันจอง > วันนี้ → ยังไม่ถึงเวลาแน่นอน
            if (bookingDate.isAfter(today)) {
                return false;
            }

            // 📌 ถ้าวันจอง = วันนี้ → เช็คเวลา
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime now = LocalTime.now();
            LocalTime bookingTime = LocalTime.parse(timeText, timeFormatter);

            return bookingTime.isBefore(now);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    private void disablePastTime(javax.swing.JPanel panel, String timeText) {
        if (isPastDateTime(day, month, year, timeText)) {
            panel.setBackground(new Color(200, 200, 200)); // สีเทา
            panel.setEnabled(false); // ปิดการคลิก
        }
    }
    
   private void selectPanel(javax.swing.JPanel panel) {
        // คืนสี panel เก่ากลับเป็นสีขาว
        if (selectedPanel != null) {
            selectedPanel.setBackground(new Color(255,255,255)); 
        }

        // เปลี่ยนสี panel ใหม่เป็น selected
        panel.setBackground(new Color(100, 149, 237)); // สี panel ที่เลือก

        // บันทึก panel นี้เป็น selected
        selectedPanel = panel;
    }
    
    private Map<String, Integer> countBookingByTime(String day, String month, String year) {
        CSVHandler csvHandler = new CSVHandler("src/main/data/history_user.csv");
    ArrayList<String[]> data = csvHandler.readCSV();

    // Key = เวลา panel เช่น "09:00", Value = จำนวนคน
    Map<String, Integer> timeCount = new HashMap<>();

    String targetDate = day + " " + month + " " + year;

    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    for (String[] parts : data) {
        if (parts.length >= 5) {
            String dateInCsv = parts[3].trim(); // index 3 คือวันที่
            String timeRange = parts[4].trim(); // index 4 คือเวลาช่วง เช่น 09:00-10:00

            if (dateInCsv.equals(targetDate)) {
                // แยกเวลาเริ่มและเวลาเลิก
                String[] times = timeRange.split("-");
                if (times.length == 2) {
                    try {
                        LocalTime start = LocalTime.parse(times[0].trim(), timeFormatter);
                        LocalTime end = LocalTime.parse(times[1].trim(), timeFormatter);

                        // สำหรับทุก panel ที่เราสนใจ
                        String[] panelTimes = {"09:00","10:00","11:00","13:00","14:00","15:00","16:00","17:00"};
                        for (String panelTimeStr : panelTimes) {
                            LocalTime panelTime = LocalTime.parse(panelTimeStr, timeFormatter);
                            // ถ้า panelTime อยู่ในช่วง start <= panelTime < end
                            if (!panelTime.isBefore(start) && panelTime.isBefore(end)) {
                                timeCount.put(panelTimeStr, timeCount.getOrDefault(panelTimeStr, 0) + 1);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
                for (Map.Entry<String, Integer> entry : timeCount.entrySet()) {
                     System.out.println("เวลา " + entry.getKey() + " มีคนจอง " + entry.getValue() + " คน");
               }

        return timeCount;
    }
    private void changeStatus() {
        Map<String, Integer> result = countBookingByTime(day, month, year);

        updatePanelColor(time9Panal,time9Label, "09:00", result);
        updatePanelColor(time10Panel,time10Label, "10:00", result);
        updatePanelColor(time11Panel,time11Label, "11:00", result);
        updatePanelColor(time13Panel,time13Label, "13:00", result);
        updatePanelColor(time14Panel,time14Label, "14:00", result);
        updatePanelColor(time15Panel,time15Label, "15:00", result);
        updatePanelColor(time16Panel,time16Label, "16:00", result);
        updatePanelColor(time17Panel,time17Label, "17:00", result);

            // เช็คเวลาที่ผ่านมาแล้วเปลี่ยนสีเทา
        disablePastTime(time9Panal, "09:00");
        disablePastTime(time10Panel, "10:00");
        disablePastTime(time11Panel, "11:00");
        disablePastTime(time13Panel, "13:00");
        disablePastTime(time14Panel, "14:00");
        disablePastTime(time15Panel, "15:00");
        disablePastTime(time16Panel, "16:00");
        disablePastTime(time17Panel, "17:00");

    }

    
    private void updatePanelColor(javax.swing.JPanel panel, javax.swing.JLabel label,  String timeKey, Map<String, Integer> timeCount) {
        int count = timeCount.getOrDefault(timeKey, 0);

       if(count > 5){
           panel.setBackground(new Color(160, 174, 192)); 
       }else if (count >= 4) {
           panel.setBackground(new Color(245, 101, 101));      // เต็ม
       } else if (count >= 2) {
           panel.setBackground(new Color(237, 137, 54));   // เริ่มเยอะ
       } else if (count == 1) {
           panel.setBackground(new Color(72, 187, 120));    // มี 1 คน
       } else {
           panel.setBackground(new Color(72, 187, 120));     // ยังว่าง
       }
        label.setText(count+"");
    }
    
    private boolean checkQueue( javax.swing.JLabel label){
         
        AlertManager manager = new AlertManager();
        PopAlert alert = new PopAlert(parentFrame, true); // ใช้ dialog เดิม
        manager.registerObserver(alert);
        
        int count =Integer.parseInt(label.getText()) ;
         return manager.checkCount(count);
    }
    
    private void loadService(){
         CSVHandler csvHandler = new CSVHandler("src/main/data/service.csv");
            ArrayList<String[]> data = csvHandler.readCSV();

             // ใส่ item พิเศษเป็นช่องแรก
            serviceCombobox.insertItemAt("-- --", 0);
            serviceCombobox.setSelectedIndex(0);

            // เริ่มอ่านตั้งแต่ index 1 เพื่อข้าม header
            for (int i = 1; i < data.size(); i++) {
                String[] parts = data.get(i);
                if (parts.length > 0) {
                    String serviceName = parts[0].trim();
                    serviceCombobox.addItem(serviceName);
                }
            }
    }
// homeBtn.setBackground(Color.GRAY);
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel8 = new RoundedPanel(30); // 30 radius;
        jPanel7 = new RoundedPanel(30); // 30 radius;
        jPanel4 = new RoundedPanel(30); // 30 radius;
        jPanel5 = new RoundedPanel(30); // 30 radius;
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel6 = new RoundedPanel(30); // 30 radius;
        serviceCombobox = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        dayLabel = new javax.swing.JLabel();
        backJPanal1 = new RoundedPanel(30); // 30 radius;
        time10Panel = new RoundedPanel(30); // 30 radius;
        jLabel7 = new javax.swing.JLabel();
        time10Label = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        backJPanal2 = new RoundedPanel(30); // 30 radius;
        time9Panal = new RoundedPanel(30); // 30 radius;
        jLabel6 = new javax.swing.JLabel();
        time9Label = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        backJPanal4 = new RoundedPanel(30); // 30 radius;
        time13Panel = new RoundedPanel(30); // 30 radius;
        jLabel10 = new javax.swing.JLabel();
        time13Label = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        backJPanal3 = new RoundedPanel(30); // 30 radius;
        time11Panel = new RoundedPanel(30); // 30 radius;
        jLabel8 = new javax.swing.JLabel();
        time11Label = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        backJPanal6 = new RoundedPanel(30); // 30 radius;
        time15Panel = new RoundedPanel(30); // 30 radius;
        jLabel11 = new javax.swing.JLabel();
        time15Label = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        backJPanal5 = new RoundedPanel(30); // 30 radius;
        time14Panel = new RoundedPanel(30); // 30 radius;
        jLabel9 = new javax.swing.JLabel();
        time14Label = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        backJPanal7 = new RoundedPanel(30); // 30 radius;
        time16Panel = new RoundedPanel(30); // 30 radius;
        jLabel12 = new javax.swing.JLabel();
        time16Label = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        backJPanal8 = new RoundedPanel(30); // 30 radius;
        time17Panel = new RoundedPanel(30); // 30 radius;
        jLabel13 = new javax.swing.JLabel();
        time17Label = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        backBtn = new javax.swing.JButton();
        comfrimBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAutoRequestFocus(false);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(43, 43, 43));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Detail Day");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 21, 137, 49));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jPanel8.setBackground(new java.awt.Color(240, 240, 240));

        jPanel7.setBackground(new java.awt.Color(255, 0, 0));
        jPanel7.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(255, 153, 51));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(51, 255, 0));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("4-5 คน");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("1 คน");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("2-3 คน");

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        serviceCombobox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Service :");

        dayLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dayLabel.setText("วันที่ ");

        backJPanal1.setBackground(new java.awt.Color(255, 255, 255));

        time10Panel.setBackground(new java.awt.Color(0, 0, 0));
        time10Panel.setPreferredSize(new java.awt.Dimension(84, 37));
        time10Panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                time10PanelMouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("10:00");

        time10Label.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        time10Label.setForeground(new java.awt.Color(255, 255, 255));
        time10Label.setText("jLabel16");

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("people");

        javax.swing.GroupLayout time10PanelLayout = new javax.swing.GroupLayout(time10Panel);
        time10Panel.setLayout(time10PanelLayout);
        time10PanelLayout.setHorizontalGroup(
            time10PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(time10PanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(time10PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(time10PanelLayout.createSequentialGroup()
                        .addComponent(time10Label, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel26)))
                .addContainerGap(106, Short.MAX_VALUE))
        );
        time10PanelLayout.setVerticalGroup(
            time10PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(time10PanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(time10PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(time10Label)
                    .addComponent(jLabel26))
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout backJPanal1Layout = new javax.swing.GroupLayout(backJPanal1);
        backJPanal1.setLayout(backJPanal1Layout);
        backJPanal1Layout.setHorizontalGroup(
            backJPanal1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backJPanal1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(time10Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );
        backJPanal1Layout.setVerticalGroup(
            backJPanal1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backJPanal1Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addComponent(time10Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        backJPanal2.setBackground(new java.awt.Color(255, 255, 255));

        time9Panal.setBackground(new java.awt.Color(0, 0, 0));
        time9Panal.setPreferredSize(new java.awt.Dimension(84, 37));
        time9Panal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                time9PanalMouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("9:00");

        time9Label.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        time9Label.setForeground(new java.awt.Color(255, 255, 255));
        time9Label.setText("jLabel16");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("people");

        javax.swing.GroupLayout time9PanalLayout = new javax.swing.GroupLayout(time9Panal);
        time9Panal.setLayout(time9PanalLayout);
        time9PanalLayout.setHorizontalGroup(
            time9PanalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(time9PanalLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(time9PanalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(time9PanalLayout.createSequentialGroup()
                        .addComponent(time9Label, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel14))
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(105, Short.MAX_VALUE))
        );
        time9PanalLayout.setVerticalGroup(
            time9PanalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(time9PanalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(time9PanalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(time9Label)
                    .addComponent(jLabel14))
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout backJPanal2Layout = new javax.swing.GroupLayout(backJPanal2);
        backJPanal2.setLayout(backJPanal2Layout);
        backJPanal2Layout.setHorizontalGroup(
            backJPanal2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backJPanal2Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addComponent(time9Panal, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        backJPanal2Layout.setVerticalGroup(
            backJPanal2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backJPanal2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(time9Panal, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        backJPanal4.setBackground(new java.awt.Color(255, 255, 255));

        time13Panel.setBackground(new java.awt.Color(0, 0, 0));
        time13Panel.setPreferredSize(new java.awt.Dimension(84, 37));
        time13Panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                time13PanelMouseClicked(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("13:00");

        time13Label.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        time13Label.setForeground(new java.awt.Color(255, 255, 255));
        time13Label.setText("jLabel16");

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("people");

        javax.swing.GroupLayout time13PanelLayout = new javax.swing.GroupLayout(time13Panel);
        time13Panel.setLayout(time13PanelLayout);
        time13PanelLayout.setHorizontalGroup(
            time13PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(time13PanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(time13PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(time13PanelLayout.createSequentialGroup()
                        .addComponent(time13Label, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel27)))
                .addContainerGap(96, Short.MAX_VALUE))
        );
        time13PanelLayout.setVerticalGroup(
            time13PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(time13PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(time13PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(time13Label)
                    .addComponent(jLabel27))
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout backJPanal4Layout = new javax.swing.GroupLayout(backJPanal4);
        backJPanal4.setLayout(backJPanal4Layout);
        backJPanal4Layout.setHorizontalGroup(
            backJPanal4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backJPanal4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(time13Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );
        backJPanal4Layout.setVerticalGroup(
            backJPanal4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backJPanal4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(time13Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        backJPanal3.setBackground(new java.awt.Color(255, 255, 255));

        time11Panel.setBackground(new java.awt.Color(0, 0, 0));
        time11Panel.setPreferredSize(new java.awt.Dimension(81, 37));
        time11Panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                time11PanelMouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("11:00");

        time11Label.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        time11Label.setForeground(new java.awt.Color(255, 255, 255));
        time11Label.setText("jLabel16");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("people");

        javax.swing.GroupLayout time11PanelLayout = new javax.swing.GroupLayout(time11Panel);
        time11Panel.setLayout(time11PanelLayout);
        time11PanelLayout.setHorizontalGroup(
            time11PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(time11PanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(time11PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(time11PanelLayout.createSequentialGroup()
                        .addComponent(time11Label, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel15)))
                .addContainerGap(92, Short.MAX_VALUE))
        );
        time11PanelLayout.setVerticalGroup(
            time11PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(time11PanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(time11PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(time11Label)
                    .addComponent(jLabel15))
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout backJPanal3Layout = new javax.swing.GroupLayout(backJPanal3);
        backJPanal3.setLayout(backJPanal3Layout);
        backJPanal3Layout.setHorizontalGroup(
            backJPanal3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backJPanal3Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addComponent(time11Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        backJPanal3Layout.setVerticalGroup(
            backJPanal3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backJPanal3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(time11Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        backJPanal6.setBackground(new java.awt.Color(255, 255, 255));

        time15Panel.setBackground(new java.awt.Color(0, 0, 0));
        time15Panel.setPreferredSize(new java.awt.Dimension(470, 37));
        time15Panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                time15PanelMouseClicked(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("15:00");

        time15Label.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        time15Label.setForeground(new java.awt.Color(255, 255, 255));
        time15Label.setText("jLabel16");

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("people");

        javax.swing.GroupLayout time15PanelLayout = new javax.swing.GroupLayout(time15Panel);
        time15Panel.setLayout(time15PanelLayout);
        time15PanelLayout.setHorizontalGroup(
            time15PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, time15PanelLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(time15PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(time15PanelLayout.createSequentialGroup()
                        .addComponent(time15Label, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel28)))
                .addGap(100, 100, 100))
        );
        time15PanelLayout.setVerticalGroup(
            time15PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(time15PanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(time15PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(time15Label)
                    .addComponent(jLabel28))
                .addGap(13, 13, 13))
        );

        javax.swing.GroupLayout backJPanal6Layout = new javax.swing.GroupLayout(backJPanal6);
        backJPanal6.setLayout(backJPanal6Layout);
        backJPanal6Layout.setHorizontalGroup(
            backJPanal6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backJPanal6Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(time15Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );
        backJPanal6Layout.setVerticalGroup(
            backJPanal6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backJPanal6Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(time15Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        backJPanal5.setBackground(new java.awt.Color(255, 255, 255));

        time14Panel.setBackground(new java.awt.Color(0, 0, 0));
        time14Panel.setPreferredSize(new java.awt.Dimension(37, 37));
        time14Panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                time14PanelMouseClicked(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("14:00");

        time14Label.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        time14Label.setForeground(new java.awt.Color(255, 255, 255));
        time14Label.setText("jLabel16");

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("people");

        javax.swing.GroupLayout time14PanelLayout = new javax.swing.GroupLayout(time14Panel);
        time14Panel.setLayout(time14PanelLayout);
        time14PanelLayout.setHorizontalGroup(
            time14PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(time14PanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(time14PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(time14PanelLayout.createSequentialGroup()
                        .addComponent(time14Label, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel24)))
                .addContainerGap(102, Short.MAX_VALUE))
        );
        time14PanelLayout.setVerticalGroup(
            time14PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(time14PanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(time14PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(time14Label)
                    .addComponent(jLabel24))
                .addGap(13, 13, 13))
        );

        javax.swing.GroupLayout backJPanal5Layout = new javax.swing.GroupLayout(backJPanal5);
        backJPanal5.setLayout(backJPanal5Layout);
        backJPanal5Layout.setHorizontalGroup(
            backJPanal5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backJPanal5Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(time14Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );
        backJPanal5Layout.setVerticalGroup(
            backJPanal5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backJPanal5Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(time14Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        backJPanal7.setBackground(new java.awt.Color(255, 255, 255));

        time16Panel.setBackground(new java.awt.Color(0, 0, 0));
        time16Panel.setPreferredSize(new java.awt.Dimension(470, 37));
        time16Panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                time16PanelMouseClicked(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("16:00");

        time16Label.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        time16Label.setForeground(new java.awt.Color(255, 255, 255));
        time16Label.setText("jLabel16");

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("people");

        javax.swing.GroupLayout time16PanelLayout = new javax.swing.GroupLayout(time16Panel);
        time16Panel.setLayout(time16PanelLayout);
        time16PanelLayout.setHorizontalGroup(
            time16PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(time16PanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(time16PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(time16PanelLayout.createSequentialGroup()
                        .addComponent(time16Label, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel25)))
                .addContainerGap(77, Short.MAX_VALUE))
        );
        time16PanelLayout.setVerticalGroup(
            time16PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(time16PanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(time16PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(time16Label)
                    .addComponent(jLabel25))
                .addGap(13, 13, 13))
        );

        javax.swing.GroupLayout backJPanal7Layout = new javax.swing.GroupLayout(backJPanal7);
        backJPanal7.setLayout(backJPanal7Layout);
        backJPanal7Layout.setHorizontalGroup(
            backJPanal7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backJPanal7Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addComponent(time16Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        backJPanal7Layout.setVerticalGroup(
            backJPanal7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backJPanal7Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(time16Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        backJPanal8.setBackground(new java.awt.Color(255, 255, 255));

        time17Panel.setBackground(new java.awt.Color(0, 0, 0));
        time17Panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                time17PanelMouseClicked(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("17:00");

        time17Label.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        time17Label.setForeground(new java.awt.Color(255, 255, 255));
        time17Label.setText("jLabel16");

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("people");

        javax.swing.GroupLayout time17PanelLayout = new javax.swing.GroupLayout(time17Panel);
        time17Panel.setLayout(time17PanelLayout);
        time17PanelLayout.setHorizontalGroup(
            time17PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, time17PanelLayout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(time17PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(time17PanelLayout.createSequentialGroup()
                        .addComponent(time17Label, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel29)))
                .addGap(97, 97, 97))
        );
        time17PanelLayout.setVerticalGroup(
            time17PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(time17PanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(time17PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(time17Label)
                    .addComponent(jLabel29))
                .addGap(13, 13, 13))
        );

        javax.swing.GroupLayout backJPanal8Layout = new javax.swing.GroupLayout(backJPanal8);
        backJPanal8.setLayout(backJPanal8Layout);
        backJPanal8Layout.setHorizontalGroup(
            backJPanal8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backJPanal8Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(time17Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );
        backJPanal8Layout.setVerticalGroup(
            backJPanal8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backJPanal8Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(time17Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(dayLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(serviceCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(backJPanal2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(backJPanal5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(backJPanal3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(backJPanal7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(backJPanal1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(backJPanal4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(backJPanal6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(backJPanal8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(50, Short.MAX_VALUE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(serviceCombobox, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dayLabel))
                .addGap(30, 30, 30)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(backJPanal1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(backJPanal2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(backJPanal4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(backJPanal3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(backJPanal6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(backJPanal5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(backJPanal7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(backJPanal8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        backBtn.setText("Back");
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });

        comfrimBtn.setText("Comfrim");
        comfrimBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comfrimBtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(backBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comfrimBtn)
                .addGap(44, 44, 44))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backBtn)
                    .addComponent(comfrimBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_backBtnActionPerformed

    private void time9PanalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_time9PanalMouseClicked
        String[] parts =jLabel6.getText().split(":");
        if(checkQueue(time9Label)){
              System.out.println("ไอน้องพี่มาแล้ว "+ parts[0] );
        }
      
       selectPanel(backJPanal2);
      
    }//GEN-LAST:event_time9PanalMouseClicked

    private void time10PanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_time10PanelMouseClicked
      
          if(checkQueue(time10Label)){
              System.out.println("ไอน้องพี่มาแล้ว " + jLabel10.getText());
        }
      
          selectPanel(backJPanal1);
    }//GEN-LAST:event_time10PanelMouseClicked

    private void time11PanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_time11PanelMouseClicked
        
         if(checkQueue(time11Label)){
              System.out.println("ไอน้องพี่มาแล้ว "+ jLabel8.getText()  );
        }
      
         selectPanel(backJPanal3);
    }//GEN-LAST:event_time11PanelMouseClicked

    private void time14PanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_time14PanelMouseClicked
        
          if(checkQueue(time14Label)){
              System.out.println("ไอน้องพี่มาแล้ว " +jLabel9.getText() );
        }
      
          selectPanel(backJPanal5);
    }//GEN-LAST:event_time14PanelMouseClicked

    private void time15PanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_time15PanelMouseClicked
      
         if(checkQueue(time15Label)){
              System.out.println("ไอน้องพี่มาแล้ว "+jLabel11.getText()  );
        }
      
         selectPanel(backJPanal6);
    }//GEN-LAST:event_time15PanelMouseClicked

    private void time16PanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_time16PanelMouseClicked
       
         if(checkQueue(time16Label)){
              System.out.println("ไอน้องพี่มาแล้ว "+jLabel12.getText()  );
        }
      
         selectPanel(backJPanal7);
        
    }//GEN-LAST:event_time16PanelMouseClicked

    private void time17PanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_time17PanelMouseClicked
       
         if(checkQueue(time17Label)){
              System.out.println("ไอน้องพี่มาแล้ว "+jLabel13.getText()  );
        }
      
         selectPanel(backJPanal8);
    }//GEN-LAST:event_time17PanelMouseClicked

    private void time13PanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_time13PanelMouseClicked
       
         if(checkQueue(time13Label)){
              System.out.println("ไอน้องพี่มาแล้ว "+ jLabel10.getText()  );
        }
      
         selectPanel(backJPanal4);
    }//GEN-LAST:event_time13PanelMouseClicked

    private void comfrimBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comfrimBtnMouseClicked
       // timeComboBox.getSelectedItem().toString().trim().equals("----")
         String[] parts = null;
          AlertManager manager = new AlertManager();
        PopAlert alert = new PopAlert(parentFrame, true); // ใช้ dialog เดิม
        manager.registerObserver(alert);
         String service = (String) serviceCombobox.getSelectedItem();
        
        if(manager.checkService(service)){//เช็ค service
            if(manager.checkTime(selectedPanel)){//เช็คว่าเลือกเวลารึยัง
                System.out.println("service " +serviceCombobox.getSelectedItem() );

            if (selectedPanel == backJPanal2) {
                System.out.println("คุณเลือกเวลา 09:00");
                parts =jLabel6.getText().split(":");

            } else if (selectedPanel == backJPanal1) {
                System.out.println("คุณเลือกเวลา 10:00");
                parts =jLabel7.getText().split(":");
                
            }else if (selectedPanel == backJPanal3) {
                System.out.println("คุณเลือกเวลา 11:00");
                parts =jLabel8.getText().split(":");
                
            }else if (selectedPanel == backJPanal4) {
                System.out.println("คุณเลือกเวลา 13:00");
                parts =jLabel10.getText().split(":");
                
            }else if (selectedPanel == backJPanal5) {
                System.out.println("คุณเลือกเวลา 14:00");
                parts =jLabel9.getText().split(":");
            }else if (selectedPanel == backJPanal6) {
                System.out.println("คุณเลือกเวลา 15:00");
                parts =jLabel11.getText().split(":");
            }else if (selectedPanel == backJPanal7) {
                System.out.println("คุณเลือกเวลา 16:00");
                parts =jLabel12.getText().split(":");
            }else if (selectedPanel == backJPanal8) {
                System.out.println("คุณเลือกเวลา 17:00");
                parts =jLabel13.getText().split(":");
            }
            
            
             System.out.println("!!!!!    "  +parts[0]);
                 dispose();
                 new PopInBooking(null, true,username,parts[0],service,day,month,year);
            
                 
            }
                
        } 
    }//GEN-LAST:event_comfrimBtnMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBtn;
    private javax.swing.JPanel backJPanal1;
    private javax.swing.JPanel backJPanal2;
    private javax.swing.JPanel backJPanal3;
    private javax.swing.JPanel backJPanal4;
    private javax.swing.JPanel backJPanal5;
    private javax.swing.JPanel backJPanal6;
    private javax.swing.JPanel backJPanal7;
    private javax.swing.JPanel backJPanal8;
    private javax.swing.JButton comfrimBtn;
    private javax.swing.JLabel dayLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JComboBox<String> serviceCombobox;
    private javax.swing.JLabel time10Label;
    private javax.swing.JPanel time10Panel;
    private javax.swing.JLabel time11Label;
    private javax.swing.JPanel time11Panel;
    private javax.swing.JLabel time13Label;
    private javax.swing.JPanel time13Panel;
    private javax.swing.JLabel time14Label;
    private javax.swing.JPanel time14Panel;
    private javax.swing.JLabel time15Label;
    private javax.swing.JPanel time15Panel;
    private javax.swing.JLabel time16Label;
    private javax.swing.JPanel time16Panel;
    private javax.swing.JLabel time17Label;
    private javax.swing.JPanel time17Panel;
    private javax.swing.JLabel time9Label;
    private javax.swing.JPanel time9Panal;
    // End of variables declaration//GEN-END:variables
}
