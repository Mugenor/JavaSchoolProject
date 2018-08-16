package javaschool.controller.dtoentity;

import java.io.Serializable;

public class TripUpdate implements Serializable {
    public static final int CREATE = 1;
    public static final int UPDATE = 2;
    public static final int DELETE = 3;
    private int action;
    private TripDTO trip;

    public TripUpdate(int action, TripDTO trip) {
        this.action = action;
        this.trip = trip;
    }

    public int getAction() {
        return action;
    }

    public TripUpdate setAction(int action) {
        this.action = action;
        return this;
    }

    public TripDTO getTrip() {
        return trip;
    }

    public TripUpdate setTrip(TripDTO trip) {
        this.trip = trip;
        return this;
    }
}
