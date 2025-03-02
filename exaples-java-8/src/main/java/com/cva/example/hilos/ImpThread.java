package com.cva.example.hilos;

public class ImpThread extends Thread {
    
    private int id;

    public ImpThread(int id) {
        this.id = id;
    }
    
    
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Hello World! Hilo " + id);  
        }
    }

}
