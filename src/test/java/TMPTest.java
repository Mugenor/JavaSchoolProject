import org.apache.log4j.Logger;
import org.joda.time.LocalDate;
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
}
