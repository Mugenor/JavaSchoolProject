import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Station} from '../entity/station';

@Injectable({
  providedIn: 'root'
})
export class StationService {

  constructor(private httpClient: HttpClient) { }

  getAllStations() {
    return this.httpClient.get<Station[]>('station');;
  }
}
