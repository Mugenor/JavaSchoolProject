package javaschool.entity.id;

import java.io.Serializable;
import java.util.Objects;
import javaschool.entity.Departure;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * The type Seat id.
 */
@Embeddable
public class SeatId implements Serializable {
    private Integer seatNumber;
    private Integer coachNumber;
    @ManyToOne
    @JoinColumn(name = "departure_id")
    private Departure departure;

    /**
     * Instantiates a new Seat id.
     */
    public SeatId() {
    }

    /**
     * Instantiates a new Seat id.
     *
     * @param seatNumber  the seat number
     * @param coachNumber the coach number
     * @param departure   the departure
     */
    public SeatId(Integer seatNumber, Integer coachNumber, Departure departure) {
        this.seatNumber = seatNumber;
        this.coachNumber = coachNumber;
        this.departure = departure;
    }

    /**
     * Gets seat number.
     *
     * @return the seat number
     */
    public Integer getSeatNumber() {
        return seatNumber;
    }

    /**
     * Sets seat number.
     *
     * @param seatNumber the seat number
     * @return the seat number
     */
    public SeatId setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
        return this;
    }

    /**
     * Gets coach number.
     *
     * @return the coach number
     */
    public Integer getCoachNumber() {
        return coachNumber;
    }

    /**
     * Sets coach number.
     *
     * @param coachNumber the coach number
     * @return the coach number
     */
    public SeatId setCoachNumber(Integer coachNumber) {
        this.coachNumber = coachNumber;
        return this;
    }

    /**
     * Gets departure.
     *
     * @return the departure
     */
    public Departure getDeparture() {
        return departure;
    }

    /**
     * Sets departure.
     *
     * @param departure the departure
     * @return the departure
     */
    public SeatId setDeparture(Departure departure) {
        this.departure = departure;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeatId seatId = (SeatId) o;
        return Objects.equals(seatNumber, seatId.seatNumber) &&
                Objects.equals(coachNumber, seatId.coachNumber) &&
                Objects.equals(departure, seatId.departure);
    }

    @Override
    public int hashCode() {

        return Objects.hash(seatNumber, coachNumber, departure);
    }
}
