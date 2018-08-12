import {Component, Input, OnInit} from '@angular/core';
import {Trip} from '../entity/trip';
import {Router} from '@angular/router';

@Component({
  selector: 'app-departure-list-with-menu',
  templateUrl: './departure-list-with-menu.component.html',
  styleUrls: ['./departure-list-with-menu.component.css']
})
export class DepartureListWithMenuComponent implements OnInit {
  @Input('trips')
  trips: Trip[];

  constructor() {
  }

  ngOnInit() {
  }
}
