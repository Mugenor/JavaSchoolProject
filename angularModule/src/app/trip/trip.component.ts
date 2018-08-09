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

  constructor() { }

  ngOnInit() {
    console.log(this.trip);
  }

  selectStation(trip: Trip, stationIndex: number) {
    trip.toggleChosen(stationIndex);
  }
}
