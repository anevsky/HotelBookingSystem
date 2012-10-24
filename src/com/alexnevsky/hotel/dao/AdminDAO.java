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
import com.alexnevsky.hotel.model.Admin;

/**
 * This class represents a SQL Database Access Object for the {@link Admin} DTO.
 * This DAO should be used as a central point for the mapping between the Admin
 * DTO and a SQL database.
 * 
 * @version 1.0 15.05.2011
 * @author Alex Nevsky
 */
public final class AdminDAO {

	// Constants ---------------------------------------------------------------

	private static final String SQL_FIND_BY_ID = "SELECT `id`, `login`, `password` FROM `Admin` WHERE `id` = ?";
	private static final String SQL_FIND_BY_LOGIN_AND_PASSWORD = "SELECT `id`, `login`, `password` FROM `Admin` WHERE `login` = ? AND `password` = ?";
	private static final String SQL_LIST_ORDER_BY_ID = "SELECT `id`, `login`, `password` FROM `Admin` ORDER BY `id`";
	private static final String SQL_INSERT = "INSERT INTO `Admin` (`login`, `password`) VALUES (?, ?)";
	private static final String SQL_UPDATE = "UPDATE `Admin` SET `login` = ?, `password` = ? WHERE `id` = ?";
	private static final String SQL_DELETE = "DELETE FROM `Admin` WHERE `id` = ?";
	private static final String SQL_EXIST_LOGIN = "SELECT `id` FROM `Admin` WHERE `login` = ?";
	private static final String SQL_EXIST_LOGIN_AND_PASSWORD = "SELECT `id` FROM `Admin` WHERE `login` = ? AND `password` = ?";

	// Vars --------------------------------------------------------------------

	private AbstractDAOFactory daoFactory;

	// Constructors ------------------------------------------------------------

	/**
	 * Construct an Admin DAO for the given AbstractDAOFactory. Package private
	 * so that it can be constructed inside the DAO package only.
	 * 
	 * @param daoFactory
	 *            The AbstractDAOFactory to construct this Admin DAO for.
	 */
	AdminDAO(AbstractDAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	// Actions -----------------------------------------------------------------

	/**
	 * Returns the Admin from the database matching the given ID, otherwise null.
	 * 
	 * @param id
	 *            The ID of the Admin to be returned.
	 * @return The Admin from the database matching the given ID, otherwise null.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	public Admin find(Long id) throws DAOException {
		return this.find(SQL_FIND_BY_ID, id);
	}

	/**
	 * Returns the Admin from the database matching the given login and
	 * password, otherwise null.
	 * 
	 * @param login
	 *            The login of the Admin to be returned.
	 * @param password
	 *            The password of the Admin to be returned.
	 * @return The Admin from the database matching the given login and
	 *         password, otherwise null.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	public Admin find(String login, String password) throws DAOException {
		return this.find(SQL_FIND_BY_LOGIN_AND_PASSWORD, login, hashCodeIfNecessary(password));
	}

	/**
	 * Returns the Admin from the database matching the given SQL query with the
	 * given values.
	 * 
	 * @param sql
	 *            The SQL query to be executed in the database.
	 * @param values
	 *            The PreparedStatement values to be set.
	 * @return The Admin from the database matching the given SQL query with the
	 *         given values.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	private Admin find(String sql, Object... values) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Admin admin = null;

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				admin = mapAdmin(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, resultSet);
		}

		return admin;
	}

	/**
	 * Returns a list of all users from the database ordered by Admin ID. The
	 * list is never null and is empty when the database does not contain any Admin.
	 * 
	 * @return A list of all users from the database ordered by Admin ID.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	public List<Admin> list() throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Admin> users = new ArrayList<Admin>();

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = connection.prepareStatement(SQL_LIST_ORDER_BY_ID);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				users.add(mapAdmin(resultSet));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, resultSet);
		}

		return users;
	}

	/**
	 * Create the given Admin in the database. The Admin ID must be null,
	 * otherwise it will throw IllegalArgumentException. If the Admin ID value is
	 * unknown, rather use {@link #save(Admin)}.
	 * After creating, the DAO will set the obtained ID in the given Admin.
	 * 
	 * @param admin
	 *            The Admin to be created in the database.
	 * @throws IllegalArgumentException
	 *             If the Admin ID is not null.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	public void create(Admin admin) throws IllegalArgumentException, DAOException {
		if (admin.getId() != null) {
			throw new IllegalArgumentException("Admin is already created, the Admin ID is not null.");
		}

		Object[] values = { admin.getLogin(), hashCodeIfNecessary(admin.getPassword()) };

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet generatedKeys = null;

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, SQL_INSERT, true, values);
			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException("Creating Admin failed, no rows affected.");
			}
			generatedKeys = preparedStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				admin.setId(generatedKeys.getLong(1));
			} else {
				throw new DAOException("Creating Admin failed, no generated key obtained.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, generatedKeys);
		}
	}

	/**
	 * Update the given Admin in the database. The Admin ID must not be null,
	 * otherwise it will throw IllegalArgumentException. If the Admin ID value is
	 * unknown, rather use {@link #save(Admin)}.
	 * 
	 * @param admin
	 *            The Admin to be updated in the database.
	 * @throws IllegalArgumentException
	 *             If the Admin ID is null.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	public void update(Admin admin) throws DAOException {
		if (admin.getId() == null) {
			throw new IllegalArgumentException("Admin is not created yet, the Admin ID is null.");
		}

		Object[] values = { admin.getLogin(), hashCodeIfNecessary(admin.getPassword()), admin.getId() };

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, SQL_UPDATE, false, values);
			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException("Updating Admin failed, no rows affected.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement);
		}
	}

	/**
	 * Save the given Admin in the database. If the Admin ID is null, then it will
	 * invoke {@link #create(Admin)}, else it will invoke {@link #update(Admin)}.
	 * 
	 * @param admin
	 *            The Admin to be saved in the database.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	public void save(Admin admin) throws DAOException {
		if (admin.getId() == null) {
			this.create(admin);
		} else {
			this.update(admin);
		}
	}

	/**
	 * Delete the given Admin from the database. After deleting, the DAO will set
	 * the ID of the given Admin to null.
	 * 
	 * @param admin
	 *            The Admin to be deleted from the database.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	public void delete(Admin admin) throws DAOException {
		Object[] values = { admin.getId() };

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, SQL_DELETE, false, values);
			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException("Deleting admin failed, no rows affected.");
			} else {
				admin.setId(null);
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
	public boolean existAdminLogin(String login) throws DAOException {
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
	public boolean existAdmin(String login, String password) throws DAOException {
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
	 * Map the current row of the given ResultSet to an Admin.
	 * 
	 * @param resultSet
	 *            The ResultSet of which the current row is to be mapped
	 *            to an Admin.
	 * @return The mapped Admin from the current row of the given ResultSet.
	 * @throws SQLException
	 *             If something fails at database level.
	 */
	private static Admin mapAdmin(ResultSet resultSet) throws SQLException {
		return new Admin(resultSet.getLong("id"), resultSet.getString("login"), resultSet.getString("password"));
	}
}
