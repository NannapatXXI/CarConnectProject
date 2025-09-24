package com.mycompany.carservice.entity;

import java.io.*;
import java.util.ArrayList;
import com.mycompany.carservice.entity.CSVHandler;


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
    // เพิ่มข้อมูลใหม่ต่อท้าย CSV
    public void appendCSV(String[] newRow) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) { // true = append
        String line = String.join(",", newRow);
        // ใช้ System.lineSeparator() เพื่อให้แน่ใจว่าจะขึ้นบรรทัดใหม่ทุก OS
        bw.write(line + System.lineSeparator());
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    public void deleteRow(int rowIndex) {
    ArrayList<String[]> data = readCSV(); // อ่านข้อมูลทั้งหมด
    if(rowIndex < 0 || rowIndex >= data.size()) {
        throw new IndexOutOfBoundsException("Invalid row index: " + rowIndex);
    }
    
    
    data.remove(rowIndex); // ลบแถว
    writeCSV(data);       // เขียนทับไฟล์ CSV
}
}