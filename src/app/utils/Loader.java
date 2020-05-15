package app.utils;

import java.lang.reflect.Constructor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import app.dal.DbContext;
import app.model.Entity;
import app.model.EntityList;

public class Loader {

	public static Entity loadOne(Entity target, ResultSet dataReader) {
		return loadOne(target, dataReader, true);
	}

	private static Entity loadOne(Entity target, ResultSet dataReader, boolean next) {
		try {
			if (next && dataReader.next() == false)
				return target;
			NamedBindersManager.bind(target, dataReader);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return target;
	}

	public static void load(ResultSet dataReader, EntityList<?> list, String entityClassName) {
		@SuppressWarnings("unchecked")
		EntityList<Entity> _list = (EntityList<Entity>) list;
		try {
			Constructor<? extends Entity> builder = getBuilder(entityClassName);
			while (dataReader.next()) {
				_list.add(loadOne(builder.newInstance(list.dbContext), dataReader, false));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Map<String, Constructor<? extends Entity>> builders = new HashMap<String, Constructor<? extends Entity>>();
	@SuppressWarnings("unchecked")
	public static Constructor<? extends Entity> getBuilder(String name) throws Exception {
		Constructor<? extends Entity> builder = builders.get(name);
		if (builder == null) {
			builder = (Constructor<? extends Entity>)Class.forName(name).getConstructor(DbContext.class);
			if(builder == null)	throw new Exception("Constructor not found: " + name);
			builders.put(name, builder);
			//System.out.printf("--- %1$s()\n", name);
		}
		return builder;
	}

}
