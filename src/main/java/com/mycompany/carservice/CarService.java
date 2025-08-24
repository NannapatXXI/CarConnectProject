/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.carservice;

import com.mycompany.carservice.gui.MainPage;
import com.mycompany.carservice.dao.DBConnection;
import java.sql.Connection;
/**
 *
 * @author nannapat
 */
public class CarService {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        new MainPage();
        
        //โหลดไฟล์ไปแล้วคอมเม้นบรรทัดพวกนี้ทิ้งด้วยเพราะเชื่อม database ไว้
         try (Connection conn = DBConnection.getConnection()) {
            System.out.println("✅ Connected to MySQL successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
