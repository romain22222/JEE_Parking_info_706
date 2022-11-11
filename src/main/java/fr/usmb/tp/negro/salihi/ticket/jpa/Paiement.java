package fr.usmb.tp.negro.salihi.ticket.jpa;

import fr.usmb.tp.negro.salihi.ticket.Constantes;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Paiement implements Serializable {
    @Id @GeneratedValue
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date datePaiement;

    private double montantPaye;

    private MoyenDePaiement moyenDePaiement;

    public Paiement() {}

    public Paiement(double montantPaye, MoyenDePaiement moyenDePaiement) {
        this.datePaiement = new Date();
        this.montantPaye = montantPaye;
        this.moyenDePaiement = moyenDePaiement;
    }

    public Date getDatePaiement() {
        return datePaiement;
    }

    public double getMontantPaye() {
        return montantPaye;
    }

    public MoyenDePaiement getMoyenDePaiement() {
        return moyenDePaiement;
    }

    public boolean stillAvailable() {
        return new Date().getTime() - getDatePaiement().getTime() <= 900000 / Constantes.TIME_MULT;
    }

    @Override
    public String toString() {
        return String.format("%s€ le %s", montantPaye, datePaiement.toString());
    }
}
