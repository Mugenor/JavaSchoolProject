import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Trip} from '../entity/trip';
import {TripInfo} from '../entity/trip-info';

@Injectable({
  providedIn: 'root',
})
export class TripService {

  constructor(private httpClient: HttpClient) {
  }

  public getAvailableTrips() {
    return this.httpClient.get<Trip[]>('trip/available');
  }

  public getTripInfo(tripId: number, departureFromIndex: number, departureToIndex: number) {
    return this.httpClient.get<TripInfo>('trip/info/' + tripId + '/' + departureFromIndex + '/' + departureToIndex);
  }

  public getTimetableByStationName(stationName: string) {
    return this.httpClient.get<Trip[]>('trip/timetable/' + stationName);
  }

  public findWithTransfers(stationFrom: string, stationTo: string, dateTimeFrom: Date, dateTimeTo: Date, maxTransferCount: number) {
    const params = new HttpParams({
      fromObject: {
        departureStation: stationFrom,
        arrivalStation: stationTo,
        dateTimeFrom: dateTimeFrom.getTime().toString(),
        dateTimeTo: dateTimeTo.getTime().toString()
      }
    });
    return this.httpClient.get<Trip[][]>('trip/find/' + maxTransferCount, {params: params});
  }

  public find(stationFrom: string, stationTo: string, dateTimeFrom: Date, dateTimeTo: Date) {
    const params = new HttpParams({
      fromObject: {
        departureStation: stationFrom,
        arrivalStation: stationTo,
        dateTimeFrom: dateTimeFrom.getTime().toString(),
        dateTimeTo: dateTimeTo.getTime().toString()
      }
    });
    return this.httpClient.get<Trip[]>('trip/find', {params: params});
  }
}
