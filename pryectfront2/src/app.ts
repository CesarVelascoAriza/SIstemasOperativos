const app = document.getElementById('app');

const input = (type: string, placeholder: string,className:string) => {
    const input = document.createElement('input');
    input.className = 'input '+ className;
    input.type = type;
    input.placeholder = placeholder;
    return input;
}
  
const Div =(className:string)=>{
    const div = document.createElement('div');
    div.className = className;
    return div;
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
const div2 = document.createElement('div');
const div3 = Div('col-2');
div.className = 'col';


div.appendChild(input('text', 'Username','col-2'));
div2.appendChild(input('password', 'Password',''));
div3.appendChild(input('text', 'Username','col'));
div3.appendChild(input('password', 'Password',''));

div2.appendChild(button('Login'));
div2.appendChild(button('Register'));
f.appendChild(div);
f.appendChild(div2);
f.appendChild(div3);












