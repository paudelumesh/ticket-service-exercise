package com.walmart.store.recruiting.ticket.domain;

import java.util.Calendar;
import java.util.Date;

public class Seat {

	private int id;
	private int r;
	private int c;

	private boolean isAvailable;

	private boolean isReserved;

	private Calendar expires;

	public Seat() {

	}

	public Seat(int r, int c) {

		super();
		this.id = r * c;
		this.r = r;
		this.c = c;
		this.isAvailable = true;
		this.isReserved = false;
	}

	public Seat(int r, int c, boolean isAvailable) {
		super();
		this.r = r;
		this.c = c;
		this.isAvailable = isAvailable;
		this.isReserved = false;
	}

	public Seat(int id, int r, int c) {
		super();
		this.id = id;
		this.r = r;
		this.c = c;
		this.isAvailable = true;
		this.isReserved = false;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

	public boolean isAvailable() {

		if (!isAvailable) {
			Calendar now = Calendar.getInstance();
			if (now.getTimeInMillis() >= this.expires.getTimeInMillis()) {
				this.isAvailable = true;

			}
		}
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Calendar getExpires() {
		return expires;
	}

	public void setExpires(Calendar expires) {
		this.expires = expires;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isReserved() {
		return isReserved;
	}

	public void setReserved(boolean isReserved) {
		this.isReserved = isReserved;
	}

	@Override
	public String toString() {
		return "Seat [id=" + id + ", r=" + r + ", c=" + c + ", isAvailable=" + isAvailable + ", isReserved="
				+ isReserved + ", expires=" + expires + "]";
	}

}
