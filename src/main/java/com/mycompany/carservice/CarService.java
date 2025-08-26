package com.mycompany.carservice;

import com.mycompany.carservice.gui.CsvTableDemo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CarService {

    public static void main(String[] args) {
        
        
        InputStream info = CarService.class.getResourceAsStream("/data.csv");
        if (info == null) {
                System.out.println("ไฟล์ CSV ไม่เจอ!");
                return;
        }       


        try (BufferedReader br = new BufferedReader(new InputStreamReader(info))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                System.out.println("Column1: " + data[0] + " , Column2: " + data[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        System.out.println("Hello World!");
       
        try {
            com.formdev.flatlaf.FlatLightLaf.setup();
        } catch (Exception ex) {
         ex.printStackTrace();
        }

        // new BookingPage("Nannapat");  // เรียก GUI ของคุณ

                new CsvTableDemo().setVisible(true);
   
    }
}
