package javaschool.service.impl;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import javaschool.controller.dtoentity.DepartureDTO;
import javaschool.controller.dtoentity.TripDTO;
import javaschool.dao.api.TripDAO;
import javaschool.service.api.SearchTripsWithTransfers;
import javaschool.service.converter.TripToTripDTOConverter;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SearchTripsWithTransfersImpl implements SearchTripsWithTransfers {
    private TripDAO tripDAO;
    private TripToTripDTOConverter tripToTripDTOConverter;

    @Autowired
    public SearchTripsWithTransfersImpl(TripDAO tripDAO,
                                        TripToTripDTOConverter tripToTripDTOConverter) {
        this.tripDAO = tripDAO;
        this.tripToTripDTOConverter = tripToTripDTOConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public List<List<TripDTO>> findFromToBetweenWithTransfers(String stFrom, String stTo,
                                                              LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo,
                                                              int maxTransfersCount) {
        LinkedList<List<TripDTO>> resultTrips = new LinkedList<>();
        LinkedList<TripDTO> tripPath = new LinkedList<>();
        HashSet<Integer> tripsVisited = new HashSet<>();
        long dateTimeFromAsLong = dateTimeFrom.toDateTime().getMillis();
        long dateTimeToAsLong = dateTimeTo.toDateTime().getMillis();
        step(resultTrips, tripPath, tripsVisited, stFrom, stTo,
                dateTimeFromAsLong, dateTimeToAsLong,
                0, maxTransfersCount);
        return resultTrips;
    }

    private void step(LinkedList<List<TripDTO>> resultTrips, LinkedList<TripDTO> tripPath,
                      HashSet<Integer> tripsVisited,
                      String currentStation, String endStation, long dateTimeFrom, long dateTimeTo,
                      int currentTransfersCount, int maxTransfersCount) {
        List<TripDTO> tripsWithCurrentStation = tripDAO.findByStationTitleFromDateTimeFromBetween(currentStation,
                new LocalDateTime(dateTimeFrom), new LocalDateTime(dateTimeTo))
                .stream().map(trip -> tripToTripDTOConverter.convertTo(trip)).collect(Collectors.toList());
        for (TripDTO tripDTO : tripsWithCurrentStation) {
            if (!tripsVisited.contains(tripDTO.getId())) {
                List<DepartureDTO> departures = tripDTO.getDepartures();
                LinkedList<DepartureDTO> departurePath = new LinkedList<>();
                TripDTO partOfPath = new TripDTO(tripDTO.getId(), departurePath, tripDTO.getCoachCount());
                tripsVisited.add(tripDTO.getId());
                tripPath.addLast(partOfPath);
                boolean foundStartStation = false;
                boolean pathFound = false;
                for (DepartureDTO departureDTO : departures) {
                    if (departureDTO.getDateTimeTo() > dateTimeTo) {
                        break;
                    }
                    if (!foundStartStation && departureDTO.getStationFrom().equals(currentStation)) {
                        foundStartStation = true;
                    }
                    if (foundStartStation) {
                        departurePath.addLast(departureDTO);
                    }
                    if (foundStartStation && departureDTO.getStationTo().equals(endStation)) {
                        pathFound = true;
                        break;
                    }
                    if (foundStartStation && currentTransfersCount < maxTransfersCount) {
                        step(resultTrips, tripPath, tripsVisited, departureDTO.getStationTo(), endStation,
                                departureDTO.getDateTimeFrom(), dateTimeTo,
                                currentTransfersCount + 1, maxTransfersCount);
                    }
                }
                if (pathFound) {
                    resultTrips.add(deepCloneLinkedList(tripPath));
                }
                tripPath.removeLast();
                tripsVisited.remove(tripDTO.getId());
            }
        }
    }

    private LinkedList<TripDTO> deepCloneLinkedList(LinkedList<TripDTO> linkedList) {
        LinkedList<TripDTO> clone = new LinkedList<>();
        for (TripDTO v : linkedList) {
            clone.addLast(new TripDTO(v));
        }
        return clone;
    }
}
