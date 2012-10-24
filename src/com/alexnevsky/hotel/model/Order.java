package com.alexnevsky.hotel.model;

import java.sql.Date;

import com.alexnevsky.hotel.model.enums.OrderStatusEnum;

/**
 * This class represents a Data Transfer Object for the Order. This
 * DTO can be used thoroughout all layers, the data layer, the controller
 * layer and the view layer.
 * 
 * @version 1.0 10.05.2011
 * @author Alex Nevsky
 */
public class Order implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Double amount;
	private Date dateCreated;
	private OrderStatusEnum orderStatus;
	private Long customerId;
	private Long formId;
	private Long roomId;

	/**
	 * Default constructor.
	 */
	public Order() {
	}

	/**
	 * Full constructor.
	 */
	public Order(Long id, Double amount, Date dateCreated, OrderStatusEnum orderStatus, Long customerId, Long formId,
			Long roomId) {
		this.id = id;
		this.amount = amount;
		this.dateCreated = dateCreated;
		this.orderStatus = orderStatus;
		this.customerId = customerId;
		this.formId = formId;
		this.roomId = roomId;
	}

	/**
	 * Returns the amount of this Order.
	 * 
	 * @return The amount of this Order.
	 */
	public Double getAmount() {
		return this.amount;
	}

	/**
	 * Returns the customerId of this Order.
	 * 
	 * @return The customerId of this Order.
	 */
	public Long getCustomerId() {
		return this.customerId;
	}

	/**
	 * Returns the dateCreated of this Order.
	 * 
	 * @return The dateCreated of this Order.
	 */
	public Date getDateCreated() {
		return this.dateCreated;
	}

	/**
	 * Returns the formId of this Order.
	 * 
	 * @return The formId of this Order.
	 */
	public Long getFormId() {
		return this.formId;
	}

	/**
	 * Returns the id of this Order.
	 * 
	 * @return The id of this Order.
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * Returns the orderStatus of this Order.
	 * 
	 * @return The orderStatus of this Order.
	 */
	public OrderStatusEnum getOrderStatus() {
		return this.orderStatus;
	}

	/**
	 * Returns the roomId of this Order.
	 * 
	 * @return The roomId of this Order.
	 */
	public Long getRoomId() {
		return this.roomId;
	}

	/**
	 * Sets the amount of this Order.
	 * 
	 * @param amount
	 *            The amount of this Order.
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * Sets the customerId of this Order.
	 * 
	 * @param customerId
	 *            The customerId of this Order.
	 */
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	/**
	 * Sets the dateCreated of this Order.
	 * 
	 * @param dateCreated
	 *            The dateCreated of this Order.
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * Sets the formId of this Order.
	 * 
	 * @param formId
	 *            The formId of this Order.
	 */
	public void setFormId(Long formId) {
		this.formId = formId;
	}

	/**
	 * Sets the ID of this Order.
	 * 
	 * @param id
	 *            The ID of this Order.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Sets the orderStatus of this Order.
	 * 
	 * @param orderStatus
	 *            The orderStatus of this Order.
	 */
	public void setOrderStatus(OrderStatusEnum orderStatus) {
		this.orderStatus = orderStatus;
	}

	/**
	 * Sets the roomId of this Order.
	 * 
	 * @param roomId
	 *            The roomId of this Order.
	 */
	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	/**
	 * The Order ID is unique for each Order.
	 * So this should compare Order by ID only.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		return (other instanceof Order) && (this.id != null) ? this.id.equals(((Order) other).id) : (other == this);
	}

	/**
	 * The Order ID is unique for each Order.
	 * So Order with same ID should return same hashcode.
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (this.id != null) ? (this.getClass().hashCode() + this.id.hashCode()) : super.hashCode();
	}

	/**
	 * Returns the String representation of this Order.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Order{" + "id=" + this.id + ", amount=" + this.amount + ", dateCreated=" + this.dateCreated
				+ ", orderStatus=" + this.orderStatus + ", customerId=" + this.customerId + ", formId=" + this.formId
				+ ", roomId=" + this.roomId + '}';
	}
}
