import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DateToStringService {
  static dateOptions = {year: 'numeric', month: 'numeric', day: 'numeric', hour: 'numeric', minute: 'numeric'};

  constructor() {
  }

  static convert(date: Date) {
    return date.toLocaleTimeString(undefined, DateToStringService.dateOptions);
  }
}
