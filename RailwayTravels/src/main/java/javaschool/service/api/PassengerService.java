package javaschool.service.api;

import java.util.List;
import javaschool.controller.dtoentity.PassengerWithoutTickets;
import javaschool.controller.dtoentity.TicketDTO;
import javaschool.entity.Passenger;

public interface PassengerService {
    /**
     * Finds all passengers and convert them to PassengerWithoutTickets.class
     *
     * @return List with all passengers converted to PassengerWithoutTickets.class
     */
    List<PassengerWithoutTickets> findAllPassengers();

    /**
     * Saves new passenger
     *
     * @param passenger Passenger to be saved
     */
    void save(Passenger passenger);

    /**
     * Finds passenger with specified id
     *
     * @param id Passenger's id
     * @return Passenger with specified id
     */
    Passenger findById(Integer id);

    /**
     * Make user buy ticket for specified departure and seat. User this method instead of buyTicketTransactional method.
     *
     * @param username    User's username
     * @param tripId      Trip's id
     * @param coachNumber Number of coach with specified seat
     * @param seatNumber  Number of seat in specified coach
     */
    void buyTicket(String username, Integer tripId, Integer leftDepartureIndex,
                   Integer rightDepartureIndex, Integer coachNumber, Integer seatNumber);

    /**
     * Finds all passengers registered on specified departure
     *
     * @param departureId Departure's id
     * @return List of passengers registered on specified departure and converted to PassengerWithoutTickets.class
     */
    List<PassengerWithoutTickets> findAllPassengersByDepartureId(Integer departureId);

    List<PassengerWithoutTickets> findAllPassengersByTripId(Integer tripId);

    /**
     * Finds passengers registered on trip with specified id and if they
     * registered on at least one departure with numberInTrip index between <b>from</b> and <b>to</b>
     *
     * @param tripId trip's id
     * @param from   departure's numberInTrip index to search from
     * @param to     departure's numberInTrip index to search to
     * @return List of passengers converted to PassengerWithoutTickets.class
     */
    List<PassengerWithoutTickets> findAllPassengersByTripIdAndDepartureIndexBounds(Integer tripId, Integer from, Integer to);

    /**
     * Finds all tickets of passenger with specified <b>username</b>
     *
     * @param username passenger's username
     * @return List of tickets converted to TicketDTO.class
     */
    List<TicketDTO> getPassengerTickets(String username);

    /**
     * Deletes ticket if it's passenger's ticket with specified username
     *
     * @param username passenger's username
     * @param ticketId ticket's id
     */
    void returnTicket(String username, Integer ticketId);

    /**
     * Finds out if passenger with specified username is registered on trip with specified id
     *
     * @param tripId   trip's id
     * @param username passenger's username
     * @return  true if passenger is registered on trip. Otherwise - false
     */
    Boolean isRegistered(Integer tripId, String username);
}
