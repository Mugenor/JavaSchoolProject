package javaschool.controller.dtoentity;

import java.util.List;
import java.util.Objects;

public class CoachDTO {
    private Integer coachNumber;
    private List<SeatDTO> seatDTOS;

    public CoachDTO(Integer coachNumber, List<SeatDTO> seatDTOS) {
        this.coachNumber = coachNumber;
        this.seatDTOS = seatDTOS;
    }

    public CoachDTO() {
    }

    public Integer getCoachNumber() {
        return coachNumber;
    }

    public CoachDTO setCoachNumber(Integer coachNumber) {
        this.coachNumber = coachNumber;
        return this;
    }

    public List<SeatDTO> getSeatDTOS() {
        return seatDTOS;
    }

    public CoachDTO setSeatDTOS(List<SeatDTO> seatDTOS) {
        this.seatDTOS = seatDTOS;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoachDTO coachDTO = (CoachDTO) o;
        return Objects.equals(coachNumber, coachDTO.coachNumber);
    }

    @Override
    public int hashCode() {

        return Objects.hash(coachNumber);
    }
}
