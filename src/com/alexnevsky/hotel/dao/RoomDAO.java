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
import com.alexnevsky.hotel.model.Room;
import com.alexnevsky.hotel.model.enums.RoomClassEnum;

/**
 * This class represents a SQL Database Access Object for the {@link Room} DTO.
 * This DAO should be used as a central point for the mapping between the Room
 * DTO and a SQL database.
 * 
 * @version 1.0 15.05.2011
 * @author Alex Nevsky
 */
public class RoomDAO {

	// Constants ---------------------------------------------------------------

	private static final String SQL_FIND_BY_ID = "SELECT `id`, `adultMax`, `childMax`, `class`, `nightPrice` FROM `Room` WHERE `id` = ?";
	private static final String SQL_LIST_ORDER_BY_ID = "SELECT `id`, `adultMax`, `childMax`, `class`, `nightPrice` FROM `Room` ORDER BY `id`";
	private static final String SQL_LIST_WHERE_ADULT_CHILD_CLASS = "SELECT `id`, `adultMax`, `childMax`, `class`, `nightPrice` FROM `Room` WHERE `adultMax` >= ? AND `childMax` >= ? AND `class` = ?";

	// Vars --------------------------------------------------------------------

	private AbstractDAOFactory daoFactory;

	// Constructors ------------------------------------------------------------

	/**
	 * Construct an Room DAO for the given AbstractDAOFactory. Package private
	 * so that it can be constructed inside the DAO package only.
	 * 
	 * @param daoFactory
	 *            The AbstractDAOFactory to construct this Room DAO for.
	 */
	public RoomDAO(AbstractDAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	// Actions -----------------------------------------------------------------

	/**
	 * Returns the Room from the database matching the given ID, otherwise null.
	 * 
	 * @param id
	 *            The ID of the Room to be returned.
	 * @return The Room from the database matching the given ID, otherwise null.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	public Room find(Long id) throws DAOException {
		return this.find(SQL_FIND_BY_ID, id);
	}

	/**
	 * Returns the Room from the database matching the given SQL query with the
	 * given values.
	 * 
	 * @param sql
	 *            The SQL query to be executed in the database.
	 * @param values
	 *            The PreparedStatement values to be set.
	 * @return The Room from the database matching the given SQL query with the
	 *         given values.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	private Room find(String sql, Object... values) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Room room = null;

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				room = mapRoom(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, resultSet);
		}

		return room;
	}

	/**
	 * Returns a list of all rooms from the database ordered by Room ID. The
	 * list is never null and is empty when the database does not contain any Room.
	 * 
	 * @return A list of all rooms from the database ordered by Room ID.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	public List<Room> list() throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Room> rooms = new ArrayList<Room>();

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = connection.prepareStatement(SQL_LIST_ORDER_BY_ID);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				rooms.add(mapRoom(resultSet));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, resultSet);
		}

		return rooms;
	}

	/**
	 * Returns a list of all rooms from the database
	 * WHERE `adultMax` = ?, `childMax` = ?, `class` = ?.
	 * The list is never null and is empty when the database does not contain
	 * any entities.
	 * 
	 * @param adult
	 * @param child
	 * @param roomClass
	 * @return A list of all rooms from the database
	 *         where WHERE `adultMax` = ?, `childMax` = ?, `class` = ?.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	public List<Room> list(Integer adult, Integer child, RoomClassEnum roomClass) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Room> rooms = new ArrayList<Room>();

		Object[] values = { adult, child, roomClass.toString() };

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, SQL_LIST_WHERE_ADULT_CHILD_CLASS, false, values);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				rooms.add(mapRoom(resultSet));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, resultSet);
		}

		return rooms;
	}

	// Helpers -----------------------------------------------------------------

	/**
	 * Map the current row of the given ResultSet to an Room.
	 * 
	 * @param resultSet
	 *            The ResultSet of which the current row is to be mapped
	 *            to an Room.
	 * @return The mapped Room from the current row of the given ResultSet.
	 * @throws SQLException
	 *             If something fails at database level.
	 */
	private static Room mapRoom(ResultSet resultSet) throws SQLException {
		return new Room(resultSet.getLong("id"), resultSet.getInt("adultMax"), resultSet.getInt("childMax"),
				RoomClassEnum.valueOf(resultSet.getString("class").toUpperCase()), resultSet.getDouble("nightPrice"));
	}
}
