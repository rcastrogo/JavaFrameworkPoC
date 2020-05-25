package app.webservices.axis.client;

public class TestWSProxy implements app.webservices.axis.client.ITestWS {
	
  private String _endpoint = null;
  private app.webservices.axis.client.ITestWS testWS = null;
  
  public TestWSProxy() {
    _initTestWSProxy();
  }
  
  public TestWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initTestWSProxy();
  }
  
  private void _initTestWSProxy() {
    try {
			testWS = (new app.webservices.axis.client.TestWSServiceLocator()).getTestWS();
      if (testWS != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)testWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)testWS)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (testWS != null)
      ((javax.xml.rpc.Stub)testWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public app.webservices.axis.client.ITestWS getTestWS() {
    if (testWS == null) _initTestWSProxy();
    return testWS;
  }
  
  public java.lang.String version() throws java.rmi.RemoteException{
    if (testWS == null) _initTestWSProxy();
    return testWS.version();
  }
  
  public java.lang.String save() throws java.rmi.RemoteException{
    if (testWS == null) _initTestWSProxy();
    return testWS.save();
  }
  
  
}