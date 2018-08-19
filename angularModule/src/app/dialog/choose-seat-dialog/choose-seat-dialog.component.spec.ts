import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChooseSeatDialogComponent } from './choose-seat-dialog.component';

describe('ChooseSeatDialogComponent', () => {
  let component: ChooseSeatDialogComponent;
  let fixture: ComponentFixture<ChooseSeatDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChooseSeatDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChooseSeatDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
