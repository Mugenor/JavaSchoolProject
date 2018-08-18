package javaschool.controller.rest;

import javaschool.controller.dtoentity.TripUpdate;
import javaschool.controller.dtoentity.UpdateDepartureInfo;
import javaschool.entity.Trip;
import javaschool.service.api.DepartureService;
import javaschool.service.api.RabbitService;
import javaschool.service.api.TripService;
import javaschool.service.converter.DepartureToDepartureDTOConverter;
import javaschool.service.impl.RabbitServiceImpl;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/departure", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DepartureController {
    private DepartureService departureService;
    private TripService tripService;
    private RabbitService rabbitService;

    @Autowired
    public DepartureController(DepartureService departureService,
                               TripService tripService,
                               RabbitService rabbitService) {
        this.departureService = departureService;
        this.tripService = tripService;
        this.rabbitService = rabbitService;
    }

    @PostMapping("/update")
    public void changeDepartureStation(@RequestBody UpdateDepartureInfo updateDepartureInfo) {
        departureService.updateDeparture(updateDepartureInfo.getTripId(),
                updateDepartureInfo.getDepartureIndex(),
                updateDepartureInfo.getDeparture());
        rabbitService.convertAndSend(
                new TripUpdate(TripUpdate.UPDATE, tripService.findById(updateDepartureInfo.getTripId())));
    }
}
