package fr.usmb.tp.negro.sahili.ticket.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

@Entity
public class Paiement implements Serializable {
    @Id @GeneratedValue
    private long id;

    public Date getDatePaiement() {
        return datePaiement;
    }

    public double getMontantPaye() {
        return montantPaye;
    }

    public MoyenDePaiement getMoyenDePaiement() {
        return moyenDePaiement;
    }

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

    @Override
    public String toString() {
        return "Paiement123";
    }
}
