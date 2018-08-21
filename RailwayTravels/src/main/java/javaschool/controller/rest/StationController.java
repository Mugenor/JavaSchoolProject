package javaschool.controller.rest;

import java.util.List;
import java.util.stream.Collectors;
import javaschool.controller.dtoentity.StationName;
import javaschool.service.api.StationService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Station controller.
 */
@RestController
@RequestMapping(path = "/station", produces = MediaType.APPLICATION_JSON_VALUE)
public class StationController {
    private StationService stationService;

    /**
     * Instantiates a new Station controller.
     *
     * @param stationService the station service
     */
    @Autowired
    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    /**
     * Gets all stations.
     *
     * @return the all stations
     */
    @GetMapping
    public List<StationName> getAllStations() {
        return stationService.findAll()
                .parallelStream().map(station -> new StationName(station.getTitle()))
                .collect(Collectors.toList());
    }

    /**
     * Add new station.
     *
     * @param stationName the station name
     */
    @PostMapping
    public void addNewStation(@Valid @RequestBody StationName stationName) {
        stationService.save(stationName.getName());
    }
}
