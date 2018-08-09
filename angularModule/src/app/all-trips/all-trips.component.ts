import {Component, OnInit} from '@angular/core';
import {TripService} from '../service/trip.service';
import {Trip} from '../entity/trip';
import {TripDTOToTripConverterService} from '../service/trip-dtoto-trip-converter.service';
import {TicketService} from '../service/ticket.service';
import {Router} from '@angular/router';
import {RouteTransferService} from '../service/route-transfer.service';

@Component({
  selector: 'app-all-departures',
  templateUrl: './all-trips.component.html',
  styleUrls: ['./all-trips.component.css']
})
export class AllTripsComponent implements OnInit {
  trips: Trip[];

  constructor(private tripService: TripService,
              private router: Router) {
  }

  ngOnInit() {
    this.tripService.getAvailableTrips().subscribe((data) => {
      this.trips = data.map(trip => TripDTOToTripConverterService.convert(trip));
      console.log(this.trips);
    });
  }
}
