package com.cva.example.ejercicio;

import java.io.Console;
import java.util.Scanner;

public class Ejercicio33 {

    /**
     * Imprimir un tablero de damas donde la "X" representa el color negro y el "_"
     * representa el blanco. El tablero debe tener n x n casillas. Por ejemplo, para
     * n=5 el tablero se vería así:
     * 
     * X_X_X
     * _X_X_
     * X_X_X
     * _X_X_
     * X_X_X
     * 
     * Tu tablero siempre debe partir con un cuadro negro (una "X") en la esquina
     * superior izquierda y el valor de n puede ir de 1 a 10. En caso de que el
     * valor de n sea diferente, asumir que n es igual a 5.
     * 
     * El código para el tamaño de n ya está ahí, puede editarlo para probar con
     * otros valores y puede hacer clic en el botón de actualización junto a él para
     * volver al valor original que se utiliza para validar su código durante la
     * prueba. Tenga en cuenta que el código debe imprimir los resultados
     * exactamente como se muestra con el fin de que la pregunta sea considerada
     * válida durante la prueba.
     * 
     * Nota: Asumir que esta escribiendo el codigo dentro de una clase, por lo que
     * no debe declarar la clase en si, solo el metodo public static void main para
     * ejecutar su código.
     * 
     */

    public void desarrollo() {
        try {
            System.out.println("Ingresar el numero del tablero");
            Scanner scanner = new Scanner(System.in);
            int n = scanner.nextInt();
            if (n > 0) {
                System.out.println(n);
                pintarTablero(n);
            } else if (n < 0) {
                System.out.println("El numero debe ser positivo");
            } else {
                n = 5;
                pintarTablero(n);
            }
        } catch (Exception e) {
            System.err.println("Error" + e.getMessage());
        }

    }

    public void pintarTablero(int n) {
        int[][] tablero = new int[n][n];
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                if (j % 2 == 0 && i % 2 == 0) {
                    System.out.print("X");
                } else if (i % 2 == 1 && j % 2 == 1) {
                    System.out.print("X");
                } else {
                    System.out.print("_");
                }
            }
            System.out.println();
        }
    }

}
