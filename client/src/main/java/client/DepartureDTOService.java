package client;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeSet;
import javaschool.controller.dtoentity.DepartureDTO;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

@Stateless
public class DepartureDTOService {
    private HashSet<DepartureDTO> departureDTOSet;
    private ResteasyClient client;

    public DepartureDTOService() {
        client = new ResteasyClientBuilder().build();
    }

    @PostConstruct
    public void init() {
        System.out.println("IN POST CONSTRUCT");
    }

    public Set<DepartureDTO> getDepartures() {
        if (departureDTOSet == null || departureDTOSet.size() == 0) {
            departureDTOSet = (HashSet<DepartureDTO>) client.target("http://localhost:8080/rwt").path("departure").
                    request(MediaType.APPLICATION_JSON).
                    get().readEntity(Set.class);
        }
        System.out.println(departureDTOSet);
        return departureDTOSet;
    }

}
