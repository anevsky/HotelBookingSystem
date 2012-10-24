package com.alexnevsky.hotel.dao;

import static com.alexnevsky.hotel.dao.DAOUtil.close;
import static com.alexnevsky.hotel.dao.DAOUtil.prepareStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.alexnevsky.hotel.dao.exception.DAOException;
import com.alexnevsky.hotel.model.CreditCard;
import com.alexnevsky.hotel.model.enums.CreditCardTypeEnum;

/**
 * This class represents a SQL Database Access Object for
 * the {@link CreditCard} DTO.
 * This DAO should be used as a central point for the mapping between
 * the CreditCard DTO and a SQL database.
 * 
 * @version 1.0 15.05.2011
 * @author Alex Nevsky
 */
public class CreditCardDAO {

	// Constants ---------------------------------------------------------------

	private static final String SQL_FIND_BY_ID = "SELECT `number`, `holderName`, `expiryDate`, `type` FROM `CreditCard` WHERE `number` = ?";

	// Vars --------------------------------------------------------------------

	private AbstractDAOFactory daoFactory;

	// Constructors ------------------------------------------------------------

	/**
	 * Construct an CreditCardDAO for the given AbstractDAOFactory.
	 * Package private so that it can be constructed inside the DAO package only.
	 * 
	 * @param daoFactory
	 *            The AbstractDAOFactory to construct this CreditCardDAO for.
	 */
	public CreditCardDAO(AbstractDAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	// Actions -----------------------------------------------------------------

	/**
	 * Returns the CreditCard from the database matching the given ID,
	 * otherwise null.
	 * 
	 * @param id
	 *            The ID of the CreditCard to be returned.
	 * @return The CreditCard from the database matching the given ID,
	 *         otherwise null.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	public CreditCard find(Long id) throws DAOException {
		return this.find(SQL_FIND_BY_ID, id);
	}

	/**
	 * Returns the CreditCard from the database matching the given SQL query
	 * with the given values.
	 * 
	 * @param sql
	 *            The SQL query to be executed in the database.
	 * @param values
	 *            The PreparedStatement values to be set.
	 * @return The CreditCard from the database matching the given SQL query
	 *         with the given values.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	private CreditCard find(String sql, Object... values) throws DAOException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		CreditCard creditCard = null;

		try {
			connection = this.daoFactory.getConnection();
			preparedStatement = prepareStatement(connection, sql, false, values);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				creditCard = mapCreditCard(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			close(connection, preparedStatement, resultSet);
		}

		return creditCard;
	}

	// Helpers -----------------------------------------------------------------

	/**
	 * Map the current row of the given ResultSet to an CreditCard.
	 * 
	 * @param resultSet
	 *            The ResultSet of which the current row is to be mapped
	 *            to an CreditCard.
	 * @return The mapped CreditCard from the current row of the given ResultSet.
	 * @throws SQLException
	 *             If something fails at database level.
	 */
	private static CreditCard mapCreditCard(ResultSet resultSet) throws SQLException {
		String cardType = resultSet.getString("type");
		if (cardType.equalsIgnoreCase("Visa")) {
			cardType = "VISA";
		} else if (cardType.equalsIgnoreCase("MasterCard")) {
			cardType = "MASTER_CARD";
		} else if (cardType.equalsIgnoreCase("American Express")) {
			cardType = "AMERICAN_EXPRESS";
		}
		return new CreditCard(resultSet.getLong("number"), resultSet.getString("holderName"),
				resultSet.getDate("expiryDate"), CreditCardTypeEnum.valueOf(cardType));
	}
}
