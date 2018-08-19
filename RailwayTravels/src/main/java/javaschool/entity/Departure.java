package javaschool.entity;


import java.util.Objects;
import javaschool.service.exception.NoSiteOnDepartureException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.joda.time.LocalDateTime;


@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"station_from", "station_to", "dateTimeFrom", "dateTimeTo"})})
public class Departure {
    public final static Integer NUMBER_IN_TRIP_OFFSET = 1;
    public final static Integer DEFAULT_SEATS_COUNT_IN_COACH = 36;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Integer freeSitsCount;
    @Column(nullable = false)
    private Integer coachCount;
    @Column(nullable = false)
    private Integer seatInCoach;
    @ManyToOne
    @JoinColumn(name = "station_from", nullable = false)
    private Station stationFrom;
    @ManyToOne
    @JoinColumn(name = "station_to", nullable = false)
    private Station stationTo;
    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;
    @Column(nullable = false)
    private LocalDateTime dateTimeFrom;
    @Column(nullable = false)
    private LocalDateTime dateTimeTo;
    @Column(nullable = false)
    private Integer numberInTrip;

    public Trip getTrip() {
        return trip;
    }

    public Departure setTrip(Trip trip) {
        this.trip = trip;
        return this;
    }

    public Integer getNumberInTrip() {
        return numberInTrip;
    }

    public Departure setNumberInTrip(Integer numberInTrip) {
        this.numberInTrip = numberInTrip;
        return this;
    }

    public Integer getFreeSitsCount() {
        return freeSitsCount;
    }

    public Departure setFreeSitsCount(Integer freeSitsCount) {
        this.freeSitsCount = freeSitsCount;
        return this;
    }

    public LocalDateTime getDateTimeFrom() {
        return dateTimeFrom;
    }

    public Departure setDateTimeFrom(LocalDateTime dateFrom) {
        this.dateTimeFrom = dateFrom;
        return this;
    }

    public LocalDateTime getDateTimeTo() {
        return dateTimeTo;
    }

    public Departure setDateTimeTo(LocalDateTime dateTo) {
        this.dateTimeTo = dateTo;
        return this;
    }

    public Station getStationFrom() {
        return stationFrom;
    }

    public Departure setStationFrom(Station from) {
        this.stationFrom = from;
        return this;
    }

    public Station getStationTo() {
        return stationTo;
    }

    public Departure setStationTo(Station to) {
        this.stationTo = to;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCoachCount() {
        return coachCount;
    }

    public Departure setCoachCount(Integer coachCount) {
        this.coachCount = coachCount;
        return this;
    }

    public Integer getSeatInCoach() {
        return seatInCoach;
    }

    public Departure setSeatInCoach(Integer seatInCoach) {
        this.seatInCoach = seatInCoach;
        return this;
    }

    public void decrementFreeSeatsCount() {
        int newFreeSeatsCount = freeSitsCount - 1;
        if (newFreeSeatsCount < 0) {
            throw new NoSiteOnDepartureException();
        }
        freeSitsCount = newFreeSeatsCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Departure departure = (Departure) o;
        return Objects.equals(freeSitsCount, departure.freeSitsCount) &&
                Objects.equals(stationFrom, departure.stationFrom) &&
                Objects.equals(stationTo, departure.stationTo) &&
                Objects.equals(dateTimeFrom, departure.dateTimeFrom) &&
                Objects.equals(dateTimeTo, departure.dateTimeTo) &&
                Objects.equals(numberInTrip, departure.numberInTrip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coachCount, seatInCoach, freeSitsCount, stationFrom, stationTo, dateTimeFrom, dateTimeTo, numberInTrip);
    }

    @Override
    public String toString() {
        return "Departure{" +
                "id=" + id +
                ", freeSitsCount=" + freeSitsCount +
                ", stationFrom=" + stationFrom +
                ", stationTo=" + stationTo +
                ", dateTimeFrom=" + dateTimeFrom +
                ", dateTimeTo=" + dateTimeTo +
                ", numberInTrip=" + numberInTrip +
                '}';
    }
}
