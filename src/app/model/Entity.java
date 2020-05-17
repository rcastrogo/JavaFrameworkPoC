package app.model;

import java.sql.Date;

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

	public static String handleNull(String value){
		return (value == null) ? "" : value;
	}

	public static String handleNullDate(Date value){
		return (value == null) ? "" : value.toString();
	}

}