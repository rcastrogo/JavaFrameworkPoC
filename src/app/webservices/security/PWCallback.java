package app.webservices.security;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.ws.security.WSPasswordCallback;

public class PWCallback implements CallbackHandler {
	
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {

		for (Callback callback : callbacks) {	
      if (callback instanceof WSPasswordCallback) {
        WSPasswordCallback pc = (WSPasswordCallback)callback; 
        String username = pc.getIdentifier();
        String password = pc.getPassword();
        if(!(username.equals("rcastro") && 
        		 password.equals("rcastro"))) {
        	throw new UnsupportedCallbackException(callback, "Credenciales de acceso incorrectas");	
        }
      } else {
        throw new UnsupportedCallbackException(callback, "Unrecognized Callback");
      }
    }   
	}
	
}