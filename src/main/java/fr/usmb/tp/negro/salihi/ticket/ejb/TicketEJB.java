package fr.usmb.tp.negro.salihi.ticket.ejb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.usmb.tp.negro.salihi.ticket.jpa.Ticket;

import java.util.List;

@Stateless
@LocalBean
public class TicketEJB {
    @PersistenceContext
    private EntityManager em;

    public TicketEJB() {
    }

    public Ticket addTicket() {
        Ticket t = new Ticket();
        em.persist(t);
        return t;
    }

    public Ticket findTicket(long id) {
        return em.find(Ticket.class, id);
    }
    public List<Ticket> findAllTicket() {
        return em
                .createQuery("SELECT t FROM Ticket t ORDER BY t.dateSortie ASC", Ticket.class)
                .getResultList();
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
