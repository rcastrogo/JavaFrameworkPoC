package app.rest.loggin;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import app.utils.Utils;

@Logged
@Provider
public class RequestLoggingFilter implements ContainerRequestFilter {
  
  @Override
  public void filter(final ContainerRequestContext requestContext) throws IOException {
    final String invokedPath = requestContext.getUriInfo().getPath();
    Utils.getLogger()
         .log("Request to %1$s path has been made", invokedPath);
  }
  
}