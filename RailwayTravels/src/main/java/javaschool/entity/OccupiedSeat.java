package javaschool.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "occupied_seat")
public class OccupiedSeat implements Serializable {
    @Id
    @OneToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;
    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    public Seat getSeat() {
        return seat;
    }

    public OccupiedSeat setSeat(Seat seat) {
        this.seat = seat;
        return this;
    }

    public Ticket getTicket() {
        return ticket;
    }

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
