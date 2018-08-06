package javaschool.controller.dtoentity;

import javaschool.controller.validator.LocalDateTimeValidator;
import javaschool.controller.validator.annotation.LocalDateConstraint;
import javaschool.controller.validator.annotation.LocalDateTimeConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.joda.time.LocalDateTime;

public class NewDepartureDTO {
    @NotNull
    private String stationFrom;
    @NotNull
    private String stationTo;
    @NotNull
    @LocalDateTimeConstraint(minDateTime = LocalDateTimeValidator.NOW)
    private LocalDateTime dateTimeFrom;
    @NotNull
    @LocalDateTimeConstraint(minDateTime = LocalDateTimeValidator.NOW)
    private LocalDateTime dateTimeTo;
    @Min(1)
    @Max(10)
    private int coachCount;

    public NewDepartureDTO() {
    }

    public NewDepartureDTO(String stationFrom, String stationTo, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo, int coachCount) {
        this.stationFrom = stationFrom;
        this.stationTo = stationTo;
        this.dateTimeFrom = dateTimeFrom;
        this.dateTimeTo = dateTimeTo;
        this.coachCount = coachCount;
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

    public int getCoachCount() {
        return coachCount;
    }

    public void setCoachCount(int coachCount) {
        this.coachCount = coachCount;
    }

    public LocalDateTime getDateTimeFrom() {
        return dateTimeFrom;
    }

    public void setDateTimeFrom(LocalDateTime dateTimeFrom) {
        this.dateTimeFrom = dateTimeFrom;
    }

    public LocalDateTime getDateTimeTo() {
        return dateTimeTo;
    }

    public void setDateTimeTo(LocalDateTime dateTimeTo) {
        this.dateTimeTo = dateTimeTo;
    }
}
