import javaschool.dao.api.StationDAO;
import javaschool.entity.Station;
import javaschool.service.api.StationService;
import javaschool.service.exception.EntityAlreadyExistsException;
import javaschool.service.exception.InvalidStationTitleException;
import javaschool.service.impl.StationServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.Silent.class)
public class StationServiceTest {
    private static final String USED_TITLE = "t1";
    @Mock
    private StationDAO stationDAO;
    private StationService stationService;

    @Before
    public void init() {
        stationService = new StationServiceImpl(stationDAO);
        ((StationServiceImpl) stationService).setSelfProxy(stationService);
        doThrow(new EntityAlreadyExistsException()).when(stationDAO).save(new Station(USED_TITLE));
    }

    @Test
    public void saveStationWithUnusedTitle() {
        Station station = new Station(USED_TITLE + "a");
        stationService.save(station);
        verify(stationDAO).save(station);
    }

    @Test
    public void saveStationByStationTitleWithUnusedTitle() {
        stationService.save(USED_TITLE + "a");
        verify(stationDAO).save(new Station(USED_TITLE + "a"));
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void saveStationWithUsedTitle() {
        Station station = new Station(USED_TITLE );
        stationService.save(station);
        verify(stationDAO).save(station);
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void saveStationByStationTitleWithUsedTitle() {
        stationService.save(USED_TITLE);
        verify(stationDAO).save(new Station(USED_TITLE));
    }

    @Test(expected = InvalidStationTitleException.class)
    public void saveStationWithNullStationTitle() {
        stationService.save(new Station());
    }
}
