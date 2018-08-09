import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { MenuComponent } from './menu/menu.component';
import {BsDropdownModule} from 'ngx-bootstrap';
import * as $ from 'jquery';
import { HomeComponent } from './home/home.component';
import {RouterModule, Routes} from '@angular/router';
import {RoutingModule} from './routing/routing.module';
import { AllTripsComponent } from './all-trips/all-trips.component';
import {HttpClientModule} from '@angular/common/http';
import { TripComponent } from './trip/trip.component';
import { ChooseSeatComponent } from './choose-seat/choose-seat.component';
import { ObjectPipe } from './pipe/object.pipe';
import { StationsComponent } from './stations/stations.component';
import { DepartureListComponent } from './departure-list/departure-list.component';
import { TimetableComponent } from './timetable/timetable.component';
import { ContainsPipe } from './pipe/contains.pipe';

window['$'] = $;
window['jQuery'] = $;



@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    MenuComponent,
    HomeComponent,
    AllTripsComponent,
    TripComponent,
    ChooseSeatComponent,
    ObjectPipe,
    StationsComponent,
    DepartureListComponent,
    TimetableComponent,
    ContainsPipe
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    BsDropdownModule.forRoot(),
    RoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
