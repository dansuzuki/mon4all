/** angular stuffs */
import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';
import { RouterModule }  from '@angular/router';
import { HttpModule }    from '@angular/http';

/** ng-bootstrap */
//import {NgbModule} from '@ng-bootstrap/ng-bootstrap';


/** app specifics */
import { AppComponent }           from './app.component';
import { DashboardComponent }     from './dashboard.component';
import { TickJobsComponent }       from './tick-jobs.component';
import { TickJobDetailComponent } from './tick-job-detail.component';
import { TickJobService }         from './tick-job.service';


@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    RouterModule.forRoot([
        {
          path: 'tick-jobs',
          component: TickJobsComponent
        },
        {
          path: 'dashboard',
          component: DashboardComponent
        },
        {
          path: '',
          redirectTo: '/dashboard',
          pathMatch: 'full'
        }
      ]),
    //NgbModule.forRoot()
  ],
  declarations: [
    AppComponent,
    DashboardComponent,
    TickJobsComponent,
    TickJobDetailComponent ],
  providers: [
    TickJobService
  ],
  bootstrap: [
    AppComponent ]
})
export class AppModule {
}
