import javaschool.config.RootConfig;
import javaschool.entity.Departure;
import javaschool.entity.Passenger;
import javaschool.entity.Station;
import javaschool.service.api.DepartureService;
import javaschool.service.api.PassengerService;
import javaschool.service.api.StationService;
import javaschool.service.api.TicketService;
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
    @Autowired
    private PassengerService passengerService;
    @Autowired
    private DepartureService departureService;
    @Autowired
    private StationService stationService;
    @Autowired
    private TicketService ticketService;

    @Before
    public void prepareDB() {
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
        System.out.println(passenger);
        System.out.println(passengerService.getById(passenger.getId()));
        System.out.println(passengerService.getAllPassengers());
    }

    @Test
    public void test2() {
        System.out.println(departureService.findAll());
        System.out.println(departureService.findFromToBetween("Bol'shevikov", "Spasskaya",
                new LocalDateTime(2018, 1, 1, 6, 0, 0),
                new LocalDateTime(2018, 3, 1,6, 0, 0)));
    }
}
