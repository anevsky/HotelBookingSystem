package com.alexnevsky.hotel.model;

import java.util.Date;

import com.alexnevsky.hotel.model.enums.CreditCardTypeEnum;

/**
 * This class represents a Data Transfer Object for the CreditCard. This
 * DTO can be used thoroughout all layers, the data layer, the controller
 * layer and the view layer.
 * 
 * @version 1.0 10.05.2011
 * @author Alex Nevsky
 */
public class CreditCard implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long number;
	private String holderName;
	private Date expiryDate;
	private CreditCardTypeEnum cardType;

	/**
	 * Default constructor.
	 */
	public CreditCard() {
	}

	/**
	 * Full constructor.
	 */
	public CreditCard(Long number, String holderName, Date expiryDate, CreditCardTypeEnum cardType) {
		this.number = number;
		this.holderName = holderName;
		this.expiryDate = expiryDate;
		this.cardType = cardType;
	}

	/**
	 * Returns the cardType of this CreditCard.
	 * 
	 * @return The cardType of this CreditCard.
	 */
	public CreditCardTypeEnum getCardType() {
		return this.cardType;
	}

	/**
	 * Returns the expiryDate of this CreditCard.
	 * 
	 * @return The expiryDate of this CreditCard.
	 */
	public Date getExpiryDate() {
		return this.expiryDate;
	}

	/**
	 * Returns the holderName of this CreditCard.
	 * 
	 * @return The holderName of this CreditCard.
	 */
	public String getHolderName() {
		return this.holderName;
	}

	/**
	 * Returns the number of this CreditCard.
	 * 
	 * @return The number of this CreditCard.
	 */
	public Long getNumber() {
		return this.number;
	}

	/**
	 * Sets the cardType of this CreditCard.
	 * 
	 * @param cardType
	 *            The cardType of this CreditCard.
	 */
	public void setCardType(CreditCardTypeEnum cardType) {
		this.cardType = cardType;
	}

	/**
	 * Sets the expiryDate of this CreditCard.
	 * 
	 * @param expiryDate
	 *            The expiryDate of this CreditCard.
	 */
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * Sets the holderName of this CreditCard.
	 * 
	 * @param holderName
	 *            The holderName of this CreditCard.
	 */
	public void setHolderName(String holderName) {
		this.holderName = holderName;
	}

	/**
	 * Sets the number of this CreditCard.
	 * 
	 * @param number
	 *            The number of this CreditCard.
	 */
	public void setNumber(Long number) {
		this.number = number;
	}

	/**
	 * The CreditCard number is unique for each CreditCard.
	 * So this should compare CreditCard by number only.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		return (other instanceof CreditCard) && (this.number != null) ? this.number.equals(((CreditCard) other).number)
				: (other == this);
	}

	/**
	 * The CreditCard number is unique for each CreditCard.
	 * So CreditCard with same number should return same hashcode.
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (this.number != null) ? (this.getClass().hashCode() + this.number.hashCode()) : super.hashCode();
	}

	/**
	 * Returns the String representation of this CreditCard.
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CreditCard{" + ", number=" + this.number + ", holderName=" + this.holderName + ", expiryDate="
				+ this.expiryDate + ", cardType=" + this.cardType + '}';
	}
}
