package javaschool.controller.rest;

import java.util.List;
import javaschool.controller.dtoentity.DepartureDTO;
import javaschool.controller.dtoentity.NewDepartureDTO;
import javaschool.service.api.DepartureService;
import javaschool.service.converter.DepartureToDepartureDTOConverter;
import javax.validation.Valid;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    @PostMapping(path="/add", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public DepartureDTO addNewDeparture(@Valid @RequestBody NewDepartureDTO newDeparture){
        return converter.convertTo(departureService.save(newDeparture));
    }
}
