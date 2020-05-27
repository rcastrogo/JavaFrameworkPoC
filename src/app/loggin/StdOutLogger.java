package app.loggin;

public class StdOutLogger implements ILogger {

	@Override
	public ILogger log(String message) {
		System.out.println(message);
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
