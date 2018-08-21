package javaschool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

/**
 * The type Almost user.
 */
@Entity
@Table(name = "almost_user",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "surname", "birthday"})})
public class AlmostUser {
    @Id
    @Column(nullable = false, unique = true)
    private String hash;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    private String name;
    private String surname;
    private LocalDate birthday;
    private LocalDateTime registered;

    /**
     * Instantiates a new Almost user.
     */
    public AlmostUser() {
    }

    /**
     * Instantiates a new Almost user.
     *
     * @param hash     the hash
     * @param password the password
     * @param username the username
     * @param email    the email
     * @param name     the name
     * @param surname  the surname
     * @param birthday the birthday
     */
    public AlmostUser(String hash, String password, String username, String email, String name, String surname, LocalDate birthday) {
        this.hash = hash;
        this.password = password;
        this.username = username;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.registered = registered;
    }

    /**
     * Gets registered.
     *
     * @return the registered
     */
    public LocalDateTime getRegistered() {
        return registered;
    }

    /**
     * Sets registered.
     *
     * @param registered the registered
     */
    public void setRegistered(LocalDateTime registered) {
        this.registered = registered;
    }

    /**
     * Gets hash.
     *
     * @return the hash
     */
    public String getHash() {
        return hash;
    }

    /**
     * Sets hash.
     *
     * @param hash the hash
     */
    public void setHash(String hash) {
        this.hash = hash;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
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
}
