import {Component, Input, OnInit} from '@angular/core';
import {Trip} from '../entity/trip';
import {Router} from '@angular/router';

@Component({
  selector: 'app-departure-list',
  templateUrl: './departure-list.component.html',
  styleUrls: ['./departure-list.component.css']
})
export class DepartureListComponent implements OnInit {
  @Input('trips')
  trips: Trip[];

  constructor(private router: Router) { }

  ngOnInit() {
  }

  routeToChooseTicket(trip: Trip) {
    window.open('client/choose-seat/' + trip.id + '/' + trip.chosen[0] + '/' + (trip.chosen[1] - 1), '_blank').focus();
    // this.router.navigate(['client/choose-seat', trip.id, trip.chosen[0], trip.chosen[1] - 1]);
  }
}
