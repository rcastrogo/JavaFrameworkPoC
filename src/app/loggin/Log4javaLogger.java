package app.loggin;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.*;

public class Log4javaLogger implements ILogger {

	private final static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	private final static Logger log4jLogger = LogManager.getLogger("app");
	
	
	@Override
	public ILogger log(String message) {
		log4jLogger.trace(String.format("%1$s %2$s", df.format(new Date()), message)); 
		return this;
	}
	
	@Override
	public ILogger log(String message, Object... args) {
		return log(String.format(message, args)); 
	}

	@Override
	public ILogger log() {
	  return log("");
	}
	
}
