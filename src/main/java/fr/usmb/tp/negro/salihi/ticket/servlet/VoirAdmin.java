package fr.usmb.tp.negro.salihi.ticket.servlet;

import fr.usmb.tp.negro.salihi.ticket.ejb.TicketEJB;
import fr.usmb.tp.negro.salihi.ticket.jpa.Ticket;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class CreateTicket
 */
@WebServlet("/VoirAdmin")
public class VoirAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private TicketEJB ejb;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public VoirAdmin() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Ticket> ts = ejb.findAllTicket();
		request.setAttribute("tickets",ts);
		request.getRequestDispatcher("/showTickets.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
