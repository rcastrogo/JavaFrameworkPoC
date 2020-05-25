package app.webservices.jax;

import java.rmi.RemoteException;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.namespace.QName;
import javax.xml.rpc.Call;
import javax.xml.rpc.ServiceException;

import app.dal.DbContext;
import app.model.entities.User;
import app.model.entities.Users;

import app.webservices.axis.client.ITestWS;
import app.webservices.axis.client.TestWSProxy;


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
  
  @WebMethod()
  public String callStub(){
    try {
    	ITestWS service = new TestWSProxy().getTestWS();  	
			return "Axis client (org.apache.axis.client.Stub):  " + service.version();
		} catch (RemoteException e) {
			return e.toString();
		}
  }

  @WebMethod()
  public String call(){
    try {
      Call call = new org.apache
      		               .axis.client
      		               .Service()
      		               .createCall();
      call.setTargetEndpointAddress("http://localhost:8080/web001/services/TestWS");
  	  call.setOperationName(new QName("http://axis.webservices.app", "version"));
      return "Axis client (org.apache.axis.client.Service):  " + (String) call.invoke(new Object[]{});
			
		} catch (RemoteException | ServiceException e) {
			return e.toString();
		}
  }
  
}