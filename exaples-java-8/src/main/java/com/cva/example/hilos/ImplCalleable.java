package com.cva.example.hilos;

import java.util.concurrent.Callable;

public class ImplCalleable implements Callable<String> {
    
    public String call() {
     return "Hello World! Hilo ";
    }

}
