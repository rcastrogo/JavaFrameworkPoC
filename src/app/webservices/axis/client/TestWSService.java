
package app.webservices.axis.client;

public interface TestWSService extends javax.xml.rpc.Service {
	
    public java.lang.String getTestWSAddress();

    public app.webservices.axis.client.ITestWS getTestWS() throws javax.xml.rpc.ServiceException;

    public app.webservices.axis.client.ITestWS getTestWS(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    
}
