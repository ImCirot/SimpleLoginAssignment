package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserDAO {
	
	private static final String TABLE_NAME = "usersData";
	
	public synchronized void doSave(UserBean user) throws SQLException {
		Connection con = null;
		PreparedStatement statement = null;
		
		String query = "INSERT INTO " + UserDAO.TABLE_NAME + 
					" (username,password,nome,cognome) VALUES (?,?,?,?);";
		
		try {
			con = DriverManagerConnectionPool.getConnection();
			statement = con.prepareStatement(query);
			
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getName());
			statement.setString(4, user.getSurname());
			
			statement.executeUpdate();
			
			con.commit();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} finally {
				DriverManagerConnectionPool.releaseConnection(con);
			}
		}
	}

	public synchronized boolean doDelete(String username) throws SQLException {
		Connection con = null;
		PreparedStatement statement = null;
		int result = 0;
		String query = "DELETE FROM " + UserDAO.TABLE_NAME + " WHERE USERNAME = ?";
		
		try {
			con = DriverManagerConnectionPool.getConnection();
			statement = con.prepareStatement(query);
			statement.setString(1, username);
			
			result = statement.executeUpdate();
		} finally {
			try {
				if(statement != null) {
					statement.close();
				}
			} finally {
				DriverManagerConnectionPool.releaseConnection(con);
			}
		}
		
		return result != 0;
	}

	public synchronized UserBean doRetrieveByKey(String username) throws SQLException {
		Connection con = null;
		PreparedStatement statement = null;
		UserBean user = new UserBean();
		
		String query = "SELECT * FROM " + UserDAO.TABLE_NAME + " WHERE username = ?";
		
		try {
			con = DriverManagerConnectionPool.getConnection();
			statement = con.prepareStatement(query);
			statement.setString(1, username);
			
			ResultSet result = statement.executeQuery();
			
			while(result.next()) {
				user.setUsername(result.getString("username"));
				user.setPassword(result.getString("password"));
				user.setName(result.getString("name"));
				user.setSurname(result.getString("surname"));
			}
		} finally {
			try {
				if(statement != null) {
					statement.close();
				}
			} finally {
				DriverManagerConnectionPool.releaseConnection(con);
			}
		}
		
		return user;
	}
	
	public synchronized List<UserBean> doRetrieveAll(String order) throws SQLException {
		Connection con = null;
		PreparedStatement statement = null;
		
		List<UserBean> users = new ArrayList<>();
		
		String query = "SELECT * FROM " + UserDAO.TABLE_NAME;
		
		if(checkOrder(order)) {
			query += " ORDER BY" + order;
		}
		
		try {
			con = DriverManagerConnectionPool.getConnection();
			statement = con.prepareStatement(query);
			
			ResultSet result = statement.executeQuery();
			
			while(result.next()) {
				UserBean user = new UserBean();
				
				user.setSurname(result.getString("username"));
				user.setPassword(result.getString("password"));
				user.setName(result.getString("name"));
				user.setSurname(result.getString("surname"));
				
				users.add(user);
			}
		} finally {
			try {
				if(statement != null) {
					statement.close();
				}
			} finally {
				DriverManagerConnectionPool.releaseConnection(con);
			}
		}
		
		return users;
	}
	
	private boolean checkOrder(String order) {
		if(order.equalsIgnoreCase("username") || order.equalsIgnoreCase("name") || order.equalsIgnoreCase("surname")) {
			return true;
		} else {
			return false;
		}
	}
}
