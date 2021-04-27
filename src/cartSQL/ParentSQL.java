package cartSQL;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class ParentSQL {
	protected static Connection c;
	protected static Statement stmt;
	protected static String database = "";

	protected abstract String getDatabase();

	protected void connect() {
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + getDatabase(), "postgres",
					"darth129");
		} catch (Exception e) {
			error(e);
		}
	}

	protected ResultSet readQuery(String sql) {
		connect();
		try {
			stmt = c.createStatement();
			return stmt.executeQuery(sql);
		} catch (Exception e) {
			error(e);
			return null;
		}
	}

	protected boolean InsertQuery(String sql) {
		try {
			connect();
			c.setAutoCommit(false);
			stmt = c.createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			error(e);
			return false;
		}
		return true;
	}

	protected boolean InsertQueryImage(String sql, File file) {
		try {
			connect();
			PreparedStatement ps = c.prepareStatement(sql);
			if (file != null) {
				FileInputStream stream = new FileInputStream(file);
				ps.setBinaryStream(1, stream, file.length());
				ps.executeUpdate();
				stream.close();
			} else {
				ps.setBinaryStream(1, null);
				ps.executeUpdate();
			}
			ps.close();
			c.close();
			return true;
		} catch (Exception e) {
			error(e);
		}
		return false;
	}

	protected void close() {
		try {
			stmt.close();
			c.close();
		} catch (SQLException e) {
			error(e);
		}
	}

	protected void error(Exception e) {
		e.printStackTrace();
		System.err.println(e.getClass().getName() + ":" + e.getMessage());
		System.exit(0);
	}

	protected boolean updateImage(String table, String id, File file) {
		try {
			connect();
			FileInputStream stream = new FileInputStream(file);
			PreparedStatement ps = c
					.prepareStatement(String.format("UPDATE %s SET image = ? WHERE id='%s';", table, id));
			ps.setBinaryStream(1, stream, file.length());
			ps.executeUpdate();
			ps.close();
			stream.close();
			c.close();
			return true;
		} catch (Exception e) {
			error(e);
		}
		return false;
	}
}
