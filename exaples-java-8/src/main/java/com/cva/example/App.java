package com.cva.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.concurrent.Future;


import com.cva.example.Entities.Person;
import com.cva.example.Entities.Student;
import com.cva.example.handdleError.PersonException;
import com.cva.example.hilos.ImpThread;
import com.cva.example.hilos.ImplCalleable;
import com.cva.example.hilos.implRunable;
/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args ) throws InterruptedException, ExecutionException
    {
        System.out.println( "Hello World!" );
        List<Object> list = new ArrayList<>();
        Person person = new Person("John", "Doe", 30);
        Student student = new Student("John", "Doe", 30, "1234");
        list.add(person);
        list.add(student);

        System.out.println(person.getName());
        /*
            java 11 se utiliza el ispresent
         */
        Optional<Integer> age = Optional.ofNullable(person.getAge()); ;
         if(age.isEmpty()){
             System.out.println("edad no presente");
         }else{
             System.out.println("edad presente");
         }
         /**
          * java 8
          */
        if(age.isPresent()){
           System.out.println("edad presente");
        }else{
            System.out.println("edad no presente");
        }

        list.forEach(App::printMessage);
        list.stream().forEach(System.out::println);

        ImpThread t1 = new ImpThread(1);
        Thread.sleep(6000);
        t1.start();
        t1.join();
        Thread t2 = new Thread(new implRunable(2));
        Thread.sleep(6000);
        t2.start();
        t2.join();



        Runnable r = () -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("Hello World! hilo 3");
            }
        };
        
        Thread t = new Thread(r);
        Thread.sleep(6000);
        t.start();
        t.join();

        /***
         * Ejecucion de hilos sincronizados
         */
        Runnable r1 = () -> {
            for (int i = 0; i < 100; i++) {
               exampled();
            }
        };
        Thread h1 = new Thread(r1);
        h1.start();
        Thread h2 = new Thread(r1); 
        h2.start();

        ExecutorService executor = Executors.newFixedThreadPool(2);
        ExecutorCompletionService<String> completionService = new ExecutorCompletionService<>(executor);
        
        /* forma 1
        Future<String> task1 = executor.submit(new ImplCalleable());
        Future<?> task2 = executor.submit(new implRunable(0));
        */
        Future<String> task1 = completionService.submit(new ImplCalleable());
        Future<String> task2 = completionService.submit(new ImplCalleable());

        /*while (!task1.isDone() && !task2.isDone()) {
            System.out.println("Task is done");
            
        }*/
        //System.out.println(task1.get());
        //System.out.println(task2.get());
        while (completionService.poll() != null) {
            Future<String> task = completionService.take();
            System.out.println(task.get());
        }

        executor.shutdown();
    }


    public static synchronized void exampled(){
        System.out.println("sincronizacion de hilos");
    }
    public static void printMessage(Object objeto) {
        
        try {
            
            if(objeto instanceof Person){
                System.out.println("Es una persona");
            }else if(objeto instanceof Student){
                System.out.println("Es un estudiante");
               
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }

}
