package com.cva.example.ejercicio;

public class Ejercicio35 {

    /***
     * Se tiene una matriz de n x n que contiene números del 1 al 9, simulado usando
     * una matriz unidimensional. Así, por ejemplo, esta matriz:
     * 
     * 1 2 9
     * 2 5 3
     * 5 1 5
     * 
     * Se representaría como (1,2,9,2,5,3,5,1,5). El objetivo es identificar el
     * camino que de la menor suma al recorrer el arreglo bi-dimencional de
     * izquierda a derecha. Se empieza en la columna izquierda y se mueve siempre
     * una columna a la derecha de la misma fila o a una fila hacia arriba o hacia
     * abajo. En el ejemplo, si parte de 1, puede pasar al 2 o al 5. De ahí, si pasó
     * al 5 puede pasar al 9 al 3 o al 5. Por otro lado, si pasa del 1 al 2, desde
     * el 2 de la columna del medio no podría pasar al 5 de la ultima fila en la
     * columna derecha. El valor de n puede ser entre 1 y 4.
     * 
     * Es necesario encontrar el camino que produce el número más bajo al suma los
     * valores de cada número que visita. Así que para el ejemplo, la ruta con la
     * menor suma sería 1,2,3
     * 
     * El código para declarar y poblar myArray ya está ahí y tambien el que asinga
     * el valor de n, puede editarlo para probar con otros valores y puede hacer
     * clic en el botón de actualizar junto a él para volver al valor original que
     * se utilizará para validar su código durante la prueba.
     * 
     * El resultado de su programa debe ser los n números por los que pasó para
     * obtener la menor suma separados por un espacio. Para el ejemplo, la salida
     * sería exactamente así:
     * 1 2 3
     * 
     * Nota: Asumir que esta escribiendo el codigo dentro de una clase Main, por lo
     * que no debe declarar la clase Main, pero si el metodo public static void main
     * para ejecutar su código. Java util ya está importado, no requiere importar
     * esta ni ninguna otra librería.
     */

     public void matriz(){
        int n = 3;
        int[] myArray = {1,2,9,2,5,3,5,1,5};
        int[][] matriz = new int[n][n];
        int k = 0;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                matriz[i][j] = myArray[k];
                k++;
            }
        }
        int[][] dp = new int[n][n];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                dp[i][j] = matriz[i][j];
                if(i > 0 && j > 0){
                    dp[i][j] += Math.min(dp[i-1][j-1], Math.min(dp[i-1][j], dp[i][j-1]));
                }else if(i > 0){
                    dp[i][j] += dp[i-1][j];
                }else if(j > 0){
                    dp[i][j] += dp[i][j-1];
                }
            }
        }
        int i = n-1;
        int j = n-1;
        while(i > 0 || j > 0){
            System.out.print(matriz[i][j] + " ");
            if(i > 0 && j > 0){
                if(dp[i-1][j-1] < dp[i-1][j] && dp[i-1][j-1] < dp[i][j-1]){
                    i--;
                    j--;
                }else if(dp[i-1][j] < dp[i][j-1]){
                    i--;
                }else{
                    j--;
                }
            }else if(i > 0){
                i--;
            }else{
                j--;
            }
        }
        System.out.println(matriz[0][0]);
     }
}
