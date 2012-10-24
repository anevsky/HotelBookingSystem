package com.alexnevsky.hotel.commands.general;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.alexnevsky.hotel.commands.ICommand;
import com.alexnevsky.hotel.controller.Controller;
import com.alexnevsky.hotel.dao.AbstractDAOFactory;
import com.alexnevsky.hotel.dao.AdminDAO;
import com.alexnevsky.hotel.dao.CustomerAccountDAO;
import com.alexnevsky.hotel.dao.exception.DAOException;
import com.alexnevsky.hotel.manager.AttributesManager;
import com.alexnevsky.hotel.manager.ConfigurationManager;
import com.alexnevsky.hotel.manager.MessageManager;
import com.alexnevsky.hotel.model.Admin;
import com.alexnevsky.hotel.model.Customer;
import com.alexnevsky.hotel.model.CustomerAccount;

/**
 * This command allows the user to log in.
 * 
 * @version 1.0 11.05.2011
 * @author Alex Nevsky
 */
public class LoginCommand implements ICommand {

	static {
		logger = Logger.getLogger(LoginCommand.class);
	}
	private static Logger logger;

	/**
	 * Logins user in the system by checking his login and password
	 * and created user's session.
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;

		try {
			String login = request.getParameter(AttributesManager.PARAM_NAME_LOGIN);
			String password = request.getParameter(AttributesManager.PARAM_NAME_PASSWORD);

			if (login == null || login.isEmpty() || password == null || password.isEmpty()) {

				logger.info("Unsuccessful attempt to log in." + " Login or password is empty or null"
						+ ". RemoteAddr: " + request.getRemoteAddr());

				page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.LOGIN_PAGE_PATH);

				return page;
			}

			if (this.isAdmin(login, password)) { // for admin
				logger.info("Administrator '" + login + "' successfully logged into the system" + ". RemoteAddr: "
						+ request.getRemoteAddr());

				HttpSession session = request.getSession(true);

				session.setAttribute(AttributesManager.ATTRIBUTE_USER_ROLE, ConfigurationManager.getInstance()
						.getProperty(ConfigurationManager.USER_ROLE_ADMIN));

				session.setAttribute(AttributesManager.ATTRIBUTE_ADMIN, this.getAdmin(login, password));

				session.setAttribute(AttributesManager.ATTRIBUTE_LOGIN, login);
			} else if (this.isCustomer(login, password)) { // for customer
				logger.info("Customer '" + login + "' successfully logged into the system" + ". RemoteAddr: "
						+ request.getRemoteAddr());

				HttpSession session = request.getSession(true);

				session.setAttribute(AttributesManager.ATTRIBUTE_USER_ROLE, ConfigurationManager.getInstance()
						.getProperty(ConfigurationManager.USER_ROLE_CUSTOMER));

				session.setAttribute(AttributesManager.ATTRIBUTE_CUSTOMER, this.getCustomer(login, password));

				session.setAttribute(AttributesManager.PARAM_NAME_LOGIN, login);
			} else { // incorrect login or password
				logger.warn("Unsuccessful attempt to log in with user name" + " '" + login + "'" + ". RemoteAddr: "
						+ request.getRemoteAddr());
				request.setAttribute(AttributesManager.ATTRIBUTE_ERROR_MESSAGE, MessageManager.LOGIN_ERROR_MESSAGE);
				page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
				return page;
			}
		} catch (DAOException ex) {
			logger.error(ex, ex);
			request.setAttribute(AttributesManager.ATTRIBUTE_ERROR_MESSAGE, MessageManager.DAO_EXCEPTION_ERROR_MESSAGE);
			page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.ERROR_PAGE_PATH);
			return page;
		}

		page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.MAIN_PAGE_PATH);

		return page;
	}

	/**
	 * Returns true if the user with given login and password is Admin.
	 * 
	 * @param login
	 *            The login which is to be checked in the database.
	 * @param password
	 *            The password which is to be checked in the database.
	 * @return True if the given user is Admin.
	 */
	public boolean isAdmin(String login, String password) throws DAOException {
		boolean success = false;
		AbstractDAOFactory daoFactory = Controller.getDAOFactory();
		AdminDAO adminDAO = daoFactory.getAdminDAO();
		success = adminDAO.existAdmin(login, password);
		return success;
	}

	/**
	 * Returns true if the user with given login and password is Customer.
	 * 
	 * @param login
	 *            The login which is to be checked in the database.
	 * @param password
	 *            The password which is to be checked in the database.
	 * @return True if the given user is Customer.
	 */
	public boolean isCustomer(String login, String password) throws DAOException {
		boolean success = false;
		AbstractDAOFactory daoFactory = Controller.getDAOFactory();
		CustomerAccountDAO customerAccountDAO = daoFactory.getCustomerAccountDAO();
		success = customerAccountDAO.existCustomerAccount(login, password);
		return success;
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
	public Admin getAdmin(String login, String password) throws DAOException {
		AbstractDAOFactory daoFactory = Controller.getDAOFactory();
		Admin admin = daoFactory.getAdminDAO().find(login, password);
		return admin;
	}

	/**
	 * Returns the Customer from the database matching the given login and
	 * password, otherwise null.
	 * 
	 * @param login
	 *            The login of the Customer to be returned.
	 * @param password
	 *            The password of the Customer to be returned.
	 * @return The Customer from the database matching the given login and
	 *         password, otherwise null.
	 * @throws DAOException
	 *             If something fails at database level.
	 */
	public Customer getCustomer(String login, String password) throws DAOException {
		AbstractDAOFactory daoFactory = Controller.getDAOFactory();
		CustomerAccount customerAccount = daoFactory.getCustomerAccountDAO().find(login, password);
		Customer customer = daoFactory.getCustomerDAO().find(customerAccount.getId());
		return customer;
	}

	@Override
	public String toString() {
		return "LoginCommand{" + '}';
	}
}
