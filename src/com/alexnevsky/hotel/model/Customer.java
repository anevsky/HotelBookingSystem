package com.alexnevsky.hotel.model;

/**
 * This class represents a Data Transfer Object for the Customer. This
 * DTO can be used thoroughout all layers, the data layer, the controller
 * layer and the view layer.
 * 
 * @author Alex Nevsky
 */
public class Customer implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String address;
	private Long creditCardNumber;

	public Customer() {
	}

	public Customer(Long id, String firstName, String lastName, String email, String phone, String address,
			Long creditCardNumber) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.creditCardNumber = creditCardNumber;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getCreditCardNumber() {
		return this.creditCardNumber;
	}

	public void setCreditCardNumber(Long creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Customer other = (Customer) obj;
		if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
			return false;
		}
		if ((this.firstName == null) ? (other.firstName != null) : !this.firstName.equals(other.firstName)) {
			return false;
		}
		if ((this.lastName == null) ? (other.lastName != null) : !this.lastName.equals(other.lastName)) {
			return false;
		}
		if ((this.email == null) ? (other.email != null) : !this.email.equals(other.email)) {
			return false;
		}
		if ((this.phone == null) ? (other.phone != null) : !this.phone.equals(other.phone)) {
			return false;
		}
		if ((this.address == null) ? (other.address != null) : !this.address.equals(other.address)) {
			return false;
		}
		if (this.creditCardNumber != other.creditCardNumber
				&& (this.creditCardNumber == null || !this.creditCardNumber.equals(other.creditCardNumber))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 83 * hash + (this.id != null ? this.id.hashCode() : 0);
		hash = 83 * hash + (this.firstName != null ? this.firstName.hashCode() : 0);
		hash = 83 * hash + (this.lastName != null ? this.lastName.hashCode() : 0);
		hash = 83 * hash + (this.email != null ? this.email.hashCode() : 0);
		hash = 83 * hash + (this.phone != null ? this.phone.hashCode() : 0);
		hash = 83 * hash + (this.address != null ? this.address.hashCode() : 0);
		hash = 83 * hash + (this.creditCardNumber != null ? this.creditCardNumber.hashCode() : 0);
		return hash;
	}

	@Override
	public String toString() {
		return "Customer{" + "id=" + this.id + ", firstName=" + this.firstName + ", lastName=" + this.lastName
				+ ", email=" + this.email + ", phone=" + this.phone + ", address=" + this.address
				+ ", creditCardNumber=" + this.creditCardNumber + '}';
	}

}
