package javaschool.controller.dtoentity;


public class DepartureDTO {
    private Integer id;
    private int sitsCount;
    private int freeSitsCount;
    private String stationFrom;
    private String stationTo;
    private long dateTimeFrom;
    private long dateTimeTo;

    public DepartureDTO(Integer id, int sitsCount, int freeSitsCount, String stationFrom, String stationTo
            , long dateTimeFrom, long dateTimeTo) {
        this.sitsCount = sitsCount;
        this.freeSitsCount = freeSitsCount;
        this.stationFrom = stationFrom;
        this.stationTo = stationTo;
        this.dateTimeFrom = dateTimeFrom;
        this.dateTimeTo = dateTimeTo;
        this.id = id;
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

    public void setDateTimeFrom(long dateTimeFrom) {
        this.dateTimeFrom = dateTimeFrom;
    }

    public long getDateTimeTo() {
        return dateTimeTo;
    }

    public void setDateTimeTo(long dateTimeTo) {
        this.dateTimeTo = dateTimeTo;
    }

    @Override
    public String toString() {
        return "DepartureDTO{" +
                "sitsCount=" + sitsCount +
                ", freeSitsCount=" + freeSitsCount +
                ", stationFrom='" + stationFrom + '\'' +
                ", stationTo='" + stationTo + '\'' +
                ", dateTimeFrom=" + dateTimeFrom +
                ", dateTimeTo=" + dateTimeTo +
                '}';
    }

}
