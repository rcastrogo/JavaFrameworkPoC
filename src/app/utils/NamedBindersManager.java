package app.utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import app.model.Entity;
import app.utils.compiler.DynamicCompiler;

public class NamedBindersManager {

	private static final String RESOURCE_NAME = "./app/utils/data/binders.txt";
	private static final Map<String, Method> binders = new HashMap<String, Method>();

	static {
		System.out.println();
    System.out.println("---------------------------------------------- Binders ---------------------------------------------");
		System.out.println(" _   _                                  _   ______   _               _                     ");
		System.out.println("| \\ | |                                | |  | ___ \\ (_)             | |                    ");
		System.out.println("|  \\| |   __ _   _ __ ___     ___    __| |  | |_/ /  _   _ __     __| |   ___   _ __   ___ ");
		System.out.println("| . ` |  / _` | | '_ ` _ \\   / _ \\  / _` |  | ___ \\ | | | '_ \\   / _` |  / _ \\ | '__| / __|");
		System.out.println("| |\\  | | (_| | | | | | | | |  __/ | (_| |  | |_/ / | | | | | | | (_| | |  __/ | |    \\__ \\");
		System.out.println("\\ | \\_/  \\__,_| |_| |_| |_|  \\___|  \\__,_|  \\____/  |_| |_| |_|  \\__,_|  \\___| |_|    |___/");
    System.out.println("----------------------------------------------------------------------------------------------------");
		System.out.println("Loading...");

		Map<String, String> defs = new HashMap<String, String>();
		StringBuilder sb = new StringBuilder();
		String key = "";

    try(InputStream stream = Utils.getResourceInputStream(RESOURCE_NAME)){

    	for( String line : Utils.readAllLines(stream)){
				if(Utils.isEmpty(line)) continue;
				if(line.charAt(0) == ';') continue;
				if(line.charAt(0) == '-') continue;
				if(line.charAt(0) != '#'){
					if(sb.length() > 0)	sb.append(";");
					sb.append(line.trim().replaceAll("\s*,\s*", ","));
					continue;
				}
				if (key.length() > 0)	defs.put(key, sb.toString());
				key = line.substring(1, line.length());
			  sb = new StringBuilder();
			}
			if (key.length() > 0)	defs.put(key, sb.toString());
      System.out.println("----------------------------------------------------------------------------------------------------");
      System.out.println(String.format("loaded %1$s named binders", defs.size()));
			System.out.println("----------------------------------------------------------------------------------------------------");
			defs.keySet()
					.stream()
					.sorted()
					.forEach( k -> {
						System.out.println(k + ":" + defs.get(k));
					});
			System.out.println("----------------------------------------------------------------------------------------------------");
			
			generateBinderClass(defs);

    } catch (IOException e) {
			e.printStackTrace();
		};
	}
	
  private static void generateBinderClass(Map<String, String> defs){
		try {		
			
			StringBuilder sb = new StringBuilder();
			sb.append("package app.binders;\n");
			sb.append("import java.sql.ResultSet;\n");
			sb.append("import java.sql.SQLException;\n");
			sb.append("public class DynamicsBinders {\n");
			for( String key : defs.keySet()){
			
				String methodName = key.split("@")[0].replace('.', '_');
				String targetType = key.split("@")[1];

				sb.append(String.format("  //public static void %1$s(%2$s target, ResultSet dataReader) {\n", methodName, targetType));
				sb.append("  //  try {\n");

				sb.append("  //  } catch (SQLException e) {\n");
				sb.append("	 //    e.printStackTrace();\n");
				sb.append("  //  }\n");
				sb.append("  //}\n");
			}

			sb.append("  public static void app_model_entities_User(app.model.entities.User target, ResultSet dataReader) {\n");
			sb.append("    try {\n");
			sb.append("	     target.id  = dataReader.getInt(1);\n");
			sb.append("	     target.nif = dataReader.getString(2);\n");
			sb.append("	     target.nombre = dataReader.getString(3);\n");
			sb.append("    } catch (SQLException e) {\n");
			sb.append("	     e.printStackTrace();\n");
			sb.append("    }\n");
			sb.append("  }\n");

			sb.append("}\n");
			
			String className = "app.binders.DynamicsBinders";
			String code = sb.toString();

			System.out.println(code);

			DynamicCompiler compiler = new DynamicCompiler();
				if (compiler.compile(className, code)){
				binders.put("app_model_entities_User", compiler.getBinder("app_model_entities_User"));
				for( String key : defs.keySet()){
					System.out.println("compiler.getBinder(" + key + ")");
				}
			}else{
				System.out.println("Error al compilar");
			};

		} catch (Exception e) {
				System.out.println(e.getMessage());
		}
	}

	public static void bind(Entity target, ResultSet dataReader) throws SQLException {
		bind(target, 
				 dataReader,
				 target.getClass()
							 .getName()
							 .replace(".", "_")
				);
	}

	public static void bind(Entity target, ResultSet dataReader, String name) throws SQLException {
		Method method = binders.get(name);
		if (method == null) {
			try {
				method = NamedBindersManager.class.getMethod(name, target.getClass(), ResultSet.class);
			} catch (NoSuchMethodException | 
			         SecurityException e) {
				e.printStackTrace();
			}
			binders.put(name, method);
		}
		try {
			method.invoke(null, target, dataReader);
		}  catch (IllegalAccessException   |
							IllegalArgumentException | 
							InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
