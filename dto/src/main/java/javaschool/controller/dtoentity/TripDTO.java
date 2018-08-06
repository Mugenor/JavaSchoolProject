package javaschool.controller.dtoentity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class TripDTO implements Serializable {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TripDTO tripDTO = (TripDTO) o;
        return Objects.equals(id, tripDTO.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TripDTO{" +
                "id=" + id +
                ", departures=" + departures +
                '}';
    }
}
