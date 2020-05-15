package app.model.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

import app.dal.*;
import app.dal.repositories.UsuariosRepository;
import app.model.EntityList;
import app.utils.Loader;

public class Users extends EntityList<User> {

	private static final long serialVersionUID = 1L;
	private static final String entityClassName = User.class.getName();	
	public Users(){ 
		super();
	}

	public Users(DbContext context)
	{
		super(context);
	}

	public Users load(){
		try( UsuariosRepository repo = new UsuariosRepository(dbContext)){
			try(ResultSet dataReader = repo.load()){
				Loader.load(dataReader, this, entityClassName);   
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return this;
	}
	
	public Integer Count(){
		try( UsuariosRepository repo = new UsuariosRepository(dbContext)){
			return repo.count();
		}
	}

}