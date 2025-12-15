package com.tourbooking.dao;

import com.tourbooking.db.DBConnection;
import com.tourbooking.model.Tour;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TourDAO {
	public List<Tour> getAllTours() {
		List<Tour> tours = new ArrayList<>();
		String sql = "SELECT * FROM Tours";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				tours.add(new Tour(rs.getInt("TourID"), rs.getString("TourName"), rs.getString("Description"),
						rs.getDouble("Price"), rs.getDate("StartDate"), rs.getDate("EndDate"),
						rs.getString("Location")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tours;
	}

	public Tour getTourByID(int tourID) {
		String sql = "SELECT * FROM Tours WHERE TourID = ?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, tourID);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Tour(rs.getInt("TourID"), rs.getString("TourName"), rs.getString("Description"),
						rs.getDouble("Price"), rs.getDate("StartDate"), rs.getDate("EndDate"),
						rs.getString("Location"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
