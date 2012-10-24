package com.alexnevsky.hotel.model;

import com.alexnevsky.hotel.model.enums.RoomClassEnum;

/**
 * This class represents a Data Transfer Object for the Room. This
 * DTO can be used thoroughout all layers, the data layer, the controller
 * layer and the view layer.
 * 
 * @version 1.0 10.05.2011
 * @author Alex Nevsky
 */
public class Room implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Integer adultMax;
	private Integer childMax;
	private RoomClassEnum roomClass;
	private Double nightPrice;

	/**
	 * Default constructor.
	 */
	public Room() {
	}

	/**
	 * Full constructor.
	 */
	public Room(Long id, Integer adultMax, Integer childMax, RoomClassEnum roomClass, Double nightPrice) {
		this.id = id;
		this.adultMax = adultMax;
		this.childMax = childMax;
		this.roomClass = roomClass;
		this.nightPrice = nightPrice;
	}

	/**
	 * Returns the adultMax of this Room.
	 * 
	 * @return The adultMax of this Room.
	 */
	public Integer getAdultMax() {
		return this.adultMax;
	}

	/**
	 * Returns the childMax of this Room.
	 * 
	 * @return The childMax of this Room.
	 */
	public Integer getChildMax() {
		return this.childMax;
	}

	/**
	 * Returns the id of this Room.
	 * 
	 * @return The id of this Room.
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * Returns the nightPrice of this Room.
	 * 
	 * @return The nightPrice of this Room.
	 */
	public Double getNightPrice() {
		return this.nightPrice;
	}

	/**
	 * Returns the roomClass of this Room.
	 * 
	 * @return The roomClass of this Room.
	 */
	public RoomClassEnum getRoomClass() {
		return this.roomClass;
	}

	/**
	 * Sets the adultMax of this Room.
	 * 
	 * @param adultMax
	 *            The adultMax of this Room.
	 */
	public void setAdultMax(Integer adultMax) {
		this.adultMax = adultMax;
	}

	/**
	 * Sets the childMax of this Room.
	 * 
	 * @param childMax
	 *            The childMax of this Room.
	 */
	public void setChildMax(Integer childMax) {
		this.childMax = childMax;
	}

	/**
	 * Sets the ID of this Room.
	 * 
	 * @param id
	 *            The ID of this Room.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Sets the nightPrice of this Room.
	 * 
	 * @param nightPrice
	 *            The nightPrice of this Room.
	 */
	public void setNightPrice(Double nightPrice) {
		this.nightPrice = nightPrice;
	}

	/**
	 * Sets the roomClass of this Room.
	 * 
	 * @param roomClass
	 *            The roomClass of this Room.
	 */
	public void setRoomClass(RoomClassEnum roomClass) {
		this.roomClass = roomClass;
	}

	/**
	 * The Room ID is unique for each Room.
	 * So this should compare Room by ID only.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		return (other instanceof Room) && (this.id != null) ? this.id.equals(((Room) other).id) : (other == this);
	}

	/**
	 * The Room ID is unique for each Room.
	 * So Room with same ID should return same hashcode.
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (this.id != null) ? (this.getClass().hashCode() + this.id.hashCode()) : super.hashCode();
	}

	/**
	 * Returns the String representation of this Room.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Room{" + "id=" + this.id + ", adultMax=" + this.adultMax + ", childMax=" + this.childMax
				+ ", roomClass=" + this.roomClass + ", nightPrice=" + this.nightPrice + '}';
	}
}
