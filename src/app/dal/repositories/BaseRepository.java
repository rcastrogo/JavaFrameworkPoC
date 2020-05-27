package app.dal.repositories;

import java.io.Closeable;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.dal.DbContext;
import app.loggin.Logmanager;
import app.utils.Utils;

public class BaseRepository implements Closeable {

  protected DbContext dbContext;

  public BaseRepository(DbContext dbContext) {
    this.dbContext = dbContext;
    auto = false;
  }

  public BaseRepository() {
    dbContext = new DbContext();
  }

  public Integer delete(Integer id) {
    return dbContext.executeNamedUpdate(getQueryNameFor("delete"), new Object[]{id});
  }

  public Integer insertWithAutoIncrement(Object[] values) {
    return dbContext.executeNamedInsert(getQueryNameFor("insert"), values);
  }

  public Integer insert(Object[] values) {
    return insert(getQueryNameFor("insert"), values);
  }

  public Integer insert(String namedQuery, Object[] values)  {
    return dbContext.executeNamedInsert(namedQuery, values);
  }

  public Integer update(Object[] values) {
    return update(getQueryNameFor("update"), values);
  }

  public Integer update(String namedQuery, Object[] values) {
    return dbContext.executeNamedUpdate(namedQuery, values);
  }

  public ResultSet load() {
    try {
      return dbContext.executeNamedQuery(getQueryNameFor("select"));
    } catch (SQLException e) {
    	((Logmanager)Utils.getLogger()).error(e);
      return null;
    }
  }

  public ResultSet loadById(Integer id) {
    try {
      return dbContext.executeNamedQuery(getQueryNameFor("select"), id);
    } catch (SQLException e) {
    	((Logmanager)Utils.getLogger()).error(e);
      return null;
    }
  }

  private String getQueryNameFor(String action){
    return this.getClass().getName() + "." + action;
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