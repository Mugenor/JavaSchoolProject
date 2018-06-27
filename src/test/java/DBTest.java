import javaschool.config.RootConfig;
import javaschool.entity.Passenger;
import javaschool.entity.Station;
import javaschool.service.api.DepartureService;
import javaschool.service.api.PassengerService;
import javaschool.service.api.StationService;
import javaschool.service.api.TicketService;
import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfig.class)
public class DBTest {
    private static final Logger log = Logger.getLogger(DBTest.class);
    @Autowired
    private PassengerService passengerService;
    @Autowired
    private DepartureService departureService;
    @Autowired
    private StationService stationService;
    @Autowired
    private TicketService ticketService;

    private static boolean isBefore = false;

    @Before
    public void prepareDB() {
        log.info("isBefore: " + isBefore);
        if(isBefore) {
            return;
        }
        isBefore = true;
        log.info("IN BEFORE, isBefore: " + isBefore);
        Station bolshevikov = new Station();
        bolshevikov.setTitle("Bol'shevikov");
        Station dostoevskaya = new Station();
        dostoevskaya.setTitle("Dostoevskaya");
        Station spasskaya = new Station();
        spasskaya.setTitle("Spasskaya");
        stationService.save(bolshevikov);
        stationService.save(dostoevskaya);
        stationService.save(spasskaya);
        departureService.save(5, bolshevikov.getTitle(), spasskaya.getTitle(),
                new LocalDateTime(2018, 1, 1, 12, 0, 0),
                new LocalDateTime(2018, 2, 1, 12, 0, 0));
    }

    @Test
    public void test1() {
        Passenger passenger = new Passenger("Ilya", "Chernov");
        passenger.setBirthday(LocalDate.now());
        passengerService.save(passenger);
        log.info(passenger);
        log.info(passengerService.getById(passenger.getId()));
        log.info(passengerService.getAllPassengers());
    }

    @Test
    public void test2() {
        log.info("All stations: " + stationService.findAll());
        log.info(departureService.findAll());
        log.info(departureService.findFromToBetween("Bol'shevikov", "Spasskaya",
                new LocalDateTime(2018, 1, 1, 6, 0, 0),
                new LocalDateTime(2018, 1, 1,9, 0, 0)));
    }
}
