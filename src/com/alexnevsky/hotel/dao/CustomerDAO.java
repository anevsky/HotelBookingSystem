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
import com.alexnevsky.hotel.model.Customer;

/**
 * This class represents a SQL Database Access Object for the {@link Customer} DTO.
 * This DAO should be used as a central point for the mapping between the Admin
 * DTO and a SQL database.
 * 
 * @version 1.0 15.05.2011
 * @author Alex Nevsky
 */
public final class CustomerDAO {

	// Constants ---------------------------------------------------------------

	private static final String SQL_FIND_BY_ID = "SELECT `id`, `firstName`, `lastName`, `email`, `phone`, `address`, `CreditCard_number` FROM `Customer` WHERE `id` = ?";
	private static final String SQL_LIST_ORDER_BY_ID = "SELECT `id`, `firstName`, `lastName`, `email`, `phone`, `address`, `CreditCard_number` FROM `Customer` ORDER BY `id`";

	// Vars --------------------------------------------------------------------

	private AbstractDAOFactory daoFactory;

	// Constructors ------------------------------------------------------------

	/**
	 * Construct an Customer DAO for the given AbstractDAOFactory.
	 * Package private so that it can be constructed inside the DAO package only.
	 * 
	 * @param daoFactory
	 *            The AbstractDAOFactory to construct this Customer DAO for.
	 */
	CustomerDAO(AbstractDAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	// Actions -----------------------------------------------------------------

	/**
	 * Returns the Customer from the database matching the given ID,
	 * otherwise null.
	 * 
	 * @param id
	 *            The ID of the Customer to be returned.
	 * @return The Customer from the database matching the given ID,
	 *         otherwise null.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	public Customer find(Long id) throws DAOException {
		return this.find(SQL_FIND_BY_ID, id);
	}

	/**
	 * Returns the Customer from the database matching the given SQL query
	 * with the given values.
	 * 
	 * @param sql
	 *            The SQL query to be executed in the database.
	 * @param values
	 *            The PreparedStatement values to be set.
	 * @return The Customer from the database matching the given SQL query
	 *         with thegiven values.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	private Customer find(String sql, Object... values) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Customer customer = null;

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				customer = mapCustomer(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, resultSet);
		}

		return customer;
	}

	/**
	 * Returns a list of all users from the database ordered by Customer ID. The
	 * list is never null and is empty when the database does not contain any Customer.
	 * 
	 * @return A list of all users from the database ordered by Customer ID.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	public List<Customer> list() throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Customer> users = new ArrayList<Customer>();

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = connection.prepareStatement(SQL_LIST_ORDER_BY_ID);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				users.add(mapCustomer(resultSet));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, resultSet);
		}

		return users;
	}

	// Helpers -----------------------------------------------------------------

	/**
	 * Map the current row of the given ResultSet to an Customer.
	 * 
	 * @param resultSet
	 *            The ResultSet of which the current row is to be mapped
	 *            to an Customer.
	 * @return The mapped Customer from the current row of the given ResultSet.
	 * @throws SQLException
	 *             If something fails at database level.
	 */
	private static Customer mapCustomer(ResultSet resultSet) throws SQLException {
		return new Customer(resultSet.getLong("id"), resultSet.getString("firstName"), resultSet.getString("lastName"),
				resultSet.getString("email"), resultSet.getString("phone"), resultSet.getString("address"),
				resultSet.getLong("CreditCard_number"));
	}
}
