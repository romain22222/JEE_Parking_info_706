package fr.usmb.tp.negro.sahili.ticket.servlet;

import fr.usmb.tp.negro.sahili.ticket.ejb.TicketEJB;
import fr.usmb.tp.negro.sahili.ticket.jpa.Ticket;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class CreateTicket
 */
@WebServlet("/CreateTicket")
public class CreateTicket extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private TicketEJB ejb;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateTicket() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Ticket t = ejb.addTicket();
		request.setAttribute("ticket",t);
		request.getRequestDispatcher("/showTicket.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
