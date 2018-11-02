package com.edu.ucentral;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JTextArea;


public class Semaforo extends JApplet{

	private JButton boton;
	private JTextArea areaColorear;
	
	
	public void init() {
		areaColorear = new JTextArea(10, 10);
		boton = new JButton("Iniciar");
		
		Container contenedor = getContentPane();
		contenedor.setLayout(new GridLayout(1, 2));
		contenedor.add(areaColorear);
		contenedor.add(boton);
		boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Colores hRojo = new Colores(areaColorear, Color.RED, "Hilo_Rojo" ,true);
				Colores hVerde = new Colores(areaColorear, Color.GREEN, "Hilo_Verde", true );
				ExecutorService ejecutor = Executors.newCachedThreadPool();
				ejecutor.execute(hRojo);
				ejecutor.execute(hVerde);
				ejecutor.shutdown();
			}
		});
	}
	
}
