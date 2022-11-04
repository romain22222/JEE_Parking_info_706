package fr.usmb.tp.negro.salihi.ticket.jpa;

import fr.usmb.tp.negro.salihi.ticket.Constantes;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
@Entity
public class Ticket implements Serializable {
    @Id @GeneratedValue
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEntree;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateSortie;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ID")
    private List<Paiement> arrayPaiement;

    public Ticket() {}

    public Ticket(Date dateEntree) {
        this.dateEntree = dateEntree;
        this.arrayPaiement = new ArrayList<>();
    }
    public long getId() {
        return this.id;
    }

    public boolean fullyPayed() {
        return lastPaiement() != null && lastPaiement().stillAvailable();
    }

    public double calcCostToPay() {
        return ((
                new Date().getTime() - (
                lastPaiement() == null
                        ? dateEntree.getTime()
                        : lastPaiement().getDatePaiement().getTime()
                )
        ) / (60000./ Constantes.TIME_MULT)) * Constantes.COUT_MINUTE;
    }

    public void payer(Paiement p) {
        arrayPaiement.add(p);
    }

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
        return arrayPaiement.size() > 0 ? arrayPaiement.get(arrayPaiement.size()-1) : null;
    }

    public double sommePaiement() {
        AtomicReference<Double> somme = new AtomicReference<>(0.0);
        arrayPaiement.forEach((paiement -> somme.updateAndGet(v -> v + paiement.getMontantPaye())));
        return somme.get();
    }

    public void setArrayPaiement(List<Paiement> allPaiement) {
        arrayPaiement = allPaiement;
    }

    public List<Paiement> getArrayPaiement() {
        return arrayPaiement;
    }
}
