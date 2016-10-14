import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

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
  private tickJobsURL = 'http://192.168.56.101:8080/mon4all/api/ticks'

  constructor(private http: Http) { }

  // for error handling
  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }

  getTickJobs(): Promise<TickJob[]>  {
    //return Promise.resolve(TICKJOBS);

    return this.http.get(this.tickJobsURL)
      .toPromise()
      .then(resp => {
        console.log("resp >>> " + resp);
        console.log("resp.json() >>> " + resp.json());
        console.log("resp.json() as TickJob[] >>> " + (resp.json() as TickJob[]));

        let ret = resp.json() as TickJob[];
        console.log("ret >>> " + ret);
        return ret;
      })
      .catch(this.handleError);
  }

  getTickJob(id: number): Promise<TickJob> {
    return Promise.resolve(this.getTickJobs().then(resp => resp.find(tickJob => tickJob.id === id)));
  }
}
