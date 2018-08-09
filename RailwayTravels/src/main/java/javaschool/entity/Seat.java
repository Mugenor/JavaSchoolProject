package javaschool.entity;


import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"siteNum", "coach_id"})})
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne(mappedBy = "seat", cascade = CascadeType.ALL)
    private OccupiedSeat occupiedSeat;
    @ManyToOne
    @JoinColumn(name = "coach_id", nullable = false)
    private Coach coach;
    @Column(nullable = false)
    private Integer siteNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OccupiedSeat getOccupiedSeat() {
        return occupiedSeat;
    }

    public Seat setOccupiedSeat(OccupiedSeat occupiedSeat) {
        this.occupiedSeat = occupiedSeat;
        return this;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public Integer getSiteNum() {
        return siteNum;
    }

    public void setSiteNum(Integer siteNum) {
        this.siteNum = siteNum;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", siteNum=" + siteNum +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return (this.siteNum.equals(seat.siteNum) && this.coach.equals(seat.coach));
    }

    @Override
    public int hashCode() {

        return Objects.hash(siteNum, this.coach);
    }
}
