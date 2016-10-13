import { Component } from '@angular/core';

@Component({
  selector: 'main-stage',
  template:`
    <h1>{{title}}</h1>
    <nav>
      <a routerLink="/dashboard">Dashboard</a>
      <a routerLink="/tick-jobs">Tick Jobs</a>
    </nav>
    <router-outlet></router-outlet>
  `
})
export class AppComponent {
    title = 'Monitoring' // this shall contain the default view
}
