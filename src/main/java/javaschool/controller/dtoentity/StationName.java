package javaschool.controller.dtoentity;

import java.util.Objects;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StationName that = (StationName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "StationName{" +
                "name='" + name + '\'' +
                '}';
    }
}
