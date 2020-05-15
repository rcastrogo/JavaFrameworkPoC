package app.model;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnore;

import app.dal.*;

public abstract class EntityList<T extends Entity> extends ArrayList<T>{
	
	private static final long serialVersionUID = 1L;

	@JsonIgnore()
	public Object tag = null;
	@JsonIgnore()
	public DbContext dbContext = null;

	public EntityList(){ }

	public EntityList(DbContext context)
	{
		dbContext = context;
	}

}