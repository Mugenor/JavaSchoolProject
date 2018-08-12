package javaschool.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"trip_id","passenger_id"})
})
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;
    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticket")
    private List<OccupiedSeat> occupiedSeats;
    @ManyToOne
    @JoinColumn(name = "departure_from")
    private Departure from;
    @ManyToOne
    @JoinColumn(name = "departure_to")
    private Departure to;

    public Departure getFrom() {
        return from;
    }

    public Ticket setFrom(Departure from) {
        this.from = from;
        return this;
    }

    public Departure getTo() {
        return to;
    }

    public Ticket setTo(Departure to) {
        this.to = to;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public Ticket setId(Integer id) {
        this.id = id;
        return this;
    }

    public Trip getTrip() {
        return trip;
    }

    public Ticket setTrip(Trip trip) {
        this.trip = trip;
        return this;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public Ticket setPassenger(Passenger passenger) {
        this.passenger = passenger;
        return this;
    }

    public List<OccupiedSeat> getOccupiedSeats() {
        return occupiedSeats;
    }

    public Ticket setOccupiedSeats(List<OccupiedSeat> occupiedSeats) {
        this.occupiedSeats = occupiedSeats;
        return this;
    }
}
