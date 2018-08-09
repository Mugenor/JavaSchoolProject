import {Injectable} from '@angular/core';
import {Trip} from '../entity/trip';
import {Departure} from '../entity/departure';

@Injectable({
  providedIn: 'root'
})
export class TripDTOToTripConverterService {

  constructor() {
  }

  static convert(obj: Trip): Trip {
    const departures: Departure[] = [];
    for (let i = 0; i < obj.departures.length; ++i) {
      const curDeparture = obj.departures[i];
      departures.push(new Departure(curDeparture.id, curDeparture.sitsCount, curDeparture.freeSitsCount,
        curDeparture.stationFrom, curDeparture.stationTo, curDeparture.dateTimeFrom, curDeparture.dateTimeTo, curDeparture.numberInTrip));
    }
    return new Trip(obj.id, departures, obj.coachCount);
  }
}
