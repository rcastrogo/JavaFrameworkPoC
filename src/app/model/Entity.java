package app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import app.dal.*;

public abstract class Entity {

  @JsonIgnore()
	public Object tag = null;
	@JsonIgnore()
	public DbContext dbContext = null;
	
	public Entity(){ }

	public Entity(DbContext context)
	{
		dbContext = context;
	}

}