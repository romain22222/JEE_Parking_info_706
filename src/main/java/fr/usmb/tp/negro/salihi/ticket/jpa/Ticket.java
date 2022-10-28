package fr.usmb.tp.negro.salihi.ticket.jpa;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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

    public double sommePaiement() {
        AtomicReference<Double> somme = new AtomicReference<>(0.0);
        paiementArray.forEach((paiement -> somme.updateAndGet(v -> v + paiement.getMontantPaye())));
        return somme.get();
    }
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEntree;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateSortie;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Paiement> paiementArray;

    public Ticket() {
        this.dateEntree = new Date();
        this.paiementArray = new ArrayList<>();
    }

    public long getId() {
        return this.id;
    }

    public boolean fullyPayed() {
        return lastPaiement() != null && lastPaiement().stillAvailable();
    }
}
