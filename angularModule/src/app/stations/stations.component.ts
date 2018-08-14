import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';
import {StationService} from '../service/station.service';
import {MatDialog, Sort} from '@angular/material';
import {ErrorDialogComponent} from '../dialog/error-dialog/error-dialog.component';

@Component({
  selector: 'app-stations',
  templateUrl: './stations.component.html',
  styleUrls: ['./stations.component.css'],
})
export class StationsComponent implements OnInit {
  stations: string[];

  constructor(private stationService: StationService,
              private dialog: MatDialog) {
  }

  ngOnInit() {
    this.stationService.getAllStations()
      .subscribe((data) => {
        this.stations = [];
        for (let i = 0; i < data.length; ++i) {
          this.stations.push(data[i].name);
        }
      }, error => {
        this.dialog.open(ErrorDialogComponent, {
          data: {message: error.error}
        });
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
