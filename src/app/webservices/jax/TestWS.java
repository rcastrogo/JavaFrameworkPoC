package app.webservices.jax;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import app.dal.DbContext;
import app.model.entities.User;
import app.model.entities.Users;

@WebService( 
		name = "TestWS", 
    targetNamespace = "http://rcastrogo.es/tests/")
@SOAPBinding( 
		style = SOAPBinding.Style.DOCUMENT, 
	  use = SOAPBinding.Use.LITERAL, 
		parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public class TestWS {
  
  @WebMethod()
  public String test(@WebParam(name="name1") String name){
    return "Hola ::  " + name;
  }
  
  @WebMethod()
  public List<User> usuarios(){
  	try(DbContext dbContext = new DbContext()){
  		return new Users(dbContext).load();
  	}
  }

}