"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.Button = void 0;
const Button = (name) => {
    // Create a button element
    const button = document.createElement('button');
    button.innerText = name;
    return button;
};
exports.Button = Button;
