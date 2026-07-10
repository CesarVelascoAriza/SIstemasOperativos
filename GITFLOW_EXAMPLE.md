# Ejemplo de Git Flow con hooks

Este ejemplo muestra un flujo simple para practicar hooks de Git junto con Git Flow.

## Ramas sugeridas
- `main` o `master`: producción
- `develop`: integración
- `feature/nombre`: nuevas funcionalidades
- `release/v1.0`: preparación de versión
- `hotfix/correccion`: arreglos urgentes

## Pasos de ejemplo

1. Crear rama de desarrollo
```bash
git checkout -b develop
```

2. Crear una rama de feature
```bash
git checkout -b feature/ejemplo-hooks
```

3. Hacer cambios y hacer commit
```bash
git add .
git commit -m "Agregar ejemplo de hooks"
```

4. Fusionar a develop
```bash
git checkout develop
git merge feature/ejemplo-hooks
```

5. Crear release
```bash
git checkout -b release/v1.0
```

6. Crear hotfix si aplica
```bash
git checkout -b hotfix/correcion-urgente
```

## Hooks incluidos
- `pre-commit`: bloquea commits directos en master/main y valida el mensaje.
- `prepare-commit-msg`: agrega un prefijo por defecto si el mensaje está vacío.
- `post-merge`: muestra un mensaje de recordatorio.

## Nota
Los hooks se ejecutan desde el directorio `.githooks` gracias a:
```bash
git config core.hooksPath .githooks
```
