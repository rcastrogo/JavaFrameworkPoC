package app.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import app.loggin.Log4javaLogger;
import app.loggin.Logmanager;
import app.loggin.StdOutLogger;

public class Utils {

	private Utils(){}
	private static Properties props = null;
	private static Logmanager logmanager = null;
 
	static {
		Utils util = new Utils();
		try {
			logmanager = new Logmanager();			
			props = util.getPropertiesFromClasspath("binders.properties"); 
			logmanager.addLogger(new StdOutLogger());
			logmanager.addLogger(new Log4javaLogger());
		} catch (IOException e) {
			if(logmanager != null) {
				logmanager.error(e);
			}
		}
	}

	public static String getRequestBody(final HttpServletRequest request) {
		try (BufferedReader reader = request.getReader()) {
			if (reader == null)
				return "";
			final StringBuilder builder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			return builder.toString();
		} catch (final Exception e) {
			return "";
		}
	}

	public static String getHeadersInfo(HttpServletRequest request) {
		final StringBuilder builder = new StringBuilder();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			builder.append(key + " : " + value);
			builder.append("\n");
		}
		return builder.toString();
	}

	public static String getParameters(HttpServletRequest request) {
		final StringBuilder builder = new StringBuilder();
		Enumeration<String> paramsNames = request.getParameterNames();
		while (paramsNames.hasMoreElements()) {
			String key = (String) paramsNames.nextElement();
			String value = request.getParameter(key);
			builder.append(key + " : " + value);
			builder.append("\n");
		}
		return builder.toString();
	}

	public static ArrayList<String> readAllLines(InputStream inputStream) {
		ArrayList<String> lines = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			String line = "";
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			logmanager.error(e);
		}
		return lines;
	}

	public static String readAll(InputStream inputStream) {
		try(Reader reader = new InputStreamReader(inputStream)){
			StringBuilder sb = new StringBuilder();
			int c = 0;
			while ((c = reader.read()) != -1) {
				sb.append((char) c);
			}
			return sb.toString();
		}catch (IOException e) {
			logmanager.error(e);
		}
		return "";
	}

	public static InputStream getResourceInputStream(String name){
		return Utils.class
								.getClassLoader()
								.getResourceAsStream(name);
	}
	
	public static boolean isEmpty(final CharSequence charSequence) {
		return charSequence == null || charSequence.length() == 0;
  }
 
  public static String getProperty(String key){
    return props.getProperty(key);
  }
 
  public static Set<Object> getkeys(){
    return props.keySet();
  }
 
  public static Properties getProperties(){
    return props;
  }
 
  private Properties getPropertiesFromClasspath(String fileName) throws IOException {
    Properties props = new Properties();
		try (InputStream  inputStream = Thread.currentThread()
																					.getContextClassLoader()
																					.getResourceAsStream(fileName)){
      if (inputStream == null) {
        throw new FileNotFoundException("property file '" + fileName + "' not found in the classpath");
      }
 			logmanager.log("Loading properties file: " + fileName);     
			props.load(inputStream);
    }
    return props;
  }

  public static Logmanager getLogger() {
  	return logmanager;
  }
  
  public static String formatXml(String xml){
    Source xmlInput = new StreamSource(new StringReader(xml));
    StringWriter stringWriter = new StringWriter();
		try {
		  TransformerFactory transformerFactory = TransformerFactory.newInstance();			
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.transform(xmlInput, new StreamResult(stringWriter));	
			return stringWriter.toString()
					               .replace("\r\n", "\n");  
		} catch (TransformerException e) {
			logmanager.error(e);
		}
	  return "";
	}
}
