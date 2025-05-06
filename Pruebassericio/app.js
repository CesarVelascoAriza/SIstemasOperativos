const express = require('express');
const Redis = require('ioredis');

const app = express();
const port = 3000;

// Configurar Redis
const redis = new Redis();
const eventos = [];

// Escuchar eventos de actualización
redis.subscribe('actualizaciones', (err, count) => {
  if (err) {
    console.error("Error suscribiéndose a Redis:", err);
  } else {
    console.log(`Suscrito a ${count} canales.`);
  }
});

redis.on('message', (channel, message) => {
  if (channel === 'actualizaciones') {
    console.log(`Evento recibido: ${message}`);
    eventos.push(message); // Guardar evento en memoria (puedes usar una BD)
  }
});

// Endpoint GET para obtener eventos
app.get('/eventos', (req, res) => {
  res.json(eventos);
});

app.listen(port, () => {
  console.log(`Microservicio corriendo en http://localhost:${port}`);
});