package fr.usmb.tp.negro.salihi.ticket.ejb;

import fr.usmb.tp.negro.salihi.ticket.jpa.MoyenDePaiement;
import fr.usmb.tp.negro.salihi.ticket.jpa.Paiement;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class PaiementEJB {
    @PersistenceContext
    private EntityManager em;

    public PaiementEJB() {
    }

    public Paiement addPaiement(double amount, MoyenDePaiement m) {
        Paiement p = new Paiement(amount, m);
        em.persist(p);
        return p;
    }
}
