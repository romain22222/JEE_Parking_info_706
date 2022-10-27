# Un exemple minimaliste d'une application JavaEE 8 (web profile) #

Pour faire simple l'application consiste à faire des mesures de température dans une maison connectée (le tout très simplifié).

Une mesure comprendra :
- un id (identifiant unique autogénéré)
- une pièce (nom de la pièce où la mesure a été faite)
- une température (en degré celsius)
- une date de prise de la mesure

On peut :
- ajouter des mesures,
- consulter les mesures (à partir de l'id de la mesure ou du nom de la pièce)

Les mesures sont stockées dans une base de données. On y accède à travers une entité (JPA).

Un EJB session sans état (stateless) sert de façade pour les opérations de création/recherche des mesures.

Un client WEB permet de réaliser l'ensemble des opérations : ajout d'une mesure, recherche et affichage des mesures.
Ce client comporte trois pages JSP et plusieurs servlet (1 par opération). La connexion au bean session se fait en local dans les servlet sans passer par une interface.

## Organisation du projet ##

### Partie JPA ###

Il y a un seul objet persistant (stockage des mesures de temperature) defini à l'aide de la classe `Mesure` :  
- <a href="src/main/java/fr/usmb/m2isc/mesure/jpa/Mesure.java" >src/main/java/fr/usmb/m2isc/mesure/jpa/Mesure.java</a> (implantation de l'entité _Mesure_ (entité JPA))
- <a href="src/main/resources/META-INF/persistence.xml" >META-INF/persistence.xml</a> (descripteur standard JPA)

### Partie EBJ ###

L'accès aux mesures se fait au travers d'un EJB (Enterprise JavaBean) sans état (et utilisable sans interface - cf. annotation `@LocalBean`) :  
- <a href="src/main/java/fr/usmb/m2isc/mesure/ejb/MesureEJB.java" >src/main/java/fr/usmb/m2isc/mesure/ejb/MesureEJB.java</a> EJB sans état (Stateless)
- src/main/resources/META-INF/ejb-jar.xml (descripteur standard pour les Enterprise Java Beans -- optionnel dans les dernières versions de javaEE)

### Partie WEB  ###

Différentes servlet permettent permettent d'executer les actions (ajout de mesures, recherche de mesures existantes (par id ou par pièce)) :  
- <a href="src/main/java/fr/usmb/m2isc/mesure/servlet/AddMesureServlet.java" >src/main/java/fr/usmb/m2isc/mesure/servlet/AddMesureServlet.java</a> (ajout d'une mesure)
- <a href="src/main/java/fr/usmb/m2isc/mesure/servlet/ShowMesureServlet.java" >src/main/java/fr/usmb/m2isc/mesure/servlet/ShowMesureServlet.java</a> (affichage d'une mesure à partir de son id)
- <a href="src/main/java/fr/usmb/m2isc/mesure/servlet/ShowMesuresServlet.java" >src/main/java/fr/usmb/m2isc/mesure/servlet/ShowMesuresServlet.java</a> (affichage des mesures d'une pièce)
- <a href="src/main/java/fr/usmb/m2isc/mesure/servlet/ShowLastMesureServlet.java" >src/main/java/fr/usmb/m2isc/mesure/servlet/ShowLastMesureServlet.java</a> (affichage de la dernière mesure d'une pièce)
- <a href="src/main/java/fr/usmb/m2isc/mesure/servlet/ShowLastMesuresServlet.java" >src/main/java/fr/usmb/m2isc/mesure/servlet/ShowLastMesuresServlet.java</a> (affichage de la dernière mesure de chaque pièce)
- src/main/webapp/WEB-INF/web.xml (descripteur standard de l'application Web -- optionnel dans les dernières versions de JavaEE)

L'affichage des mesures se fait à la fin des servlet en redirigeant les requêtes vers des pages JSP (JavaServer Pages) :  
- <a href="src/main/webapp/showMesure.jsp" >src/main/webapp/showMesure.jsp</a> (affichage d'une mesure)
- <a href="src/main/webapp/showMesures.jsp" >src/main/webapp/showMesures.jsp</a> (affichage d'une liste de mesures)
- <a href="src/main/webapp/index.jsp" >src/main/webapp/index.jsp</a> (page d'accueil)

## Fonctionnement ##

### Manipulation des objets persistants dans l'EJB ###

Toutes les manipulations sur les objets persistants se font dans l'EJB en utilisant l'__entity manager__ correspondant à l'__unité de persistance__ des objets persistants manipulés. 

Dans l'EJB on utilise l'annotation `@PersistenceContext` pour récupérer auprès du serveur JavaEE l'_entity manager_ désiré.  

```java
@Stateless
@LocalBean
public class MesureEJB {
	@PersistenceContext
	private EntityManager em;
```

L'_entity manager_ est ensuite utilisé dans les méthodes de l'EJB pour ajouter des mesures :  

```java
	public Mesure addMesure(String piece, double val) {
		Mesure m = new Mesure(piece, val);
		em.persist(m);
		return m;
	}
```

où retrouver des mesures dans la base :  

```java
	public Mesure findMesure(long id) {
		Mesure m = em.find(Mesure.class, id);
		return m;
	}
```
```java
	public List<Mesure> findMesures(String piece) {
		TypedQuery<Mesure> rq = em.createQuery("SELECT m FROM Mesure m WHERE m.piece = :piece ORDER BY m.dateMesure ASC", Mesure.class);
		rq.setParameter("piece", piece);
		return rq.getResultList();
	}
```

### Utilisation de l'EJB dans les servlet ###

Dans les _servlet_ on utilise l'annotation `@EJB` pour obtenir une référence de l'_EJB session_ :

```java
@WebServlet("/CreerCompteServlet")
public class CreerCompteServlet extends HttpServlet {
	@EJB
	private Operation ejb;
```

Pour l'affichage, dans les servlet, on ajoute dans la requete http les objets java à afficher et on redirige les requêtes vers les pages JSP :  

```java
// appel de l'ejb
List<Mesure> l = ejb.findMesures(piece);		
// ajout de la resure dans la requete
request.setAttribute("mesures",l);
// transfert a la JSP d'affichage
request.getRequestDispatcher("/showMesures.jsp").forward(request, response);
```


## Packaging ##

Comme c'est une application ciblant le profil web de la spécification JavaEE 8, tout (Servlet/JSP/EJB/entité JPA) peut être empaqueté dans la même archive web (Thermo.war).

### L'application WEB est entièrement packagée dans un fichier d'archive war ###

Les resources web (pages html, JSP, feuilles de styles CSS, etc. sont ajoutées à la racine du fichier d'archive.

Deux dossiers dossiers spécifiques, `META-INF` et `WEB-INF` dans l'archive war permettent de configurer l'application web et gérer les parties en java.
<pre>
Thermo.war
  |-- <a href="src/main/webapp/index.jsp" >index.jsp</a> (page d'accueil -- formulaires html permettant de d'ajouter ou rechercher des mesures)
  |-- <a href="src/main/webapp/showMesure.jsp" >showMesure.jsp</a> (affichage d'une mesure)
  |-- <a href="src/main/webapp/showMesures.jsp" >showMesures.jsp</a> (affichage d'une liste de mesures)
  |-- <a href="src/main/webapp/default.css" >default.css</a> (feuille de style css)
  |-- <a href="src/main/webapp/META-INF/MANIFEST.MF" >META-INF/MANIFEST.MF</a> (java manifeste)
  |-- WEB-INF/web.xml (descripteur standard de l'application Web -- optionnel dans les dernières versions de JavaEE)
  |-- WEB-INF/lib (librairies supplémentaires java utilisées par les classes java)
  |-- WEB-INF/classes (classes java : servlet / EJB / entités JPA)
                |-- <a href="src/main/resources/META-INF/persistence.xml" >META-INF/persistence.xml</a> (descripteur standard JPA)
                |-- META-INF/orm.xml (descripteur optionnel pour le mapping objet-relationnel -- absent ici)
                |-- <a href="src/main/java/fr/usmb/m2isc/mesure/servlet/AddMesureServlet.java" >fr/usmb/m2isc/mesure/servlet/AddMesureServlet.class</a> (ajout d'une mesure)
                |-- <a href="src/main/java/fr/usmb/m2isc/mesure/servlet/ShowMesureServlet.java" >fr/usmb/m2isc/mesure/servlet/ShowMesureServlet.class</a> (affichage d'une mesure à partir de son id)
                |-- <a href="src/main/java/fr/usmb/m2isc/mesure/servlet/ShowMesuresServlet.java" >fr/usmb/m2isc/mesure/servlet/ShowMesuresServlet.class</a> (affichage des mesures d'une pièce)
                |-- <a href="src/main/java/fr/usmb/m2isc/mesure/servlet/ShowLastMesureServlet.java" >fr/usmb/m2isc/mesure/servlet/ShowLastMesureServlet.class</a> (affichage de la dernière mesure d'une pièce)
                |-- <a href="src/main/java/fr/usmb/m2isc/mesure/servlet/ShowLastMesuresServlet.java" >fr/usmb/m2isc/mesure/servlet/ShowLastMesuresServlet.class</a> (affichage de la dernière mesure de chaque pièce)
                |-- <a href="src/main/java/fr/usmb/m2isc/mesure/ejb/MesureEJB.java" >fr/usmb/m2isc/mesure/ejb/MesureEJB.class</a> (EJB sans état)
                |-- <a href="src/main/java/fr/usmb/m2isc/mesure/jpa/Mesure.java" >fr/usmb/m2isc/mesure/jpa/Mesure.class</a> (entité Mesure (entité JPA))
</pre>

## Usage : ##

Pour voir les sources, il suffit de cloner le projet git et de l'importer (sous forme de projet gradle) dans votre IDE favori. 
Cela devrait permettre la création d'un projet (ou module) web.

La compilation des classes et la création de l'archive war peut se faire via gradle en appelant la tâche `build` sur le projet principal.

Pour utiliser l'exemple il suffit de déployer le fichier _Thermo.war_ sur un serveur JavaEE 8. 
Le client Web est alors déployé avec le préfixe _/Thermo_.

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
