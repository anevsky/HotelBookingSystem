package com.alexnevsky.hotel.model.enums;

/**
 * Room Classes.
 * 
 * @version 1.0 10.05.2011
 * @author Alex Nevsky
 */
public enum RoomClassEnum {

	STANDART {

		@Override
		public String toString() {
			return "Standart";
		}
	},

	LUXURY {

		@Override
		public String toString() {
			return "Luxury";
		}
	},

	GRAND {

		@Override
		public String toString() {
			return "Grand";
		}
	}
}
