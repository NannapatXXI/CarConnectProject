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
            notifyObservers("Choose Day");
        } else if(time <= 0){
            notifyObservers("Choose Time");
        } else if(service <= 0){
            notifyObservers("Choose Service");
        }
    }
    
    
    public boolean checkCount(int count){
        if(count >= 5 ){
         notifyObservers("This time is full");
         return false;
        }
        return true;
    }
    
      public boolean checkService( String service){
        if(service.equals("-- --")){
         notifyObservers("Choose service");
         return false;
        }
        return true;
    }
      public boolean checkTime( javax.swing.JPanel panel){
        if(panel == null){
         notifyObservers("Choose Time");
         return false;
        }
        return true;
    }
      public boolean checkCantBook( boolean check){
        if(!check){
         notifyObservers("previous time was full");
         return true;
        }
        return false;
    }
      public boolean checkTimeInDetail( boolean check){
        if(check){
         notifyObservers("Time has passed");
         return true;
        }
        return false;
    }
    public void showMessage(String message) {
    notifyObservers(message); // เด้งข้อความไปยัง observer ที่ register ไว้
    }
    
    
    
    
}
