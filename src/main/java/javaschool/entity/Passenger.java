package javaschool.entity;


import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "surname", "birthday"})})
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date birthday;
    @OneToMany(mappedBy = "passenger", fetch = FetchType.LAZY)
    private List<Ticket> tickets;


    public Passenger() {}

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
        return id + " " + name + " " + surname + " " + birthday;
    }
}
