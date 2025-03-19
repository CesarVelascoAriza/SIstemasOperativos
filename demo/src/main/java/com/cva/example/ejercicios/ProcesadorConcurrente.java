package com.cva.example.ejercicios;

import java.util.ArrayList;
import java.util.List;

class ProcesadorSubconjunto extends Thread {
    private List<Integer> subset;
    private List<Object> result;
    private final Object lock;

    public ProcesadorSubconjunto(List<Integer> subset, List<Object> result, Object lock) {
        this.subset = subset;
        this.result = result;
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            int sumaPares = 0;
            for (int num : subset) {
                if (num < 0) {
                    // Lanzar excepción si el número es negativo
                    throw new Exception("Número negativo encontrado: " + num);
                }
                if (num % 2 == 0) {
                    sumaPares += num;
                }
            }

            // Bloquear el acceso al resultado compartido
            synchronized (lock) {
                result.add(sumaPares);
            }
        } catch (Exception e) {
            // Si ocurre un error, agregar el número negativo a un arreglo
            synchronized (lock) {
                result.add(e.getMessage());
            }
            System.out.println("Excepción capturada: " + e.getMessage());
        }
    }
}

public class ProcesadorConcurrente {
    public static List<Object> procesarLista(List<Integer> lista) {
        int numHilos = 10;
        List<List<Integer>> subconjuntos = new ArrayList<>();
        for (int i = 0; i < lista.size(); i += 100) {
            subconjuntos.add(lista.subList(i, Math.min(i + 100, lista.size())));
        }

        List<Object> result = new ArrayList<>();  // Lista para almacenar resultados o excepciones
        Object lock = new Object();  // Lock para proteger el acceso concurrente a la lista result

        List<Thread> hilos = new ArrayList<>();
        for (List<Integer> subset : subconjuntos) {
            Thread hilo = new ProcesadorSubconjunto(subset, result, lock);
            hilos.add(hilo);
            hilo.start();
        }

        // Esperar que todos los hilos terminen
        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public static void main(String[] args) {
        // Lista de ejemplo de números enteros
        List<Integer> numeros = new ArrayList<>();
        // Rellenamos la lista con algunos valores (ejemplo)
        for (int i = 1; i <= 500; i++) {
            numeros.add(i);
        }
        numeros.add(-5); // Número negativo para probar la excepción
        numeros.add(101); // Número impar para probar

        // Llamar a la función principal
        List<Object> resultado = procesarLista(numeros);

        // Mostrar el resultado
        System.out.println("Resultado del procesamiento:");
        for (Object res : resultado) {
            System.out.println(res);
        }
    }
}
