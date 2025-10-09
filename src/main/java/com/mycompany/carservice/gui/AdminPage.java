/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.carservice.gui;

import com.mycompany.carservice.entity.CSVHandler;
import com.mycompany.carservice.entity.RoundedPanel;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.AbstractCellEditor;
import javax.swing.border.MatteBorder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class AdminPage extends javax.swing.JFrame {
    private ArrayList<String[]> users;
    private TableRowSorter<DefaultTableModel> sorter;
    private CSVHandler csvHandler;
    private String userName;
     private String role;
     String selectedTable = "User";

    public AdminPage(String user,String role) {
       this.userName = user;
       this.role = role;
        initComponents();
        csvHandler = new CSVHandler("src/main/data/user.csv");
        
         SetupUi();
         SetupIcon();
         SetupInfo();
          getContentPane().setBackground(java.awt.Color.WHITE);

         loadCsvData();
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setVisible(true);
        username.setHorizontalAlignment(JLabel.RIGHT);
        username.setText(user);
       
       setupSearchComboBox();
    

    }
    
   
   
    private void SetupUi() {
        UIManager.put("Table.selectionBackground", new Color(60, 60, 60));
        UIManager.put("Table.selectionForeground",new Color(255, 255, 255));
        UIManager.put("Table.alternateRowColor", new Color(240, 240, 240));
        totaluser.setHorizontalTextPosition(JLabel.LEFT); // ข้อความอยู่ซ้าย, icon อยู่ขวา
        complete.setHorizontalTextPosition(JLabel.LEFT); // ข้อความอยู่ซ้าย, icon อยู่ขวา
        inprocess.setHorizontalTextPosition(JLabel.LEFT); // ข้อความอยู่ซ้าย, icon อยู่ขวา
        task.setHorizontalTextPosition(JLabel.LEFT); // ข้อความอยู่ซ้าย, icon อยู่ขวา
        
        bookingBtn.setContentAreaFilled(false);
        bookingBtn.setBorderPainted(false);

        historyBtn.setContentAreaFilled(false);
        historyBtn.setBorderPainted(false);

        profileBtn.setContentAreaFilled(false);
        profileBtn.setBorderPainted(false);

        homeBtn.setContentAreaFilled(false);
        homeBtn.setBorderPainted(false);
    
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
                    
                    URL totaluserIconURL = new File("src/main/image/user.png").toURI().toURL();
                    URL completeIconURL = new File("src/main/image/checked.png").toURI().toURL();
                    URL inprocessIconURL = new File("src/main/image/iteration.png").toURI().toURL();
                    URL taskIconURL = new File("src/main/image/file.png").toURI().toURL();
                    
                    URL adduserIconURL = new File("src/main/image/adduser.png").toURI().toURL();
                   
                    logo.setIcon(new ImageIcon(logoIconURL));
                    iconHome.setIcon(new ImageIcon(homeIconURL));
                    iconBooking.setIcon(new ImageIcon(bookingIconURL));
                    iconHistory.setIcon(new ImageIcon(historyIconURL));
                    iconProfile.setIcon(new ImageIcon(profileIconURL));
                    iconAdmin.setIcon(new ImageIcon(adminIconURL));
                    iconExit.setIcon(new ImageIcon(exitIconURL));
                    
                    totaluser.setIcon(new ImageIcon(totaluserIconURL));
                    complete.setIcon(new ImageIcon(completeIconURL));
                    inprocess.setIcon(new ImageIcon(inprocessIconURL));
                    task.setIcon(new ImageIcon(taskIconURL));
                    
                    adduserLable.setIcon(new ImageIcon(adduserIconURL));
                    
                } catch (Exception e) {
                    System.out.println(e);
                   
                }
    
    }
     
     
   private void SetupInfo(){
        CSVHandler csvHandler = new CSVHandler("src/main/data/user.csv");
          CSVHandler csvHandlerHistory = new CSVHandler("src/main/data/history_user.csv");
        LocalDate today = LocalDate.now();//ดึงวันปัจจุบัน
        int countProcess = 0;
        int countCompleted = 0;
         int countTask = 0;
      //สำหรับ Total
         ArrayList<String[]> users = new ArrayList<>(csvHandler.readCSV());
        int count =  users.size() - 1;
        System.out.println("จำนวน User :" + count);
        
        if(count != 0){
             totalUserLable.setText(Integer.toString(count));
        }else{
             totalUserLable.setText("!!!! โหลดผู้ใช้ไม่ได้");
        }
        
   
        // อ่านข้อมูลทั้งหมดจาก CSV
         ArrayList<String[]> usersHistory = csvHandlerHistory.readCSV();
         
         for (int i = 1; i < usersHistory.size(); i++) {   // เริ่มที่ 1 ไม่เอา header
             String[] history = usersHistory.get(i);
                if(history[7].equalsIgnoreCase("complete")){
                    System.out.println("complete " );
                    countCompleted++;
                }else if(history[7].equalsIgnoreCase("process")){
                     System.out.println("process"  );
                     countProcess++;
                }   
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH);
                LocalDate inputDate = LocalDate.parse(history[3], formatter);

               //นับจำนวนงานนั้นวันนั้น
                if(today.equals(inputDate)){
                    System.out.println("วันนี้ตรงกับ " + history[3]);
                    countTask++;
                }
        }
         
        if(countCompleted != 0){
             completedLable.setText(Integer.toString(countCompleted));
        }else{
             completedLable.setText("!!!! โหลดข้อมูลไม่ได้");
        }
        if(countProcess != 0){
             processLable.setText(Integer.toString(countProcess));
        }else{
             processLable.setText("!!!! โหลดข้อมูลไม่ได้");
        }
        if(countTask != 0){
             taskLable.setText(Integer.toString(countTask));
        }else{
             taskLable.setText("!!!! โหลดข้อมูลไม่ได้");
        }
         
      
   }

   private void loadCsvData() {
    users = new ArrayList<>(csvHandler.readCSV());
    if (users.isEmpty()){
        return;
    }

    // header จากไฟล์จริง
    String[] fileHeaders = users.get(0); // บรรทัดแรกคือ header
    String[] headers = new String[fileHeaders.length + 2];
    headers[0] = "No";//สร้างแถวขึ้นมาใหม่เพราะจะ run เลขเอง
    System.arraycopy(fileHeaders, 0, headers, 1, fileHeaders.length);
    headers[headers.length - 1] = "Action";

    DefaultTableModel model = new DefaultTableModel(headers, 0);//เริ่มจากแถว 0

    // เติมข้อมูล (เริ่มจากบรรทัดที่ 2 ของ CSV)
    for (int i = 1; i < users.size(); i++) {
        String[] userRow = users.get(i);
        if (userRow == null || userRow.length == 0) continue;

        String[] row = new String[userRow.length + 2];
        row[0] = null; // No
        System.arraycopy(userRow, 0, row, 1, userRow.length);//ดึงข้อมูลจาก CSV 1 แถวไปเก็บใน row
        row[row.length - 1] = "Edit"; // Action ปุ่ม

        model.addRow(row);
    }

    jTable1.setModel(model);
    jTable1.setRowHeight(50);

    // สร้าง คอลัมน์ No 
    jTable1.getColumn("No").setCellRenderer(new RowNumberRenderer());

    // sorter
    sorter = new TableRowSorter<>(model);
    jTable1.setRowSorter(sorter);

    // จัดข้อความให้อยู่ตรงกลาง
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

    for (int i = 0; i < jTable1.getColumnCount(); i++) {
        if (!jTable1.getColumnName(i).equals("Action") &&
            !jTable1.getColumnName(i).equals("No")) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    
    // --- ปุ่ม Action ---
    TableColumn actionColumn = jTable1.getColumn("Action");
    actionColumn.setCellRenderer(new ButtonRenderer());//วาดปุ่มออกมา
    actionColumn.setCellEditor(new ButtonEditor(jTable1, csvHandler));//จัดการ action
    actionColumn.setPreferredWidth(120);

    // ถ้าเลือกดู History ให้ใส่ status เพิ่ม 
    String selected = chooseTable.getSelectedItem().toString();
    if (selected.equals("History")) {
        int statusColumnIndex = headers.length - 2; // Status อยู่ก่อน Action
        jTable1.getColumnModel().getColumn(statusColumnIndex).setCellRenderer(new StatusRenderer());//สร้าง คอลัมน์ Status ลง Table
    }

    setupFilter();
}

    
   /**
    * ค้นหาข้อมูลใน Table
    */
    private void setupFilter() {
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void filter() {
                if (sorter == null) return; // ป้องกัน null
                String text = searchField.getText().trim();
                if (text.isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    int colIndex = getSelectedColumnIndex(); // เอาไปดึงว่าเลือกจะค้นหาอะไร ex. วัน , บริการ ส่งออกมาเป็นเลข index ของ คอลัมน์
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, colIndex));//กรองข้อความจากแถวที่เลือกแบบ ไม่สนใจพิมพ์เล๋กใหญ่ (?i)
                }
            }

            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }
        });

        // เวลาเปลี่ยน combobox ก็ให้รีเฟรช filter ด้วย
       
    }

    
    /**
     * ใส่ item เข้า combobox
     */
    private void setupSearchComboBox() {
        
    searchComboBox.removeAllItems();
    if (selectedTable.equals("User")) {
        searchComboBox.addItem("Search : ID");
        searchComboBox.addItem("Search : Name");
    } else {
        searchComboBox.addItem("Search : ID");
        searchComboBox.addItem("Search : Service");
        searchComboBox.addItem("Search : Date");
        searchComboBox.addItem("Search : Status");
    }
    
}
   
    
    /**
    * เอาไว้ดึงเลข index จาก item ที่เลือกใน searchComboBox
    * @return index ของ คอลัมน์
    */
    private int getSelectedColumnIndex() {
       
        String selected;
        System.out.println(" Table :" +  selectedTable);
        
        if(selectedTable.equals("User")){     
            selected = (String) searchComboBox.getSelectedItem();
              
            switch (selected) {//สำหรับ Table History
                case "Search : ID":
                    return 1; // column 0 = ID
                case "Search : Name":
                    return 2; // column 2 = Service
                default:
                    return 1; // fallback ไปที่ Name
            }
        
        }else{
            selected = (String) searchComboBox.getSelectedItem();
              
             switch (selected) {//สำหรับ Table History
                case "Search : ID":
                    return 1; // column 0 = ID
                case "Search : Service":
                    return 3; // column 2 = Service
                case "Search : Date":
                    return 4; // column 3 = Date
                case "Search : Status":
                    return 8; // column 6 = Status
                default:
                    return 1; // fallback ไปที่ Name
            }
        }
        
        
        
    }


    /**
    * เอาไว้สร้างคอลัมน์ Status
    */
    class StatusRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (value != null) {
                
                 String status = value.toString().toLowerCase();
                 
                    if(status.equals("process")) {
                         setBackground(Color.YELLOW);
                    } else if (status.equals("complete")) {
                         setBackground(Color.GREEN);
                    }else if (status.equals("booked")) {
                         setBackground(Color.GRAY);
                    } else {
                         setBackground(table.getBackground());
                    }
            } else {
                  setBackground(table.getBackground());
            }

            setForeground(Color.BLACK);

             // สร้างขอบด้านบนและล่าง สีดำ หนา 1 px
             setBorder(new MatteBorder(1, 0, 1, 0, Color.BLACK));

             setHorizontalAlignment(SwingConstants.CENTER);

             return this;
        }
    }

   /**
    * สร้างปุ่มใน Action
    */
    class ButtonRenderer extends JPanel implements TableCellRenderer { // Renderer สำหรับปุ่ม  / Renderer = แสดงผล
       private final JButton editButton;
       private final JButton deleteButton;

        public ButtonRenderer() {
            setOpaque(true);
             editButton = new JButton("Edit");
            deleteButton = new JButton("Delete");
            
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 12));
            
          editButton.setMargin(new Insets(10,10,5,10));
          deleteButton.setMargin(new Insets(10,10,5,10));
           
           
            add(editButton);
            add(deleteButton);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
           
            if (isSelected) {
               setBackground(table.getSelectionBackground());
            } else {
               setBackground(table.getBackground());
            }
            
             return this;
        }
    }

    
    // Editor สำหรับปุ่ม  /Editor = “รับ event คลิก แล้วทำงาน
    /**
     * ทำ Action ให้กับปุ่ม
     */
    class ButtonEditor extends AbstractCellEditor implements TableCellEditor {//TableCellEditor คือ interface ที่ JTable ใช้เมื่อต้องการ component ที่ตอบสนองการแก้ไข
        private final JPanel panel;
        private final JButton editButton;
        private final JButton deleteButton;
        private final JTable table;
        private final CSVHandler csvHandler;

        public ButtonEditor(JTable table, CSVHandler handler) {
            this.table = table;
            this.csvHandler = handler;

            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 12));

            editButton = new JButton("Edit");
            deleteButton = new JButton("Delete");

            editButton.setMargin(new Insets(10,10,5,10));
            deleteButton.setMargin(new Insets(10,10,5,10));
            editButton.setPreferredSize(new Dimension(50, 50));
            deleteButton.setPreferredSize(new Dimension(50, 50));


            panel.add(editButton);
            panel.add(deleteButton);

            // Event Edit
            editButton.addActionListener(e -> {
                // เพิ่มบรรทัดนี้เพื่อให้คลิกครั้งเดียวทำงาน
                if (table.getCellEditor() != null) table.getCellEditor().stopCellEditing();

                int row = table.getSelectedRow(); // ใช้ selectedRow แทน editingRow
                if(row != -1) {
                    openEditDialog(row);
                }
                fireEditingStopped();

            });
            // Event Delete
           deleteButton.addActionListener(e -> {
                if (table.getCellEditor() != null){
                    table.getCellEditor().stopCellEditing();
                }
                int row = table.getSelectedRow();
                if(row != -1) {
                    int confirm = JOptionPane.showConfirmDialog(
                                 SwingUtilities.getWindowAncestor(table),
                                "Are you sure you want to delete this row?",
                                "Confirm Delete",
                                JOptionPane.YES_NO_OPTION
                    );
                    if(confirm == JOptionPane.YES_OPTION) {
                        deleteRow(row);  // เรียก method ภายใน ButtonEditor
                    }
                }
                fireEditingStopped();
            });



    }
     /**
      * เอาไว้เปิดหน้าต่างการแก้ไขข้อมูล
      * @param รับ row  ที่จะแก้
      */
    private void openEditDialog(int row) {
        int colCount = table.getColumnCount() - 2;
        String[] data = new String[colCount];
        for (int i = 0; i < colCount; i++) {
            Object value = table.getValueAt(row, i + 1);
            data[i] = value != null ? value.toString() : "";
        }

        String selected = chooseTable.getSelectedItem().toString();
        if(selected.equals("User")) {
            new PopInAdiminUser((Frame) SwingUtilities.getWindowAncestor(table), true, row, data, csvHandler).setVisible(true);
        } else if(selected.equals("History")) {
            new PopInAdiminHistory((Frame) SwingUtilities.getWindowAncestor(table), true, row, data, csvHandler).setVisible(true);
        }
    }
    /**
     * เอาไว้ลบ row ที่เลือก
     * @param row  ที่จะแก้
     */
    private void deleteRow(int row) {
        try {
            // แปลง index หลัง filter/sort เป็น model index
            int modelRow = table.convertRowIndexToModel(row);//อาจ sort/filter ทำให้ index view != index model
            csvHandler.deleteRow(modelRow); 
            ((DefaultTableModel) table.getModel()).removeRow(modelRow);
        } catch(Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(table, "Failed to delete row: " + ex.getMessage());
        }
    }


    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
         
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }
    }

   
    /**
     * เอาไว้สลับตาราง
     */
    private void switchTable() {
         selectedTable = chooseTable.getSelectedItem().toString();
        if(selectedTable.equals("User")) {
             csvHandler = new CSVHandler("src/main/data/user.csv");
        } else if(selectedTable.equals("History")) {
            csvHandler = new CSVHandler("src/main/data/history_user.csv");
        }
        setupSearchComboBox();
        loadCsvData(); // โหลดข้อมูลใหม่
    }
    
    //เขียนเลขขึ้นมาเองตามจำนวนข้อมูล
    class RowNumberRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // แสดงหมายเลขลำดับตาม row ที่เห็น (หลัง filter/sort แล้ว)
            setText(String.valueOf(row + 1));//ดึง index ของ table มาบวก
            setHorizontalAlignment(SwingConstants.CENTER);

       
            return this;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new RoundedPanel(30); // 30 radius;
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        searchField = new javax.swing.JTextField();
        chooseTable = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        addUserBtn = new RoundedPanel(30); // 30 radius;
        adduserLable = new javax.swing.JLabel();
        searchComboBox = new javax.swing.JComboBox<>();
        jPanel4 = new RoundedPanel(30); // 30 radius;
        totalUserLable = new javax.swing.JLabel();
        totaluser = new javax.swing.JLabel();
        jPanel7 = new RoundedPanel(30); // 30 radius;
        completedLable = new javax.swing.JLabel();
        complete = new javax.swing.JLabel();
        jPanel8 = new RoundedPanel(30); // 30 radius;
        processLable = new javax.swing.JLabel();
        inprocess = new javax.swing.JLabel();
        jPanel9 = new RoundedPanel(30); // 30 radius;
        taskLable = new javax.swing.JLabel();
        task = new javax.swing.JLabel();
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
        iconExit = new javax.swing.JLabel();
        username = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(240, 240, 240));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jTable1);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 820, 390));

        searchField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchFieldActionPerformed(evt);
            }
        });
        jPanel3.add(searchField, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, 230, 30));

        chooseTable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "User", "History" }));
        chooseTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseTableActionPerformed(evt);
            }
        });
        jPanel3.add(chooseTable, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 30, 110, 30));

        jLabel4.setText("Table :");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 30, -1, 30));

        addUserBtn.setBackground(new java.awt.Color(255, 255, 255));
        addUserBtn.setForeground(new java.awt.Color(216, 216, 216));
        addUserBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addUserBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addUserBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addUserBtnMouseExited(evt);
            }
        });
        addUserBtn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        adduserLable.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        adduserLable.setText("Add user");
        addUserBtn.add(adduserLable, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 90, 40));

        jPanel3.add(addUserBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 20, 130, 40));

        searchComboBox.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        searchComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchComboBoxActionPerformed(evt);
            }
        });
        jPanel3.add(searchComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 120, 30));

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        totalUserLable.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        totalUserLable.setText("jLabel5");
        jPanel4.add(totalUserLable, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 140, 46));

        totaluser.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        totaluser.setText("Total users  ");
        jPanel4.add(totaluser, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 30));

        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        completedLable.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        completedLable.setText("jLabel5");
        jPanel7.add(completedLable, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 140, 46));

        complete.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        complete.setText("Competed  ");
        jPanel7.add(complete, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 30));

        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        processLable.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        processLable.setText("jLabel5");
        jPanel8.add(processLable, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 140, 46));

        inprocess.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        inprocess.setText("In process  ");
        jPanel8.add(inprocess, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 30));

        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        taskLable.setFont(new java.awt.Font("Tahoma", 1, 28)); // NOI18N
        taskLable.setText("jLabel5");
        jPanel9.add(taskLable, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 140, 46));

        task.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        task.setText("Today tasks  ");
        jPanel9.add(task, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, 30));

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

        adminBtn.setBackground(new java.awt.Color(255, 157, 0));
        adminBtn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        adminBtn.setForeground(new java.awt.Color(255, 255, 255));
        adminBtn.setText("Admin   ");
        adminBtn.setPreferredSize(new java.awt.Dimension(164, 90));
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

        iconExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconExitMouseClicked(evt);
            }
        });

        username.setBackground(new java.awt.Color(0, 0, 0));
        username.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        username.setText("..");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(iconExit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(56, 56, 56)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(54, 54, 54)
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 896, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(61, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(iconExit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chooseTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseTableActionPerformed
        switchTable();
    }//GEN-LAST:event_chooseTableActionPerformed

    private void addUserBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addUserBtnMouseEntered
        addUserBtn.setBackground(new Color(216,216,216));
        

    }//GEN-LAST:event_addUserBtnMouseEntered

    private void addUserBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addUserBtnMouseExited
        addUserBtn.setBackground(new Color(255,255,255));
         adduserLable.setForeground(Color.BLACK);
    }//GEN-LAST:event_addUserBtnMouseExited

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

    private void bookingBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bookingBtnMouseClicked
        dispose();
        new BookingPage(userName,role);
    }//GEN-LAST:event_bookingBtnMouseClicked

    private void bookingBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bookingBtnMouseEntered
       
           bookingBtn.setForeground(Color.WHITE);
    }//GEN-LAST:event_bookingBtnMouseEntered

    private void bookingBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bookingBtnMouseExited

         bookingBtn.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_bookingBtnMouseExited

    private void homeBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeBtnMouseEntered
       
          homeBtn.setForeground(Color.WHITE);
    }//GEN-LAST:event_homeBtnMouseEntered

    private void homeBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeBtnMouseExited
       homeBtn.setForeground(new Color(204,204,204));
    }//GEN-LAST:event_homeBtnMouseExited

    private void addUserBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addUserBtnMouseClicked
       new AddUser();
    }//GEN-LAST:event_addUserBtnMouseClicked

    private void iconExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconExitMouseClicked
        dispose();
        new Login();
    }//GEN-LAST:event_iconExitMouseClicked

    private void searchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchFieldActionPerformed
       if (!searchField.getText().trim().isEmpty()) {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchField.getText().trim(),
                                                         getSelectedColumnIndex()));
               
            }
    }//GEN-LAST:event_searchFieldActionPerformed

    private void searchComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchComboBoxActionPerformed
       searchField.setText("");
    }//GEN-LAST:event_searchComboBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel addUserBtn;
    private javax.swing.JLabel adduserLable;
    private javax.swing.JButton adminBtn;
    private javax.swing.JButton bookingBtn;
    private javax.swing.JComboBox<String> chooseTable;
    private javax.swing.JLabel complete;
    private javax.swing.JLabel completedLable;
    private javax.swing.JButton historyBtn;
    private javax.swing.JButton homeBtn;
    private javax.swing.JLabel iconAdmin;
    private javax.swing.JLabel iconBooking;
    private javax.swing.JLabel iconExit;
    private javax.swing.JLabel iconHistory;
    private javax.swing.JLabel iconHome;
    private javax.swing.JLabel iconProfile;
    private javax.swing.JLabel inprocess;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel logo;
    private javax.swing.JLabel processLable;
    private javax.swing.JButton profileBtn;
    private javax.swing.JComboBox<String> searchComboBox;
    private javax.swing.JTextField searchField;
    private javax.swing.JLabel task;
    private javax.swing.JLabel taskLable;
    private javax.swing.JLabel totalUserLable;
    private javax.swing.JLabel totaluser;
    private javax.swing.JLabel username;
    // End of variables declaration//GEN-END:variables
}
