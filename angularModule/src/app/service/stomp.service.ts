import {Injectable} from '@angular/core';
import * as Stomp from 'stompjs';

@Injectable({
  providedIn: 'root'
})
export class StompService {
  private static TICKET_ENDPOINT_PREFIX = '/topic/ticket';
  private ws: any;

  constructor() {
    console.log('Stomp');
    const socket = new WebSocket('ws://localhost:8080/rwt/tickets');
    this.ws = Stomp.over(socket);
    const that = this;
    this.ws.connect({}, function (frame) {
    });
  }

  subscribeForTicketId(ticketId: number, callbackFn) {
    return this.ws.subscribe(StompService.TICKET_ENDPOINT_PREFIX + '/' + ticketId, callbackFn);
  }
}
