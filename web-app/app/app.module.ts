import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';


import { AppComponent } from './app.component';
import { TickJobDetailComponent } from './tick-job-detail.component';

@NgModule({
  imports:      [ BrowserModule, FormsModule ],
  declarations: [ AppComponent, TickJobDetailComponent ],
  bootstrap:    [ AppComponent ]
})
export class AppModule { }
