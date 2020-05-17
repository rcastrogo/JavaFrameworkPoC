package app.model.entities;

import app.dal.*;
import app.dal.repositories.UsuariosRepository;
import app.model.Entity;

import app.utils.Loader;

public class User extends Entity {

	public Integer id = 0;
	public String nif = "";
	public String nombre = "";
	public String descripcion = "";
	public String fechaDeAlta = null;

	public User() {
		super();
	}

	public User(DbContext context) {
		super(context);
	}

	public User load() {
		return load(id);
	}

	public User delete(){
		try( UsuariosRepository repo = new UsuariosRepository(dbContext)){
			repo.delete(id);
		}
		return this;
	}

	public User save(){
		try( UsuariosRepository repo = new UsuariosRepository(dbContext)){
			if (id == 0){
				id = repo.insert(nif, nombre, descripcion);
			} else {
				repo.update(id, nif, nombre, descripcion, fechaDeAlta);
			}
		}
		return this;
	}

	public User load(Integer Id) {
		try( UsuariosRepository repo = new UsuariosRepository(dbContext)){
			Loader.loadOne(this, repo.loadById(Id));
		}
		return this;
	}

}