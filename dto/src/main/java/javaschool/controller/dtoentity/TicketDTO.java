package javaschool.controller.dtoentity;

public class TicketDTO {
    private int id;
    private String stationFrom;
    private String stationTo;
    private long dateTimeFrom;
    private long dateTimeTo;
    private int coachNum;
    private int seatNum;

    public TicketDTO(int id, String stationFrom, String stationTo, long dateTimeFrom, long dateTimeTo, int coachNum, int seatNum) {
        this.stationFrom = stationFrom;
        this.stationTo = stationTo;
        this.dateTimeFrom = dateTimeFrom;
        this.dateTimeTo = dateTimeTo;
        this.coachNum = coachNum;
        this.seatNum = seatNum;
        this.id = id;
    }

    public TicketDTO() {
    }

    public int getId() {
        return id;
    }

    public TicketDTO setId(int id) {
        this.id = id;
        return this;
    }

    public String getStationFrom() {
        return stationFrom;
    }

    public TicketDTO setStationFrom(String stationFrom) {
        this.stationFrom = stationFrom;
        return this;
    }

    public String getStationTo() {
        return stationTo;
    }

    public TicketDTO setStationTo(String stationTo) {
        this.stationTo = stationTo;
        return this;
    }

    public long getDateTimeFrom() {
        return dateTimeFrom;
    }

    public TicketDTO setDateTimeFrom(long dateTimeFrom) {
        this.dateTimeFrom = dateTimeFrom;
        return this;
    }

    public long getDateTimeTo() {
        return dateTimeTo;
    }

    public TicketDTO setDateTimeTo(long dateTimeTo) {
        this.dateTimeTo = dateTimeTo;
        return this;
    }

    public int getCoachNum() {
        return coachNum;
    }

    public TicketDTO setCoachNum(int coachNum) {
        this.coachNum = coachNum;
        return this;
    }

    public int getSeatNum() {
        return seatNum;
    }

    public TicketDTO setSeatNum(int seatNum) {
        this.seatNum = seatNum;
        return this;
    }
}
