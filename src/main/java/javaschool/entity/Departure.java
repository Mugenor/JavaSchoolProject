package javaschool.entity;


import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.util.List;


@Entity
public class Departure {
    public static final int DEFAULT_SITS_COUNT=10;

    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private Integer sitsCount;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets;
    @ManyToOne
    @JoinColumn(name = "station_from", nullable = false)
    private Station stationFrom;
    @ManyToOne
    @JoinColumn(name = "station_to", nullable = false)
    private Station stationTo;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;

    public LocalDateTime getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDateTime getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDateTime dateTo) {
        this.dateTo = dateTo;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Station getStationFrom() {
        return stationFrom;
    }

    public void setStationFrom(Station from) {
        this.stationFrom = from;
    }

    public Station getStationTo() {
        return stationTo;
    }

    public void setStationTo(Station to) {
        this.stationTo = to;
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

    @Override
    public String toString() {
        return "Departure{" +
                "id=" + id +
                ", sitsCount=" + sitsCount +
                ", stationFrom=" + stationFrom +
                ", stationTo=" + stationTo +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                '}';
    }
}
