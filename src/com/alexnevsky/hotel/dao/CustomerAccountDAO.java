package com.alexnevsky.hotel.dao;

import static com.alexnevsky.hotel.dao.DAOUtil.close;
import static com.alexnevsky.hotel.dao.DAOUtil.prepareStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alexnevsky.hotel.dao.exception.DAOException;
import com.alexnevsky.hotel.model.CustomerAccount;

/**
 * This class represents a SQL Database Access Object for the {@link CustomerAccount} DTO. This DAO should be used as a central point for
 * the mapping between the CustomerAccount DTO and a SQL database.
 * 
 * @version 1.0 15.05.2011
 * @author Alex Nevsky
 */
public final class CustomerAccountDAO {

	// Constants ---------------------------------------------------------------
	private static final String SQL_FIND_BY_ID = "SELECT `id`, `login`, `password` FROM `CustomerAccount` WHERE `id` = ?";
	private static final String SQL_FIND_BY_LOGIN_AND_PASSWORD = "SELECT `id`, `login`, `password` FROM `CustomerAccount` WHERE `login` = ? AND `password` = ?";
	private static final String SQL_LIST_ORDER_BY_ID = "SELECT `id`, `login`, `password` FROM `CustomerAccount` ORDER BY `id`";
	private static final String SQL_INSERT = "INSERT INTO `CustomerAccount` (`login`, `password`) VALUES (?, ?)";
	private static final String SQL_UPDATE = "UPDATE `CustomerAccount` SET `login` = ?, `password` = ? WHERE `id` = ?";
	private static final String SQL_DELETE = "DELETE FROM `CustomerAccount` WHERE `id` = ?";
	private static final String SQL_EXIST_LOGIN = "SELECT `id` FROM `CustomerAccount` WHERE `login` = ?";
	private static final String SQL_EXIST_LOGIN_AND_PASSWORD = "SELECT `id` FROM `CustomerAccount` WHERE `login` = ? AND `password` = ?";

	// Vars --------------------------------------------------------------------

	private AbstractDAOFactory daoFactory;

	// Constructors ------------------------------------------------------------

	/**
	 * Construct an CustomerAccount DAO for the given AbstractDAOFactory.
	 * Package private so that it can be constructed inside the DAO package only.
	 * 
	 * @param daoFactory
	 *            The AbstractDAOFactory to construct this CustomerAccount DAO for.
	 */
	CustomerAccountDAO(AbstractDAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	// Actions -----------------------------------------------------------------

	/**
	 * Returns the CustomerAccount from the database matching the given ID,
	 * otherwise null.
	 * 
	 * @param id
	 *            The ID of the CustomerAccount to be returned.
	 * @return The CustomerAccount from the database matching the given ID,
	 *         otherwise null.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	public CustomerAccount find(Long id) throws DAOException {
		return this.find(SQL_FIND_BY_ID, id);
	}

	/**
	 * Returns the CustomerAccount from the database matching the given login and
	 * password, otherwise null.
	 * 
	 * @param login
	 *            The login of the CustomerAccount to be returned.
	 * @param password
	 *            The password of the CustomerAccount to be returned.
	 * @return The CustomerAccount from the database matching the given login and
	 *         password, otherwise null.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	public CustomerAccount find(String login, String password) throws DAOException {
		return this.find(SQL_FIND_BY_LOGIN_AND_PASSWORD, login, hashCodeIfNecessary(password));
	}

	/**
	 * Returns the CustomerAccount from the database matching the given SQL
	 * query with the given values.
	 * 
	 * @param sql
	 *            The SQL query to be executed in the database.
	 * @param values
	 *            The PreparedStatement values to be set.
	 * @return The CustomerAccount from the database matching the given SQL
	 *         query with the given values.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	private CustomerAccount find(String sql, Object... values) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		CustomerAccount customerAccount = null;

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				customerAccount = mapCustomerAccount(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, resultSet);
		}

		return customerAccount;
	}

	/**
	 * Returns a list of all customer accounts from the database ordered
	 * by CustomerAccount ID. The list is never null and is empty when
	 * the database does not contain any CustomerAccount.
	 * 
	 * @return A list of all users from the database ordered
	 *         by CustomerAccount ID.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	public List<CustomerAccount> list() throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<CustomerAccount> users = new ArrayList<CustomerAccount>();

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = connection.prepareStatement(SQL_LIST_ORDER_BY_ID);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				users.add(mapCustomerAccount(resultSet));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, resultSet);
		}

		return users;
	}

	/**
	 * Create the given CustomerAccount in the database. The CustomerAccount ID
	 * must be null, otherwise it will throw IllegalArgumentException. If the
	 * CustomerAccount ID value is unknown, rather use {@link #save(CustomerAccount)}.
	 * After creating, the DAO will set the obtained ID in the given CustomerAccount.
	 * 
	 * @param customerAccount
	 *            The CustomerAccount to be created in the database.
	 * @throws IllegalArgumentException
	 *             If the CustomerAccount ID is not null.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	public void create(CustomerAccount customerAccount) throws IllegalArgumentException, DAOException {
		if (customerAccount.getId() != null) {
			throw new IllegalArgumentException(
					"CustomerAccount is already created, the CustomerAccount ID is not null.");
		}

		Object[] values = { customerAccount.getLogin(), hashCodeIfNecessary(customerAccount.getPassword()) };

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet generatedKeys = null;

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, SQL_INSERT, true, values);
			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException("Creating CustomerAccount failed, no rows affected.");
			}
			generatedKeys = preparedStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				customerAccount.setId(generatedKeys.getLong(1));
			} else {
				throw new DAOException("Creating CustomerAccount failed, no generated key obtained.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, generatedKeys);
		}
	}

	/**
	 * Update the given CustomerAccount in the database. The CustomerAccount ID
	 * must not be null, otherwise it will throw IllegalArgumentException. If
	 * the CustomerAccount ID value is unknown, rather use {@link #save(CustomerAccount)}.
	 * 
	 * @param customerAccount
	 *            The CustomerAccount to be updated in the database.
	 * @throws IllegalArgumentException
	 *             If the CustomerAccount ID is null.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	public void update(CustomerAccount customerAccount) throws DAOException {
		if (customerAccount.getId() == null) {
			throw new IllegalArgumentException("CustomerAccount is not created yet, the CustomerAccount ID is null.");
		}

		Object[] values = { customerAccount.getLogin(), hashCodeIfNecessary(customerAccount.getPassword()),
				customerAccount.getId() };

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, SQL_UPDATE, false, values);
			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException("Updating CustomerAccount failed, no rows affected.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement);
		}
	}

	/**
	 * Save the given CustomerAccount in the database. If the CustomerAccount ID
	 * is null, then it will invoke {@link #create(CustomerAccount)}, else it
	 * will invoke {@link #update(CustomerAccount)}.
	 * 
	 * @param customerAccount
	 *            The CustomerAccount to be saved in the database.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	public void save(CustomerAccount customerAccount) throws DAOException {
		if (customerAccount.getId() == null) {
			this.create(customerAccount);
		} else {
			this.update(customerAccount);
		}
	}

	/**
	 * Delete the given CustomerAccount from the database. After deleting, the
	 * DAO will set the ID of the given CustomerAccount to null.
	 * 
	 * @param customerAccount
	 *            The CustomerAccount to be deleted from the database.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	public void delete(CustomerAccount customerAccount) throws DAOException {
		Object[] values = { customerAccount.getId() };

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, SQL_DELETE, false, values);
			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException("Deleting CustomerAccount failed, no rows affected.");
			} else {
				customerAccount.setId(null);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement);
		}
	}

	/**
	 * Returns true if the given login exist in the database.
	 * 
	 * @param login
	 *            The login which is to be checked in the database.
	 * @return True if the given login exist in the database.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	public boolean existCustomerAccountLogin(String login) throws DAOException {
		return this.exist(SQL_EXIST_LOGIN, login);
	}

	/**
	 * Returns true if the given login and password exist in the database.
	 * 
	 * @param login
	 *            The login which is to be checked in the database.
	 * @param password
	 *            The password which is to be checked in the database.
	 * @return True if the given login exist in the database.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	public boolean existCustomerAccount(String login, String password) throws DAOException {
		return this.exist(SQL_EXIST_LOGIN_AND_PASSWORD, login, hashCodeIfNecessary(password));
	}

	/**
	 * Returns true if the given SQL query with the given values returns at
	 * least one row.
	 * 
	 * @param sql
	 *            The SQL query to be executed in the database.
	 * @param values
	 *            The PreparedStatement values to be set.
	 * @return True if the given SQL query with the given values returns at
	 *         least one row.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	private boolean exist(String sql, Object... values) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		boolean exist = false;

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			exist = resultSet.next();
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, resultSet);
		}

		return exist;
	}

	// Helpers -----------------------------------------------------------------

	/**
	 * Generate hash code for the given password if necessary. That is, if it is
	 * not already hashed.
	 * 
	 * @param password
	 *            The password to generate a hash for if necessary.
	 * @return The hash of the given password or the same value if it is already
	 *         hashed.
	 */
	private static Long hashCodeIfNecessary(String password) {
		return !"^((-)?[0-9]*)".matches(password) ? password.hashCode() : new Long(password);
	}

	/**
	 * Map the current row of the given ResultSet to an CustomerAccount.
	 * 
	 * @param resultSet
	 *            The ResultSet of which the current row is to be mapped to an
	 *            CustomerAccount.
	 * @return The mapped CustomerAccount from the current row of the given ResultSet.
	 * @throws SQLException
	 *             If something fails at database level.
	 */
	private static CustomerAccount mapCustomerAccount(ResultSet resultSet) throws SQLException {
		return new CustomerAccount(resultSet.getLong("id"), resultSet.getString("login"),
				resultSet.getString("password"));
	}
}