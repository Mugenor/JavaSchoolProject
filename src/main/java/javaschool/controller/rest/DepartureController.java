package javaschool.controller.rest;

import java.util.List;
import java.util.stream.Collectors;
import javaschool.controller.dtoentity.DepartureDTO;
import javaschool.service.api.DepartureService;
import javaschool.service.converter.DepartureToDepartureDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/departure", produces = MediaType.APPLICATION_JSON_VALUE)
public class DepartureController {
    private DepartureService departureService;
    private DepartureToDepartureDTOConverter departureDTOConverter;

    @Autowired
    public DepartureController(DepartureService departureService, DepartureToDepartureDTOConverter departureDTOConverter) {
        this.departureService = departureService;
        this.departureDTOConverter = departureDTOConverter;
    }

    @GetMapping
    public List<DepartureDTO> getAllDepartures() {
        return departureService.findAll(true, false)
                .parallelStream().map((departure -> departureDTOConverter.convertTo(departure)))
                .collect(Collectors.toList());
    }
}
