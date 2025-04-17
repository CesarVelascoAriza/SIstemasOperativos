export function Button(label: string): HTMLButtonElement {
  const button = document.createElement('button');
  button.innerText = label;
  return button;
}
