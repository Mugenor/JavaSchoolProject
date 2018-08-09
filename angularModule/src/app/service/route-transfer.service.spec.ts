import { TestBed, inject } from '@angular/core/testing';

import { RouteTransferService } from './route-transfer.service';

describe('RouteTransferService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [RouteTransferService]
    });
  });

  it('should be created', inject([RouteTransferService], (service: RouteTransferService) => {
    expect(service).toBeTruthy();
  }));
});
