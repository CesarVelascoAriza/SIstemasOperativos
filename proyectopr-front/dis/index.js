"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const app = document.getElementById('app');
// Create a button element
//app?.appendChild(Button('Hola mundo'));
const button = document.createElement('button');
button.innerText = 'Hola mundo';
button.addEventListener('click', () => {
    alert('Hola mundo');
});
app === null || app === void 0 ? void 0 : app.appendChild(button);
