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

/**
 * Servlet implementation class CreateTicket
 */
@WebServlet("/ImprimerJustif")
public class ImprimerJustif extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private TicketEJB ejbT;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImprimerJustif() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Ticket t = ejbT.findTicket(Long.parseLong(request.getParameter("ticket")));
		request.setAttribute("ticket",t);
		request.getRequestDispatcher("/justif.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
