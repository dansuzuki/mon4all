import { Injectable } from '@angular/core';

import { TickJob } from './tick-job';

const TICKJOBS: TickJob[] =  [
  { id: 1, name: 'FA Purchase Score BT' },
  { id: 2, name: 'Sim Birth BT' },
  { id: 3, name: 'FA Daily Recomute' },
  { id: 10, name: 'Cash Credit Daily' },
  { id: 20, name: 'Usage Profiling' }
];

@Injectable()
export class TickJobService {
  getTickJobs(): Promise<TickJob[]>  {
    return Promise.resolve(TICKJOBS);
  }

  getTickJob(id: number): Promise<TickJob> {
    return Promise.resolve(TICKJOBS.find(tickJob => tick.Job.id === id));
  }
}
