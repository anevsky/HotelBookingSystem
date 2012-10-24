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
import com.alexnevsky.hotel.model.Order;
import com.alexnevsky.hotel.model.enums.OrderStatusEnum;

/**
 * This class represents a SQL Database Access Object for the {@link Order} DTO.
 * This DAO should be used as a central point for the mapping between the Order
 * DTO and a SQL database.
 * 
 * @version 1.0 15.05.2011
 * @author Alex Nevsky
 */
public class OrderDAO {

	// Constants ---------------------------------------------------------------

	private static final String SQL_FIND_BY_ID = "SELECT `id`, `amount`, `dateCreated`, `status`, `Customer_id`, `Form_id`, `Room_id` FROM `Order` WHERE `id` = ?";
	private static final String SQL_INSERT = "INSERT INTO `Order` (`Customer_id`, `Form_id`) VALUES (?, ?)";
	private static final String SQL_UPDATE_STATUS = "UPDATE `Order` SET `status` = ? WHERE `id` = ?";
	private static final String SQL_UPDATE_ROOM_AMOUNT = "UPDATE `Order` SET `Room_id` = ?, `amount` = ? WHERE `id` = ?";
	private static final String SQL_LIST_ORDER_BY_ID_DESC = "SELECT `id`, `amount`, `dateCreated`, `status`, `Customer_id`, `Form_id`, `Room_id` FROM `Order` ORDER BY `status`, `id` DESC";
	private static final String SQL_LIST_WHERE_CUSTOMER_ID = "SELECT `id`, `amount`, `dateCreated`, `status`, `Customer_id`, `Form_id`, `Room_id` FROM `Order` WHERE `Customer_id` = ? ORDER BY `status`, `id` DESC";
	private static final String SQL_LIST_WHERE_ROOM_ID_AND_STATUS = "SELECT `id`, `amount`, `dateCreated`, `status`, `Customer_id`, `Form_id`, `Room_id` FROM `Order` WHERE `Room_id` = ? and `status` = ?";
	private static final String SQL_DELETE = "DELETE FROM `Order` WHERE `id` = ?";

	// Vars --------------------------------------------------------------------

	private AbstractDAOFactory daoFactory;

	// Constructors ------------------------------------------------------------

	/**
	 * Construct an Order DAO for the given AbstractDAOFactory. Package private
	 * so that it can be constructed inside the DAO package only.
	 * 
	 * @param daoFactory
	 *            The AbstractDAOFactory to construct this Order DAO for.
	 */
	public OrderDAO(AbstractDAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	// Actions -----------------------------------------------------------------

	/**
	 * Returns the Order from the database matching the given ID, otherwise null.
	 * 
	 * @param id
	 *            The ID of the Order to be returned.
	 * @return The Order from the database matching the given ID, otherwise null.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	public Order find(Long id) throws DAOException {
		return this.find(SQL_FIND_BY_ID, id);
	}

	/**
	 * Returns the Order from the database matching the given SQL query with the
	 * given values.
	 * 
	 * @param sql
	 *            The SQL query to be executed in the database.
	 * @param values
	 *            The PreparedStatement values to be set.
	 * @return The Order from the database matching the given SQL query with the
	 *         given values.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	private Order find(String sql, Object... values) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Order order = null;

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				order = mapOrder(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, resultSet);
		}

		return order;
	}

	/**
	 * Create the given Order in the database. The Order ID must be null,
	 * otherwise it will throw IllegalArgumentException.
	 * After creating, the DAO will set the obtained ID in the given Order.
	 * 
	 * @param order
	 *            The Order to be created in the database.
	 * @throws IllegalArgumentException
	 *             If the Order ID is not null.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	public void create(Order order) throws IllegalArgumentException, DAOException {
		if (order.getId() != null) {
			throw new IllegalArgumentException("Order is already created, the Order ID is not null.");
		}

		Object[] values = { order.getCustomerId(), order.getFormId() };

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet generatedKeys = null;

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, SQL_INSERT, true, values);
			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException("Creating Order failed, no rows affected.");
			}
			generatedKeys = preparedStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				order.setId(generatedKeys.getLong(1));
			} else {
				throw new DAOException("Creating Order failed, no generated key obtained.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, generatedKeys);
		}
	}

	/**
	 * Update the Order status in the database.
	 * 
	 * @param status
	 *            OrderStatusEnum.
	 * @param id
	 *            Order id.
	 * @throws DAOException
	 */
	public void update(OrderStatusEnum status, Long id) throws DAOException {
		this.update(SQL_UPDATE_STATUS, status.toString(), id);
	}

	/**
	 * Update the Order Room Id and Amount in the database.
	 * 
	 * @param roomId
	 *            Room id.
	 * @param amount
	 *            Order amount.
	 * @param id
	 *            Order id.
	 * @throws DAOException
	 */
	public void update(Long roomId, Double amount, Long id) throws DAOException {
		this.update(SQL_UPDATE_ROOM_AMOUNT, roomId, amount, id);
	}

	/**
	 * Update the Order in the database. The Order ID must not be null,
	 * otherwise it will throw IllegalArgumentException.
	 * 
	 * @param sql
	 * @param values
	 * @throws IllegalArgumentException
	 *             If the Order ID is null.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	public void update(String sql, Object... values) throws DAOException {
		if (values[values.length - 1] == null) {
			throw new IllegalArgumentException("Order is not created yet, the Order ID is null.");
		}

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, sql, false, values);
			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException("Updating Order failed, no rows affected.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement);
		}
	}

	/**
	 * Returns a list of all orders from the database ordered by ID.
	 * The list is never null and is empty when the database does not contain
	 * any entities.
	 * 
	 * @return A list of all orders from the database ordered by ID.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	public List<Order> list() throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Order> orders = new ArrayList<Order>();

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = connection.prepareStatement(SQL_LIST_ORDER_BY_ID_DESC);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				orders.add(mapOrder(resultSet));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, resultSet);
		}

		return orders;
	}

	/**
	 * Returns a list of all orders from the database WHERE `Customer_id` = ?.
	 * The list is never null and is empty when the database does not contain
	 * any entities.
	 * 
	 * @param customerId
	 * @return A list of all orders from the database
	 *         WHERE `Customer_id` = ?.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	public List<Order> listWhereCustomer(Long customerId) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Order> orders = new ArrayList<Order>();

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, SQL_LIST_WHERE_CUSTOMER_ID, false, customerId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				orders.add(mapOrder(resultSet));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, resultSet);
		}

		return orders;
	}

	/**
	 * Returns a list of all orders from the database WHERE `Room_id` = ?.
	 * The list is never null and is empty when the database does not contain
	 * any entities.
	 * 
	 * @param roomId
	 * @param status
	 * @return A list of all orders from the database
	 *         WHERE `Room_id` = ?.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	public List<Order> listWhereRoomAndStatus(Long roomId, OrderStatusEnum status) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Order> orders = new ArrayList<Order>();

		Object[] values = { roomId, status.toString() };

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, SQL_LIST_WHERE_ROOM_ID_AND_STATUS, false, values);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				orders.add(mapOrder(resultSet));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, resultSet);
		}

		return orders;
	}

	/**
	 * Delete the given Order from the database. After deleting, the DAO will set
	 * the ID of the given Order to null.
	 * 
	 * @param order
	 *            The Order to be deleted from the database.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	public void delete(Order order) throws DAOException {
		Object[] values = { order.getId() };

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, SQL_DELETE, false, values);
			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException("Deleting Order failed, no rows affected.");
			} else {
				order.setId(null);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement);
		}
	}

	// Helpers -----------------------------------------------------------------

	/**
	 * Map the current row of the given ResultSet to an Order.
	 * 
	 * @param resultSet
	 *            The ResultSet of which the current row is to be mapped
	 *            to an Order.
	 * @return The mapped Order from the current row of the given ResultSet.
	 * @throws SQLException
	 *             If something fails at database level.
	 */
	private static Order mapOrder(ResultSet resultSet) throws SQLException {
		return new Order(resultSet.getLong("id"), resultSet.getDouble("amount"), resultSet.getDate("dateCreated"),
				OrderStatusEnum.valueOf(resultSet.getString("status").toUpperCase()), resultSet.getLong("Customer_id"),
				resultSet.getLong("Form_id"), resultSet.getLong("Room_id"));
	}
}
