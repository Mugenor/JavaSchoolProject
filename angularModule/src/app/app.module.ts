import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {HeaderComponent} from './header/header.component';
import {MenuComponent} from './menu/menu.component';
import {BsDropdownModule} from 'ngx-bootstrap';
import {HomeComponent} from './home/home.component';
import {RoutingModule} from './routing/routing.module';
import {AllTripsComponent} from './all-trips/all-trips.component';
import {HttpClientModule} from '@angular/common/http';
import {TripComponent} from './trip/trip.component';
import {ObjectPipe} from './pipe/object.pipe';
import {StationsComponent} from './stations/stations.component';
import {TimetableComponent} from './timetable/timetable.component';
import {ContainsPipe} from './pipe/contains.pipe';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {FindTripComponent} from './find-trip/find-trip.component';
import {
  MatAutocompleteModule,
  MatButtonModule,
  MatCheckboxModule,
  MatDialogModule,
  MatFormFieldModule,
  MatInputModule,
  MatProgressSpinnerModule,
  MatTableModule
} from '@angular/material';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import * as $ from 'jquery';
import {DatetimepickerComponent} from './datetimepicker/datetimepicker.component';
import {DepartureListWithMenuComponent} from './departure-list-with-menu/departure-list-with-menu.component';
import {DepartureListComponent} from './departure-list/departure-list.component';
import {ErrorDialogComponent} from './dialog/error-dialog/error-dialog.component';
import {ChooseSeatDialogComponent} from './dialog/choose-seat-dialog/choose-seat-dialog.component';

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
    ObjectPipe,
    StationsComponent,
    DepartureListComponent,
    DepartureListWithMenuComponent,
    TimetableComponent,
    ContainsPipe,
    FindTripComponent,
    DatetimepickerComponent,
    ErrorDialogComponent,
    ChooseSeatDialogComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatAutocompleteModule,
    MatCheckboxModule,
    MatTableModule,
    MatProgressSpinnerModule,
    MatDialogModule,
    HttpClientModule,
    BsDropdownModule.forRoot(),
    RoutingModule
  ],
  entryComponents: [
    ErrorDialogComponent,
    ChooseSeatDialogComponent
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
