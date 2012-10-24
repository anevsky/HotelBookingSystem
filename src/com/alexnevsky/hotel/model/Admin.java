package com.alexnevsky.hotel.model;

/**
 * This class represents a Data Transfer Object for the Admin. This DTO can be
 * used thoroughout all layers, the data layer, the controller layer and the
 * view layer.
 * 
 * @version 1.0 10.05.2011
 * @author Alex Nevsky
 */
public class Admin implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String login;
	private String password;

	/**
	 * Default constructor.
	 */
	public Admin() {
	}

	/**
	 * Full constructor.
	 * 
	 * @param id
	 *            The ID of this Admin. Set it to null in case of a new and
	 *            unexisting admin.
	 * @param login
	 *            The login of this Admin.
	 * @param password
	 *            The password of this Admin.
	 */
	public Admin(Long id, String login, String password) {
		this.id = id;
		this.login = login;
		this.password = password;
	}

	/**
	 * Returns the ID of this Admin.
	 * 
	 * @return The ID of this Admin.
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * Returns the login of this Admin.
	 * 
	 * @return The login of this Admin.
	 */
	public String getLogin() {
		return this.login;
	}

	/**
	 * Returns the password of this Admin.
	 * 
	 * @return The password of this Admin.
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Sets the ID of this Admin.
	 * 
	 * @param id
	 *            The ID of this Admin.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Sets the login of this Admin.
	 * 
	 * @param login
	 *            The login of this Admin.
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Sets the password of this Admin.
	 * 
	 * @param password
	 *            The password of this Admin.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * The admin ID is unique for each Admin. So this should compare Admin by
	 * ID only.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		return (other instanceof Admin) && (this.id != null) ? this.id.equals(((Admin) other).id) : (other == this);
	}

	/**
	 * The admin ID is unique for each Admin. So Admin with same ID should
	 * return same hashcode.
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (this.id != null) ? (this.getClass().hashCode() + this.id.hashCode()) : super.hashCode();
	}

	/**
	 * Returns the String representation of this Admin.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Admin{" + "id=" + this.id + ", login=" + this.login + ", password=" + this.password + '}';
	}

}
