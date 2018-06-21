import javaschool.config.Config;
import javaschool.entity.Passenger;
import javaschool.service.PassengerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class DBTest {
    @Autowired
    private PassengerService service;

    @Test
    public void test() {
        Passenger passenger = new Passenger("Ilya", "Chernov");
        service.save(passenger);
        System.out.println(service.getAllPassengers());
    }
}
