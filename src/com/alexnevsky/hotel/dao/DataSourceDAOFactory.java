package com.alexnevsky.hotel.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * The DataSource based AbstractDAOFactory.
 * Implements Singleton pattern.
 * 
 * @version 1.0 11.05.2011
 * @author Alex Nevsky
 */
class DataSourceDAOFactory extends AbstractDAOFactory {

	private static DataSourceDAOFactory instance;
	private DataSource dataSource;

	/**
	 * Singleton, hide constructor.
	 */
	private DataSourceDAOFactory(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * Returns DataSourceDAOFactory instance.
	 * 
	 * @return Ready-to-use DataSourceDAOFactory instance.
	 */
	public static synchronized DataSourceDAOFactory getInstance(DataSource dataSource) {
		if (instance == null) {
			instance = new DataSourceDAOFactory(dataSource);
		}
		return instance;
	}

	/**
	 * Returns a connection to the database. Package private so that it can be
	 * used inside the DAO package only.
	 * 
	 * @return A connection to the database.
	 * @throws SQLException
	 *             If acquiring the connection fails.
	 */
	@Override
	Connection getConnection() throws SQLException {
		return this.dataSource.getConnection();
	}
}
