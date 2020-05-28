package app.dal;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import app.utils.Utils;

public class NamedQueriesManager {

  private static final String RESOURCE_NAME = "./app/dal/queries.txt";
  private static Map<String, String> namedQueries = new HashMap<String, String>(); 

  static{
    Utils.getLogger()
         .log()
		     .log("---------------------------------------------- Queries ---------------------------------------------")
		     .log(" _   _                                  _    _____                         _              ")
		     .log("| \\ | |                                | |  |  _  |                       (_)             ")
		     .log("|  \\| |   __ _   _ __ ___     ___    __| |  | | | |  _   _    ___   _ __   _    ___   ___ ")
		     .log("| . ` |  / _` | | '_ ` _ \\   / _ \\  / _` |  | | | | | | | |  / _ \\ | '__| | |  / _ \\ / __|")
		     .log("| |\\  | | (_| | | | | | | | |  __/ | (_| |  \\ \\/' / | |_| | |  __/ | |    | | |  __/ \\__ \\")
		     .log("\\_| \\_/  \\__,_| |_| |_| |_|  \\___|  \\__,_|   \\_/\\_\\  \\__,_|  \\___| |_|    |_|  \\___| |___/")
		     .log("----------------------------------------------------------------------------------------------------")
		     .log("Loading...");
    try(InputStream stream = Utils.getResourceInputStream(RESOURCE_NAME)){
      Utils.readAllLines(stream)
           .forEach( line -> {
             if(Utils.isEmpty(line)) return;
             if(line.charAt(0) == ';') return;
             if(line.charAt(0) == '-') return;
             int index = line.indexOf('%');
             Utils.getLogger().log(" - " + line.substring(1, index));
             namedQueries.put(line.substring(1, index), line.substring(index + 1, line.length()));
           });
      Utils.getLogger().log();
      Utils.getLogger().log(String.format("%1$s named queries", namedQueries.size()));
      Utils.getLogger().log("----------------------------------------------------------------------------------------------------");
    } catch (IOException e) {
    	Utils.getLogger().error(e);
		};   
  }

  public static String getNamedQuery(String name){
    return namedQueries.get(name);
  }

}