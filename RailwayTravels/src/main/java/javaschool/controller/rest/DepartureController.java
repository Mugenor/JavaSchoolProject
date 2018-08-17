package javaschool.controller.rest;

import javaschool.controller.dtoentity.UpdateDepartureInfo;
import javaschool.service.api.DepartureService;
import javaschool.service.converter.DepartureToDepartureDTOConverter;
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

    @Autowired
    public DepartureController(DepartureService departureService) {
        this.departureService = departureService;
    }

    @PostMapping("/update")
    public void changeDepartureStation(@RequestBody UpdateDepartureInfo updateDepartureInfo) {
        departureService.updateDeparture(updateDepartureInfo.getTripId(),
                updateDepartureInfo.getDepartureIndex(),
                updateDepartureInfo.getDeparture());
    }
}
