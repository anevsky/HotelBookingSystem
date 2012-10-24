package com.alexnevsky.hotel.dao.exception;

/**
 * This class represents a generic DAO exception. It should wrap any exception
 * of the underlying code, such as SQLExceptions.
 * 
 * @version 1.0 15.05.2011
 * @author Alex Nevsky
 */
public class DAOException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a DAOException with the given detail message.
	 * 
	 * @param message
	 *            The detail message of the DAOException.
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * Constructs a DAOException with the given root cause.
	 * 
	 * @param cause
	 *            The root cause of the DAOException.
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a DAOException with the given detail message and root cause.
	 * 
	 * @param message
	 *            The detail message of the DAOException.
	 * @param cause
	 *            The root cause of the DAOException.
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

}
