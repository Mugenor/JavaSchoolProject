import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ValidationErrors, Validators} from '@angular/forms';
import {StationService} from '../service/station.service';
import {map, startWith} from 'rxjs/operators';
import {TripService} from '../service/trip.service';
import {TripDTOToTripConverterService} from '../service/trip-dtoto-trip-converter.service';

@Component({
  selector: 'app-find-trip',
  templateUrl: './find-trip.component.html',
  styleUrls: ['./find-trip.component.css']
})
export class FindTripComponent implements OnInit {
  tripForm: FormGroup;
  stationTitles: string[];
  filteredStationsFrom: string[];
  filteredStationsTo: string[];
  dateTimeConfig: any;
  dateTimeFromState: DateState;
  dateTimeToState: DateState;
  trips = [];

  constructor(private formBuilder: FormBuilder,
              private stationService: StationService,
              private tripService: TripService) {
    const today = new Date();
    today.setMinutes(today.getMinutes());
    this.tripForm = this.formBuilder.group({
      stations: this.formBuilder.group({
        stationFrom: ['', Validators.required],
        stationTo: ['', Validators.required]
      }, {validator: this.stationsDifferent}),
      times: this.formBuilder.group({
        dateTimeFrom: [today, Validators.required],
        dateTimeTo: [today, Validators.required]
      })
    });
    const endDate = new Date();
    endDate.setFullYear(endDate.getFullYear() + 5);
    this.dateTimeConfig = {
      format: 'dd.mm.yyyy, hh:ii',
      startDate: today,
      endDate: endDate,
      immediateUpdates: true
    };
    this.dateTimeFromState = new DateState();
    this.dateTimeFromState.initializeDateState(today, endDate, today);
    this.dateTimeToState = new DateState();
    this.dateTimeToState.initializeDateState(today, endDate, today);
  }

  get stationFrom() {
    return this.stations.get('stationFrom') as FormControl;
  }

  get stationTo() {
    return this.stations.get('stationTo') as FormControl;
  }

  get stations() {
    return this.tripForm.get('stations') as FormGroup;
  }

  get times() {
    return this.tripForm.get('times');
  }

  get dateTimeFrom() {
    return this.times.get('dateTimeFrom');
  }

  get dateTimeTo() {
    return this.times.get('dateTimeTo');
  }

  ngOnInit() {
    const includeMap = (value) => {
      return this.stationTitles.filter
      (station => station.toLowerCase().includes(value.toLowerCase()));
    };
    this.stationService.getAllStations()
      .subscribe((data) => {
        this.stationTitles = data.map(v => v.name);
        this.stationFrom.valueChanges.pipe(startWith(''), map(includeMap))
          .subscribe((titles) => {
            this.filteredStationsFrom = titles.filter(title => title !== this.stationTo.value);
            this.filteredStationsTo = titles.filter(title => title !== this.stationFrom.value);
          });
        this.stationTo.valueChanges.pipe(startWith(''), map(includeMap))
          .subscribe((titles) => {
            this.filteredStationsFrom = titles.filter(title => title !== this.stationTo.value);
            this.filteredStationsTo = titles.filter(title => title !== this.stationFrom.value);
          });
      });
    this.dateTimeFrom.valueChanges.subscribe(value => {
      if (value.getTime() < this.dateTimeFromState.minDate.getTime()) {
        this.dateTimeFrom.setValue(this.dateTimeFromState.minDate);
        this.dateTimeFromState.initialDate = this.dateTimeFromState.minDate;
      } else if (value.getTime() > this.dateTimeFromState.maxDate.getTime()) {
        this.dateTimeFrom.setValue(this.dateTimeFromState.maxDate);
        this.dateTimeFromState.initialDate = this.dateTimeFromState.minDate;
      }
      this.dateTimeToState.minDate = this.dateTimeFrom.value;
      if (this.dateTimeToState.initialDate.getTime() < this.dateTimeToState.minDate.getTime()) {
        this.dateTimeToState.initialDate = this.dateTimeToState.minDate;
      }
    });
    this.dateTimeTo.valueChanges.subscribe(value => {
      if (value.getTime() < this.dateTimeToState.minDate.getTime()) {
        this.dateTimeTo.setValue(this.dateTimeToState.minDate);
        this.dateTimeToState.initialDate = this.dateTimeToState.minDate;
      } else if (value.getTime() > this.dateTimeToState.maxDate.getTime()) {
        this.dateTimeTo.setValue(this.dateTimeToState.maxDate);
        this.dateTimeToState.initialDate = this.dateTimeToState.minDate;
      }
      this.dateTimeFromState.maxDate = this.dateTimeTo.value;
      if (this.dateTimeFromState.initialDate.getTime() > this.dateTimeFromState.maxDate.getTime()) {
        this.dateTimeFromState.initialDate = this.dateTimeFromState.minDate;
      }
    });
  }

  checkStationExists(stationControl: FormControl, stationTitles: string[]) {
    const lowerCaseValue: string = stationControl.value.toLowerCase();
    for (let i = 0; i < stationTitles.length; ++i) {
      if (stationTitles[i].toLowerCase().includes(lowerCaseValue)) {
        stationControl.setValue(stationTitles[i]);
        return;
      }
    }
    stationControl.setValue('');
  }


  stationsDifferent(control: FormGroup): ValidationErrors | null {
    const stationFrom = control.get('stationFrom');
    const stationTo = control.get('stationTo');
    return stationFrom && stationTo && stationFrom.value === stationTo.value ? {'stationIdentical': true} : null;
  }

  find() {
    console.log('find');
    this.tripService.find(this.stationFrom.value, this.stationTo.value, this.dateTimeFrom.value, this.dateTimeTo.value)
      .subscribe((data) => {
        this.trips = data.map(trip => TripDTOToTripConverterService.convert(trip));
      });
  }
}

class DateState {
  minDate: Date;
  maxDate: Date;
  initialDate: Date;

  initializeDateState(minDate: Date, maxDate: Date, initialDate: Date) {
    this.minDate = minDate;
    this.maxDate = maxDate;
    this.initialDate = initialDate;
  }

}
