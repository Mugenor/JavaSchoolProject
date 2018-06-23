import javaschool.entity.Passenger;
import javaschool.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = Config.class)
public class DBTest {
    @Autowired
    private PassengerService service;

//    @Test
    public void test() {
        Passenger passenger = new Passenger("Ilya", "Chernov");
        service.save(passenger);
        System.out.println(service.getAllPassengers());
    }
}
