package com.tourbooking.servlet;

import com.tourbooking.dao.BookingDAO;
import com.tourbooking.dao.TourDAO;
import com.tourbooking.model.Booking;
import com.tourbooking.model.Tour;
import com.tourbooking.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class MyBookingsServlet
 */
@WebServlet("/MyBookingsServlet")
public class MyBookingsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyBookingsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        BookingDAO bookingDAO = new BookingDAO();
        List<Booking> bookings = bookingDAO.getBookingsByUser(user.getUserID());
        TourDAO tourDAO = new TourDAO();
        for (Booking booking : bookings) {
            Tour tour = tourDAO.getTourByID(booking.getTourID());
            request.setAttribute("tour_" + booking.getBookingID(), tour);  // Để hiển thị tên tour
        }
        request.setAttribute("bookings", bookings);
        request.getRequestDispatcher("/myBookings.jsp").forward(request, response);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
