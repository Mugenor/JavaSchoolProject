import {DateToStringService} from '../service/date-to-string.service';

export class Departure {
  dateTimeFromAsString: string;
  dateTimeToAsString: string;

  constructor(public id: any, public sitsCount: number, public freeSitsCount: number,
              public stationFrom: string, public stationTo: string,
              public dateTimeFrom: number, public dateTimeTo: number,
              public numberInTrip: number) {
    this.dateTimeFromAsString = DateToStringService.convert(new Date(this.dateTimeFrom));
    this.dateTimeToAsString = DateToStringService.convert(new Date(this.dateTimeTo));
  }
}
