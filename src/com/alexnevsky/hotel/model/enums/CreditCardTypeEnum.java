package com.alexnevsky.hotel.model.enums;

/**
 * Credit Card Types.
 * 
 * @version 1.0 10.05.2011
 * @author Alex Nevsky
 */
public enum CreditCardTypeEnum {

	VISA {

		@Override
		public String toString() {
			return "Visa";
		}
	},

	MASTER_CARD {

		@Override
		public String toString() {
			return "MasterCard";
		}
	},

	AMERICAN_EXPRESS {

		@Override
		public String toString() {
			return "American Express";
		}
	}
}
