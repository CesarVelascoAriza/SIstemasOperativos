package com.edu.ucentral;

import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class semaf1 extends JApplet {
	private JTextArea areaColorear;
	private JButton boton;

	public void init() {
		areaColorear = new JTextArea(10, 10);
		boton = new JButton("Iniciar");

		boton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Colores hRojo = new Colores(areaColorear, Color.RED, "Hilo_Rojo");
				Colores hVerde = new Colores(areaColorear, Color.GREEN, "Hilo_Verde");
				ExecutorService app= Executors.newCachedThreadPool();
				app.execute(hVerde);
				app.execute(hRojo);
				//hRojo.start();
				//hVerde.start();
				app.shutdown();
			}
		});
		Container contenedor = getContentPane();
		contenedor.setLayout(new GridLayout(1, 2));
		contenedor.add(areaColorear);
		contenedor.add(boton);
	}

	class Colores extends Thread {
		Color color;
		JTextArea area;
		String nombre;
		boolean ocupado=false;
		public Colores(JTextArea area, Color color, String nombre) {
			this.area = area;
			this.color = color;
			this.nombre = nombre;
		}

		public void run() {
			for (int i = 0; i < 10; i++) {
				area.setBackground(color);
				long rango = (long) (Math.random() * 5001);
				System.err.println(nombre + " " + i);
				try {
					if(obtener()) {
						estabablecer(i);
					}else {
						estabablecer(i);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		public synchronized void estabablecer(long valor) throws InterruptedException {
			
			while(ocupado) {
				System.out.println("nombre hilo : " + valor);
				wait();
			}
			ocupado=true;
			notify();
		}
		public synchronized boolean  obtener() throws InterruptedException {
			while(!ocupado) {
				wait();
			}
			ocupado= false ;
			notify();
			return ocupado;
		} 
		
	}
	
	
	
	

}
