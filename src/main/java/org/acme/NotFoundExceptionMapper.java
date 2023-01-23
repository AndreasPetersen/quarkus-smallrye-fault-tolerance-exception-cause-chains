package org.acme;

import java.net.HttpURLConnection;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

public class NotFoundExceptionMapper implements ResponseExceptionMapper<NotFoundException> {
    @Override
    public NotFoundException toThrowable(Response response) {
        return new NotFoundException();
    }

    @Override
    public boolean handles(int status, MultivaluedMap headers) {
        return HttpURLConnection.HTTP_NOT_FOUND == status;
    }
}
