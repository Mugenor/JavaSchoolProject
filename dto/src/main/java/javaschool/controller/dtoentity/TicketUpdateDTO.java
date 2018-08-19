package javaschool.controller.dtoentity;

public class TicketUpdateDTO {
    public static final String BUY = "buy";
    public static final String RETURN = "return";
    private String status;
    private int departureFromIndex;
    private int departureToIndex;
    private int coachNumber;
    private int seatNumber;

    public TicketUpdateDTO(String status, int departureFromIndex, int departureToIndex, int coachNumber, int seatNumber) {
        this.status = status;
        this.departureFromIndex = departureFromIndex;
        this.departureToIndex = departureToIndex;
        this.coachNumber = coachNumber;
        this.seatNumber = seatNumber;
    }

    public int getCoachNumber() {
        return coachNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public String getStatus() {
        return status;
    }

    public int getDepartureFromIndex() {
        return departureFromIndex;
    }

    public int getDepartureToIndex() {
        return departureToIndex;
    }

    @Override
    public String toString() {
        return "TicketUpdateDTO{" +
                "status='" + status + '\'' +
                ", departureFromIndex=" + departureFromIndex +
                ", departureToIndex=" + departureToIndex +
                ", coachNumber=" + coachNumber +
                ", seatNumber=" + seatNumber +
                '}';
    }

}
