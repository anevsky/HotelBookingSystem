package com.alexnevsky.hotel.model;

/**
 * This class represents a Data Transfer Object for the CustomerAccount. This
 * DTO can be used thoroughout all layers, the data layer, the controller
 * layer and the view layer.
 * 
 * @version 1.0 10.05.2011
 * @author Alex Nevsky
 */
public class CustomerAccount implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String login;
	private String password;

	/**
	 * Default constructor.
	 */
	public CustomerAccount() {
	}

	/**
	 * Full constructor.
	 * 
	 * @param id
	 *            The ID of this CustomerAccount. Set it to null in case of a
	 *            new and unexisting customerAccount.
	 * @param login
	 *            The login of this CustomerAccount.
	 * @param password
	 *            The password of this CustomerAccount.
	 */
	public CustomerAccount(Long id, String login, String password) {
		this.id = id;
		this.login = login;
		this.password = password;
	}

	/**
	 * Returns the ID of this CustomerAccount.
	 * 
	 * @return The ID of this CustomerAccount.
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * Returns the adminname of this CustomerAccount.
	 * 
	 * @return The adminname of this CustomerAccount.
	 */
	public String getLogin() {
		return this.login;
	}

	/**
	 * Returns the password of this CustomerAccount.
	 * 
	 * @return The password of this CustomerAccount.
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Sets the ID of this CustomerAccount.
	 * 
	 * @param id
	 *            The ID of this CustomerAccount.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Sets the adminname of this CustomerAccount.
	 * 
	 * @param login
	 *            The adminname of this CustomerAccount.
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Sets the password of this CustomerAccount.
	 * 
	 * @param password
	 *            The password of this CustomerAccount.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * The customerAccount ID is unique for each CustomerAccount.
	 * So this should compare CustomerAccount by ID only.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		return (other instanceof CustomerAccount) && (this.id != null) ? this.id.equals(((CustomerAccount) other).id)
				: (other == this);
	}

	/**
	 * The customerAccount ID is unique for each CustomerAccount.
	 * So CustomerAccount with same ID should return same hashcode.
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (this.id != null) ? (this.getClass().hashCode() + this.id.hashCode()) : super.hashCode();
	}

	/**
	 * Returns the String representation of this CustomerAccount.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CustomerAccount{" + "id=" + this.id + ", login=" + this.login + ", password=" + this.password + '}';
	}

}
