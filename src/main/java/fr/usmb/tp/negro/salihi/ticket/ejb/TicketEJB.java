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
        // TODO a delete quand plus de prob
        em.createQuery("DELETE FROM Ticket").executeUpdate();
        em.createQuery("DELETE FROM Paiement").executeUpdate();
        em.joinTransaction();
        // // //
        List<Long> ids = em
                .createQuery("SELECT t.id FROM Ticket t ORDER BY t.dateSortie ASC", Long.class)
                .getResultList();
        List<Ticket> validTickets = new ArrayList<>();
        ids.listIterator().forEachRemaining(id -> validTickets.add(this.findTicket(id)));
        return validTickets;
    }
    public void payTicket(Ticket t, double amount, MoyenDePaiement m, PaiementEJB ejbP) {
        Ticket newT = em.find(Ticket.class, t.getId());
//        Paiement p = ejbP.addPaiement(amount, m);
        newT.payer(new Paiement(amount, m));
    }

/*    public List<Mesure> findMesures(String piece) {
        TypedQuery<Mesure> rq = em.createQuery("SELECT m FROM Mesure m WHERE m.piece = :piece ORDER BY m.dateMesure ASC", Mesure.class);
        rq.setParameter("piece", piece);
        return rq.getResultList();
    }

    public Mesure findLastMesure(String piece) {
        TypedQuery<Mesure> rq = em.createQuery("SELECT m FROM Mesure m WHERE m.piece = :piece ORDER BY m.dateMesure DESC", Mesure.class);
        rq.setParameter("piece", piece);
        rq.setMaxResults(1);
        return rq.getSingleResult();
    }

    public List<Mesure> getLastMesures() {
        List<Mesure> res = new LinkedList<>();
        TypedQuery<String> rq = em.createQuery("SELECT DISTINCT m.piece FROM Mesure m group by m.piece", String.class);
        for(String p : rq.getResultList()) {
            Mesure m = findLastMesure(p);
            res.add(m);
        }
        return res;
    }*/
}
