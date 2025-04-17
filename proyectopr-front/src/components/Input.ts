export function Input(placeholder: string) {
  const input = document.createElement('input');
  input.placeholder = placeholder;
  return input;
}