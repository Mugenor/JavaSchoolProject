package javaschool.controller.dtoentity;

import org.hibernate.validator.constraints.Length;

public class StationName {
    @Length(min=1, max = 40)
    private String name;

    public StationName(){}

    public StationName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "StationName{" +
                "name='" + name + '\'' +
                '}';
    }
}
