import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FindTripComponent } from './find-trip.component';

describe('FindTripComponent', () => {
  let component: FindTripComponent;
  let fixture: ComponentFixture<FindTripComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FindTripComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FindTripComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
