package javaschool.entity;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String surname;
    @Temporal(TemporalType.DATE)
    private Date birthday;
    @OneToMany(mappedBy = "passenger", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Ticket> tickets;


    public Passenger() {}

    public Passenger(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public String toString() {
        return name + " " + surname;
    }
}
