package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/")
public class Resource {
    @RestClient
    AsyncNotFoundRestClient asyncNotFoundRestClient;

    @RestClient
    SyncNotFoundRestClient syncNotFoundRestClient;

    @Path("/async")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String async() {
        return asyncNotFoundRestClient.notFound().await().indefinitely();
    }

    @Path("sync")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sync() {
        return syncNotFoundRestClient.notFound();
    }
}