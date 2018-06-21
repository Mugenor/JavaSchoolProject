package java.school.dao;

import java.school.entity.Passanger;
import java.util.List;

public interface PassangerDAO {
    void save(Passanger passanger);
    List<Passanger> list();
}
