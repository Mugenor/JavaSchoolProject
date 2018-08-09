import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Trip} from '../entity/trip';
import {TripInfo} from '../entity/trip-info';

@Injectable({
  providedIn: 'root',
})
export class TripService {

  constructor(private httpClient: HttpClient) { }

  public getAvailableTrips() {
    return this.httpClient.get<Trip[]>('trip/available');
  }

  public getTripInfo(tripId: number, departureFromIndex: number, departureToIndex: number) {
    return this.httpClient.get<TripInfo>('trip/info/' + tripId + '/' + departureFromIndex + '/' + departureToIndex);
  }

  public getTimetableByStationName(stationName: string) {
    return this.httpClient.get<Trip[]>('trip/timetable/' + stationName);
  }
}
