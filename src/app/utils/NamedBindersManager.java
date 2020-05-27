package app.utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import app.loggin.Logmanager;
import app.model.Entity;
import app.utils.compiler.DynamicCompiler;
import app.utils.Utils;

public class NamedBindersManager {

	private static final String RESOURCE_NAME = "./app/model/binders.txt";
	private static final Map<String, Method> binders = new HashMap<String, Method>();

	static {
		Utils.getLogger()
		     .log()
			   .log("---------------------------------------------- Binders ---------------------------------------------")
				 .log(" _   _                                  _   ______   _               _                     ")
				 .log("| \\ | |                                | |  | ___ \\ (_)             | |                    ")
				 .log("|  \\| |   __ _   _ __ ___     ___    __| |  | |_/ /  _   _ __     __| |   ___   _ __   ___ ")
				 .log("| . ` |  / _` | | '_ ` _ \\   / _ \\  / _` |  | ___ \\ | | | '_ \\   / _` |  / _ \\ | '__| / __|")
				 .log("| |\\  | | (_| | | | | | | | |  __/ | (_| |  | |_/ / | | | | | | | (_| | |  __/ | |    \\__ \\")
				 .log("\\ | \\_/  \\__,_| |_| |_| |_|  \\___|  \\__,_|  \\____/  |_| |_| |_|  \\__,_|  \\___| |_|    |___/")
			   .log("----------------------------------------------------------------------------------------------------")
				 .log("Loading...");
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
					sb.append(line.trim().replaceAll("\\s*,\\s*", ",")) ;
					continue;
				}
				if (key.length() > 0)	defs.put(key, sb.toString());
				key = line.substring(1, line.length());
			  sb = new StringBuilder();
			}
			if (key.length() > 0)	defs.put(key, sb.toString());
			defs.keySet()
					.stream()
					.sorted()
					.forEach( k -> Utils.getLogger().log(" - " + k + ":" + defs.get(k)));	
					Utils.getLogger().log();
					generateBinderClass(defs);		
			Utils.getLogger().log();
			Utils.getLogger().log("%1$s named binders", defs.size());
			Utils.getLogger().log("----------------------------------------------------------------------------------------------------");	
    } catch (IOException e) {
    	((Logmanager)Utils.getLogger()).error(e);
		};
	}
	
	private static String parseDbMethod(String index, String dbType){
		String type = dbType.toLowerCase().trim();
		Integer i = 1 + Integer.parseInt(index);
		if(type.startsWith("int")){
			return "dataReader.getInt(" + i  + ")";
		}
		if(type.startsWith("date")){
			return "app.model.Entity.handleNullDate(dataReader.getDate(" + i  + "))";
		}
		return "app.model.Entity.handleNull(dataReader.getString(" + i + "))";
	} 

	private static void writeBinder(StringBuilder sb, String methodName, String targetType, String[] fieldDefinitions){
		String name = methodName.replace('.', '_');
		sb.append(String.format("  public static void %1$s(%2$s target, ResultSet dataReader) {\n", name, targetType));
		sb.append("    try {\n");
		for( String data : fieldDefinitions){
			String[] tokens = data.split(",");
			String index = tokens[0];
			String field = tokens[1];
			String dbType = tokens.length > 2 ? tokens[2] : "String";
			String dbMethod = parseDbMethod(index, dbType);
			sb.append(String.format("      target.%1$s = %2$s;\n", field, dbMethod));
		}
		sb.append("    } catch (SQLException e) {\n");
		sb.append("      ((Logmanager)app.utils.Utils.getLogger()).error(e);\n");
		sb.append("    }\n");
		sb.append("  }\n");	
	}

  private static void generateBinderClass(Map<String, String> defs){
		try {		
			StringBuilder sb = new StringBuilder();
			sb.append("package app.binders;\n");
			sb.append("import app.loggin.Logmanager;\n");
			sb.append("import app.model.entities.*;\n");
			sb.append("import java.sql.ResultSet;\n");
			sb.append("import java.sql.SQLException;\n");
			sb.append("public class DynamicsBinders {\n");
			for( String key : defs.keySet()){
				String methodName = key.split("@")[0];
				String targetType = key.split("@")[1];
			  String[] metadata =  defs.get(key).split(";");
				writeBinder(sb, methodName, targetType, metadata);
			}
			sb.append("}\n");
			DynamicCompiler compiler = new DynamicCompiler();
			String className = "app.binders.DynamicsBinders";
			String code = sb.toString();
			Utils.getLogger().log("Compiling...");
			// Utils.getLogger().log(code);
			if (compiler.compile(className, code)){
				Utils.getLogger().log("Mapping...");
				for( String key : defs.keySet()){
					String methodName = key.split("@")[0].replace('.', '_');
					Utils.getLogger().log(" - " + methodName);
					binders.put(methodName, compiler.getBinder(methodName));
				}
			}else{
				Utils.getLogger().log("Compilation error");
			};

		} catch (Exception e) {
				Utils.getLogger().log(e.getMessage());
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
				((Logmanager)Utils.getLogger()).error(e);
			}
			binders.put(name, method);
		}
		try {
			method.invoke(null, target, dataReader);
		}  catch (IllegalAccessException   |
							IllegalArgumentException | 
							InvocationTargetException e) {
			((Logmanager)Utils.getLogger()).error(e);
		}
	}

}
