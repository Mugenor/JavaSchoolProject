package javaschool.service.api;

import javaschool.entity.AlmostUser;

public interface AlmostUserService {
    void save(AlmostUser almostUser);
    AlmostUser findByHash(String hash);
    int deleteByHash(String hash);
    void removeOldEntries();
}
