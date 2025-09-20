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
    public void checkCount(int count){
        if(count >= 5 )
         notifyObservers("วันนี้จองเต็มแล้ว");
    }
    
}
