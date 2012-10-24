package com.alexnevsky.hotel.dao;

import static com.alexnevsky.hotel.dao.DAOUtil.close;
import static com.alexnevsky.hotel.dao.DAOUtil.prepareStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.alexnevsky.hotel.dao.exception.DAOException;
import com.alexnevsky.hotel.model.Form;
import com.alexnevsky.hotel.model.enums.RoomClassEnum;

/**
 * This class represents a SQL Database Access Object for the {@link Form} DTO.
 * This DAO should be used as a central point for the mapping between the Form
 * DTO and a SQL database.
 * 
 * @version 1.0 15.05.2011
 * @author Alex Nevsky
 */
public class FormDAO {

	// Constants ---------------------------------------------------------------

	private static final String SQL_INSERT = "INSERT INTO `Form` (`id`, `adult`, `child`, `roomClass`, `arrival`, `nights`, `commentary`) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_FIND_BY_ID = "SELECT `id`, `adult`, `child`, `roomClass`, `arrival`, `nights`, `commentary` FROM `Form` WHERE `id` = ?";
	private static final String SQL_DELETE = "DELETE FROM `Form` WHERE `id` = ?";

	// Vars --------------------------------------------------------------------

	private AbstractDAOFactory daoFactory;

	// Constructors ------------------------------------------------------------

	/**
	 * Construct an Form DAO for the given AbstractDAOFactory. Package private
	 * so that it can be constructed inside the DAO package only.
	 * 
	 * @param daoFactory
	 *            The AbstractDAOFactory to construct this Form DAO for.
	 */
	FormDAO(AbstractDAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	// Actions -----------------------------------------------------------------

	/**
	 * Returns the Form from the database matching the given ID, otherwise null.
	 * 
	 * @param id
	 *            The ID of the Form to be returned.
	 * @return The Form from the database matching the given ID, otherwise null.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	public Form find(Long id) throws DAOException {
		return this.find(SQL_FIND_BY_ID, id);
	}

	/**
	 * Returns the Form from the database matching the given SQL query with the
	 * given values.
	 * 
	 * @param sql
	 *            The SQL query to be executed in the database.
	 * @param values
	 *            The PreparedStatement values to be set.
	 * @return The Form from the database matching the given SQL query with the
	 *         given values.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	private Form find(String sql, Object... values) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Form form = null;

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				form = mapForm(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, resultSet);
		}

		return form;
	}

	/**
	 * Create the given Form in the database. The Form ID must be null,
	 * otherwise it will throw IllegalArgumentException.
	 * After creating, the DAO will set the obtained ID in the given Form.
	 * 
	 * @param form
	 *            The Form to be created in the database.
	 * @throws IllegalArgumentException
	 *             If the Form ID is not null.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	public void create(Form form) throws IllegalArgumentException, DAOException {
		Object[] values = { form.getId(), form.getAdult(), form.getChild(), form.getRoomClass().toString(),
				form.getArrival(), form.getNights(), form.getCommentary() };

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, SQL_INSERT, true, values);
			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException("Creating Form failed, no rows affected.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement);
		}
	}

	/**
	 * Delete the given Form from the database. After deleting, the DAO will set
	 * the ID of the given Form to null.
	 * 
	 * @param form
	 *            The Form to be deleted from the database.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	public void delete(Form form) throws DAOException {
		Object[] values = { form.getId() };

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, SQL_DELETE, false, values);
			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows == 0) {
				throw new DAOException("Deleting Form failed, no rows affected.");
			} else {
				form.setId(null);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement);
		}
	}

	// Helpers -----------------------------------------------------------------

	/**
	 * Map the current row of the given ResultSet to an Form.
	 * 
	 * @param resultSet
	 *            The ResultSet of which the current row is to be mapped
	 *            to an Form.
	 * @return The mapped Form from the current row of the given ResultSet.
	 * @throws SQLException
	 *             If something fails at database level.
	 */
	private static Form mapForm(ResultSet resultSet) throws SQLException {
		return new Form(resultSet.getLong("id"), resultSet.getInt("adult"), resultSet.getInt("child"),
				RoomClassEnum.valueOf(resultSet.getString("roomClass").toUpperCase()), resultSet.getDate("arrival"),
				resultSet.getInt("nights"), resultSet.getString("commentary"));
	}
}
