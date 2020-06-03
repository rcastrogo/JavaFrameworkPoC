package app.rest;

import javax.ws.rs.Consumes; 
import javax.ws.rs.GET; 
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces; 
import javax.ws.rs.core.MediaType; 
import javax.ws.rs.core.Response;

import app.model.entities.User;
import app.model.entities.Users;
import app.rest.loggin.Logged; 

@Logged
@Path("/users") 
@Consumes(MediaType.APPLICATION_JSON) 
public class UserRestService {               

  @GET() 
  @Produces(MediaType.APPLICATION_XML) 
  public Response getVersion() {     
      return Response.ok(new User(), MediaType.APPLICATION_XML)
      		           .build();   
  }
  
  @Path("/data/{id}")
  @GET() 
  @Produces(MediaType.APPLICATION_JSON)
  public Response getNumber(@PathParam("id") String id) {     
      return Response.ok("Hello World " + id, MediaType.APPLICATION_JSON)
      		           .build();   
  }
  
  @Path("/html")
  @Produces("text/html") 
  @GET() 
  public String getHtml() {
      return "<html lang=\"en\"><body><h1>Hello, World!!</body></h1></html>";
  }
  
  @Path("/all")
  @GET() 
  @Produces(MediaType.APPLICATION_JSON)
  public Users getUsers() {     
      return new Users().load();   
  }
  
  
}