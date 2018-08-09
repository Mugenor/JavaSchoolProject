package javaschool.controller.dtoentity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import org.joda.time.LocalDateTime;

public class DepartureDTO implements Serializable {
    private Integer id;
    private int sitsCount;
    private int freeSitsCount;
    private String stationFrom;
    private String stationTo;
    private long dateTimeFrom;
    private long dateTimeTo;
    private int numberInTrip;

    public DepartureDTO() {
    }

    public DepartureDTO(Integer id, int sitsCount, int freeSitsCount, String stationFrom, String stationTo
            , long dateTimeFrom, long dateTimeTo, int numberInTrip) {
        this.sitsCount = sitsCount;
        this.freeSitsCount = freeSitsCount;
        this.stationFrom = stationFrom;
        this.stationTo = stationTo;
        this.dateTimeFrom = dateTimeFrom;
        this.dateTimeTo = dateTimeTo;
        this.id = id;
        this.numberInTrip = numberInTrip;
    }

    public int getNumberInTrip() {
        return numberInTrip;
    }

    public DepartureDTO setNumberInTrip(int numberInTrip) {
        this.numberInTrip = numberInTrip;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getSitsCount() {
        return sitsCount;
    }

    public void setSitsCount(int sitsCount) {
        this.sitsCount = sitsCount;
    }

    public int getFreeSitsCount() {
        return freeSitsCount;
    }

    public void setFreeSitsCount(int freeSitsCount) {
        this.freeSitsCount = freeSitsCount;
    }

    public String getStationFrom() {
        return stationFrom;
    }

    public void setStationFrom(String stationFrom) {
        this.stationFrom = stationFrom;
    }

    public String getStationTo() {
        return stationTo;
    }

    public void setStationTo(String stationTo) {
        this.stationTo = stationTo;
    }

    public long getDateTimeFrom() {
        return dateTimeFrom;
    }

    @JsonIgnore
    public Date getDateTimeFromAsDate() {
        return new Date(dateTimeFrom);
    }

    public void setDateTimeFrom(long dateTimeFrom) {
        this.dateTimeFrom = dateTimeFrom;
    }

    public long getDateTimeTo() {
        return dateTimeTo;
    }

    @JsonIgnore
    public Date getDateTimeToAsDate() {
        return new Date(dateTimeTo);
    }

    public void setDateTimeTo(long dateTimeTo) {
        this.dateTimeTo = dateTimeTo;
    }

    @JsonIgnore
    public boolean isTooLate() {
        return LocalDateTime.now().plusMinutes(10).compareTo(new LocalDateTime(dateTimeFrom)) > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartureDTO that = (DepartureDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DepartureDTO{" +
                "id=" + id +
                "sitsCount=" + sitsCount +
                ", freeSitsCount=" + freeSitsCount +
                ", stationFrom='" + stationFrom + '\'' +
                ", stationTo='" + stationTo + '\'' +
                ", dateTimeFrom=" + dateTimeFrom +
                ", dateTimeTo=" + dateTimeTo +
                '}';
    }

}
