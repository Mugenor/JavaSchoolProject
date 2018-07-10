package javaschool.controller.rest;

import java.util.List;
import java.util.stream.Collectors;
import javaschool.controller.dtoentity.DepartureDTO;
import javaschool.service.api.DepartureService;
import javaschool.service.converter.DepartureToDepartureDTOConverter;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/departure", produces = MediaType.APPLICATION_JSON_VALUE)
public class DepartureController {
    private DepartureService departureService;

    @Autowired
    public DepartureController(DepartureService departureService) {
        this.departureService = departureService;
    }

    @GetMapping
    public List<DepartureDTO> getAllDepartures() {
        return departureService.findAll(true, false);
    }

    @GetMapping("/find")
    public List<DepartureDTO> getDeparturesByStationFromAndStationToAndDateTimeFromAndDateTimeTo(
            @RequestParam String stationFrom, @RequestParam String stationTo,
            @RequestParam Long dateTimeFrom, @RequestParam Long dateTimeTo) {
        return departureService.findFromToBetween(stationFrom, stationTo, new LocalDateTime(dateTimeFrom), new LocalDateTime(dateTimeTo));
    }
}
