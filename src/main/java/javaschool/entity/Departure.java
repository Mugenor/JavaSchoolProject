package javaschool.entity;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
public class Departure {
    private static final int DEFAULT_SITS_COUNT=10;

    @Id
    @GeneratedValue
    private Integer id;
    @NotNull
    private Integer sitsCount;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets;
    @ManyToOne
    @JoinColumn(name = "station_from", nullable = false)
    private Station from;
    @ManyToOne
    @JoinColumn(name = "station_to", nullable = false)
    private Station to;

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Station getFrom() {
        return from;
    }

    public void setFrom(Station from) {
        this.from = from;
    }

    public Station getTo() {
        return to;
    }

    public void setTo(Station to) {
        this.to = to;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSitsCount() {
        return sitsCount;
    }

    public void setSitsCount(Integer sitsCount) {
        this.sitsCount = sitsCount;
    }
}
