const app = document.getElementById('app');
const container = document.createElement('div');
container.className = 'container';
const button = document.createElement('button');
button.innerText = 'Click me';
const title = document.createElement('h1');
title.innerText = 'Hello World';
const from = document.createElement('form');
const input = document.createElement('input');
input.placeholder = 'Enter your name';
const input2 = document.createElement('input');
input2.placeholder = 'Enter your email';
const input3 = document.createElement('input');
input3.placeholder = 'Enter your password';
const input4 = document.createElement('input');
input4.placeholder = 'Enter your password again';
input.className = 'input';
input2.className = 'input';
input3.className = 'input';
input4.className = 'input';
app?.appendChild(container);

container.appendChild(title);
container.appendChild(button);
container.appendChild(from);
from.appendChild(input);
from.appendChild(input2); 
from.appendChild(input3);
from.appendChild(input4);
     









