package javaschool.service.api;

import java.util.List;
import javaschool.controller.dtoentity.TripDTO;
import org.joda.time.LocalDateTime;

public interface SearchTripsWithTransfers {
    List<List<TripDTO>> findFromToBetweenWithTransfers(String stFrom, String stTo,
                                                       LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo, int maxTransfersCount);
}
