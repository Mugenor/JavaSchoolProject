import org.apache.log4j.Logger;
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
        log.info(new LocalDateTime());
        DateTimeFormatter format = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm");
        log.info(new LocalDateTime().toString(format));
    }
}
