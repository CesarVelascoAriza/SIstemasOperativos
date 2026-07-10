# Ejemplo de hilos virtuales con base de datos

Este proyecto muestra un ejemplo simple en Java usando hilos virtuales con cuatro tareas coordinadas que realizan operaciones sobre una base de datos H2 en memoria.

## Requisitos
- Java 21+
- Maven 3.8+

## Ejecutar
```bash
mvn clean compile exec:java -Dexec.mainClass=com.example.VirtualThreadsDbDemo
```

## Qué hace el ejemplo
- Crea una tabla en H2.
- Prepara datos iniciales.
- Lanza 4 hilos virtuales controlados con un `CountDownLatch`.
- Cada hilo realiza una operación distinta: insertar, actualizar, consultar y borrar.
