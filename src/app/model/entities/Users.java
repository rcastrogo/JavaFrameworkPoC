package app.model.entities;


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
			Loader.load(repo.load(), this, entityClassName);   
		}
		return this;
	}
	
	public Integer Count(){
		try( UsuariosRepository repo = new UsuariosRepository(dbContext)){
			return repo.count();
		}
	}

}