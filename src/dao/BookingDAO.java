package com.tourbooking.dao;

import com.tourbooking.db.DBConnection;
import com.tourbooking.model.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
	public boolean bookTour(int userID, int tourID) {
		String sql = "INSERT INTO Bookings (UserID, TourID) VALUES (?, ?)";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userID);
			ps.setInt(2, tourID);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Booking> getBookingsByUser(int userID) {
		List<Booking> bookings = new ArrayList<>();
		String sql = "SELECT * FROM Bookings WHERE UserID = ?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userID);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bookings.add(new Booking(rs.getInt("BookingID"), rs.getInt("UserID"), rs.getInt("TourID"),
						rs.getDate("BookingDate"), rs.getString("Status")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bookings;
	}
}
