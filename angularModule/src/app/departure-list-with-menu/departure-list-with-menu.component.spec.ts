import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DepartureListWithMenuComponent } from './departure-list-with-menu.component';

describe('DepartureListComponent', () => {
  let component: DepartureListWithMenuComponent;
  let fixture: ComponentFixture<DepartureListWithMenuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DepartureListWithMenuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DepartureListWithMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
