import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Trip} from '../entity/trip';
import {OccupiedSeat} from '../entity/occupied-seat';
import {Coach} from '../entity/coach';
import {TrainInfo} from '../entity/train-info';
import {Ticket} from '../entity/ticket';

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  constructor(private httpClient: HttpClient) {
  }

  getOccupiedSeatWithTrip(trip: Trip) {
    if (trip.chosen.length !== 2) {
      throw new Error('Incorrect trip.chosen length');
    }
    return this.httpClient.get('trip/occupied-seats/' + trip.id + '/' + trip.chosen[0] + '/' + trip.chosen[1]);
  }

  isRegistered(tripId: number) {
    return this.httpClient.get<Boolean>('trip/registered/' + tripId);
  }

  getOccupiedSeatWith(tripId: number, departureFromIndex: number, departureToIndex: number) {
    return this.httpClient.get<TrainInfo>('trip/occupied-seats/' + tripId + '/' + departureFromIndex + '/' + departureToIndex);
  }

  buyTicket(ticket: Ticket) {
    return this.httpClient.post('client/tickets/buy', ticket);
  }
}
