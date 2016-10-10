import { Component } from '@angular/core';


export class TickJob {
  id: number;
  name: string;
}


const TICKJOBS: TickJob[] =  [
  { id: 1, name: 'FA Purchase Score BT' },
  { id: 2, name: 'Sim Birth BT' },
  { id: 3, name: 'FA Daily Recomute' },
  { id: 10, name: 'Cash Credit Daily' }
];


@Component({
  selector: 'my-app',
  template: `
    <h1>Dashboard</h1>
    <h2>Jobs</h2>
    <ul class="jobs">
      <li *ngFor="let tickJob of tickJobs"
        [class.selected] = "tickJob === selectedTickJob"
        (click) = "onSelect(tickJob)"
        >
        <span class="badge">{{tickJob.id}}</span> {{tickJob.name}}
      </li>
    </ul>
    <div *ngIf="selectedTickJob">
      <h2>{{selectedTickJob.name}} details</h2>
      <div>
        <label>id:</label>{{selectedTickJob.id}}
      </div>
      <div>
        <label>name: </label><input [(ngModel)]="selectedTickJob.name" placeHolder="job name"/>
      </div>
    </div>
    `,
    styles: [`
    .selected {
      background-color: #CFD8DC !important;
      color: white;
    }
    .jobs {
      margin: 0 0 2em 0;
      list-style-type: none;
      padding: 0;
      width: 15em;
    }
    .jobs li {
      cursor: pointer;
      position: relative;
      left: 0;
      background-color: #EEE;
      margin: .5em;
      padding: .3em 0;
      height: 1.6em;
      border-radius: 4px;
    }
    .jobs li.selected:hover {
      background-color: #BBD8DC !important;
      color: white;
    }
    .jobs li:hover {
      color: #607D8B;
      background-color: #DDD;
      left: .1em;
    }
    .jobs .text {
      position: relative;
      top: -3px;
    }
    .jobs .badge {
      display: inline-block;
      font-size: small;
      color: white;
      padding: 0.8em 0.7em 0 0.7em;
      background-color: #607D8B;
      line-height: 1em;
      position: relative;
      left: -1px;
      top: -4px;
      height: 1.8em;
      margin-right: .8em;
      border-radius: 4px 0 0 4px;
    }
  `]

})
export class AppComponent {
  selectedTickJob: TickJob;

  tickJobs = TICKJOBS;

  onSelect(tickJob: TickJob): void {
    this.selectedTickJob = tickJob;
  }
}
