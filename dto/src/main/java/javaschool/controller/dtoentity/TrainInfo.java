package javaschool.controller.dtoentity;

import java.util.List;

public class TrainInfo {
    private Integer coachCount;
    private List<OccupiedSeatDTO> occupiedSeats;

    public TrainInfo(Integer coachCount, List<OccupiedSeatDTO> occupiedSeats) {
        this.coachCount = coachCount;
        this.occupiedSeats = occupiedSeats;
    }

    public TrainInfo() {
    }

    public Integer getCoachCount() {
        return coachCount;
    }

    public TrainInfo setCoachCount(Integer coachCount) {
        this.coachCount = coachCount;
        return this;
    }

    public List<OccupiedSeatDTO> getOccupiedSeats() {
        return occupiedSeats;
    }

    public TrainInfo setOccupiedSeats(List<OccupiedSeatDTO> occupiedSeats) {
        this.occupiedSeats = occupiedSeats;
        return this;
    }
}
