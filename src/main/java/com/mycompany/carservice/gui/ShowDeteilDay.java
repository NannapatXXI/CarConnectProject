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
import javax.swing.JOptionPane;


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
          dayLabel.setText("Day : "+day +" "+ month + " " + year);
        
        setLocationRelativeTo(null);
        setVisible(true);  
        
        
      
        
    }
    /**
     * เช็คว่าวันนี้ผ่านมาแล้วหรือยัง
     * @param day วันที่จะเช็ค
     * @param month เดือนที่จะเช็ค
     * @param year ปีที่จะเช็ค
     * @param timeText เวลาที่จะเช็ค
     * @return  จริง ถ้าเป็นวันที่ผ่านมาแล้ว
     */
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
    
    
    /**
     * เช็คว่าเวลานี้ผ่านมาแล้วหรือยัง และปรับ UI
     * @param panel ที่ต้องการจะเช็ค
     * @param timeText เวลาเท่าไหร่
     */
    private void disablePastTime(javax.swing.JPanel panel, String timeText) {
        if (isPastDateTime(day, month, year, timeText)) {
            panel.setBackground(new Color(200, 200, 200)); // สีเทา
              panel.setEnabled(false); // ปิดการคลิก
            panel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR)); // เปลี่ยน cursor
            if (selectedPanel == panel) {
                selectedPanel = null; // เคลียร์ selectedPanel ถ้า panel ผ่านเวลา
            }
        }
    }
    
    
   /**
    * เช็ค Panel ที่เลือก
    * @param panel ที่ต้องการเลือก
    */
   private void selectPanel(javax.swing.JPanel panel) {
        if (!panel.isEnabled()) {
            selectedPanel = null;
            return; // panel ผ่านเวลาแล้ว เลือกไม่ได้
        }
        // คืนสี panel เก่ากลับเป็นสีขาว
        if (selectedPanel != null) {
            selectedPanel.setBackground(new Color(255,255,255)); 
        }

        // เปลี่ยนสี panel ใหม่เป็น selected
        panel.setBackground(new Color(100, 149, 237)); // สี panel ที่เลือก

        // บันทึก panel นี้เป็น selected
        selectedPanel = panel;
    }
    
   /**
    * นับจำนวนคนจองในวันนั้นว่าในแต่ละ ชม มีกี่คน
    * @param day วันที่จอง
    * @param month เดือนที่จอง
    * @param year ปีที่จอง
    * @return  ค่าที่ Map มาของวันนั้นว่าแต่ละ ชม มีกี่คน
    */
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
                String[] times = timeRange.split("-");
                if (times.length == 2) {
                    try {
                        LocalTime start = LocalTime.parse(times[0].trim(), timeFormatter);
                        LocalTime end = LocalTime.parse(times[1].trim(), timeFormatter);

                        // panel ที่สนใจ
                        String[] panelTimes = {"09:00","10:00","11:00","12:00",
                                               "13:00","14:00","15:00","16:00","17:00"};
                        
                        for (String panelTimeStr : panelTimes) {
                            LocalTime panelTime = LocalTime.parse(panelTimeStr, timeFormatter);
                            
                            //  นับเฉพาะ start <= panelTime < end
                            //  start <= panelTime < end  OR   start <= panelTime <= end
                            if ((panelTime.equals(start) || panelTime.isAfter(start)) &&  panelTime.isBefore(end)) {
                               

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

    // Debug
    for (Map.Entry<String, Integer> entry : timeCount.entrySet()) {
        System.out.println("เวลา " + entry.getKey() + " มีคนจอง " + entry.getValue() + " คน");
    }

    return timeCount;
}
   
   /**
    * ไว้เช็คว่าเวลาที่จะจองตั้งแต่เริ่มถึงจบนั้นว่างที่จะจองได้ไหม
    * @param startTime เวลาที่เริ่ม
    * @param endTime    เวลาที่จบ
    * @param timeCount  จำนวนคนจองในเวลานั้น
    * @param limit จำนวน จำกัด ต่อ ชม
    * @return  true ต่อเมื่อสามารถจองได้ , false ต่อเมื่อไม่สามารถจองได้
    */
   private boolean canBookTimeRange(String startTime, String endTime, Map<String, Integer> timeCount, int limit) {
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    LocalTime start = LocalTime.parse(startTime, timeFormatter);
    LocalTime end = LocalTime.parse(endTime, timeFormatter);

    String[] panelTimes = {"09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00"};

    for(String t : panelTimes){
        LocalTime panelTime = LocalTime.parse(t, timeFormatter);

        // panelTime อยู่ในช่วง start <= panelTime < end
        if(!panelTime.isBefore(start) && panelTime.isBefore(end)) {
            
            int count = timeCount.getOrDefault(t,0); // เอา t ที่เป็นเวลาไปเทียบหาจำนวนคนที่จองในเวลานั้น
            if(count >= limit){
                return false; // panel เต็ม
            }
        }
    }
    return true; // ทุก panel ยังจองได้
}

   /**
    * ปรับ UI ตามจำนวน คนจองและเวลา
    */
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

    /**
     * ค่อยเช็คว่าจำนวนคนเท่าไหร่ แล้วเปลี่ยนสี
     * @param panel ที่เปลี่ยนสี
     * @param label ที่จำเปลี่ยนจำนวน
     * @param timeKey ช่วงเวลาใน panel นั้น
     * @param timeCount ค่าที่ Map มาแล้วในแต่ละช่วงเวลา
     */
    private void updatePanelColor(javax.swing.JPanel panel, javax.swing.JLabel label,  String timeKey, Map<String, Integer> timeCount) {
        int count = timeCount.getOrDefault(timeKey, 0);

       
       if (count >= 5) {
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
    
    /**
     * เอาไว้เช็คว่ายังจองได้ไหม
     * @param label ที่จะเช็ค
     * @return   true = จองได้ , false = จองไม่ได้
     */
    private boolean checkQueue( javax.swing.JLabel label){
         
        AlertManager manager = new AlertManager();
        PopAlert alert = new PopAlert(parentFrame, true); // ใช้ dialog เดิม
        manager.registerObserver(alert);
        
        int count =Integer.parseInt(label.getText()) ;
         return manager.checkCount(count);
    }
    
    /**
     * เช็คว่าช่วงเวลานี้จองได้หริือไม่
     * @param panel ที่จะเช็ค
     * @return  เป็น true = ผ่านมาแล้ว , false = ยังไม่ผ่านมา
     */
    private boolean checkTime( javax.swing.JPanel panel ){
         boolean status;
        if(panel.isEnabled()){
            status = true;
        }else{
            status = false;
        }
        AlertManager manager = new AlertManager();
        PopAlert alert = new PopAlert(parentFrame, true); // ใช้ dialog เดิม
        manager.registerObserver(alert);
        
        
         return manager.checkTimeInDetail(status);
    }
    
   /**
    * โหลดข้อมูลจาก csv มาลงใน combobox
    */
   private void loadService() {
    CSVHandler csvHandler = new CSVHandler("src/main/data/service.csv");
    ArrayList<String[]> data = csvHandler.readCSV();

    // ใส่ item พิเศษเป็นช่องแรก
    serviceCombobox.insertItemAt("-- --", 0);
    serviceCombobox.setSelectedIndex(0);

    // เริ่มอ่านตั้งแต่ index 1 เพื่อข้าม header
    for (int i = 1; i < data.size(); i++) {
        String[] parts = data.get(i);
        if (parts.length >= 4) { // ตรวจสอบว่ามีคอลัมน์ Service, Details, Price, Time
            String serviceName = parts[0].trim();
            String time = parts[3].trim();
            String item = serviceName + " - " + time; // รวมชื่อ service กับเวลา
            serviceCombobox.addItem(item);
        }
    }
}

    /**
     * คำนวญเวลาที่จะจบ
     * @param startTime ชม ที่เริ่ม
     * @param hoursToAdd ทำกี่ชม
     * @return  ส่ง ชม ที่จะจบออกไป
     */
    private String calculateFinishTime(String startTime, int hoursToAdd) {
        try {
            String[] parts = startTime.split(":"); // แยกชั่วโมง เช่น 10:00 เป็น 10
            int hour = Integer.parseInt(parts[0]);
            int minute = Integer.parseInt(parts[1]);

            hour += hoursToAdd; // บวกชั่วโมง
            if(hour >= 24) hour -= 24; // ถ้าเกิน 24 ชั่วโมง

            return String.format("%02d:%02d", hour, minute);
        } catch (Exception e) {
            e.printStackTrace();
            return startTime; // fallback
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
            .addGap(0, 20, Short.MAX_VALUE)
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
            .addGap(0, 20, Short.MAX_VALUE)
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
            .addGap(0, 20, Short.MAX_VALUE)
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("เต็ม");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("ว่าง");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("ใกล้เต็ม");

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        serviceCombobox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        serviceCombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serviceComboboxActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Service :");

        dayLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        dayLabel.setText("Day : ");

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
                .addContainerGap(40, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(backJPanal2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addContainerGap(40, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(dayLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(42, 42, 42)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(serviceCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
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
                .addContainerGap(62, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(54, 54, 54))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(backBtn)
                        .addGap(18, 18, 18)
                        .addComponent(comfrimBtn)
                        .addGap(32, 32, 32))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40))))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comfrimBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(backBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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
        dispose();
    }//GEN-LAST:event_backBtnActionPerformed

    private void time9PanalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_time9PanalMouseClicked
        String[] parts =jLabel6.getText().split(":");
        
        //ต้องเช็คว่ามันเป็นของวันปัจจุบันไหม
        if(isPastDateTime(day, month, year, "09:00")){
             if(checkTime(backJPanal2)){
                System.out.println("เวลานี้ผ่านมาแล้ว ");
            }
        }else{
            if(checkQueue(time9Label)){
                System.out.println("จองได้ "+ parts[0] );
                selectPanel(backJPanal2);
            }else{
                 System.out.println("จองไม่ได้ "+ parts[0] );
            }
            
             
        }
       
        
      
       
      
    }//GEN-LAST:event_time9PanalMouseClicked

    private void time10PanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_time10PanelMouseClicked
        //เช็คว่าเวลานี้ผ่านมาแล้วหรือยัง
        if(isPastDateTime(day, month, year, "10:00")){
             if(checkTime(backJPanal1)){
                System.out.println("เวลานี้ผ่านมาแล้ว ");
            }
        }else{
            //เช็คว่าคนของเต็มยัง
            if(checkQueue(time10Label)){
                System.out.println("จองได้ " + jLabel10.getText());
                selectPanel(backJPanal1);
            }else{
                 System.out.println("จองไม่ได้ " + jLabel10.getText());
            }
            
             
        }
       
    }//GEN-LAST:event_time10PanelMouseClicked

    private void time11PanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_time11PanelMouseClicked
        if(isPastDateTime(day, month, year, "11:00")){
             if(checkTime(backJPanal3)){
                System.out.println("เวลานี้ผ่านมาแล้ว ");
            }
        }else{
            if(checkQueue(time11Label)){
                System.out.println("จองได้ " + jLabel11.getText());
                selectPanel(backJPanal3);
            }else{
                 System.out.println("จองไม่ได้ " + jLabel11.getText());
            }
            
             
        }
      
    }//GEN-LAST:event_time11PanelMouseClicked

    private void time14PanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_time14PanelMouseClicked
         if(isPastDateTime(day, month, year, "14:00")){
             if(checkTime(backJPanal5)){
                System.out.println("เวลานี้ผ่านมาแล้ว ");
            }
        }else{
            if(checkQueue(time14Label)){
                System.out.println("จองได้ " + jLabel9.getText());
                selectPanel(backJPanal5);
            }else{
                 System.out.println("จองไม่ได้ " + jLabel9.getText());
            }
            
             
        }
       
        
    }//GEN-LAST:event_time14PanelMouseClicked

    private void time15PanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_time15PanelMouseClicked
       if(isPastDateTime(day, month, year, "15:00")){
             if(checkTime(backJPanal6)){
                System.out.println("เวลานี้ผ่านมาแล้ว ");
            }
        }else{
            if(checkQueue(time15Label)){
                System.out.println("จองได้ " + jLabel11.getText());
                selectPanel(backJPanal6);
            }else{
                 System.out.println("จองไม่ได้ " + jLabel11.getText());
            }
            
             
        }
       
    }//GEN-LAST:event_time15PanelMouseClicked

    private void time16PanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_time16PanelMouseClicked
        if(isPastDateTime(day, month, year, "16:00")){
             if(checkTime(backJPanal7)){
                System.out.println("เวลานี้ผ่านมาแล้ว ");
            }
        }else{
            if(checkQueue(time16Label)){
                System.out.println("จองได้ " + jLabel12.getText());
                selectPanel(backJPanal7);
            }else{
                 System.out.println("จองไม่ได้ " + jLabel12.getText());
            }
            
             
        }
       
      
    }//GEN-LAST:event_time16PanelMouseClicked

    private void time17PanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_time17PanelMouseClicked
       if(isPastDateTime(day, month, year, "17:00")){
             if(checkTime(backJPanal8)){
                System.out.println("เวลานี้ผ่านมาแล้ว ");
            }
        }else{
            if(checkQueue(time17Label)){
                System.out.println("จองได้ " + jLabel13.getText());
                selectPanel(backJPanal8);
            }else{
                 System.out.println("จองไม่ได้ " + jLabel13.getText());
            }
            
             
        }
       
      
       
    }//GEN-LAST:event_time17PanelMouseClicked

    private void time13PanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_time13PanelMouseClicked
         if(isPastDateTime(day, month, year, "13:00")){
             if(checkTime(backJPanal4)){
                System.out.println("เวลานี้ผ่านมาแล้ว ");
            }
        }else{
            if(checkQueue(time13Label)){
                System.out.println("จองได้ " + jLabel10.getText());
                selectPanel(backJPanal4);
            }else{
                 System.out.println("จองไม่ได้ " + jLabel10.getText());
            }
            
             
        }
       
      
      
    }//GEN-LAST:event_time13PanelMouseClicked

    private void comfrimBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comfrimBtnMouseClicked
       /*
        if (selectedPanel == null || !selectedPanel.isEnabled()) {
            JOptionPane.showMessageDialog(this, "โปรดเลือกเวลาที่สามารถจองได้");
            selectedPanel = null; // ป้องกันกรณี panel ผ่านเวลาแต่ selectedPanel ยังชี้อยู่
            return;
        }*/
       
        AlertManager manager = new AlertManager();
        PopAlert alert = new PopAlert(parentFrame, true); // ใช้ dialog เดิม
        manager.registerObserver(alert);
        String service = (String) serviceCombobox.getSelectedItem();
         
        int serviceHours = 1; // default
        // กำหนด startTime ตาม panel
        String startTime = null;
        if(selectedPanel == backJPanal2){ startTime="09:00"; }
        else if(selectedPanel == backJPanal1){ startTime="10:00"; }
        else if(selectedPanel == backJPanal3){ startTime="11:00"; }
        else if(selectedPanel == backJPanal4){ startTime="13:00"; }
        else if(selectedPanel == backJPanal5){ startTime="14:00"; }
        else if(selectedPanel == backJPanal6){ startTime="15:00"; }
        else if(selectedPanel == backJPanal7){ startTime="16:00"; }
        else if(selectedPanel == backJPanal8){ startTime="17:00"; }

        // ดึง service และจำนวนชั่วโมง
        String serviceItem = (String) serviceCombobox.getSelectedItem();
    
        if(manager.checkService(service)){
            if(manager.checkTime(selectedPanel)){
                String[] splitItem = serviceItem.split("-");
                
                    if(splitItem.length == 2){
                        String timeStr = splitItem[1].trim(); // เช่น "1 hr" หรือ "2 hrs"
                        try {
                            serviceHours = Integer.parseInt(timeStr.split(" ")[0]);
                        } catch (NumberFormatException e) {
                            serviceHours = 1; // fallback
                        }
                    }

                
                String endTime = calculateFinishTime(startTime, serviceHours);//คืนเวลาที่งานเสร็จ

                
                Map<String,Integer> timeCount = countBookingByTime(day, month, year);// นับคนจองปัจจุบัน

                
                int limit = 5;// ตรวจสอบว่าช่วงเวลาว่าง
               
                if( manager.checkCantBook(canBookTimeRange(startTime, endTime, timeCount, limit))){
                    return;
                }
                dispose();
                 new PopInBooking(null, true,username,startTime,endTime,service,day,month,year);
        
            }

      
        }

      
    }//GEN-LAST:event_comfrimBtnMouseClicked

    private void serviceComboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serviceComboboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_serviceComboboxActionPerformed


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
