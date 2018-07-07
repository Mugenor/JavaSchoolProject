package javaschool.controller.rest;

import java.util.List;
import java.util.stream.Collectors;
import javaschool.entity.Station;
import javaschool.service.api.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/station", produces = MediaType.APPLICATION_JSON_VALUE)
public class StationController {
    private StationService stationService;

    @Autowired
    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping
    public List<String> getAllStations() {
        return stationService.findAll()
                .parallelStream().map(Station::getTitle)
                .collect(Collectors.toList());
    }
}
