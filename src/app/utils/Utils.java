package app.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class Utils {

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
			e.printStackTrace();
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
			e.printStackTrace();
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

}
