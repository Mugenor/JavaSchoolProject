package javaschool.entity;

import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * The type Ticket.
 */
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
    @Column(nullable = false, unique = true)
    private UUID uuid;

    /**
     * Gets uuid.
     *
     * @return the uuid
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Sets uuid.
     *
     * @param uuid the uuid
     * @return the uuid
     */
    public Ticket setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    /**
     * Gets from.
     *
     * @return the from
     */
    public Departure getFrom() {
        return from;
    }

    /**
     * Sets from.
     *
     * @param from the from
     * @return the from
     */
    public Ticket setFrom(Departure from) {
        this.from = from;
        return this;
    }

    /**
     * Gets to.
     *
     * @return the to
     */
    public Departure getTo() {
        return to;
    }

    /**
     * Sets to.
     *
     * @param to the to
     * @return the to
     */
    public Ticket setTo(Departure to) {
        this.to = to;
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
     * @return the id
     */
    public Ticket setId(Integer id) {
        this.id = id;
        return this;
    }

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
    public Ticket setTrip(Trip trip) {
        this.trip = trip;
        return this;
    }

    /**
     * Gets passenger.
     *
     * @return the passenger
     */
    public Passenger getPassenger() {
        return passenger;
    }

    /**
     * Sets passenger.
     *
     * @param passenger the passenger
     * @return the passenger
     */
    public Ticket setPassenger(Passenger passenger) {
        this.passenger = passenger;
        return this;
    }

    /**
     * Gets occupied seats.
     *
     * @return the occupied seats
     */
    public List<OccupiedSeat> getOccupiedSeats() {
        return occupiedSeats;
    }

    /**
     * Sets occupied seats.
     *
     * @param occupiedSeats the occupied seats
     * @return the occupied seats
     */
    public Ticket setOccupiedSeats(List<OccupiedSeat> occupiedSeats) {
        this.occupiedSeats = occupiedSeats;
        return this;
    }
}
