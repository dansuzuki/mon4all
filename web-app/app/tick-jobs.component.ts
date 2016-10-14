import { Component, OnInit } from '@angular/core';

import { TickJob } from './tick-job';
import { TickJobService } from './tick-job.service';

@Component({
  moduleId: module.id,
  selector: 'tick-jobs',
  templateUrl: 'tick-jobs.component.html'
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
