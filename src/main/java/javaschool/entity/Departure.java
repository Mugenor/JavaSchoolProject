package javaschool.entity;


import java.util.Objects;
import java.util.Set;
import javaschool.service.exception.NoSiteOnDepartureException;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.joda.time.LocalDateTime;


@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"station_from", "station_to", "dateTimeFrom", "dateTimeTo"})})
public class Departure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Integer sitsCount;
    @Column(nullable = false)
    private Integer freeSitsCount;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,
            orphanRemoval = true, mappedBy = "departure")
    @OrderBy("coachNumber")
    private Set<Coach> coaches;
    @ManyToOne
    @JoinColumn(name = "station_from", nullable = false)
    private Station stationFrom;
    @ManyToOne
    @JoinColumn(name = "station_to", nullable = false)
    private Station stationTo;
    @Column(nullable = false)
    private LocalDateTime dateTimeFrom;
    @Column(nullable = false)
    private LocalDateTime dateTimeTo;

    public Integer getFreeSitsCount() {
        return freeSitsCount;
    }

    public void setFreeSitsCount(Integer freeSitsCount) {
        this.freeSitsCount = freeSitsCount;
    }

    public LocalDateTime getDateTimeFrom() {
        return dateTimeFrom;
    }

    public void setDateTimeFrom(LocalDateTime dateFrom) {
        this.dateTimeFrom = dateFrom;
    }

    public LocalDateTime getDateTimeTo() {
        return dateTimeTo;
    }

    public void setDateTimeTo(LocalDateTime dateTo) {
        this.dateTimeTo = dateTo;
    }

    public Set<Coach> getCoaches() {
        return coaches;
    }

    public void setCoaches(Set<Coach> coaches) {
        this.coaches = coaches;
    }

    public Station getStationFrom() {
        return stationFrom;
    }

    public void setStationFrom(Station from) {
        this.stationFrom = from;
    }

    public Station getStationTo() {
        return stationTo;
    }

    public void setStationTo(Station to) {
        this.stationTo = to;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSitsCount() {
        return sitsCount;
    }

    public void setSitsCount(Integer sitsCount) {
        this.sitsCount = sitsCount;
        this.freeSitsCount = sitsCount;
    }

    public void decrementFreeSeatsCount() {
        int newFreeSeatsCount = freeSitsCount - 1;
        if(newFreeSeatsCount < 0) {
            throw new NoSiteOnDepartureException();
        }
        freeSitsCount = newFreeSeatsCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Departure departure = (Departure) o;
        return Objects.equals(sitsCount, departure.sitsCount) &&
                Objects.equals(freeSitsCount, departure.freeSitsCount) &&
                Objects.equals(stationFrom, departure.stationFrom) &&
                Objects.equals(stationTo, departure.stationTo) &&
                Objects.equals(dateTimeFrom, departure.dateTimeFrom) &&
                Objects.equals(dateTimeTo, departure.dateTimeTo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(sitsCount, freeSitsCount, stationFrom, stationTo, dateTimeFrom, dateTimeTo);
    }

    @Override
    public String toString() {
        return "Departure{" +
                "id=" + id +
                ", sitsCount=" + sitsCount +
                ", freeSitsCount=" + freeSitsCount +
                ", stationFrom=" + stationFrom +
                ", stationTo=" + stationTo +
                ", dateFrom=" + dateTimeFrom +
                ", dateTo=" + dateTimeTo +
                '}';
    }
}
