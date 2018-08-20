package javaschool.controller.rest;

import javaschool.controller.dtoentity.TripUpdate;
import javaschool.controller.dtoentity.UpdateDepartureInfo;
import javaschool.service.api.DepartureService;
import javaschool.service.api.RabbitService;
import javaschool.service.api.TripService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public void changeDepartureStation(@Valid @RequestBody UpdateDepartureInfo updateDepartureInfo) {
        departureService.updateDeparture(updateDepartureInfo.getTripId(),
                updateDepartureInfo.getDepartureIndex(),
                updateDepartureInfo.getDeparture());
        rabbitService.convertAndSend(
                new TripUpdate(TripUpdate.UPDATE, tripService.findById(updateDepartureInfo.getTripId())));
    }
}
