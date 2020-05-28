package app.utils;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;


public class JsonSerializer {

	private final static ObjectMapper serializer = new ObjectMapper();
	private final static XmlMapper xmlSerializer = new XmlMapper();

	static{
		serializer.setVisibility(PropertyAccessor.ALL, Visibility.PUBLIC_ONLY);
		serializer.setVisibility(PropertyAccessor.GETTER, Visibility.NONE);
		xmlSerializer.setVisibility(PropertyAccessor.ALL, Visibility.PUBLIC_ONLY);
		xmlSerializer.setVisibility(PropertyAccessor.GETTER, Visibility.NONE);
		//serializer.enable(SerializationFeature.WRAP_ROOT_VALUE);
	}

	public static String toJsonString(Object value) {
		try {
			return serializer.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			Utils.getLogger().error(e);
		}
		return "{}";
	}	

	public static String toXmlString(Object value) {
		try {
			return xmlSerializer.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			Utils.getLogger().error(e);
		}
		return "{}";
	}	

}
