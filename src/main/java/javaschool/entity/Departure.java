package javaschool.entity;


import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.joda.time.LocalDateTime;


@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"station_from", "station_to", "dateTimeFrom", "dateTimeTo"})})
public class Departure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Integer sitsCount;
    @Column(nullable = false)
    private Integer freeSitsCount;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets;
    @ManyToOne
    @JoinColumn(name = "station_from", nullable = false)
    private Station stationFrom;
    @ManyToOne
    @JoinColumn(name = "station_to", nullable = false)
    private Station stationTo;
    private LocalDateTime dateTimeFrom;
    private LocalDateTime dateTimeTo;

    public Integer getFreeSitsCount() {
        return freeSitsCount;
    }

    public void setFreeSitsCount(Integer freeSitsCount) {
        this.freeSitsCount = freeSitsCount;
    }

    public LocalDateTime getDateTimeFrom() {
        return dateTimeFrom;
    }

    public void setDateTimeFrom(LocalDateTime dateFrom) {
        this.dateTimeFrom = dateFrom;
    }

    public LocalDateTime getDateTimeTo() {
        return dateTimeTo;
    }

    public void setDateTimeTo(LocalDateTime dateTo) {
        this.dateTimeTo = dateTo;
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
        this.freeSitsCount = sitsCount;
    }

    @Override
    public String toString() {
        return "Departure{" +
                "id=" + id +
                ", sitsCount=" + sitsCount +
                ", freeSitsCount=" + freeSitsCount +
                ", stationFrom=" + stationFrom +
                ", stationTo=" + stationTo +
                ", dateFrom=" + dateTimeFrom +
                ", dateTo=" + dateTimeTo +
                '}';
    }
}
