package javaschool.entity;

import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * The type Station.
 */
@Entity
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String title;
    @OneToMany(mappedBy = "stationFrom", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Departure> departures;

    /**
     * Instantiates a new Station.
     */
    public Station() {}

    /**
     * Instantiates a new Station.
     *
     * @param title the title
     */
    public Station(String title) {
        this.title = title;
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
     */
    public void setDepartures(List<Departure> departures) {
        this.departures = departures;
    }

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
    public Station setId(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return Objects.equals(title, station.title);
    }

    @Override
    public int hashCode() {

        return Objects.hash(title);
    }

    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
