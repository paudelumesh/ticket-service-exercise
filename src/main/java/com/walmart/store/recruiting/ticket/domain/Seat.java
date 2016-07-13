
package com.walmart.store.recruiting.ticket.domain;

import java.util.Calendar;
import java.util.Date;

public class Seat {

	/**
	 * ID
	 */
	private int id;

	/**
	 * Row
	 */
	private int r;

	/**
	 * Column
	 */
	private int c;

	/**
	 * is Available or not
	 */
	private boolean isAvailable;

	/**
	 * Is Reserved or Not
	 */
	private boolean isReserved;

	/**
	 * Expiry date of the seat
	 */
	private Calendar expires;

	/**
	 * 
	 */
	public Seat() {

	}

	/**
	 * Constructor with row, column
	 * 
	 * @param r
	 * @param c
	 */
	public Seat(int r, int c) {

		super();
		this.id = r * c;
		this.r = r;
		this.c = c;
		this.isAvailable = true;
		this.isReserved = false;
	}

	/**
	 * Constructor with row, column, available
	 * 
	 * @param r
	 * @param c
	 * @param isAvailable
	 */
	public Seat(int r, int c, boolean isAvailable) {
		super();
		this.r = r;
		this.c = c;
		this.isAvailable = isAvailable;
		this.isReserved = false;
	}

	/**
	 * Constructor with id, row, col
	 * 
	 * @param id
	 * @param r
	 * @param c
	 */
	public Seat(int id, int r, int c) {
		super();
		this.id = id;
		this.r = r;
		this.c = c;
		this.isAvailable = true;
		this.isReserved = false;
	}

	/**
	 * if the seat is not available and not reserved,but the hold has expired,
	 * make it available. Lazy update on available field.
	 * 
	 * @return
	 */
	public boolean isAvailable() {

		if (!isAvailable && !isReserved) {
			Calendar now = Calendar.getInstance();
			if (now.getTimeInMillis() >= this.expires.getTimeInMillis()) {
				this.isAvailable = true;

			}
		}
		return isAvailable;
	}

	/**
	 * 
	 * @param isAvailable
	 */
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	/**
	 * 
	 * @return
	 */
	public Calendar getExpires() {
		return expires;
	}

	public void setExpires(Calendar expires) {
		this.expires = expires;
	}

	/**
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isReserved() {
		return isReserved;
	}

	/**
	 * 
	 * @param isReserved
	 */
	public void setReserved(boolean isReserved) {
		this.isReserved = isReserved;
	}

	/**
	 * For debugging purpose
	 */
	@Override
	public String toString() {
		return "Seat [id=" + id + ", r=" + r + ", c=" + c + ", isAvailable=" + isAvailable + ", isReserved="
				+ isReserved + ", expires=" + expires + "]";
	}

}
