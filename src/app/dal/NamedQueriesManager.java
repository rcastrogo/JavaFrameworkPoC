package app.dal;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import app.utils.Utils;

public class NamedQueriesManager {

  private static final String RESOURCE_NAME = "./app/utils/data/queries.txt";
  private static Map<String, String> namedQueries = new HashMap<String, String>(); 

  static{
    System.out.println();
    System.out.println("---------------------------------------------- Queries ---------------------------------------------");
    System.out.println(" _   _                                  _    _____                         _              ");
    System.out.println("| \\ | |                                | |  |  _  |                       (_)             ");
    System.out.println("|  \\| |   __ _   _ __ ___     ___    __| |  | | | |  _   _    ___   _ __   _    ___   ___ ");
    System.out.println("| . ` |  / _` | | '_ ` _ \\   / _ \\  / _` |  | | | | | | | |  / _ \\ | '__| | |  / _ \\ / __|");
    System.out.println("| |\\  | | (_| | | | | | | | |  __/ | (_| |  \\ \\/' / | |_| | |  __/ | |    | | |  __/ \\__ \\");
    System.out.println("\\_| \\_/  \\__,_| |_| |_| |_|  \\___|  \\__,_|   \\_/\\_\\  \\__,_|  \\___| |_|    |_|  \\___| |___/");
    System.out.println("----------------------------------------------------------------------------------------------------");
    System.out.println("Loading...");
    try(InputStream stream = Utils.getResourceInputStream(RESOURCE_NAME)){
      Utils.readAllLines(stream)
           .forEach( line -> {
             if(Utils.isEmpty(line)) return;
             if(line.charAt(0) == ';') return;
             if(line.charAt(0) == '-') return;
             int index = line.indexOf('%');
             System.out.println(" - " + line.substring(1, index));
             namedQueries.put(line.substring(1, index), line.substring(index + 1, line.length()));
           });
      System.out.println();
      System.out.println(String.format("%1$s named queries", namedQueries.size()));
      System.out.println("----------------------------------------------------------------------------------------------------");
    } catch (IOException e) {
			e.printStackTrace();
		};   
  }

  public static String getNamedQuery(String name){
    return namedQueries.get(name);
  }

}