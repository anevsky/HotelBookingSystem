package com.alexnevsky.hotel.model;

import java.util.Date;

import com.alexnevsky.hotel.model.enums.RoomClassEnum;

/**
 * This class represents a Data Transfer Object for the Form. This
 * DTO can be used thoroughout all layers, the data layer, the controller
 * layer and the view layer.
 * 
 * @version 1.0 10.05.2011
 * @author Alex Nevsky
 */
public class Form implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Integer adult;
	private Integer child;
	private RoomClassEnum roomClass;
	private Date arrival;
	private Integer nights;
	private String commentary;

	/**
	 * Default constructor.
	 */
	public Form() {
	}

	/**
	 * Full constructor.
	 */
	public Form(Long id, Integer adult, Integer child, RoomClassEnum roomClass, Date arrival, Integer nights,
			String commentary) {
		this.id = id;
		this.adult = adult;
		this.child = child;
		this.roomClass = roomClass;
		this.arrival = arrival;
		this.nights = nights;
		this.commentary = commentary;
	}

	/**
	 * Returns the adult of this Form.
	 * 
	 * @return The adult of this Form.
	 */
	public Integer getAdult() {
		return this.adult;
	}

	/**
	 * Returns the arrival of this Form.
	 * 
	 * @return The arrival of this Form.
	 */
	public Date getArrival() {
		return this.arrival;
	}

	/**
	 * Returns the child of this Form.
	 * 
	 * @return The child of this Form.
	 */
	public Integer getChild() {
		return this.child;
	}

	/**
	 * Returns the commentary of this Form.
	 * 
	 * @return The commentary of this Form.
	 */
	public String getCommentary() {
		return this.commentary;
	}

	/**
	 * Returns the id of this Form.
	 * 
	 * @return The id of this Form.
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * Returns the nights of this Form.
	 * 
	 * @return The nights of this Form.
	 */
	public Integer getNights() {
		return this.nights;
	}

	/**
	 * Returns the roomClass of this Form.
	 * 
	 * @return The roomClass of this Form.
	 */
	public RoomClassEnum getRoomClass() {
		return this.roomClass;
	}

	/**
	 * Sets the adult of this Form.
	 * 
	 * @param adult
	 *            The adult of this Form.
	 */
	public void setAdult(Integer adult) {
		this.adult = adult;
	}

	/**
	 * Sets the arrival of this Form.
	 * 
	 * @param arrival
	 *            The arrival of this Form.
	 */
	public void setArrival(Date arrival) {
		this.arrival = arrival;
	}

	/**
	 * Sets the child of this Form.
	 * 
	 * @param child
	 *            The child of this Form.
	 */
	public void setChild(Integer child) {
		this.child = child;
	}

	/**
	 * Sets the commentary of this Form.
	 * 
	 * @param commentary
	 *            The commentary of this Form.
	 */
	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}

	/**
	 * Sets the ID of this Form.
	 * 
	 * @param id
	 *            The ID of this Form.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Sets the nights of this Form.
	 * 
	 * @param nights
	 *            The nights of this Form.
	 */
	public void setNights(Integer nights) {
		this.nights = nights;
	}

	/**
	 * Sets the roomClass of this Form.
	 * 
	 * @param roomClass
	 *            The roomClass of this Form.
	 */
	public void setRoomClass(RoomClassEnum roomClass) {
		this.roomClass = roomClass;
	}

	/**
	 * The Form ID is unique for each Form.
	 * So this should compare Form by ID only.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		return (other instanceof Form) && (this.id != null) ? this.id.equals(((Form) other).id) : (other == this);
	}

	/**
	 * The Form ID is unique for each Form.
	 * So Form with same ID should return same hashcode.
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (this.id != null) ? (this.getClass().hashCode() + this.id.hashCode()) : super.hashCode();
	}

	/**
	 * Returns the String representation of this Form.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Form{" + "id=" + this.id + ", adult=" + this.adult + ", child=" + this.child + ", roomClass="
				+ this.roomClass + ", arrival=" + this.arrival + ", nights=" + this.nights + ", commentary="
				+ this.commentary + '}';
	}
}
