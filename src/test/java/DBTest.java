import javaschool.config.RootConfig;
import javaschool.entity.Passenger;
import javaschool.service.PassengerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfig.class)
public class DBTest {
    @Autowired
    private PassengerService service;

    @Test
    public void test() {
        Passenger passenger = new Passenger("Ilya", "Chernov");
        passenger.setBirthday(new Date());
        service.save(passenger);
        System.out.println(passenger);
        System.out.println(service.getById(passenger.getId()));
        System.out.println(service.getAllPassengers());
    }
}
