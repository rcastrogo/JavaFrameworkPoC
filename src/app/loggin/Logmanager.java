package app.loggin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Logmanager implements ILogger {

	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	private List<ILogger> loggers;
	
	public Logmanager() {
		loggers = new ArrayList<ILogger>();
		loggers.add(new StdOutLogger());
		log("Init app logmanager with StdOutLogger");
	}
	
	@Override
	public ILogger log(String message, Object... args) {
		return log(String.format(message, args));
	}
	
	@Override
	public ILogger log(String message) {
		String s = String.format("%1$s %2$s", df.format(new Date()), message);
		for(ILogger logger : loggers) {
			logger.log(s);
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
