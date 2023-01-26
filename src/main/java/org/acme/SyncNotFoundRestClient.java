package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
@RegisterProvider(NotFoundExceptionMapper.class)
@Retry(abortOn = NotFoundException.class)
public interface SyncNotFoundRestClient {
    @GET
    @Path("/notFound")
    String notFound();
}
