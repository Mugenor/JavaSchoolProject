package javaschool.controller.dtoentity;


public class DepartureDTO {
    private int sitsCount;
    private int freeSitsCount;
    private String stationFrom;
    private String stationTo;
    private String dateTimeFrom;
    private String dateTimeTo;

    public DepartureDTO(int sitsCount, int freeSitsCount, String stationFrom, String stationTo
            , String dateTimeFrom, String dateTimeTo) {
        this.sitsCount = sitsCount;
        this.freeSitsCount = freeSitsCount;
        this.stationFrom = stationFrom;
        this.stationTo = stationTo;
        this.dateTimeFrom = dateTimeFrom;
        this.dateTimeTo = dateTimeTo;
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

    public String getDateTimeFrom() {
        return dateTimeFrom;
    }

    public void setDateTimeFrom(String dateTimeFrom) {
        this.dateTimeFrom = dateTimeFrom;
    }

    public String getDateTimeTo() {
        return dateTimeTo;
    }

    public void setDateTimeTo(String dateTimeTo) {
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
