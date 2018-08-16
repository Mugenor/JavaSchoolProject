package javaschool.controller.rest;

import javaschool.service.api.DepartureService;
import javaschool.service.converter.DepartureToDepartureDTOConverter;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/departure", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DepartureController {
    private DepartureService departureService;
    private DepartureToDepartureDTOConverter converter;

    @Autowired
    public DepartureController(DepartureService departureService,
                               DepartureToDepartureDTOConverter converter) {
        this.departureService = departureService;
        this.converter = converter;
    }

    @PostMapping("/change/departure/station")
    public void changeDepartureStation(@RequestParam Integer tripId,
                                       @RequestParam Integer departureIndex,
                                       @RequestParam String stationName) {
        departureService.changeDepartureStation(tripId, departureIndex, stationName);
    }

    @PostMapping("/change/arrival/station")
    public void changeArrivalStation(@RequestParam Integer tripId,
                                     @RequestParam Integer departureIndex,
                                     @RequestParam String stationName) {
        departureService.changeArrivalStation(tripId, departureIndex, stationName);
    }

    @PostMapping("/change/departure/time")
    public void changeDepartureTime(@RequestParam Integer tripId,
                                    @RequestParam Integer departureIndex,
                                    @RequestParam Long time) {
        departureService.changeDepartureTime(tripId, departureIndex, new LocalDateTime(time));
    }

    @PostMapping("/change/arrival/time")
    public void changeArrivalTime(@RequestParam Integer tripId,
                                  @RequestParam Integer departureIndex,
                                  @RequestParam Long time) {
        departureService.changeArrivalTime(tripId, departureIndex, new LocalDateTime(time));
    }
}
