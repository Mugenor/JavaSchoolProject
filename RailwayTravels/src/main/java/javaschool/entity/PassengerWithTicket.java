package javaschool.entity;

import javaschool.entity.id.PassengerTicketId;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "ticket_passenger_departure",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"ticket_id", "passenger_id"}),
                @UniqueConstraint(columnNames = {"passenger_id", "departure_id"})})
public class PassengerWithTicket {
    @EmbeddedId
    private PassengerTicketId passengerTicketId;
    @OneToOne
    @JoinColumn(name = "departure_id", nullable = false)
    private Departure departure;


    public PassengerWithTicket(PassengerTicketId passengerTicketId, Departure departure) {
        this.passengerTicketId = passengerTicketId;
        this.departure = departure;
    }

    public PassengerWithTicket() {
    }

    public Departure getDeparture() {
        return departure;
    }

    public void setDeparture(Departure departure) {
        this.departure = departure;
    }

    public PassengerTicketId getPassengerTicketId() {
        return passengerTicketId;
    }

    public void setPassengerTicketId(PassengerTicketId passengerTicketId) {
        this.passengerTicketId = passengerTicketId;
    }
}
