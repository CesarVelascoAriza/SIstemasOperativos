package com.cva.example.ejercicio;

import java.util.Scanner;

public class Ejercicio34 {

    /*
     * Escribir un programa que imprima una X construida a base de la letra X y
     * utilizar el carácter de subrayado como espacio. El tamaño de la x se basa en
     * una variable n que indicará el tamaño de la letra para imprimir (en una
     * matriz de n x n). Por ejemplo, para n: = 5 se obtiene:
     * 
     * X___X
     * _X_X_
     * __X__
     * _X_X_
     * X___X
     * 
     * y para n:=6 se obtiene:
     * 
     * 
     * X____X
     * _X__X_
     * __XX__
     * __XX__
     * _X__X_
     * X____X
     * 
     * Si n es igual a cero imprimir "ERROR"
     * 
     * El código para el tamaño de n ya está ahí, puede editarlo para probar con
     * otros valores y puede hacer clic en el botón de actualización junto a él para
     * volver al valor original que se utiliza para validar su código durante la
     * prueba. Tenga en cuenta que el código debe imprimir los resultados
     * exactamente como se muestra con el fin de que la pregunta sea considerada
     * válida durante la prueba (El carácter "X" en mayúscula y el guion bajo "_"
     * para los espacios)
     * 
     * Nota: Asumir que esta escribiendo el codigo dentro de una clase, por lo que
     * no debe declarar la clase en si, solo el metodo public static void main para
     * ejecutar su código.
     * 
     * 
     * 
     */

    public void desarrollo() {
        try {
            System.out.println("Ingresar el numero de la matriz");
            Scanner scanner = new Scanner(System.in);
            int n = scanner.nextInt();
            if (n > 0) {
                System.out.println(n);
                pintar(n); 
            } else if (n == 0) {
                System.out.println("Error");
            } else {
                n = 5;
               pintar(n);
            }
        } catch (Exception e) {
            System.err.println("Error" + e.getMessage());
        }
    }
    private void pintar(int n) {
        int[][] tablero = new int[n][n];
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero.length; j++) {
                if (  i+j == n-1 || i == j) {
                    System.out.print("X");
                }else {
                    System.out.print("_");
                }
            }
            System.out.println();
        }
    }
}
