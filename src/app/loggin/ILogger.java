package app.loggin;

public interface ILogger {

	ILogger log();
	ILogger log(String message);
	ILogger log(String message, Object... args);
	
}
