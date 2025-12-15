package com.tourbooking.model;

import java.util.Date;

public class Booking {
	private int bookingID;
	private int userID;
	private int tourID;
	private Date bookingDate;
	private String status;

	public Booking() {
	}

	public Booking(int bookingID, int userID, int tourID, Date bookingDate, String status) {
		this.bookingID = bookingID;
		this.userID = userID;
		this.tourID = tourID;
		this.bookingDate = bookingDate;
		this.status = status;
	}

	public int getBookingID() {
		return bookingID;
	}

	public void setBookingID(int bookingID) {
		this.bookingID = bookingID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getTourID() {
		return tourID;
	}

	public void setTourID(int tourID) {
		this.tourID = tourID;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
