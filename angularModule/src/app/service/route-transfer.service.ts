import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RouteTransferService {
  public data: any[];

  constructor() {
    this.data = [];
  }

  push(data: any) {
    this.data.push(data);
  }

  pop(): any {
    return this.data.pop();
  }
}
