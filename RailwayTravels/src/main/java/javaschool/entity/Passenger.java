package javaschool.entity;


import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.Hibernate;
import org.joda.time.LocalDate;

/**
 * The type Passenger.
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "surname", "birthday"})})
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = false)
    private LocalDate birthday;
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "passenger")
    private User user;


    /**
     * Instantiates a new Passenger.
     */
    public Passenger() {
    }

    /**
     * Instantiates a new Passenger.
     *
     * @param name    the name
     * @param surname the surname
     */
    public Passenger(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    /**
     * Instantiates a new Passenger.
     *
     * @param name     the name
     * @param surname  the surname
     * @param birthday the birthday
     */
    public Passenger(String name, String surname, LocalDate birthday) {
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
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
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets surname.
     *
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets surname.
     *
     * @param surname the surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets birthday.
     *
     * @return the birthday
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * Sets birthday.
     *
     * @param birthday the birthday
     */
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthday=" + birthday +
                ", user=" + (Hibernate.isInitialized(user) ? user : "NOT INIT") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return Objects.equals(name, passenger.name) &&
                Objects.equals(surname, passenger.surname) &&
                Objects.equals(birthday, passenger.birthday);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, surname, birthday);
    }
}
