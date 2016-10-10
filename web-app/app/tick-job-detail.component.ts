import { Component, Input } from '@angular/core';

import { TickJob } from './tickjob';

@Component({
  selector: 'tick-job-detail',
  template: `
  <div *ngIf="tickJob">
    <h2>{{tickJob.name}} details</h2>
    <div>
      <label>name: </label>
      <input [(ngModel)]="tickJob.name" placeHolder="job name"/>
    </div>
  </div>
  `
})
export class TickJobDetailComponent {
  @Input()
  tickJob: TickJob;
}
