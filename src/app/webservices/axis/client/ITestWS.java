package app.webservices.axis.client;

public interface ITestWS extends java.rmi.Remote {
  public java.lang.String version() throws java.rmi.RemoteException;
  public java.lang.String save() throws java.rmi.RemoteException;
}
