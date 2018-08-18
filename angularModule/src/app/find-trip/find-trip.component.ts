import {AfterViewInit, Component, ElementRef, Input, OnInit, ViewChild, ViewChildren} from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  FormGroup,
  FormGroupDirective,
  NgForm,
  ValidationErrors,
  Validators
} from '@angular/forms';
import {StationService} from '../service/station.service';
import {map, startWith} from 'rxjs/operators';
import {TripService} from '../service/trip.service';
import {TripDTOToTripConverterService} from '../service/trip-dtoto-trip-converter.service';
import {Trip} from '../entity/trip';
import {ErrorDialogComponent} from '../dialog/error-dialog/error-dialog.component';
import {ErrorStateMatcher, MatDialog} from '@angular/material';

declare let $: any;

@Component({
  selector: 'app-find-trip',
  templateUrl: './find-trip.component.html',
  styleUrls: ['./find-trip.component.css']
})
export class FindTripComponent implements OnInit {
  private DIDNT_FIND_ANYTHING = 'We didn\'t find anything';
  dateTimeErrorStateMatcher = new DateTimeErrorStateMatcher();
  @ViewChild('dateTimeFromInput')
  _dateTimeFromInput: ElementRef;
  @ViewChild('dateTimeToInput')
  _dateTimeToInput: ElementRef;
  tripForm: FormGroup;
  stationTitles: string[];
  filteredStationsFrom: string[];
  filteredStationsTo: string[];
  dateTimeConfig: any;
  dateTimeFromState: DateState;
  dateTimeToState: DateState;
  maxTransferValue = 2;
  minTransferValue = 0;
  showWithTransfers = false;
  trips = [];
  tripsWithTransfers: Trip[][];
  searching = false;
  message = 'Enter your request';
  private dateTimeFromPicker = {picker: null};
  private dateTimeToPicker = {picker: null};
  private $dateTimeFromInput: any;
  private $dateTimeToInput: any;

  constructor(private formBuilder: FormBuilder,
              private stationService: StationService,
              private tripService: TripService,
              private dialog: MatDialog) {
    const today = new Date();
    today.setMinutes(today.getMinutes());
    const startDate = new Date(Date.UTC(1900, 1));
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
    $(() => {
      this.$dateTimeFromInput = $(this._dateTimeFromInput.nativeElement);
      this.$dateTimeToInput = $(this._dateTimeToInput.nativeElement);
      this.dateTimeFromPicker.picker = this.$dateTimeFromInput.datetimepicker(this.dateTimeConfig).data('datetimepicker');
      this.dateTimeToPicker.picker = this.$dateTimeToInput.datetimepicker(this.dateTimeConfig).data('datetimepicker');
      $(this._dateTimeFromInput.nativeElement).on('change.dp', (event) => {
        if (this.$dateTimeFromInput.val().trim().length !== 0) {
          this.dateTimeToState.minDate =
            this.dateTimeToPicker.picker.startDate = this.dateTimeFromPicker.picker.getUTCDate();
          if (this.dateTimeToPicker.picker.initialDate < this.dateTimeToPicker.picker.startDate) {
            this.dateTimeToPicker.picker.initialDate = this.dateTimeToPicker.picker.startDate;
          }
          this.dateTimeToState.minDate = this.dateTimeFromPicker.picker.getUTCDate();
          this.dateTimeFrom.setValue(this.dateTimeFromPicker.picker.getFormattedDate());
          this.$dateTimeFromInput.focus();
        }
        this.dateTimeFrom.updateValueAndValidity();
      });
      $(this._dateTimeToInput.nativeElement).on('change.dp', (event) => {
        if (this.$dateTimeToInput.val().trim().length !== 0) {
          this.dateTimeFromState.maxDate =
            this.dateTimeFromPicker.picker.endDate = this.dateTimeToPicker.picker.getUTCDate();
          if (this.dateTimeFromPicker.picker.initialDate > this.dateTimeFromPicker.picker.endDate) {
            this.dateTimeFromPicker.picker.initialDate = this.dateTimeFromPicker.picker.endDate;
          }
          this.dateTimeTo.setValue(this.dateTimeToPicker.picker.getFormattedDate());
          this.$dateTimeToInput.focus();
        }
        this.dateTimeTo.updateValueAndValidity();
      });
    });
    this.tripForm = this.formBuilder.group({
      stations: this.formBuilder.group({
        stationFrom: ['', Validators.required],
        stationTo: ['', Validators.required]
      }, {validator: this.stationsDifferent}),
      times: this.formBuilder.group({
        dateTimeFrom: ['', [this.betweenDates(this.dateTimeFromState, this.dateTimeFromPicker)]],
        dateTimeTo: ['', [this.betweenDates(this.dateTimeToState, this.dateTimeToPicker)]]
      }),
      transfers: this.formBuilder.group({
        findWithTransfers: [false],
        maxTransferCount: [0]
      })
    });
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

  get transfers() {
    return this.tripForm.get('transfers');
  }

  get findWithTransfers() {
    return this.transfers.get('findWithTransfers');
  }

  get maxTransferCount() {
    return this.transfers.get('maxTransferCount');
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
          }, error => {
            this.dialog.open(ErrorDialogComponent, {
              data: {message: error.error}
            });
          });
        this.stationTo.valueChanges.pipe(startWith(''), map(includeMap))
          .subscribe((titles) => {
            this.filteredStationsFrom = titles.filter(title => title !== this.stationTo.value);
            this.filteredStationsTo = titles.filter(title => title !== this.stationFrom.value);
          });
      }, error => {
        this.dialog.open(ErrorDialogComponent, {
          data: {message: error.error}
        });
      });
    this.maxTransferCount.valueChanges.subscribe(value => {
      if (value > this.maxTransferValue) {
        this.maxTransferCount.setValue(this.maxTransferValue);
      } else if (value < this.minTransferValue) {
        this.maxTransferCount.setValue(this.minTransferValue);
      }
      this.maxTransferCount.setErrors(this.requiredIfFindWithTransfers(this.transfers as FormGroup));
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

  requiredIfFindWithTransfers(control: FormGroup): ValidationErrors | null {
    const findWithTransfers = control.get('findWithTransfers');
    const maxTransferCount = control.get('maxTransferCount');
    return (!findWithTransfers || !maxTransferCount) || (findWithTransfers.value && !maxTransferCount.value) ? {'required': true} : null;
  }

  betweenDates(datesState: DateState, picker) {
    return (control: AbstractControl): ValidationErrors | null => {
      if (picker.picker) {
        debugger;
        const date: Date = picker.picker.getDate();
        if (date.getTime() > 0) {
          if (!control.value || control.value.trim().length === 0) {
            return {required: true};
          } else if (datesState.minDate.getTime() > date.getTime()) {
            return {beforeMin: true};
          } else if (datesState.maxDate.getTime() < date.getTime()) {
            return {afterMax: true};
          }
        }
      }
      return null;
    };
  }

  find() {
    this.dateTimeFrom.updateValueAndValidity();
    this.dateTimeTo.updateValueAndValidity();
    if (this.tripForm.valid) {
      if (!this.dateTimeFrom.value) {
        this.dateTimeFrom.setErrors({required: true});
      }
      this.message = '';
      this.searching = true;
      if (this.findWithTransfers.value) {
        this.tripService.findWithTransfers(this.stationFrom.value, this.stationTo.value,
          this.dateTimeFromPicker.picker.getDate(), this.dateTimeToPicker.picker.getDate(), this.maxTransferCount.value)
          .subscribe((data) => {
            this.trips = [];
            this.tripsWithTransfers = data.map(trips => trips.map(trip => TripDTOToTripConverterService.convert(trip)));
            if (this.tripsWithTransfers.length === 0) {
              this.message = this.DIDNT_FIND_ANYTHING;
            } else {
              this.message = undefined;
            }
            this.searching = false;
            this.showWithTransfers = true;
          }, error => {
            this.dialog.open(ErrorDialogComponent, {
              data: {message: error.error}
            });
          });
      } else {
        this.tripService.find(this.stationFrom.value, this.stationTo.value,
          this.dateTimeFromPicker.picker.getDate(), this.dateTimeToPicker.picker.getDate())
          .subscribe((data) => {
            this.tripsWithTransfers = [];
            this.trips = data.map(trip => TripDTOToTripConverterService.convert(trip));
            if (this.trips.length === 0) {
              this.message = this.DIDNT_FIND_ANYTHING;
            } else {
              this.message = undefined;
            }
            this.searching = false;
            this.showWithTransfers = false;
          }, error => {
            this.dialog.open(ErrorDialogComponent, {
              data: {message: error.error}
            });
          });
      }
    }
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

export class DateTimeErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    console.log('error state matcher: ', control, form);
    return true;
  }
}
