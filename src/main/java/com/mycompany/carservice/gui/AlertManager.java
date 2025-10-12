/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.carservice.gui;

import com.mycompany.carservice.entity.Observer;
import com.mycompany.carservice.entity.Subject;
import java.util.ArrayList;

/**
 *
 * @author nannapat
 */
public class AlertManager implements Subject {

    private final ArrayList<Observer> observers = new ArrayList<>();

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer o : observers) {
            o.update(message);
        }
    }

    // ตัวอย่าง method สำหรับแจ้งเตือน
    public void checkBooking(int day, int time, int service) {
        if(day <= 0){
            notifyObservers("ยังไม่ได้เลือกวัน");
        } else if(time <= 0){
            notifyObservers("ยังไม่ได้เลือกเวลา");
        } else if(service <= 0){
            notifyObservers("ยังไม่ได้เลือกบริการ");
        }
    }
    
    
    public boolean checkCount(int count){
        if(count >= 5 ){
         notifyObservers("เวลานี้จองเต็มแล้ว");
         return false;
        }
        return true;
    }
    
      public boolean checkService( String service){
        if(service.equals("-- --")){
         notifyObservers("เลือก Service ก่อน");
         return false;
        }
        return true;
    }
      public boolean checkTime( javax.swing.JPanel panel){
        if(panel == null){
         notifyObservers("เลือก เวลา ก่อน");
         return false;
        }
        return true;
    }
      public boolean checkCantBook( boolean check){
        if(!check){
         notifyObservers("เวลาก่อนหน้าเต็มแล้ว");
         return true;
        }
        return false;
    }
      public boolean checkTimeInDetail( boolean check){
        if(check){
         notifyObservers("เวลานี้ผ่านมาแล้ว");
         return true;
        }
        return false;
    }
    public void showMessage(String message) {
    notifyObservers(message); // เด้งข้อความไปยัง observer ที่ register ไว้
    }
    
    
    
    
}
