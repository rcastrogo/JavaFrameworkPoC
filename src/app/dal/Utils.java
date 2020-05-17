package app.dal;

public class Utils{

  public static String parseString(String value){
    return app.utils.Utils.isEmpty(value) ? null : value;
  }

  public static String parseDate(String value){
    if(app.utils.Utils.isEmpty(value)) return null;
    return value;
  }

}
