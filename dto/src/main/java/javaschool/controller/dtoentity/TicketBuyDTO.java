package javaschool.controller.dtoentity;

import javax.validation.constraints.NotNull;

public class TicketBuyDTO {
    @NotNull
    private Integer tripId;
    @NotNull
    private Integer departureFromIndex;
    @NotNull
    private Integer departureToIndex;
    @NotNull
    private Integer coachNumber;
    @NotNull
    private Integer seatNum;

    public TicketBuyDTO(@NotNull Integer tripId, @NotNull Integer departureFromIndex, @NotNull Integer departureToIndex, @NotNull Integer coachNumber, @NotNull Integer seatNum) {
        this.tripId = tripId;
        this.departureFromIndex = departureFromIndex;
        this.departureToIndex = departureToIndex;
        this.coachNumber = coachNumber;
        this.seatNum = seatNum;
    }

    public TicketBuyDTO() {
    }

    public Integer getTripId() {
        return tripId;
    }

    public TicketBuyDTO setTripId(Integer tripId) {
        this.tripId = tripId;
        return this;
    }

    public Integer getDepartureFromIndex() {
        return departureFromIndex;
    }

    public TicketBuyDTO setDepartureFromIndex(Integer departureFromIndex) {
        this.departureFromIndex = departureFromIndex;
        return this;
    }

    public Integer getDepartureToIndex() {
        return departureToIndex;
    }

    public TicketBuyDTO setDepartureToIndex(Integer departureToIndex) {
        this.departureToIndex = departureToIndex;
        return this;
    }

    public Integer getCoachNumber() {
        return coachNumber;
    }

    public TicketBuyDTO setCoachNumber(Integer coachNumber) {
        this.coachNumber = coachNumber;
        return this;
    }

    public Integer getSeatNum() {
        return seatNum;
    }

    public TicketBuyDTO setSeatNum(Integer seatNum) {
        this.seatNum = seatNum;
        return this;
    }
}
