package javaschool.dao.api;

import java.util.List;
import javaschool.entity.Trip;
import org.joda.time.LocalDateTime;

/**
 * The interface Trip dao.
 */
public interface TripDAO extends GenericDAO<Trip, Integer> {
    /**
     * Find all after list.
     *
     * @param dateTime the date time
     * @return the list
     */
    List<Trip> findAllAfter(LocalDateTime dateTime);

    /**
     * Find date time from between between or date time to between list.
     *
     * @param begin the begin
     * @param end   the end
     * @return the list
     */
    List<Trip> findDateTimeFromBetweenBetweenOrDateTimeToBetween(LocalDateTime begin, LocalDateTime end);

    /**
     * Find by station from title and date time from after list.
     *
     * @param stationFromTitle the station from title
     * @param dateTimeFrom     the date time from
     * @return the list
     */
    List<Trip> findByStationFromTitleAndDateTimeFromAfter(String stationFromTitle, LocalDateTime dateTimeFrom);

    /**
     * Find by station title to date time to between list.
     *
     * @param stationTitleTo     the station title to
     * @param dateTimeLeftBound  the date time left bound
     * @param dateTimeRightBound the date time right bound
     * @return the list
     */
    List<Trip> findByStationTitleToDateTimeToBetween(String stationTitleTo,
                                                         LocalDateTime dateTimeLeftBound, LocalDateTime dateTimeRightBound);

    /**
     * Find by station title from date time from between list.
     *
     * @param stationTitleFrom   the station title from
     * @param dateTimeLeftBound  the date time left bound
     * @param dateTimeRightBound the date time right bound
     * @return the list
     */
    List<Trip> findByStationTitleFromDateTimeFromBetween(String stationTitleFrom,
                                                     LocalDateTime dateTimeLeftBound, LocalDateTime dateTimeRightBound);
}
