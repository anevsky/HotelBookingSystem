package com.alexnevsky.hotel.model.enums;

/**
 * Order Statuses.
 * 
 * @version 1.0 10.05.2011
 * @author Alex Nevsky
 */
public enum OrderStatusEnum {

	UNCHECKED {

		@Override
		public String toString() {
			return "Unchecked";
		}
	},

	CHECKED {

		@Override
		public String toString() {
			return "Checked";
		}
	},

	CANCELLED {

		@Override
		public String toString() {
			return "Cancelled";
		}
	}
}
