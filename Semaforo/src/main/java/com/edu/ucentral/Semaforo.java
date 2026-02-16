package com.edu.ucentral;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;


public class Semaforo extends JFrame {

    private JButton boton;
    private JTextArea areaColorear;

    public Semaforo() {
        areaColorear = new JTextArea(10, 10);
        boton = new JButton("Iniciar");

        Container contenedor = getContentPane();
        contenedor.setLayout(new GridLayout(1, 2));
        contenedor.add(areaColorear);
        contenedor.add(boton);
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Colores hRojo = new Colores(areaColorear, Color.RED, "Hilo_Rojo", true);
                Colores hVerde = new Colores(areaColorear, Color.GREEN, "Hilo_Verde", true);
                ExecutorService ejecutor = Executors.newCachedThreadPool();
                ejecutor.execute(hRojo);
                ejecutor.execute(hVerde);
                ejecutor.shutdown();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Semaforo frame = new Semaforo();
            frame.setTitle("Semaforo");
            frame.setSize(400, 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

