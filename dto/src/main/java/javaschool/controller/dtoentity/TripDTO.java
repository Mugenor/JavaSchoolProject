package javaschool.controller.dtoentity;

import java.util.List;

public class TripDTO {
    private Integer id;
    private List<DepartureDTO> departures;

    public TripDTO() {
    }

    public TripDTO(Integer id, List<DepartureDTO> departures) {
        this.id = id;
        this.departures = departures;
    }

    public Integer getId() {
        return id;
    }

    public TripDTO setId(Integer id) {
        this.id = id;
        return this;
    }

    public List<DepartureDTO> getDepartures() {
        return departures;
    }

    public TripDTO setDepartures(List<DepartureDTO> departures) {
        this.departures = departures;
        return this;
    }
}
