import {OccupiedSeat} from './occupied-seat';

export class TrainInfo {
  constructor(public coachCount: number, public occupiedSeats: OccupiedSeat[]) {

  }
}
