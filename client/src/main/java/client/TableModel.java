package client;

import java.util.Locale;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TableModel {
    private static final DateTimeFormatter format = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm");
    private String nodeName;
    private String departureStation;
    private String arrivalStation;
    private String departureTime;
    private String arrivalTime;

    public TableModel(String nodeName, String departureStation, String arrivalStation, long departureTime, long arrivalTime) {
        this.nodeName = nodeName;
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.departureTime = new LocalDateTime(departureTime).toString(format);
        this.arrivalTime = new LocalDateTime(arrivalTime).toString(format);
    }

    public TableModel(String departureStation, String arrivalStation, long departureTime, long arrivalTime) {
        this(departureStation + " - " + arrivalStation, departureStation, arrivalStation, departureTime, arrivalTime);
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
