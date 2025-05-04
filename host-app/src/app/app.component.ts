import { Component, HostListener } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'host-app';
  constructor(
     private router: Router
  ) {}
  @HostListener('window:childRouteChanged', ['$event'])
  onChildRouteChanged(event: any) {
    console.log('Child route changed:', event.detail.route);
    this.router.navigate([event.detail.route], event.detail.extras);
  }
}
