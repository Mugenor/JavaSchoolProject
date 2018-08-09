import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';
import {StationService} from '../service/station.service';

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

}
