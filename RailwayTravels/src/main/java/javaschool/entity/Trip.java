package javaschool.entity;

import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

/**
 * The type Trip.
 */
@Entity
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "trip", fetch = FetchType.EAGER)
    @OrderBy("numberInTrip")
    private List<Departure> departures;

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     * @return the id
     */
    public Trip setId(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * Gets departures.
     *
     * @return the departures
     */
    public List<Departure> getDepartures() {
        return departures;
    }

    /**
     * Sets departures.
     *
     * @param departures the departures
     * @return the departures
     */
    public Trip setDepartures(List<Departure> departures) {
        this.departures = departures;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return Objects.equals(id, trip.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
