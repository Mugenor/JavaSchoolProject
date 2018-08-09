import {DateToStringService} from '../service/date-to-string.service';

export class TripInfo {
  dateTimeFromAsString: string;
  dateTimeToAsString: string;

  constructor(public stationFrom: string,
          public stationTo: string,
          public dateTimeFrom: number,
          public dateTimeTo: number,
          public coachCount: number) {
    this.dateTimeFromAsString = DateToStringService.convert(new Date(this.dateTimeFrom));
    this.dateTimeToAsString = DateToStringService.convert(new Date(this.dateTimeTo));
  }
}
