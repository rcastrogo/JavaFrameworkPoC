package app.rest.loggin;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import app.utils.Utils;

@Logged
@Provider
public class ResponseLoggingFilter implements ContainerResponseFilter {
  
  @Override
  public void filter(final ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
    final String invokedPath = requestContext.getUriInfo().getPath();
    Utils.getLogger()
         .log("Response to %1$s path has been made", invokedPath);
  }

  
}