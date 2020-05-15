package app.dal.repositories;

import app.dal.DbContext;

public class UsuariosRepository extends BaseRepository {
   
  public UsuariosRepository(DbContext dbContext) {
    super(dbContext);
  }

  public int count() {
  	return dbContext.executeNamedScalar("app.dal.repositories.UsuariosRepository.count");
  }
    
}