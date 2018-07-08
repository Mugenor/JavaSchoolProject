package javaschool.controller.dtoentity;

public class StationName {
    private String name;

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
