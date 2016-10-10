import { Component } from '@angular/core';
@Component({
  selector: 'my-app',
  template: `
    <h1>Monitoring Application</h1>
    <h2>{{name}}</h2>
    <input [(ngModel)] = "name"/>
    `
})
export class AppComponent {
    name = 'Your name is...'
}
