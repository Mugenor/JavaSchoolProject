import java.util.Date;
import java.util.UUID;
import org.apache.log4j.Logger;
import org.joda.time.Instant;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

public class TMPTest {
    private static final Logger log = Logger.getLogger(TMPTest.class);
    @Test
    public void tst() {
        DateTimeFormatter format = DateTimeFormat.forPattern("dd.mm.yyyy");
        log.info(LocalDate.parse("01.01.2015", format));
    }

    @Test
    public void tst1() {
        log.info(new Date());
        log.info(new LocalDateTime());
        DateTimeFormatter format = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm");
    }

    @Test
    public void tst2() {
        log.info(System.nanoTime());
        log.info(System.currentTimeMillis());
        log.info(new LocalDateTime(Instant.parse(new LocalDateTime().toString()).getMillis()));
        log.info(UUID.randomUUID());
    }
}
