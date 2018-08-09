package javaschool.controller.dtoentity;

import java.util.Objects;

public class SeatDTO {
    private Integer seatNumber;

    public SeatDTO(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public SeatDTO() {
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public SeatDTO setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeatDTO seatDTO = (SeatDTO) o;
        return Objects.equals(seatNumber, seatDTO.seatNumber);
    }

    @Override
    public int hashCode() {

        return Objects.hash(seatNumber);
    }
}
