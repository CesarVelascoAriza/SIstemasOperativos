package com.cva.example.ejercicios;

import java.util.ArrayList;
import java.util.List;

public class EjeccioRunable {

    public static void main(String[] args) {
        EjeccioRunable e = new EjeccioRunable();
        e.procesarNumeros();
    }

    public void procesarNumeros() {
        Long rangoA = 1L;
        Long rangoB = 10000L;
        List<Long> listaNumeros = listadoAprocesar(rangoA, rangoB);

        int cantidadHilos = listaNumeros.size() / 100;
        System.out.println("Cantidad de hilos : "+cantidadHilos);
        for (int i = 0; i < cantidadHilos; i++) {
            List<Long> listaNumerosHilo = listaNumeros.subList(i * 100, (i + 1) * 100);
            Procesar pr =   new Procesar(listaNumerosHilo);
            Thread hilo = new Thread(pr);
            System.out.println(pr.listaNumerosPares.size());
            System.out.println(pr.listaNumerosNegativos.size());
            hilo.start();
        }

    }

    private List<Long> listadoAprocesar(Long rangoA, Long rangoB) {
        List<Long> listaNumeros = new ArrayList<>();
        for (Long i = rangoA; i <= rangoB; i++) {
            listaNumeros.add(i);
        }
        return listaNumeros;
    }
}

class Procesar extends Thread {

    List<Long> listaNumerosHilo = new ArrayList<>();


    public List<Long> listaNumerosPares = new ArrayList<>();
    public List<Long> listaNumerosNegativos = new ArrayList<>();

    Procesar(List<Long> listaNumerosHilo) {
        this.listaNumerosHilo = listaNumerosHilo;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        for (Long numero : listaNumerosHilo) {
            if (numero < 0) {
                try {
                    listaNumerosNegativos.add(numero);
                    throw new NegativeNumberException("El número no puede ser negativo");
                } catch (NegativeNumberException e) {
                    // TODO Auto-generated catch block
                    System.err.println(e.getMessage());
                }
            } else if (numero % 2 == 0) {
                listaNumerosPares.add(numero);

            }

        }

    }
}

class NegativeNumberException extends Exception {
    public NegativeNumberException(String message) {
        super(message);
    }
}