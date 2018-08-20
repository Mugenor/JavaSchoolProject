package javaschool.controller.dtoentity;

import javaschool.controller.validator.annotation.IsEmail;
import javaschool.controller.validator.annotation.LocalDateConstraint;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.joda.time.LocalDate;

public class NewUser {
    @NotNull
    @Length(min = 2, max = 15)
    private String name;
    @NotNull
    @Length(min = 2, max = 15)
    private String surname;
    @NotNull
    @Length(min = 2, max = 15)
    private String username;
    @NotNull
    @Length(min = 5, max = 20)
    private String password;
    @NotNull
    @IsEmail
    private String email;
    @NotNull
    @LocalDateConstraint(minDate = "01.01.1950", maxDate = "01.01.2018")
    private LocalDate birthday;

    public NewUser() {
    }

    public NewUser(String name, String surname, String username, String password, String email, LocalDate birthday) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.birthday = birthday;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "NewUser{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", birthday='" + birthday + '\'' +
                '}';
    }
}
