package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.ConnectionImpl;
import connection.GenericConnection;
import model.Database;

public class DataBaseDaoImpl implements DatabaseDao {
	
	private Connection c;
	
	public DataBaseDaoImpl() {
		GenericConnection gc = new ConnectionImpl();
		c = gc.getConnection();
	}

	@Override
	public List<Database> listaDatabase() throws SQLException {
		List<Database> lista = new ArrayList<Database>();
		
		String sql = "SELECT dbid,name,convert(varchar(10),crdate, 103) as data, "
					+ "substring(convert(varchar(10),crdate, 108),1,5) as hora "
					+ "FROM sys.sysdatabases where name not like 'tempdb'";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		while ( rs.next() ) {
			Database db = new Database();
			db.setId(rs.getLong("dbid"));
			db.setNome(rs.getString("name"));
			db.setData(rs.getDate("data"));
			db.setHora(rs.getString("hora"));
			lista.add(db);
		}
		
		return lista;
	}



}
