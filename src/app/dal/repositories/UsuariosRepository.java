package app.dal.repositories;

import app.dal.Utils;
import app.dal.DbContext;

public class UsuariosRepository extends BaseRepository {
   
  public UsuariosRepository(DbContext dbContext) {
    super(dbContext);
  }

  public Integer count() {
  	return (Integer)dbContext.executeNamedScalar("app.dal.repositories.UsuariosRepository.count");
  }
    
  public Integer insert(String nif, 
                        String nombre, 
                        String descripcion){
    return super.insertWithAutoIncrement( new Object[] {
      Utils.parseString(nif),
      Utils.parseString(nombre),
      Utils.parseString(descripcion),
    });      
  }

  public Integer update(Integer id, 
                        String nif, 
                        String nombre, 
                        String descripcion, 
                        String fechaDeAlta){
    return super.update( new Object[] {
      Utils.parseString(nif),
      Utils.parseString(nombre),
      Utils.parseString(descripcion),
      Utils.parseDate(fechaDeAlta),
      id
    });      
  }

}