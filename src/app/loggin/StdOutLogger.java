package app.loggin;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StdOutLogger implements ILogger {

	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	@Override
	public ILogger log(String message) {
		System.out.println(String.format("%1$s %2$s", df.format(new Date()), message));
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
