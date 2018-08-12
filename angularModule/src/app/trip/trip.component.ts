import {Component, Input, OnInit} from '@angular/core';
import {Trip} from '../entity/trip';

@Component({
  selector: 'app-trip',
  templateUrl: './trip.component.html',
  styleUrls: ['./trip.component.css']
})
export class TripComponent implements OnInit {
  @Input('trip')
  trip: Trip;
  @Input('selectable')
  selectable = true;

  constructor() {
  }

  ngOnInit() {
  }

  selectStation(trip: Trip, stationIndex: number) {
    if (this.selectable) {
      trip.toggleChosen(stationIndex);
    }
  }
}
