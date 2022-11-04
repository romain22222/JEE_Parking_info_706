package fr.usmb.tp.negro.salihi.ticket.servlet;

import fr.usmb.tp.negro.salihi.ticket.ejb.TicketEJB;
import fr.usmb.tp.negro.salihi.ticket.jpa.MoyenDePaiement;
import fr.usmb.tp.negro.salihi.ticket.jpa.Ticket;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * Servlet implementation class CreateTicket
 */
@WebServlet("/Payer")
public class Payer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private TicketEJB ejbT;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Payer() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Ticket t = ejbT.findTicket(Long.parseLong(request.getParameter("ticket")));
		if (!Objects.equals(request.getParameter("moyen"), "")) {
			MoyenDePaiement m = MoyenDePaiement.valueOf(request.getParameter("moyen"));
			double amount = Double.parseDouble(request.getParameter("amount").replace(',', '.'));
			ejbT.payTicket(t, amount, m);
			t = ejbT.findTicket(Long.parseLong(request.getParameter("ticket")));
		}
		request.setAttribute("ticket",t);
		request.setAttribute("error", Objects.equals(request.getParameter("moyen"), "") ? "noMoyen" : "");
		request.getRequestDispatcher("/bornePaiement.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
