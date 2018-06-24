package javaschool.entity;



import org.hibernate.Hibernate;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "surname", "birthday"})})
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
//    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate birthday;
    @OneToMany(mappedBy = "passenger", fetch = FetchType.LAZY)
    private List<Ticket> tickets;


    public Passenger() {
    }

    public Passenger(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthday=" + birthday +
                ", tickets=" + (Hibernate.isInitialized(tickets) ? tickets : "NOT INIT") +
                '}';
    }
}
