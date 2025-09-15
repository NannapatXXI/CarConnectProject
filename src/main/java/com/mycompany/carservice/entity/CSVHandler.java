package com.mycompany.carservice.entity;

import java.io.*;
import java.util.ArrayList;


public class CSVHandler {

    private String filePath;

    public CSVHandler(String filePath) {
        this.filePath = filePath;
    }

    // อ่าน CSV ทั้งหมด
    public ArrayList<String[]> readCSV() {
        ArrayList<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // แยกข้อมูลด้วย comma
                String[] values = line.split(",");
                data.add(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    // เขียน CSV ทับไฟล์เดิม
    public void writeCSV(ArrayList<String[]> data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String[] row : data) {
                String line = String.join(",", row);
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}