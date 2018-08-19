import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from '../home/home.component';
import {AllTripsComponent} from '../all-trips/all-trips.component';
import {StationsComponent} from '../stations/stations.component';
import {TimetableComponent} from '../timetable/timetable.component';
import {FindTripComponent} from '../find-trip/find-trip.component';

const appRoutes: Routes = [
  {path: 'client', redirectTo: '/client/home', pathMatch: 'full'},
  {path: 'client/home', component: HomeComponent},
  {path: 'client/departures', component: AllTripsComponent},
  {path: 'client/stations', component: StationsComponent},
  {path: 'client/departures/:stationTitle', component: TimetableComponent},
  {path: 'client/find', component: FindTripComponent},
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot(
      appRoutes
    )
  ],
  declarations: [],
  exports: [
    RouterModule
  ]
})
export class RoutingModule {
}
