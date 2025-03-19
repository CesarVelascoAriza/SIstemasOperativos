package com.cva.example.ejercicios;

import java.util.ArrayList;
import java.util.List;

public class EjeccioRunable {

    public static void main(String[] args) throws InterruptedException {
        EjeccioRunable e = new EjeccioRunable();
        e.procesarNumeros();
    }

    public void procesarNumeros() throws InterruptedException {
        Long rangoA = 1L;
        Long rangoB = 1000L;
        List<Long> listaNumeros = listadoAprocesar(rangoA, rangoB);
        Integer total = 0;
        int cantidadHilos = listaNumeros.size() / 100;
        System.out.println("Cantidad de hilos : " + cantidadHilos);
        for (int i = 0; i < cantidadHilos; i++) {
            List<Long> listaNumerosHilo = listaNumeros.subList(i * 100, (i + 1) * 100);
            Procesar procesar = new Procesar(listaNumerosHilo, i);
            Thread hilo = new Thread(procesar);
            hilo.start();
            hilo.join();
            // System.out.println(procesar.getListaNumerosPares().size());
            total += procesar.getNumerosPares();
            // procesar.getListaNumerosPares().forEach(System.out::println);
            procesar.getListaNumerosNegativos().forEach(System.out::println);

        }
        System.out.println(total);
        System.out.println("Listado de numeros negativos : " + "");
        System.out.println("Fin de la ejecución");
    }

    private List<Long> listadoAprocesar(Long rangoA, Long rangoB) {
        List<Long> listaNumeros = new ArrayList<>();
        for (Long i = rangoA; i <= rangoB; i++) {
            listaNumeros.add(i);
        }
        listaNumeros.add(-1L); // Agregar un número negativo a la lista
        return listaNumeros;
    }
}

class Procesar implements Runnable {

    List<Long> listaNumerosHilo = new ArrayList<>();
    int hilo = 0;
    int suma = 0;

    public List<Long> listaNumerosPares = new ArrayList<>();
    public List<Long> listaNumerosNegativos = new ArrayList<>();

    Procesar(List<Long> listaNumerosHilo, int hilo) {
        this.listaNumerosHilo = listaNumerosHilo;
        this.hilo = hilo;
    }

    @Override
    public void run() {
        for (Long numero : listaNumerosHilo) {
            try {
                // System.err.println("Numero : " + numero + " Hilo : " + hilo);
                if (numero < 0) {
                    throw new NegativeNumberException("El número no puede ser negativo");
                } else if (numero % 2 == 0) {
                    listaNumerosPares.add(numero);

                }
            } catch (NegativeNumberException e) {
                System.err.println("Numero Par : " + numero + " Hilo : " + hilo);
                listaNumerosNegativos.add(numero);
            }

        }
        // suma = listaNumerosPares.stream().mapToInt(Long::intValue).sum();
        // System.out.println("Suma de numeros pares : " + suma);
        // System.out.println("Lista de numeros pares : " + listaNumerosPares.size());
        // listaNumerosPares.forEach(System.out::println);
        // System.out.println("Lista de numeros negativos : " +
        // listaNumerosNegativos.size());
    }

    public synchronized List<Long> getListaNumerosPares() {
        return listaNumerosPares;
    }

    public synchronized int getNumerosPares() {
        return listaNumerosPares.stream().mapToInt(Long::intValue).sum();
    }

    public synchronized List<Long> getListaNumerosNegativos() {
        return listaNumerosNegativos;
    }
}

class NegativeNumberException extends Exception {
    public NegativeNumberException(String message) {
        super(message);
    }
}