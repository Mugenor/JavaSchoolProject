package javaschool.entity;

import java.io.Serializable;
import javax.persistence.Column;
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
}
