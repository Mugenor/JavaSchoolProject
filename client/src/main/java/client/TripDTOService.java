package client;

import java.util.HashSet;
import java.util.Set;
import javaschool.controller.dtoentity.TripDTO;
import javax.ejb.Stateless;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

@Stateless
public class TripDTOService {
    private ResteasyClient client;

    public TripDTOService() {
        client = new ResteasyClientBuilder().build();
    }


    public Set<TripDTO> getTrips() {
        return client.target("http://localhost:8080/rwt").path("trip/today").
                request(MediaType.APPLICATION_JSON).
                get().readEntity(new GenericType<Set<TripDTO>>(){});
    }

}
