package app.loggin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Logmanager implements ILogger {
	
	private List<ILogger> loggers;
	
	public Logmanager() {
		loggers = new ArrayList<ILogger>();
	}
	
	public Logmanager addLogger(ILogger logger) {
		log("AddLogger " + logger.getClass().getName());
		loggers.add(logger);
		return this;
	}
	
	@Override
	public ILogger log(String message, Object... args) {
		return log(String.format(message, args));
	}
	
	@Override
	public ILogger log(String message) {
		for(ILogger logger : loggers) {
			logger.log(message);
		}
		return this;
	}
	
	@Override
	public ILogger log() {
	  return log("");
	}

	public ILogger error(Exception e) {
		log(e.getMessage());
		StringBuilder sb = new StringBuilder();
		Arrays.stream(e.getStackTrace())
		      .map( item -> item.toString())
		      .forEach(line -> sb.append("    ")
		      		               .append(line.toString())
		      		               .append("\n"));		      
		for(ILogger logger : loggers) {
			logger.log(sb.toString());
		}
		return this;
	}
	
}
