import { loadRemoteModule } from '@angular-architects/native-federation';
import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: 'micro-frontend-1',
        loadComponent: () => loadRemoteModule('micro-frontend-1', './Component').then(m => m.AppComponent)
    },
    {
        path: 'micro-frontend-2',
        loadComponent: () => loadRemoteModule('micro-frontend-2', './Component').then(m => m.AppComponent)
    }
];
