package javaschool.controller.dtoentity;

import javax.validation.constraints.NotNull;

public class TicketBuyDTO {
    @NotNull
    private Integer seatNum;
    @NotNull
    private Integer departureId;
    @NotNull
    private Integer coachNumber;

    public TicketBuyDTO() {
    }

    public TicketBuyDTO(@NotNull Integer seatNum, @NotNull Integer departureId) {
        this.seatNum = seatNum;
        this.departureId = departureId;
    }

    public Integer getCoachNumber() {
        return coachNumber;
    }

    public void setCoachNumber(Integer coachNumber) {
        this.coachNumber = coachNumber;
    }

    public Integer getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(Integer seatNum) {
        this.seatNum = seatNum;
    }

    public Integer getDepartureId() {
        return departureId;
    }

    public void setDepartureId(Integer departureId) {
        this.departureId = departureId;
    }
}
