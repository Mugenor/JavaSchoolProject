package client;

import java.util.Locale;
import java.util.Objects;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TableModel {
    private static final DateTimeFormatter format = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm");
    private int id;
    private String nodeName;
    private String departureStation;
    private String arrivalStation;
    private String departureTime;
    private String arrivalTime;

    public TableModel(int id, String nodeName, String departureStation, String arrivalStation, long departureTime, long arrivalTime) {
        this.nodeName = nodeName;
        this.id = id;
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.departureTime = new LocalDateTime(departureTime).toString(format);
        this.arrivalTime = new LocalDateTime(arrivalTime).toString(format);
    }

    public TableModel(int id, String departureStation, String arrivalStation, long departureTime, long arrivalTime) {
        this(id, departureStation + " - " + arrivalStation, departureStation, arrivalStation, departureTime, arrivalTime);
    }

    public int getId() {
        return id;
    }

    public TableModel setId(int id) {
        this.id = id;
        return this;
    }

    public String getNodeName() {
        return nodeName;
    }

    public TableModel setNodeName(String nodeName) {
        this.nodeName = nodeName;
        return this;
    }

    public String getDepartureStation() {
        return departureStation;
    }

    public TableModel setDepartureStation(String departureStation) {
        this.departureStation = departureStation;
        return this;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public TableModel setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
        return this;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public TableModel setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
        return this;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public TableModel setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableModel that = (TableModel) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "TableModel{" +
                "nodeName='" + nodeName + '\'' +
                ", departureStation='" + departureStation + '\'' +
                ", arrivalStation='" + arrivalStation + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                '}';
    }
}
