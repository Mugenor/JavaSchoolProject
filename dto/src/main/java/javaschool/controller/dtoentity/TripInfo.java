package javaschool.controller.dtoentity;

public class TripInfo {
    private String stationFrom;
    private String stationTo;
    private Long dateTimeFrom;
    private Long dateTimeTo;
    private Integer coachCount;

    public TripInfo(String stationFrom, String stationTo, Long dateTimeFrom, Long dateTimeTo, Integer coachCount) {
        this.stationFrom = stationFrom;
        this.stationTo = stationTo;
        this.dateTimeFrom = dateTimeFrom;
        this.dateTimeTo = dateTimeTo;
        this.coachCount = coachCount;
    }

    public TripInfo() {
    }

    public String getStationFrom() {
        return stationFrom;
    }

    public TripInfo setStationFrom(String stationFrom) {
        this.stationFrom = stationFrom;
        return this;
    }

    public String getStationTo() {
        return stationTo;
    }

    public TripInfo setStationTo(String stationTo) {
        this.stationTo = stationTo;
        return this;
    }

    public Long getDateTimeFrom() {
        return dateTimeFrom;
    }

    public TripInfo setDateTimeFrom(Long dateTimeFrom) {
        this.dateTimeFrom = dateTimeFrom;
        return this;
    }

    public Long getDateTimeTo() {
        return dateTimeTo;
    }

    public TripInfo setDateTimeTo(Long dateTimeTo) {
        this.dateTimeTo = dateTimeTo;
        return this;
    }

    public Integer getCoachCount() {
        return coachCount;
    }

    public TripInfo setCoachCount(Integer coachCount) {
        this.coachCount = coachCount;
        return this;
    }
}
