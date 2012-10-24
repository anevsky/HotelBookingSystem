package com.alexnevsky.hotel.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.alexnevsky.hotel.dao.exception.DAOConfigurationException;

/**
 * This class represents a DAO factory for a SQL database.
 * You can obtain DAO's for the DAO factory instance using the DAO getters.
 * This class requires a properties file named 'dao.properties' in the classpath
 * with under each the following properties:
 * 
 * <pre>
 * name.url
 * </pre>
 * 
 * The 'name.url' must represent either the JNDI name of the database.
 * 
 * @version 1.0 11.05.2011
 * @author Alex Nevsky
 */
public abstract class AbstractDAOFactory {

	// Constants ---------------------------------------------------------------

	private static AbstractDAOFactory instance;
	private static final String PROPERTY_URL = "url";
	private static final String JNDI_ROOT = "java:comp/env/";

	// Actions -----------------------------------------------------------------

	/**
	 * Returns a new AbstractDAOFactory instance for the given database name.
	 * 
	 * @param name
	 *            The database name to return a new AbstractDAOFactory instance for.
	 * @return A new AbstractDAOFactory instance for the given database name.
	 * @throws DAOConfigurationException
	 *             If the database name is null, or if the
	 *             properties file is missing in the classpath or cannot be loaded, or if a
	 *             required property is missing in the properties file, or if the datasource
	 *             cannot be found.
	 */
	public static synchronized AbstractDAOFactory getInstance(String name) throws DAOConfigurationException {
		if (instance == null) {
			if (name != null) {
				DAOProperties properties = new DAOProperties(name);
				String url = properties.getProperty(PROPERTY_URL, true);
				// URL as DataSource URL and lookup it in the JNDI.
				DataSource dataSource;
				try {
					dataSource = (DataSource) new InitialContext().lookup(JNDI_ROOT + url);
				} catch (NamingException e) {
					throw new DAOConfigurationException("DataSource '" + url + "' is missing in JNDI.", e);
				}
				DataSourceDAOFactory dataSourceDAOFactory = DataSourceDAOFactory.getInstance(dataSource);
				instance = dataSourceDAOFactory;
			} else {
				throw new DAOConfigurationException("Database name is null.");
			}
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
	abstract Connection getConnection() throws SQLException;

	// DAO getters -------------------------------------------------------------

	// Возможно, можно обойтись без synchronized

	/**
	 * Returns the Admin DAO associated with the current AbstractDAOFactory.
	 * 
	 * @return The Admin DAO associated with the current AbstractDAOFactory.
	 */
	public synchronized AdminDAO getAdminDAO() {
		return new AdminDAO(this);
	}

	/**
	 * Returns the Customer DAO associated with the current AbstractDAOFactory.
	 * 
	 * @return The Customer DAO associated with the current AbstractDAOFactory.
	 */
	public synchronized CustomerDAO getCustomerDAO() {
		return new CustomerDAO(this);
	}

	/**
	 * Returns the Customer Account DAO associated with the current AbstractDAOFactory.
	 * 
	 * @return The Customer Account DAO associated with the current AbstractDAOFactory.
	 */
	public synchronized CustomerAccountDAO getCustomerAccountDAO() {
		return new CustomerAccountDAO(this);
	}

	/**
	 * Returns the Form DAO associated with the current AbstractDAOFactory.
	 * 
	 * @return The Form DAO associated with the current AbstractDAOFactory.
	 */
	public synchronized FormDAO getFormDAO() {
		return new FormDAO(this);
	}

	/**
	 * Returns the Order DAO associated with the current AbstractDAOFactory.
	 * 
	 * @return The Order DAO associated with the current AbstractDAOFactory.
	 */
	public synchronized OrderDAO getOrderDAO() {
		return new OrderDAO(this);
	}

	/**
	 * Returns the Room DAO associated with the current AbstractDAOFactory.
	 * 
	 * @return The Room DAO associated with the current AbstractDAOFactory.
	 */
	public synchronized RoomDAO getRoomDAO() {
		return new RoomDAO(this);
	}

	/**
	 * Returns the Credit Card DAO associated with the current AbstractDAOFactory.
	 * 
	 * @return The Credit Card DAO associated with the current AbstractDAOFactory.
	 */
	public synchronized CreditCardDAO getCreditCardDAO() {
		return new CreditCardDAO(this);
	}

	// You can add more DAO getters here.
}
