package javaschool.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"siteNum", "departure_id"})})
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;
    @ManyToOne
    @JoinColumn(name = "departure_id", nullable = false)
    private Departure departure;
    @Column(nullable = false)
    private Integer siteNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Departure getDeparture() {
        return departure;
    }

    public void setDeparture(Departure departure) {
        this.departure = departure;
    }

    public Integer getSiteNum() {
        return siteNum;
    }

    public void setSiteNum(Integer siteNum) {
        this.siteNum = siteNum;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", departure=" + departure +
                ", siteNum=" + siteNum +
                '}';
    }
}
