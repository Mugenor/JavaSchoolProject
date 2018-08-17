package javaschool.controller.dtoentity;

import javax.validation.constraints.NotNull;

public class UpdateDepartureInfo {
    @NotNull
    private Integer tripId;
    @NotNull
    private Integer departureIndex;
    @NotNull
    private NewDepartureDTO departure;

    public Integer getTripId() {
        return tripId;
    }

    public UpdateDepartureInfo setTripId(Integer tripId) {
        this.tripId = tripId;
        return this;
    }

    public Integer getDepartureIndex() {
        return departureIndex;
    }

    public UpdateDepartureInfo setDepartureIndex(Integer departureIndex) {
        this.departureIndex = departureIndex;
        return this;
    }

    public NewDepartureDTO getDeparture() {
        return departure;
    }

    public UpdateDepartureInfo setDeparture(NewDepartureDTO departure) {
        this.departure = departure;
        return this;
    }
}
