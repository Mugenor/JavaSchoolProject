package javaschool.entity;

import java.io.Serializable;
import java.util.Objects;
import javaschool.entity.id.SeatId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * The type Occupied seat.
 */
@Entity
@Table(name = "occupied_seat",
        uniqueConstraints = @UniqueConstraint(columnNames = {"seatNumber", "coachNumber", "departure_id"}))
public class OccupiedSeat implements Serializable {
    @Id
    private SeatId seat;
    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    /**
     * Gets seat.
     *
     * @return the seat
     */
    public SeatId getSeat() {
        return seat;
    }

    /**
     * Sets seat.
     *
     * @param seat the seat
     * @return the seat
     */
    public OccupiedSeat setSeat(SeatId seat) {
        this.seat = seat;
        return this;
    }

    /**
     * Gets ticket.
     *
     * @return the ticket
     */
    public Ticket getTicket() {
        return ticket;
    }

    /**
     * Sets ticket.
     *
     * @param ticket the ticket
     * @return the ticket
     */
    public OccupiedSeat setTicket(Ticket ticket) {
        this.ticket = ticket;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OccupiedSeat that = (OccupiedSeat) o;
        return Objects.equals(seat, that.seat);
    }

    @Override
    public int hashCode() {

        return Objects.hash(seat);
    }
}
