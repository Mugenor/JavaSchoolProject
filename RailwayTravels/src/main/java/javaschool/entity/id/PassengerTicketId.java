package javaschool.entity.id;

import java.io.Serializable;
import java.util.Objects;
import javaschool.entity.Passenger;
import javaschool.entity.Ticket;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Embeddable
public class PassengerTicketId implements Serializable {
    @OneToOne
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;
    @OneToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    public PassengerTicketId(Passenger passenger, Ticket ticket) {
        this.passenger = passenger;
        this.ticket = ticket;
    }

    public PassengerTicketId() {
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PassengerTicketId that = (PassengerTicketId) o;
        return passenger.getId().equals(that.passenger.getId()) &&
                ticket.getId().equals(that.ticket.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(passenger.getId(), ticket.getId());
    }
}
