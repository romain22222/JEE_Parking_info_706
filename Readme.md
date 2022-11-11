# INFO 706, TP JEE représentation d'un parking #

Ceci est en bref un parking cher de Paris dont vous pouvez modifier le coût minute et le passage du temps afin de faire payer plus vos utilisateurs.

Un ticket comprendra :
- un id (identifiant unique autogénéré)
- une date d'entrée et de sortie (donnant les moments d'utilisation du dit ticket)
- une liste de paiements (les diférents paiements effectués par l'utilisateur)

Un paiement comprendra :
- un id (identifiant unique autogénéré)
- une date de paiement (quand le paiement a été effectué)
- un montant payé
- un moyen de paiement

Le moyen de paiement est un type énuméré comprenant 5 valeurs possibles, toutes correspondant à un moyen de payer différent

On peut :
- créer un nouveau ticket
- payer le ticket
- imprimer un justificatif du ticket
- entrer - sortir du parking
- calculer le prix à payer pour un ticket donné, en fonction du temps passé dans le parking et des précédents paiements
- Savoir si le ticket est payé
- Savoir si le paiement du ticket a expiré

Les tickets et les paiements sont stockées dans une base de données. On y accède à travers une entité (JPA).

Un EJB session sans état (stateless) sert de façade pour les opérations de création/recherche des tickets et des paiements.

Un client WEB permet de réaliser l'ensemble des opérations cités au-dessus.
Ce client comporte sept pages JSP et plusieurs servlet (1 par opération distincte). La connexion au bean session se fait en local dans les servlet sans passer par une interface.

Il y a aussi une classe Constantes, permettant à votre guise de modifier le passage du temps ainsi que le coût minute du parking.

## Organisation du projet ##

### Partie JPA ###

Il y a deux objets persistants defini à l'aide des classes `Ticket` et `Paiement` :  
- <a href="src/main/java/fr/usmb/tp/negro/salihi/ticket/jpa/Ticket.java" >Ticket</a> (implantation de l'entité _Ticket_ (entité JPA))
- <a href="src/main/java/fr/usmb/tp/negro/salihi/ticket/jpa/Paiement.java" >Paiement</a> (implantation de l'entité _Paiement_ (entité JPA))
- <a href="src/main/resources/META-INF/persistence.xml" >Fichier persistence</a> (descripteur standard JPA)

### Partie EBJ ###

L'accès aux tickets et aux paiements se font au travers d'un EJB (Enterprise JavaBean) sans état (et utilisable sans interface - cf. annotation `@LocalBean`) pour chaque entité :  
- <a href="src/main/java/fr/usmb/tp/negro/salihi/ticket/ejb/TicketEJB.java" >TicketEJB</a> EJB sans état (Stateless)
- <a href="src/main/java/fr/usmb/tp/negro/salihi/ticket/ejb/PaiementEJB.java" >PaiementEJB</a> EJB sans état (Stateless)

### Partie WEB  ###

Différentes servlet permettent permettent d'executer les actions :  
- <a href="src/main/java/fr/usmb/tp/negro/salihi/ticket/servlet/AllerBorneSortie.java" >AllerBorneSortie</a> (Aller à la borne de sortie)
- <a href="src/main/java/fr/usmb/tp/negro/salihi/ticket/servlet/AllerPayer.java" >AllerPayer</a> (Aller payer à la borne de paiement)
- <a href="src/main/java/fr/usmb/tp/negro/salihi/ticket/servlet/ContinuerStationnement.java" >ContinuerStationnement</a> (Quitter la borne de paiement car on a pas fini de stationner)
- <a href="src/main/java/fr/usmb/tp/negro/salihi/ticket/servlet/CreateTicket.java" >CreateTicket</a> (Créer un ticket et entrer dans le parking)
- <a href="src/main/java/fr/usmb/tp/negro/salihi/ticket/servlet/ImprimerJustif.java" >ImprimerJustif</a> (Impression d'un justificatif)
- <a href="src/main/java/fr/usmb/tp/negro/salihi/ticket/servlet/MenuExt.java" >MenuExt</a> (Sortir du parking (ou au moins tenter de...))
- <a href="src/main/java/fr/usmb/tp/negro/salihi/ticket/servlet/Payer.java" >Payer</a> (Payer le ticket)
- <a href="src/main/java/fr/usmb/tp/negro/salihi/ticket/servlet/SortirParking.java" >SortirParking</a> (Aller à la sortie du parking)
- <a href="src/main/java/fr/usmb/tp/negro/salihi/ticket/servlet/VoirAdmin.java" >VoirAdmin</a> (Voir le menu administrateur (menu qui affiche l'état de tous les tickets et des informations complémentaires))

L'affichage des tickets se fait à la fin des servlet en redirigeant les requêtes vers des pages JSP (JavaServer Pages) :  
- <a href="src/main/webapp/bornePaiement.jsp" >bornePaiement</a> (représente la borne de paiement)
- <a href="src/main/webapp/borneSortie.jsp" >borneSortie</a> (représente la borne de sortie)
- <a href="src/main/webapp/exterieur.jsp" >exterieur</a> (représente l'extérieur du parking)
- <a href="src/main/webapp/index.jsp" >index</a> (représente la borne d'entrée du parking)
- <a href="src/main/webapp/justif.jsp" >justif</a> (représente un justificatif de ticket)
- <a href="src/main/webapp/showTicket.jsp" >showTicket</a> (représente le ticket hors des bornes (par exemple quand on a sa voiture de garée et qu'on part marcher dehors))
- <a href="src/main/webapp/showTickets.jsp" >showTickets</a> (Partie administrateur : permet de voir tous les tickets enregistrés ainsi que leurs états (payé, sorti, expiré, ...))


## Fonctionnement ##

### Manipulation des objets persistants dans l'EJB ###

Toutes les manipulations sur les objets persistants se font dans l'EJB en utilisant l'__entity manager__ correspondant à l'__unité de persistance__ des objets persistants manipulés. 

Dans l'EJB on utilise l'annotation `@PersistenceContext` pour récupérer auprès du serveur JavaEE l'_entity manager_ désiré.  

```java
@Stateless
@LocalBean
public class TicketEJB {
	@PersistenceContext
	private EntityManager em;
```

L'_entity manager_ est ensuite utilisé dans les méthodes de l'EJB pour ajouter des tickets :  

```java
	public Ticket addTicket() {
        Ticket t = new Ticket(new Date());
        em.persist(t);
        return t;
    }
```

ou retrouver des tickets dans la base :  

```java
	public Ticket findTicket(long id) {
        return em.find(Ticket.class, id);
    }
```
```java
	public List<Ticket> findAllTicket() {
        List<Long> ids = em
        .createQuery("SELECT t.id FROM Ticket t ORDER BY t.dateEntree ASC", Long.class)
        .getResultList();
        List<Ticket> validTickets = new ArrayList<>();
        ids.listIterator().forEachRemaining(id -> validTickets.add(this.findTicket(id)));
        return validTickets;
    }
```

ou payer le ticket :

```java
    public void payTicket(Ticket t, double amount, MoyenDePaiement m) {
        Ticket newT = em.find(Ticket.class, t.getId());
        newT.payer(new Paiement(amount, m));
    }
```

ou sortir avec le ticket :
```java
    public void ticketSortie(Ticket t) {
        Ticket newT = em.find(Ticket.class, t.getId());
        newT.setDateSortie(new Date());
    }
```

### Utilisation de l'EJB dans les servlet ###

Dans les _servlet_ on utilise l'annotation `@EJB` pour obtenir une référence de l'_EJB session_ :

```java
@WebServlet("/Payer")
public class Payer extends HttpServlet {
	@EJB
	private TicketEJB ejb;
```

Pour l'affichage, dans les servlet, on ajoute dans la requete http les objets java à afficher et on redirige les requêtes vers les pages JSP :  

```java

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Ticket t=ejbT.findTicket(Long.parseLong(request.getParameter("ticket")));
        if(!Objects.equals(request.getParameter("moyen"),"")){
        	MoyenDePaiement m=MoyenDePaiement.valueOf(request.getParameter("moyen"));
 	       double amount=Double.parseDouble(request.getParameter("amount").replace(',','.'));
 	       ejbT.payTicket(t,amount,m);
 	       t=ejbT.findTicket(Long.parseLong(request.getParameter("ticket")));
        }
        request.setAttribute("ticket",t);
        request.setAttribute("error",Objects.equals(request.getParameter("moyen"),"")?"noMoyen":"");
        request.getRequestDispatcher("/bornePaiement.jsp").forward(request,response);
    }
```


## Packaging ##

Comme c'est une application ciblant le profil web de la spécification JavaEE 8, tout (Servlet/JSP/EJB/entité JPA) peut être empaqueté dans la même archive web (INFO706TP.war).

### L'application WEB est entièrement packagée dans un fichier d'archive war ###

Les resources web (pages html, JSP, feuilles de styles CSS, etc. sont ajoutées à la racine du fichier d'archive.

Deux dossiers dossiers spécifiques, `META-INF` et `WEB-INF` dans l'archive war permettent de configurer l'application web et gérer les parties en java.
<pre>
INFO706TP.war
  |-- <a href="src/main/webapp/bornePaiement.jsp" >bornePaiement</a> (représente la borne de paiement)
  |-- <a href="src/main/webapp/borneSortie.jsp" >borneSortie</a> (représente la borne de sortie)
  |-- <a href="src/main/webapp/exterieur.jsp" >exterieur</a> (représente l'extérieur du parking)
  |-- <a href="src/main/webapp/index.jsp" >index</a> (représente la borne d'entrée du parking)
  |-- <a href="src/main/webapp/justif.jsp" >justif</a> (représente un justificatif de ticket)
  |-- <a href="src/main/webapp/showTicket.jsp" >showTicket</a> (représente le ticket hors des bornes (par exemple quand on a sa voiture de garée et qu'on part marcher dehors))
  |-- <a href="src/main/webapp/showTickets.jsp" >showTickets</a> (Partie administrateur : permet de voir tous les tickets enregistrés ainsi que leurs états (payé, sorti, expiré, ...))

  |-- <a href="src/main/webapp/default.css" >default.css</a> (feuille de style css)
  |-- <a href="src/main/webapp/META-INF/MANIFEST.MF" >MANIFEST.MF</a> (java manifest)

                |-- <a href="src/main/resources/META-INF/persistence.xml" >Fichier persistence</a> (descripteur standard JPA)
                
                |-- <a href="src/main/java/fr/usmb/tp/negro/salihi/ticket/servlet/AllerBorneSortie.java" >AllerBorneSortie</a> (Aller à la borne de sortie)
                |-- <a href="src/main/java/fr/usmb/tp/negro/salihi/ticket/servlet/AllerPayer.java" >AllerPayer</a> (Aller payer à la borne de paiement)
                |-- <a href="src/main/java/fr/usmb/tp/negro/salihi/ticket/servlet/ContinuerStationnement.java" >ContinuerStationnement</a> (Quitter la borne de paiement car on a pas fini de stationner)
                |-- <a href="src/main/java/fr/usmb/tp/negro/salihi/ticket/servlet/CreateTicket.java" >CreateTicket</a> (Créer un ticket et entrer dans le parking)
                |-- <a href="src/main/java/fr/usmb/tp/negro/salihi/ticket/servlet/ImprimerJustif.java" >ImprimerJustif</a> (Impression d'un justificatif)
                |-- <a href="src/main/java/fr/usmb/tp/negro/salihi/ticket/servlet/MenuExt.java" >MenuExt</a> (Sortir du parking (ou au moins tenter de...))
                |-- <a href="src/main/java/fr/usmb/tp/negro/salihi/ticket/servlet/Payer.java" >Payer</a> (Payer le ticket)
                |-- <a href="src/main/java/fr/usmb/tp/negro/salihi/ticket/servlet/SortirParking.java" >SortirParking</a> (Aller à la sortie du parking)
                |-- <a href="src/main/java/fr/usmb/tp/negro/salihi/ticket/servlet/VoirAdmin.java" >VoirAdmin</a> (Voir le menu administrateur (menu qui affiche l'état de tous les tickets et des informations complémentaires))
                
                |-- <a href="src/main/java/fr/usmb/tp/negro/salihi/ticket/ejb/TicketEJB.java" >TicketEJB</a> EJB sans état (Stateless)
                |-- <a href="src/main/java/fr/usmb/tp/negro/salihi/ticket/ejb/PaiementEJB.java" >PaiementEJB</a> EJB sans état (Stateless)
                
                |-- <a href="src/main/java/fr/usmb/tp/negro/salihi/ticket/jpa/Ticket.java" >Ticket</a> (implantation de l'entité _Ticket_ (entité JPA))
                |-- <a href="src/main/java/fr/usmb/tp/negro/salihi/ticket/jpa/Paiement.java" >Paiement</a> (implantation de l'entité _Paiement_ (entité JPA))

                |-- <a href="src/main/java/fr/usmb/tp/negro/salihi/ticket/Constantes.java" >Constantes</a> (Constantes du fonctionnement du parking modifiables)
</pre>

## Usage : ##

Pour voir les sources, il suffit de cloner le projet git et de l'importer (sous forme de projet gradle) dans votre IDE favori. 
Cela devrait permettre la création d'un projet (ou module) web.

La compilation des classes et la création de l'archive war peut se faire via gradle en appelant la tâche `build` sur le projet principal.

Pour utiliser l'exemple il suffit de déployer le fichier _INFO706TP.war_ sur un serveur JavaEE 8. 
Le client Web est alors déployé avec le préfixe _/INFO706TP_.

## Documentation : ##

Java EE 7 (Oracle)
- Doc : http://docs.oracle.com/javaee/7
- Tutoriel : https://docs.oracle.com/javaee/7/tutorial
- API (javadoc) : http://docs.oracle.com/javaee/7/api
- Spécifications : https://www.oracle.com/java/technologies/javaee/javaeetechnologies.html#javaee7

Jave EE 8 (Oracle)
- Doc : https://javaee.github.io/glassfish/documentation
- Tutoriel : https://javaee.github.io/tutorial/
- API (javadoc) : https://javaee.github.io/javaee-spec/javadocs/
- Spécifications : https://www.oracle.com/java/technologies/javaee/javaeetechnologies.html#javaee8
- Serveurs compatibles : https://www.oracle.com/java/technologies/compatibility-jsp.html

Jakarta EE 8 (Fondation Eclipse)
- Doc : https://javaee.github.io/glassfish/documentation
- Tutoriel : https://javaee.github.io/tutorial/
- API (javadoc) : https://jakarta.ee/specifications/platform/8/apidocs/
- Spécifications : https://jakarta.ee/specifications
- Serveurs compatibles : https://jakarta.ee/compatibility/#tab-8

Jakarta EE 9 (Fondation Eclipse)
- Doc : https://jakarta.ee/resources/#documentation
- Tutoriel : https://eclipse-ee4j.github.io/jakartaee-tutorial/
- API (javadoc) : https://jakarta.ee/specifications/platform/9/apidocs/
- Spécifications : https://jakarta.ee/specifications
- Serveurs compatibles : 
    - https://jakarta.ee/compatibility/#tab-9
    - https://jakarta.ee/compatibility/#tab-9-1
