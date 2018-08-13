import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {TicketService} from '../service/ticket.service';
import {TicketToDisplay} from '../entity/ticket-to-display';
import {MatButton, MatTable, MatTableDataSource} from '@angular/material';
import {Subject} from 'rxjs';
import {DateToStringService} from '../service/date-to-string.service';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, AfterViewInit {
  private ticketList: TicketToDisplay[] = [];
  ticketDataSource = new MatTableDataSource();
  ticketSubject;
  displayedColumns: string[] = ['departureStation', 'arrivalStation', 'dateTimeFrom',
    'dateTimeTo', 'coachNumber', 'seatNumber', 'returnButton'];

  constructor(private ticketService: TicketService) {
  }

  ngOnInit() {
    this.ticketSubject = this.ticketDataSource.connect();
  }

  ngAfterViewInit() {
    this.ticketService.getMyTickets()
      .subscribe(data => {
        this.ticketList = data;
        this.ticketSubject.next(data);
      });
  }

  getDateAsString(date: number): string {
    return DateToStringService.convert(new Date(date));
  }

  returnTicket(button: MatButton, ticket: TicketToDisplay) {
    console.log(button, ticket);
    button.disabled = true;
    this.ticketService.returnTicket(ticket.id)
      .subscribe((data) => {
        this.ticketList = this.ticketList.filter(curTicket => curTicket.id !== ticket.id);
        this.ticketSubject.next(this.ticketList);
      });
  }

}