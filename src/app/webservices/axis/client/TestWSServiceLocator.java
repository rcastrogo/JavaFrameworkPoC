
package app.webservices.axis.client;

public class TestWSServiceLocator 
  extends org.apache.axis.client.Service 
  implements app.webservices.axis.client.TestWSService {

	private static final long serialVersionUID = 1L;
	private java.lang.String TestWS_address = "http://localhost:8080/web001/services/TestWS";	
	

	public TestWSServiceLocator() {	}

	public TestWSServiceLocator(org.apache.axis.EngineConfiguration config) {
		super(config);
	}

	public TestWSServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
		super(wsdlLoc, sName);
	}

	public java.lang.String getTestWSAddress() {
		return TestWS_address;
	}

	// The WSDD service name defaults to the port name.
	private java.lang.String TestWSWSDDServiceName = "TestWS";

	public java.lang.String getTestWSWSDDServiceName() {
		return TestWSWSDDServiceName;
	}

	public void setTestWSWSDDServiceName(java.lang.String name) {
		TestWSWSDDServiceName = name;
	}

	public app.webservices.axis.client.ITestWS getTestWS() throws javax.xml.rpc.ServiceException {
		java.net.URL endpoint;
		try {
			endpoint = new java.net.URL(TestWS_address);
		} catch (java.net.MalformedURLException e) {
			throw new javax.xml.rpc.ServiceException(e);
		}
		return getTestWS(endpoint);
	}

	public app.webservices.axis.client.ITestWS getTestWS(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
		try {
			app.webservices.axis.client.TestWSSoapBindingStub _stub = new app.webservices.axis.client.TestWSSoapBindingStub(portAddress,
					this);
			_stub.setPortName(getTestWSWSDDServiceName());
			return _stub;
		} catch (org.apache.axis.AxisFault e) {
			return null;
		}
	}

	public void setTestWSEndpointAddress(java.lang.String address) {
		TestWS_address = address;
	}

	/**
	 * For the given interface, get the stub implementation. If this service has no
	 * port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
		try {
			if (app.webservices.axis.TestWS.class.isAssignableFrom(serviceEndpointInterface)) {
				app.webservices.axis.client.TestWSSoapBindingStub _stub = new app.webservices.axis.client.TestWSSoapBindingStub(
						new java.net.URL(TestWS_address), this);
				_stub.setPortName(getTestWSWSDDServiceName());
				return _stub;
			}
		} catch (java.lang.Throwable t) {
			throw new javax.xml.rpc.ServiceException(t);
		}
		throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  "
				+ (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
	}

	/**
	 * For the given interface, get the stub implementation. If this service has no
	 * port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
		if (portName == null) {
			return getPort(serviceEndpointInterface);
		}
		java.lang.String inputPortName = portName.getLocalPart();
		if ("TestWS".equals(inputPortName)) {
			return getTestWS();
		} else {
			java.rmi.Remote _stub = getPort(serviceEndpointInterface);
			((org.apache.axis.client.Stub) _stub).setPortName(portName);
			return _stub;
		}
	}

	public javax.xml.namespace.QName getServiceName() {
		return new javax.xml.namespace.QName("http://axis.webservices.app", "TestWSService");
	}

	private java.util.HashSet ports = null;

	public java.util.Iterator getPorts() {
		if (ports == null) {
			ports = new java.util.HashSet();
			ports.add(new javax.xml.namespace.QName("http://axis.webservices.app", "TestWS"));
		}
		return ports.iterator();
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(java.lang.String portName, java.lang.String address)
			throws javax.xml.rpc.ServiceException {

		if ("TestWS".equals(portName)) {
			setTestWSEndpointAddress(address);
		} else { // Unknown Port Name
			throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
		}
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address)
			throws javax.xml.rpc.ServiceException {
		setEndpointAddress(portName.getLocalPart(), address);
	}

}
