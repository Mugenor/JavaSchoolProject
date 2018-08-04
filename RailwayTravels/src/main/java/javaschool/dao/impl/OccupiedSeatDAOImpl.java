package javaschool.dao.impl;

import javaschool.dao.api.OccupiedSeatDAO;
import javaschool.entity.OccupiedSeat;
import javaschool.entity.Seat;
import org.springframework.stereotype.Repository;

@Repository
public class OccupiedSeatDAOImpl extends GenericAbstractDAO<OccupiedSeat, Seat> implements OccupiedSeatDAO {

}
