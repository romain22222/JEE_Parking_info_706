package fr.usmb.tp.negro.salihi.ticket.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.usmb.tp.negro.salihi.ticket.jpa.MoyenDePaiement;
import fr.usmb.tp.negro.salihi.ticket.jpa.Paiement;
import fr.usmb.tp.negro.salihi.ticket.jpa.Ticket;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Stateless
@LocalBean
public class TicketEJB {
    @PersistenceContext
    private EntityManager em;

    public TicketEJB() {
    }

    public Ticket addTicket() {
        Ticket t = new Ticket(new Date());
        em.persist(t);
        return t;
    }

    public Ticket findTicket(long id) {
        return em.find(Ticket.class, id);
    }
    public List<Ticket> findAllTicket() {
        List<Long> ids = em
                .createQuery("SELECT t.id FROM Ticket t ORDER BY t.dateEntree ASC", Long.class)
                .getResultList();
        List<Ticket> validTickets = new ArrayList<>();
        ids.listIterator().forEachRemaining(id -> validTickets.add(this.findTicket(id)));
        return validTickets;
    }
    public void payTicket(Ticket t, double amount, MoyenDePaiement m) {
        Ticket newT = em.find(Ticket.class, t.getId());
        newT.payer(new Paiement(amount, m));
    }

    public void ticketSortie(Ticket t) {
        Ticket newT = em.find(Ticket.class, t.getId());
        newT.setDateSortie(new Date());
    }
}
