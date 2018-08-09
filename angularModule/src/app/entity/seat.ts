import {Coach} from './coach';

export class Seat {
  style: any;
  engagedByYou = false;
  constructor(public seatNumber: number, public engaged: boolean) {
  }
}
