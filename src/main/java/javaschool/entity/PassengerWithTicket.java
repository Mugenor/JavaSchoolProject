package javaschool.entity;

import java.io.Serializable;
import javaschool.entity.id.PassengerTicketId;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ticket_passenger")
public class PassengerWithTicket {
    @EmbeddedId
    private PassengerTicketId passengerTicketId;

    public PassengerWithTicket(PassengerTicketId passengerTicketId) {
        this.passengerTicketId = passengerTicketId;
    }

    public PassengerWithTicket() {
    }

    public PassengerTicketId getPassengerTicketId() {
        return passengerTicketId;
    }

    public void setPassengerTicketId(PassengerTicketId passengerTicketId) {
        this.passengerTicketId = passengerTicketId;
    }
}
