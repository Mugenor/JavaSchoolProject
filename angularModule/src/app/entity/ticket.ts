export class Ticket {
  constructor(public tripId: number,
              public  departureFromIndex: number,
              public  departureToIndex: number,
              public  coachNumber: number,
              public seatNum: number) {
  }

}
