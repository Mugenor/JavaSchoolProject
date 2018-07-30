package javaschool.controller.dtoentity;

public class PassengerWithoutTickets {
    private String name;
    private String surname;
    private long birthday;

    public PassengerWithoutTickets() {
    }

    public PassengerWithoutTickets(String name, String surname, long birthday) {
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
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

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }
}
