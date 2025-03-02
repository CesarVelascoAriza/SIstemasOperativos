package com.cva.example;

import com.cva.example.Entities.Person;
import com.cva.example.Entities.Student;

import java.util.ArrayList;
import java.util.List;
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
    }


    public static void printMessage(Object objeto){
        if(objeto instanceof Person){
            System.out.println("Es una persona");
        }else if(objeto instanceof Student){
            System.out.println("Es un estudiante");
        }
    }

}
