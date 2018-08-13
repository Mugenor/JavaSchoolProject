import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';
import {StationService} from '../service/station.service';
import {Sort} from '@angular/material';

@Component({
  selector: 'app-stations',
  templateUrl: './stations.component.html',
  styleUrls: ['./stations.component.css'],
})
export class StationsComponent implements OnInit {
  stations: string[];

  constructor(private stationService: StationService) {
  }

  ngOnInit() {
    this.stationService.getAllStations()
      .subscribe((data) => {
        this.stations = [];
        for (let i = 0; i < data.length; ++i) {
          this.stations.push(data[i].name);
        }
      });
  }

  sortStations(sort: Sort) {
    if (!sort.active || sort.direction === '') {
      return;
    }
    this.stations = this.stations.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'station': return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
        default: return 0;
      }
    });
  }

}
