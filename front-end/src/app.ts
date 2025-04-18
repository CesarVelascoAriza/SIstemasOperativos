import https from 'https';
import fs from 'fs';
import express from 'express';
import { networkInterfaces } from 'os';
import { hostname } from 'os';
import dns from 'dns';

const app = express();

// Middleware example
app.get('/', (req, res) => {
    // Obtener la IP del cliente desde las cabeceras o la conexión
    const clientIp = req.headers['x-forwarded-for'] || req.connection.remoteAddress;

    // Resolver el hostname del cliente (si es posible)
    if (typeof clientIp === 'string') {
        dns.reverse(clientIp, (err, hostnames) => {
            if (err) {
                console.log(`Error resolving hostname for IP ${clientIp}:`, err.message);
                res.send(`Hello, SSL! Your IP is: ${clientIp}\nHostname could not be resolved.\nHeaders: ${JSON.stringify(req.headers, null, 2)}`);
            } else {
                console.log(`Client IP: ${clientIp}, Hostname: ${hostnames[0] || 'Unknown'}`);
                res.send(`Hello, SSL! Your IP is: ${clientIp}\nHostname: ${hostnames[0] || 'Unknown'}\nHeaders: ${JSON.stringify(req.headers, null, 2)}`);
            }
        });
    } else {
        res.send(`Hello, SSL! Your IP is: Unknown\nHostname could not be resolved.\nHeaders: ${JSON.stringify(req.headers, null, 2)}`);
    }
});

let sslOptions;
try {
    sslOptions = {
        key: fs.readFileSync('/workspaces/SIstemasOperativos/front-end/server-key.pem'), // Path to your private key
        cert: fs.readFileSync('/workspaces/SIstemasOperativos/front-end/server-key-public.pem'), // Path to your public certificate
    };
} catch (error) {
    console.error('Error reading SSL files:', error);
    process.exit(1);
}

// Create HTTPS server
const server = https.createServer(sslOptions, app);

// Start server
const PORT = 8443; // Use a non-privileged port
server.listen(PORT, () => {
    const nets = networkInterfaces();
    const results: { [key: string]: string[] } = {};

    for (const name of Object.keys(nets)) {
        for (const net of nets[name]!) {
            // Skip over non-IPv4 and internal (i.e., 127.0.0.1) addresses
            if (net.family === 'IPv4' && !net.internal) {
                if (!results[name]) {
                    results[name] = [];
                }
                results[name].push(net.address);
            }
        }
    }

    console.log(`HTTPS server running on https://localhost:${PORT}`);
    console.log(`Hostname: ${hostname()}`);
    console.log('IP Addresses:', results);
});