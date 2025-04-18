const app = document.getElementById('app');

const input = (type: string, placeholder: string,className:string) => {
    const input = document.createElement('input');
    input.className = 'input '+ className;
    input.type = type;
    input.placeholder = placeholder;
    return input;
}
  
const button = (placeholder: string) => {
    const b = document.createElement('button');
    b.innerText = placeholder;
    b.className = 'btn';
    return b;
}

const c = document.createElement('div');
c.className = 'container';
app?.appendChild(c);
const h1 = document.createElement('h1');
h1.innerText = 'Login';
c.appendChild(h1); 
const f = document.createElement('form');
c.appendChild(f);
const div = document.createElement('div');
div.className = 'col';
div.appendChild(input('text', 'Username','form-row .col-1'));
div.appendChild(input('password', 'Password',''));
div.appendChild(button('Login'));
div.appendChild(button('Register'));
f.appendChild(div);













