package app.dal.repositories;

import java.io.Closeable;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.dal.DbContext;

public class BaseRepository implements Closeable {

  protected DbContext dbContext;

  public BaseRepository(DbContext dbContext) {
    this.dbContext = dbContext;
    auto = false;
  }

  public BaseRepository() {
    dbContext = new DbContext();
  }

  public ResultSet load() throws SQLException {
    String name = getDefaultSelectName();
    return dbContext.executeNamedQuery(name);
  }

  public ResultSet loadById(Integer id) throws SQLException {
    String name = getDefaultSelectName();
    return dbContext.executeNamedQuery(name, id);
  }

  private String getDefaultSelectName(){
    return this.getClass().getName() + ".select";
  }

  private boolean auto = true;    /* To avoid close shared context */
  private boolean closing = false; /* To avoid recursive closing */
  @Override
  public void close() {
    synchronized (this) {
			if (!closing) {
        closing = true;
        if(auto) dbContext.close();
      }
    }
  }

}