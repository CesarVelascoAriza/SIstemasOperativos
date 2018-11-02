package com.edu.ucentral;

import java.awt.Color;

import javax.swing.JTextArea;

public class Colores implements Runnable   {

	Color color;
	JTextArea area;
	String nombre;
	boolean ocupado = false;

	public Colores(JTextArea area, Color color, String nombre, boolean ocupado) {
		this.area = area;
		this.color = color;
		this.nombre = nombre;
		this.ocupado = ocupado;
	}

	
	public void run() {
		for (int i = 0; i < 10; i++) {
			long rango = (long) (Math.random() * 5001);
			try {
				this.obtener(i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}

	public synchronized void obtener(int valor) throws InterruptedException {
		area.setBackground(color);
		System.err.println(nombre + " " + valor);
		if(nombre =="Hilo_Rojo") {
			wait(1000);
		}else if(nombre =="Hilo_Rojo"){
			wait(1000);
		}else {
			notify();
		}
	}
}
