package app.model.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

import app.dal.*;
import app.dal.repositories.UsuariosRepository;
import app.model.Entity;

import app.utils.Loader;

public class User extends Entity {

	public Integer id = 0;
	public String nif = "";
	public String nombre = "";
	public String descripcion = "";

	public User() {
		super();
	}

	public User(DbContext context) {
		super(context);
	}

	public User load() {
		return load(id);
	}

	public User load(Integer Id) {
		try( UsuariosRepository repo = new UsuariosRepository(dbContext)){
			try(ResultSet dataReader = repo.loadById(Id)){
			  Loader.loadOne(this, dataReader);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return this;
	}

	public Integer getAge(){
		return 6;
	}

}