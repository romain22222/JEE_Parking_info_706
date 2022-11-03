package fr.usmb.tp.negro.salihi.ticket.servlet;

import fr.usmb.tp.negro.salihi.ticket.ejb.PaiementEJB;
import fr.usmb.tp.negro.salihi.ticket.ejb.TicketEJB;
import fr.usmb.tp.negro.salihi.ticket.jpa.Paiement;
import fr.usmb.tp.negro.salihi.ticket.jpa.Ticket;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Servlet implementation class CreateTicket
 */
@WebServlet("/SortirParking")
public class SortirParking extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private TicketEJB ejbT;
	@EJB
	private PaiementEJB ejbP;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SortirParking() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Ticket t = ejbT.findTicket(Long.parseLong(request.getParameter("ticket")));
		Paiement lastPaiment = t.lastPaiement();
		if (lastPaiment == null) {
			System.out.println("TA PAS PAYé GROS NAZE");
			return;
		}
		Date lastDonePaiment = lastPaiment.getDatePaiement();
		if (lastDonePaiment.getTime() - new Date().getTime() <= 900000){
			System.out.println("c bon");
		}
		else
			System.out.println("PAIE !!!!!!");
		request.setAttribute("ticket",t);
		request.getRequestDispatcher("/bornePaiement.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
