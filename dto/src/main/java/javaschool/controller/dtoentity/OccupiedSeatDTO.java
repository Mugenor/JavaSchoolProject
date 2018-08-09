package javaschool.controller.dtoentity;

import java.util.Objects;

public class OccupiedSeatDTO {
    private Integer coachNumber;
    private Integer seatNumber;

    public OccupiedSeatDTO(Integer coachNumber, Integer seatNumber) {
        this.coachNumber = coachNumber;
        this.seatNumber = seatNumber;
    }

    public OccupiedSeatDTO() {
    }

    public Integer getCoachNumber() {
        return coachNumber;
    }

    public OccupiedSeatDTO setCoachNumber(Integer coachNumber) {
        this.coachNumber = coachNumber;
        return this;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public OccupiedSeatDTO setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OccupiedSeatDTO that = (OccupiedSeatDTO) o;
        return Objects.equals(coachNumber, that.coachNumber) &&
                Objects.equals(seatNumber, that.seatNumber);
    }

    @Override
    public int hashCode() {

        return Objects.hash(coachNumber, seatNumber);
    }
}
