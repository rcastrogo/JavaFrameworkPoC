package app.webservices.axis;

import org.apache.axis.AxisFault;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.handlers.BasicHandler;

public class DebugHandler extends BasicHandler {

	private static final long serialVersionUID = 1L;
	private static final String logHeader = "==================== Begin %1$s ======================\n";
	private static final String logFooter = "====================== End %1$s ======================\n";
			
	@Override
	public void invoke(MessageContext msgContext) throws AxisFault {
		Message msg = null; 
		String mode = "";
		if (msgContext.getResponseMessage() != null) {
			msg = msgContext.getResponseMessage();
			mode = "Response";
    }
    if (msg == null) {
			msg = msgContext.getRequestMessage();
			mode = "Request";
    }
    if (msg != null) {
			System.out.print(String.format(logHeader, mode));
			System.out.println(msg.getSOAPPartAsString());
			System.out.print(String.format(logFooter, mode));
    }	
	}

}
