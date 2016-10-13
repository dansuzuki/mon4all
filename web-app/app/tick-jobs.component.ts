import { Component, OnInit } from '@angular/core';

import { TickJob } from './tick-job';
import { TickJobService } from './tick-job.service';

@Component({
  moduleId: module.id,
  selector: 'tick-jobs',
  template: `
    <h2>Tick Jobs</h2>
    <ul class="jobs">
      <li *ngFor="let tickJob of tickJobs"
        [class.selected] = "tickJob === selectedTickJob"
        (click) = "onSelect(tickJob)"
        >
        <span class="badge">{{tickJob.id}}</span> {{tickJob.name}}
      </li>
    </ul>
    <tick-job-detail [tickJob] = "selectedTickJob">
    </tick-job-detail>
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
export class TickJobsComponent implements OnInit {
  selectedTickJob: TickJob;
  tickJobs: TickJob[];

  constructor(private tickJobService: TickJobService) { }

  getTickJobs(): void {
    this.tickJobService.getTickJobs().then(tickJobs => this.tickJobs = tickJobs);
  }

  ngOnInit(): void {
    this.getTickJobs();
  }

  onSelect(tickJob: TickJob): void {
    this.selectedTickJob = tickJob;
  }
}
