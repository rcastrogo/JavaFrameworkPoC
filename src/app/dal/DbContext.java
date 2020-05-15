package app.dal;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

public class DbContext implements Closeable {
	
  Connection conn = null; 
  String dbURL    = "jdbc:sqlserver://DESKTOP-MCSK9K9:1433;databaseName=TOLEDO;user=sa;password=Rcastro#";
  
  static {
    try {
    	DriverManager.registerDriver(new SQLServerDriver());
    } catch (SQLException ex) {
      ex.printStackTrace();
    }  	
  }

	public DbContext(){
    try {
			conn = DriverManager.getConnection(dbURL);
			System.out.printf("Connetion.open: %1$d\n", conn.hashCode());
    } catch (SQLException ex) {
      ex.printStackTrace();
    }		
	}
	
  public ResultSet executeNamedQuery(String name) throws SQLException {
		//System.out.println("executeNamedQuery: " + name);
		String query = NamedQueriesManager.getNamedQuery(name);
    return executeQuery(query);
	}
	
	public ResultSet executeNamedQuery(String name, Integer id) throws SQLException {
		//System.out.println("executeNamedQuery: " + name);
		String query = NamedQueriesManager.getNamedQuery(name);
    return executeQuery(query + " WHERE Id = " + id.toString());
	}

	public ResultSet executeQuery(String sqlQuery) throws SQLException {
		System.out.println(sqlQuery);
		return createCommand().executeQuery(sqlQuery);
	}
	
	public int executeNamedScalar(String name){
		//System.out.println("executeNamedScalar: " + name);
		String query = NamedQueriesManager.getNamedQuery(name);
		return executeScalar(query);
	}
	
	public int executeScalar(String sqlQuery){
		try( ResultSet dataReader = executeQuery(sqlQuery) ){
			if (dataReader.next() ) {  
        return dataReader.getInt(1);  
      }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;			
	}
	
	private Statement createCommand() throws SQLException {
		return conn.createStatement();
	}
	
	private boolean closing = false; /* To avoid recursive closing */
	/**
	 * Close the DbContext
	 */
	@Override
	public void close() {
		synchronized (this) {
			if (! closing) {
				closing = true;
				try {
					if (conn != null && !conn.isClosed()) {
						System.out.printf("Connetion.close: %1$d\n", conn.hashCode());
						conn.close();
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}	
				conn = null;
			}
		}
	}	
	
}