package javaschool.controller.dtoentity;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class TripDTO implements Serializable {
    private Integer id;
    private int coachCount;
    private List<DepartureDTO> departures;

    public TripDTO() {
    }

    public TripDTO(TripDTO tripDTO) {
        this.id = tripDTO.id;
        this.coachCount = tripDTO.coachCount;
        this.departures = new LinkedList<>();
        for (DepartureDTO departureDTO: tripDTO.departures) {
            this.departures.add(new DepartureDTO(departureDTO));
        }
    }

    public TripDTO(Integer id, List<DepartureDTO> departures, Integer coachCount) {
        this.id = id;
        this.departures = departures;
        this.coachCount = coachCount;
    }

    public int getCoachCount() {
        return coachCount;
    }

    public TripDTO setCoachCount(int coachCount) {
        this.coachCount = coachCount;
        return this;
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
