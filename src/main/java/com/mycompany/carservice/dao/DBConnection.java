/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carservice.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


//คอมเม้นทั้งไฟล์นี้ด้วย

/**
 *
 * @author nannapat
 */
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/CarServiceProject";
    private static final String USER = "root";      
    private static final String PASSWORD = "480621@Pk";  

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to database", e);
        }
    }
}
