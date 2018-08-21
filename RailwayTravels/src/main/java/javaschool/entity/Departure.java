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


/**
 * The type Departure.
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"station_from", "station_to", "dateTimeFrom", "dateTimeTo"})})
public class Departure {
    /**
     * The constant NUMBER_IN_TRIP_OFFSET.
     */
    public static final Integer NUMBER_IN_TRIP_OFFSET = 1;
    /**
     * The constant DEFAULT_SEATS_COUNT_IN_COACH.
     */
    public static final Integer DEFAULT_SEATS_COUNT_IN_COACH = 36;
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

    /**
     * Gets trip.
     *
     * @return the trip
     */
    public Trip getTrip() {
        return trip;
    }

    /**
     * Sets trip.
     *
     * @param trip the trip
     * @return the trip
     */
    public Departure setTrip(Trip trip) {
        this.trip = trip;
        return this;
    }

    /**
     * Gets number in trip.
     *
     * @return the number in trip
     */
    public Integer getNumberInTrip() {
        return numberInTrip;
    }

    /**
     * Sets number in trip.
     *
     * @param numberInTrip the number in trip
     * @return the number in trip
     */
    public Departure setNumberInTrip(Integer numberInTrip) {
        this.numberInTrip = numberInTrip;
        return this;
    }

    /**
     * Gets free sits count.
     *
     * @return the free sits count
     */
    public Integer getFreeSitsCount() {
        return freeSitsCount;
    }

    /**
     * Sets free sits count.
     *
     * @param freeSitsCount the free sits count
     * @return the free sits count
     */
    public Departure setFreeSitsCount(Integer freeSitsCount) {
        this.freeSitsCount = freeSitsCount;
        return this;
    }

    /**
     * Gets date time from.
     *
     * @return the date time from
     */
    public LocalDateTime getDateTimeFrom() {
        return dateTimeFrom;
    }

    /**
     * Sets date time from.
     *
     * @param dateFrom the date from
     * @return the date time from
     */
    public Departure setDateTimeFrom(LocalDateTime dateFrom) {
        this.dateTimeFrom = dateFrom;
        return this;
    }

    /**
     * Gets date time to.
     *
     * @return the date time to
     */
    public LocalDateTime getDateTimeTo() {
        return dateTimeTo;
    }

    /**
     * Sets date time to.
     *
     * @param dateTo the date to
     * @return the date time to
     */
    public Departure setDateTimeTo(LocalDateTime dateTo) {
        this.dateTimeTo = dateTo;
        return this;
    }

    /**
     * Gets station from.
     *
     * @return the station from
     */
    public Station getStationFrom() {
        return stationFrom;
    }

    /**
     * Sets station from.
     *
     * @param from the from
     * @return the station from
     */
    public Departure setStationFrom(Station from) {
        this.stationFrom = from;
        return this;
    }

    /**
     * Gets station to.
     *
     * @return the station to
     */
    public Station getStationTo() {
        return stationTo;
    }

    /**
     * Sets station to.
     *
     * @param to the to
     * @return the station to
     */
    public Departure setStationTo(Station to) {
        this.stationTo = to;
        return this;
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
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets coach count.
     *
     * @return the coach count
     */
    public Integer getCoachCount() {
        return coachCount;
    }

    /**
     * Sets coach count.
     *
     * @param coachCount the coach count
     * @return the coach count
     */
    public Departure setCoachCount(Integer coachCount) {
        this.coachCount = coachCount;
        return this;
    }

    /**
     * Gets seat in coach.
     *
     * @return the seat in coach
     */
    public Integer getSeatInCoach() {
        return seatInCoach;
    }

    /**
     * Sets seat in coach.
     *
     * @param seatInCoach the seat in coach
     * @return the seat in coach
     */
    public Departure setSeatInCoach(Integer seatInCoach) {
        this.seatInCoach = seatInCoach;
        return this;
    }

    /**
     * Decrement free seats count.
     */
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
