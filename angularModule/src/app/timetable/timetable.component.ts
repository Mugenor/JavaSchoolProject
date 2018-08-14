import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {TripService} from '../service/trip.service';
import {Trip} from '../entity/trip';
import {TripDTOToTripConverterService} from '../service/trip-dtoto-trip-converter.service';
import {ErrorDialogComponent} from '../dialog/error-dialog/error-dialog.component';
import {MatDialog} from '@angular/material';

@Component({
  selector: 'app-timetable',
  templateUrl: './timetable.component.html',
  styleUrls: ['./timetable.component.css']
})
export class TimetableComponent implements OnInit {
  stationTitle: string;
  trips: Trip[];

  constructor(private activatedRoute: ActivatedRoute,
              private tripService: TripService,
              private dialog: MatDialog) {
  }

  ngOnInit() {
    this.activatedRoute.params.subscribe((params) => {
      this.stationTitle = params['stationTitle'];
      this.tripService.getTimetableByStationName(this.stationTitle)
        .subscribe((data) => {
          this.trips = data.map(trip => TripDTOToTripConverterService.convert(trip));
        }, error => {
          this.dialog.open(ErrorDialogComponent, {
            data: {message: error.error}
          });
        });
    });
  }

}
