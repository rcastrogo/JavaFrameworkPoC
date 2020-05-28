package app.webservices.axis.client.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;

import org.apache.axis.client.Call;
import org.apache.axis.encoding.XMLType;
import org.apache.axis.message.SOAPHeaderElement;

import app.utils.Utils;


@WebServlet("/test-ws-security")
public class TestWSServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/plain");

		PrintWriter out = response.getWriter();
		out.println("Method : " + request.getMethod()); 
		
    try {
      Call call = createWsClientCall("http://localhost:8080/web001/services/MathWS", "rcastro", "rcastro");
  
  	  call.setOperationName(new QName("http://axis.webservices.app", "one"));
  	  call.setReturnType(XMLType.XSD_INT);
  	 
   	  Integer result = (Integer) call.invoke(new Object[]{}); 

  	  String req = Utils.formatXml(call.getMessageContext().getRequestMessage().getSOAPPartAsString());   
  	  String res = Utils.formatXml(call.getMessageContext().getResponseMessage().getSOAPPartAsString());
  	  
  	  out.println( "Axis client (org.apache.axis.client.Service):  " + result);
  	  out.println( "Request:\n" + req);
  	  out.println();
  	  out.println( "Response:\n" + res);
 			
		} catch (RemoteException | ServiceException | SOAPException e) {
			out.println(e.toString());
		}
		
		out.close();
	}
	
	private Call createWsClientCall(String endPoint, String wsUser, String wsPass) throws SOAPException, ServiceException {
    Call call = (org.apache.axis.client.Call) new org.apache.axis.client.Service().createCall();
		call.setTargetEndpointAddress(endPoint);
    QName headerName = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Security");
    SOAPHeaderElement header = new org.apache.axis.message.SOAPHeaderElement(headerName);
    header.setActor(null);
    header.setPrefix("wsse");
    header.setMustUnderstand(true);

    SOAPElement utElem = header.addChildElement("UsernameToken");
    SOAPElement userNameElem = utElem.addChildElement("Username");
    SOAPElement passwordElem = utElem.addChildElement("Password");
    userNameElem.removeContents();
    passwordElem.removeContents();
    userNameElem.setValue(wsUser);
    passwordElem.setValue(wsPass);
    call.addHeader(header);
    return call;
  }
  
}
