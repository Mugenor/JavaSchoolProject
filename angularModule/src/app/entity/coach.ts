import {Seat} from './seat';

export class Coach {
  static DEFAULT_SEAT_COUNT = 36;
  style: any;
  constructor(public coachNumber: number, public seats: Seat[]) {
  }
}
