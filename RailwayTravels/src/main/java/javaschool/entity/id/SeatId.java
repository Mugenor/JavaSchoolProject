package javaschool.entity.id;

import java.io.Serializable;
import java.util.Objects;
import javaschool.entity.Departure;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class SeatId implements Serializable {
    private Integer seatNumber;
    private Integer coachNumber;
    @ManyToOne
    @JoinColumn(name = "departure_id")
    private Departure departure;

    public SeatId() {
    }

    public SeatId(Integer seatNumber, Integer coachNumber, Departure departure) {
        this.seatNumber = seatNumber;
        this.coachNumber = coachNumber;
        this.departure = departure;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public SeatId setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
        return this;
    }

    public Integer getCoachNumber() {
        return coachNumber;
    }

    public SeatId setCoachNumber(Integer coachNumber) {
        this.coachNumber = coachNumber;
        return this;
    }

    public Departure getDeparture() {
        return departure;
    }

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
