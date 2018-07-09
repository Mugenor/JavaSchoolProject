package javaschool.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Coach {
    public static final int DEFAULT_SEATS_NUM = 35;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "departure_id")
    private Departure departure;
    @Column(name = "coach_number", nullable = false)
    private int coachNumber;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "coach")
    private List<Ticket> tickets;

    public Coach() {
    }

    public int getCoachNumber() {
        return coachNumber;
    }

    public void setCoachNumber(int coachNumber) {
        this.coachNumber = coachNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Departure getDeparture() {
        return departure;
    }

    public void setDeparture(Departure departure) {
        this.departure = departure;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
