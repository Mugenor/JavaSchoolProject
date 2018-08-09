import { TestBed, inject } from '@angular/core/testing';

import { TripDTOToTripConverterService } from './trip-dtoto-trip-converter.service';

describe('TripDTOToTripConverterService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TripDTOToTripConverterService]
    });
  });

  it('should be created', inject([TripDTOToTripConverterService], (service: TripDTOToTripConverterService) => {
    expect(service).toBeTruthy();
  }));
});
