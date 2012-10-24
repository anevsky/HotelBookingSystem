package com.alexnevsky.hotel.dao;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

/**
 * Utility class for DAO's. This class contains commonly used DAO logic which is
 * been refactored in single static methods. As far it contains a
 * PreparedStatement values setter, several quiet close methods and a MD5 hasher
 * which conforms under each MySQL own md5() function.
 * 
 * @version 1.0 15.05.2011
 * @author Alex Nevsky
 */
public final class DAOUtil {

	static {
		logger = Logger.getLogger(DAOUtil.class);
	}
	private static Logger logger;

	// Constructors ------------------------------------------------------------

	/**
	 * Utility class, hide constructor.
	 */
	private DAOUtil() {
	}

	// Actions -----------------------------------------------------------------

	/**
	 * Returns a PreparedStatement of the given connection, set with the given
	 * SQL query and the given parameter values.
	 * 
	 * @param connection
	 *            The Connection to create the PreparedStatement from.
	 * @param sql
	 *            The SQL query to construct the PreparedStatement with.
	 * @param returnGeneratedKeys
	 *            Set whether to return generated keys or not.
	 * @param values
	 *            The parameter values to be set in the created PreparedStatement.
	 * @return preparedStatement The PreparedStatement of the given connection.
	 * @throws SQLException
	 *             If something fails during creating the
	 *             PreparedStatement.
	 */
	public static synchronized PreparedStatement prepareStatement(Connection connection, String sql,
			boolean returnGeneratedKeys, Object... values) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement(sql,
				returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
		setValues(preparedStatement, values);
		return preparedStatement;
	}

	/**
	 * Set the given parameter values in the given PreparedStatement.
	 * 
	 * @param preparedStatement
	 * @param values
	 *            The parameter values to be set in the created PreparedStatement.
	 * @throws SQLException
	 *             If something fails during setting the
	 *             PreparedStatement values.
	 */
	public static synchronized void setValues(PreparedStatement preparedStatement, Object... values)
			throws SQLException {
		for (int i = 0; i < values.length; i++) {
			preparedStatement.setObject(i + 1, values[i]);
		}
	}

	/**
	 * Quietly close the Connection. Any errors will be printed to the stderr.
	 * 
	 * @param connection
	 *            The Connection to be closed quietly.
	 */
	public static synchronized void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error("Closing Connection failed: " + e.getMessage());
			}
		}
	}

	/**
	 * Quietly close the Statement. Any errors will be printed to the stderr.
	 * 
	 * @param statement
	 *            The Statement to be closed quietly.
	 */
	public static synchronized void close(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				logger.error("Closing Statement failed: " + e.getMessage());
			}
		}
	}

	/**
	 * Quietly close the ResultSet. Any errors will be printed to the stderr.
	 * 
	 * @param resultSet
	 *            The ResultSet to be closed quietly.
	 */
	public static synchronized void close(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				logger.error("Closing ResultSet failed: " + e.getMessage());
			}
		}
	}

	/**
	 * Quietly close the Connection and Statement. Any errors will be printed to
	 * the stderr.
	 * 
	 * @param connection
	 *            The Connection to be closed quietly.
	 * @param statement
	 *            The Statement to be closed quietly.
	 */
	public static synchronized void close(Connection connection, Statement statement) {
		close(statement);
		close(connection);
	}

	/**
	 * Quietly close the Connection, Statement and ResultSet. Any errors will be
	 * printed to the stderr.
	 * 
	 * @param connection
	 *            The Connection to be closed quietly.
	 * @param statement
	 *            The Statement to be closed quietly.
	 * @param resultSet
	 *            The ResultSet to be closed quietly.
	 */
	public static synchronized void close(Connection connection, Statement statement, ResultSet resultSet) {
		close(resultSet);
		close(statement);
		close(connection);
	}

	/**
	 * Generate MD5 hash for the given String. MD5 is kind of an one-way encryption.
	 * Very useful for hashing passwords before saving in database. This function
	 * generates exactly the same hash as MySQL's own md5() function should do.
	 * 
	 * @param string
	 *            The String to generate the MD5 hash for.
	 * @return The 32-char hexadecimal MD5 hash of the given String.
	 */
	public static synchronized String hashMD5(String string) {
		byte[] hash;

		try {
			hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			// Unexpected exception. "MD5" is just hardcoded and supported.
			throw new RuntimeException("MD5 should be supported?", e);
		} catch (UnsupportedEncodingException e) {
			// Unexpected exception. "UTF-8" is just hardcoded and supported.
			throw new RuntimeException("UTF-8 should be supported?", e);
		}

		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xff) < 0x10) {
				hex.append("0");
			}
			hex.append(Integer.toHexString(b & 0xff));
		}
		return hex.toString();
	}

}