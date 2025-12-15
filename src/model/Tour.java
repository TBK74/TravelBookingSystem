package com.tourbooking.model;

import java.util.Date;

public class Tour {
	private int tourID;
	private String tourName;
	private String description;
	private double price;
	private Date startDate;
	private Date endDate;
	private String location;

	public Tour() {
		super();
	}

	public Tour(int tourID, String tourName, String description, double price, Date startDate, Date endDate,
			String location) {
		super();
		this.tourID = tourID;
		this.tourName = tourName;
		this.description = description;
		this.price = price;
		this.startDate = startDate;
		this.endDate = endDate;
		this.location = location;
	}

	public int getTourID() {
		return tourID;
	}

	public void setTourID(int tourID) {
		this.tourID = tourID;
	}

	public String getTourName() {
		return tourName;
	}

	public void setTourName(String tourName) {
		this.tourName = tourName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
