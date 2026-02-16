package com.edu.ucentral;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import javax.swing.JTextArea;

import org.junit.jupiter.api.Test;

public class SemaforoTest {

    @Test
    public void testMainDoesNotThrow() {
        assertDoesNotThrow(() -> Semaforo.main(new String[0]));
    }

    @Test
    public void testColoresRun() {
        JTextArea area = new JTextArea();
        Colores colores = new Colores(area, java.awt.Color.BLUE, "Hilo_Test", false);
        assertDoesNotThrow(colores::run);
    }
}