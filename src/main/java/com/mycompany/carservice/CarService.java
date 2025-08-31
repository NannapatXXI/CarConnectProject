package com.mycompany.carservice;

import com.mycompany.carservice.entity.CSVHandler;
import com.mycompany.carservice.gui.CsvTableDemo;

import java.io.InputStream;
import java.util.List;       // สำหรับ List
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;

public class CarService {

    public static void main(String[] args) {
          String path = "/Users/nannapat/Downloads/CarserviceProjectV0.1-main/src/main/data/user.csv";
        CSVHandler csvHandler = new CSVHandler(path);

        // อ่าน CSV
        List<String[]> data = csvHandler.readCSV();
        System.out.println("ข้อมูลที่อ่านจาก CSV:");
        for (String[] row : data) {
            System.out.println(String.join(" | ", row));
        }

        // แก้ไขข้อมูล (เช่น เปลี่ยน status ของ row แรก)
        if (data.size() > 1) { // row แรกคือ header
            data.get(2)[4] = "WTF"; 
            
            //แถว 2 ช่อง 4
        }

        // เขียนทับไฟล์ CSV
        csvHandler.writeCSV(data);
        System.out.println("เขียนไฟล์ CSV เสร็จเรียบร้อย!");
        System.out.println("Hello World!");
        try {
        UIManager.setLookAndFeel(new FlatLightLaf());
        UIManager.put("Button.arc", 20);
        UIManager.put("Component.arc", 20);
        UIManager.put("CheckBox.arc", 20);
        UIManager.put("ProgressBar.arc", 20);
    } catch (Exception e) {
        e.printStackTrace();
    }
    java.awt.EventQueue.invokeLater(() -> {
       // new BookingPage("User");
         new CsvTableDemo().setVisible(true);
    });
       
         // new CsvTableDemo().setVisible(true);
    }
}
