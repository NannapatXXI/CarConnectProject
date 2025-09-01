package com.mycompany.carservice;

import com.mycompany.carservice.entity.CSVHandler;
import com.mycompany.carservice.gui.HomePage;

import java.io.InputStream;
import java.util.List;       // สำหรับ List
import com.formdev.flatlaf.FlatLightLaf;
import com.mycompany.carservice.gui.Login;
import com.mycompany.carservice.gui.Register;
import javax.swing.*;

public class CarService {

    public static void main(String[] args) {
          String path = "/Users/nannapat/Downloads/CarserviceProject v0.3/src/main/data/history_user.csv";

        CSVHandler csvHandler = new CSVHandler(path);

        // อ่าน CSV
        List<String[]> data = csvHandler.readCSV();
        System.out.println("ข้อมูลที่อ่านจาก CSV:");
        for (String[] row : data) {
            System.out.println(String.join(" | ", row));
        }

        // แก้ไขข้อมูล (เช่น เปลี่ยน status ของ row แรก)
        if (data.size() > 1) { // row แรกคือ header
            data.get(2)[6] = "WTF"; 
            
            //แถว 2 ช่อง 4
        }

        // เขียนทับไฟล์ CSV
        csvHandler.writeCSV(data);
        System.out.println("เขียนไฟล์ CSV เสร็จเรียบร้อย!");
        System.out.println(" V0.3");
        
        try {
            
        UIManager.put("Button.arc", 555);
        UIManager.put("Component.arc", 555);
        UIManager.put("CheckBox.arc", 555);
        UIManager.put("TextComponent.arc", 555);
        
        UIManager.setLookAndFeel(new FlatLightLaf());
    } catch (Exception e) {
        e.printStackTrace();
    }
    java.awt.EventQueue.invokeLater(() -> {
       //new HomePage();
       // new CsvTableDemo().setVisible(true);
       new Login();
       //new Register();
        
    });
       
         
    }
}
