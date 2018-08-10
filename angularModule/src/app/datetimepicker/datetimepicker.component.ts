import {Component, ContentChild, ElementRef, EventEmitter, forwardRef, Input, OnInit, Output, ViewChild} from '@angular/core';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from '@angular/forms';

declare let $: any;

@Component({
  selector: 'app-datetimepicker',
  templateUrl: './datetimepicker.component.html',
  styleUrls: ['./datetimepicker.component.css'],
  providers: [{
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => DatetimepickerComponent),
    multi: true
  }]
})

export class DatetimepickerComponent implements OnInit, ControlValueAccessor {
  private datetimepickerInput: any;
  private datetimepicker: any;
  // private inputText: string;
  @ContentChild('datetime')
  dateTimeInput: ElementRef;
  @Input()
  configuration = {};
  @Input('datetime')
  _datetime: Date;
  private _minDate: Date;
  private _maxDate: Date;
  private _initialDate: Date;
  @Output()
  onDateChange = new EventEmitter<Date>();

  onChangeFn: any = () => {
  };
  onTouchedFn: any = () => {
  };

  constructor() {
  }

  ngOnInit() {
    $(() => {
      this.datetimepickerInput = $(this.dateTimeInput.nativeElement).datetimepicker(this.configuration);
      this.datetimepicker = this.datetimepickerInput.data('datetimepicker');
      this.datetimepickerInput.on('change.dp', (event) => {
        $(this.datetimepickerInput).focus();
        this.datetime = this.datetimepicker.getDate();
        this.onDateChange.emit(this.datetimepicker.getDate());
      });
    });
  }

  get minDate(): Date {
    return this._minDate;
  }

  @Input() set minDate(val: Date) {
    if (this.datetimepicker) {
      this.datetimepicker.setStartDate(val);
      this._minDate = val;
    }
  }

  get maxDate(): Date {
    return this._maxDate;
  }

  @Input() set maxDate(val: Date) {
    if (this.datetimepicker) {
      this.datetimepicker.setEndDate(val);
      this._maxDate = val;
    }
  }

  get initialDate(): Date {
    return this._initialDate;
  }

  @Input() set initialDate(val: Date) {
    if (this.datetimepicker) {
      this.datetimepicker.setInitialDate(val);
      this._initialDate = val;
    }
  }


  get datetime() {
    return this._datetime;
  }

  @Input() set datetime(val: Date) {
    if (this.datetimepicker) {
      this.datetimepicker.setDate(val);
    }
    this._datetime = val;
    this.onChangeFn(val);
  }

  registerOnChange(fn: any): void {
    this.onChangeFn = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouchedFn = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.dateTimeInput.nativeElement.disabled = isDisabled;
  }

  writeValue(obj: any): void {
    if (obj) {
      this.datetime = obj;
    }
  }


}
