import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material';
import {TripInfo} from '../../entity/trip-info';
import {OccupiedSeat} from '../../entity/occupied-seat';
import {Coach} from '../../entity/coach';
import {Seat} from '../../entity/seat';
import {Ticket} from '../../entity/ticket';
import {ErrorDialogComponent} from '../error-dialog/error-dialog.component';
import {ActivatedRoute} from '@angular/router';
import {TripService} from '../../service/trip.service';
import {TicketService} from '../../service/ticket.service';

@Component({
  selector: 'app-choose-seat-dialog',
  templateUrl: './choose-seat-dialog.component.html',
  styleUrls: ['./choose-seat-dialog.component.css']
})
export class ChooseSeatDialogComponent implements OnInit {
  private static REGISTERED_MESSAGE = 'You already bought a ticket on this trip';
  private static TICKET_BOUGHT_MESSAGE = 'You bought a ticket';
  tripInfo: TripInfo;
  tripId: number;
  departureFromIndex: number;
  departureToIndex: number;
  chosenSeat: OccupiedSeat;
  coaches: Coach[];

  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
              private activatedRoute: ActivatedRoute,
              private tripService: TripService,
              private ticketService: TicketService,
              private dialogRef: MatDialogRef<ChooseSeatDialogComponent>,
              private dialog: MatDialog) {
    this.coaches = [];
  }

  isChosen(coach: Coach, seat: Seat): boolean {
    return this.chosenSeat &&
      coach.coachNumber === this.chosenSeat.coachNumber &&
      seat.seatNumber === this.chosenSeat.seatNumber;
  }

  chooseSeat(coach: Coach, seat: Seat) {
    if (!seat.engaged && !seat.engagedByYou) {
      if (!this.chosenSeat) {
        this.chosenSeat = new OccupiedSeat(coach.coachNumber, seat.seatNumber);
      } else {
        this.chosenSeat.coachNumber = coach.coachNumber;
        this.chosenSeat.seatNumber = seat.seatNumber;
      }
    }
  }

  buyTicket() {
    if (this.chosenSeat) {
      this.ticketService.buyTicket(new Ticket(this.tripId, this.departureFromIndex, this.departureToIndex,
        this.chosenSeat.coachNumber, this.chosenSeat.seatNumber))
        .subscribe((data) => {
          this.dialogRef.close();
          this.dialog.open(ErrorDialogComponent, {
            data: { message: ChooseSeatDialogComponent.TICKET_BOUGHT_MESSAGE}
          });
        }, error => {
          this.dialog.open(ErrorDialogComponent, {
            data: {message: error.error}
          });
        });
    }
  }

  ngOnInit() {
    this.tripId = this.data.tripId;
    this.departureFromIndex = this.data.departureFromIndex;
    this.departureToIndex = this.data.departureToIndex;
    this.ticketService.isRegistered(this.tripId)
      .subscribe((data) => {
        if (data.valueOf()) {
          this.dialogRef.close();
          this.dialog.open(ErrorDialogComponent, {
            data: {message: ChooseSeatDialogComponent.REGISTERED_MESSAGE}
          });
        }
      }, error => {
        this.dialog.open(ErrorDialogComponent, {
          data: {message: error.error}
        });
      });
    this.tripService.getTripInfo(this.tripId, this.departureFromIndex, this.departureToIndex)
      .subscribe((data) => {
        this.tripInfo = new TripInfo(data.stationFrom, data.stationTo, data.dateTimeFrom, data.dateTimeTo, data.coachCount);
      }, error => {
        this.dialog.open(ErrorDialogComponent, {
          data: {message: error.error}
        });
      });
    this.ticketService.getOccupiedSeatWith(this.tripId, this.departureFromIndex, this.departureToIndex)
      .subscribe((data) => {
        for (let i = 0; i < data.coachCount; ++i) {
          const seats: Seat[] = [];
          for (let j = 0; j < Coach.DEFAULT_SEAT_COUNT; ++j) {
            const seat: Seat = new Seat(j + 1,
              this.arrayContain(data.occupiedSeats, new OccupiedSeat(i + 1, j + 1)));
            seat.style = {};
            switch (seat.seatNumber % 4) {
              case 3:
                seat.style.left = 157 + 65 * ((seat.seatNumber - (seat.seatNumber % 4)) / 4);
                seat.style.top = 19 + (seat.seatNumber % 2) * 25;
                break;
              case 0:
                seat.style.left = 157 + 65 * (((seat.seatNumber - (seat.seatNumber % 4)) / 4) - 1);
                seat.style.top = 19 + (seat.seatNumber % 2) * 25;
                break;
              default:
                seat.style.left = 117 + 65 * ((seat.seatNumber - (seat.seatNumber % 4)) / 4);
                seat.style.top = 19 + (seat.seatNumber % 2) * 25;
            }
            seat.style.left += 'px';
            seat.style.top += 'px';
            seats.push(seat);
          }
          this.coaches.push(new Coach(i + 1, seats));
        }
      }, error => {
        this.dialog.open(ErrorDialogComponent, {
          data: {message: error.error}
        });
      });
  }

  private arrayContain(arr: any[], obj: any) {
    for (let i = 0; i < arr.length; ++i) {
      let found = true;
      for (const key in obj) {
        if (obj[key] !== arr[i][key]) {
          found = false;
        }
      }
      if (found) {
        return true;
      }
    }
    return false;
  }

}
