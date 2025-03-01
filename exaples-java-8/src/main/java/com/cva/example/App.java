package com.cva.example;

import com.cva.example.Entities.Person;
import java.util.Optional;
/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Person person = new Person("John", "Doe", 30);
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

    }



}
