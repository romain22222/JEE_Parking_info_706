package fr.usmb.tp.negro.sahili.ticket.jpa;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.*;

@Entity
public class Ticket implements Serializable {
    @Id @GeneratedValue
    private long id;

    public Date getDateEntree() {
        return dateEntree;
    }

    public Date getDateSortie() {
        return dateSortie;
    }

    public void setDateSortie(Date dateSortie) {
        this.dateSortie = dateSortie;
    }

    public Paiement lastPaiement() {
        return paiementArray.size() > 0 ? paiementArray.get(paiementArray.size()-1) : null;
    }

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEntree;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateSortie;

    @OneToMany
    private ArrayList<Paiement> paiementArray;

    public Ticket() {
        this.dateEntree = new Date();
        this.paiementArray = new ArrayList<Paiement>();
    }

    public long getId() {
        return this.id;
    }
}
