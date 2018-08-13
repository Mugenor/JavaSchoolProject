export class TicketToDisplay {
  constructor(public id: number,
              public stationFrom: string, public stationTo: string,
              public dateTimeTo: number, public dateTimeFrom: number,
              public coachNumber: number, public seatNumber: number) {
  }

}
