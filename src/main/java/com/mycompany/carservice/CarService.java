package com.mycompany.carservice;

import com.mycompany.carservice.entity.CSVHandler;
import com.mycompany.carservice.gui.HomePage;

import java.io.InputStream;
import java.util.List;       // สำหรับ List
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;


public class CarService {

    public static void main(String[] args) {
        System.out.println(" V0.3");
        try {
        UIManager.setLookAndFeel(new FlatLightLaf());
        UIManager.put("Button.arc", 20);
        UIManager.put("Component.arc", 20);
        UIManager.put("TextComponent.arc", 20);
        UIManager.put("ProgressBar.arc", 20);
    } catch (Exception e) {
        e.printStackTrace();
    }
    java.awt.EventQueue.invokeLater(() -> {
       new HomePage();
       // Profile();
        // new CsvTableDemo().setVisible(true);
    });
       
         
    }
}
