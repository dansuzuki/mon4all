// Keep the Input import for now, we'll remove it later:
import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Params }   from '@angular/router';
import { Location }                 from '@angular/common';

import { TickJobService } from './tick-job.service';

import { TickJob } from './tick-job'

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
export class TickJobDetailComponent implements OnInit {
  @Input()
  tickJob: TickJob;

  constructor(
    private tickJobService: TickJobService,
    private route: ActivatedRoute,
    private location: Location
  ) { }

  ngOnInit(): void {
    this.route.params.forEach(
      (params: Params) => {
          let id = +params['id'];
          this.tickJobService
            .getTickJob(id)
            .then(tickJob => this.tickJob = tickJob);
      }
    );
  }
}
