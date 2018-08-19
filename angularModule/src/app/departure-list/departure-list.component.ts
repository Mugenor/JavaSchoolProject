import {Component, Input, OnInit} from '@angular/core';
import {Trip} from '../entity/trip';
import {Router} from '@angular/router';
import {MatDialog} from '@angular/material';
import {ChooseSeatDialogComponent} from '../dialog/choose-seat-dialog/choose-seat-dialog.component';

@Component({
  selector: 'app-departure-list',
  templateUrl: './departure-list.component.html',
  styleUrls: ['./departure-list.component.css']
})
export class DepartureListComponent implements OnInit {
  @Input('trips')
  trips: Trip[];

  constructor(private router: Router,
              private dialog: MatDialog) { }

  ngOnInit() {
  }

  showChooseSeatDialog(trip: Trip) {
    // window.open('client/choose-seat/' + trip.id + '/' + trip.chosen[0] + '/' + (trip.chosen[1] - 1), '_blank').focus();
    // this.router.navigate(['client/choose-seat', trip.id, trip.chosen[0], trip.chosen[1] - 1]);
    this.dialog.open(ChooseSeatDialogComponent, {
      data: {
        tripId: trip.id,
        departureFromIndex: trip.chosen[0],
        departureToIndex: trip.chosen[1]
      }
    });
  }
}
