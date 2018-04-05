package avis;

import java.util.LinkedList;

import donnes.Item;
import donnes.ItemBook;
import donnes.ItemFilm;
import donnes.Member;
import donnes.Review;
import exception.BadEntry;
import exception.ItemBookAlreadyExists;
import exception.ItemFilmAlreadyExists;
import exception.MemberAlreadyExists;
import exception.NotItem;
import exception.NotMember;

/** 
 * @author A. Beugnard, 
 * @author G. Ouvradou
 * @author B. Prou
 * @date fevrier - octobre 2017
 * @version V0.7
 */


/** 
 * <p>
 * <b>Systeme de mutualisation d'opinions portant sur des domaines
 * varies (litterature, cinema, art, gastronomie, etc.) et non limites.</b>
 * </p>
 * <p>
 * L'acces aux items et aux opinions qui leurs sont associees
 * est public. La creation d'item et le depet d'opinion necessite en revanche 
 * que l'utilisateur cree son profil au prealable.
 * </p>
 * <p>
 * Dans une version avancee (version 2), une opinion peut egalement
 * etre evaluee. Chaque membre se voit dans cette version decerner un "karma" qui mesure
 * la moyenne des notes portant sur les opinions qu'il a emises.
 * L'impact des opinions entrant dans le calcul de la note moyenne attribuee ae un item
 * est pondere par le karma des membres qui les emettent.
 * </p>
 */

public class SocialNetwork implements SN{

	protected SocialNetworkOperation sno = new SocialNetworkOperation();
	protected LinkedList <Member> lesmembers = new LinkedList <Member>();
	protected LinkedList <ItemFilm> lesfilms = new LinkedList <ItemFilm>();
	protected LinkedList <ItemBook> leslivres = new LinkedList <ItemBook>();


	/**
	 * constructeur de <i>SocialNetwok</i> 
	 * 
	 */
	public SocialNetwork() {
	}

	/**
	 * Obtenir le nombre de membres du <i>SocialNetwork</i>
	 * 
	 * @return le nombre de membres
	 */
	public int nbMembers() {
		return lesmembers.size();
	}

	/**
	 * Obtenir le nombre de films du <i>SocialNetwork</i>
	 * 
	 * @return le nombre de films
	 */
	public int nbFilms() {
		return lesfilms.size();
	}

	/**
	 * Obtenir le nombre de livres du <i>SocialNetwork</i>
	 * 
	 * @return le nombre de livres
	 */
	public int nbBooks() {
		return leslivres.size();
	}


	/**
	 * Ajouter un nouveau membre au <i>SocialNetwork</i>
	 * 
	 * @param pseudo son pseudo
	 * @param password son mot de passe 
	 * @param profil son profil
	 * 
	 * @throws BadEntry :
	 * <ul>
	 *  <li>  si le pseudo n'est pas instancie ou a moins de 1 caractere autre que des espaces .  </li>
	 *  <li>  si le password n'est pas instancie ou a moins de 4 caracteres autres que des leadings or trailing blanks. </li>
	 *  <li>  si le profil n'est pas instancie.  </li>
	 * </ul><br>       
	 * 
	 * @throws MemberAlreadyExists membre de meme pseudo dejae present dans le <i>SocialNetwork</i> (meme pseudo : indifferent ae  la casse  et aux leadings et trailings blanks)
	 * 
	 */
	public void addMember(String pseudo, String password, String profil) throws BadEntry, MemberAlreadyExists  {
		Member m = new Member(pseudo, password, profil);
		if(lesmembers.contains(m)) {
			throw new MemberAlreadyExists();
		} else {
			lesmembers.add(m);
		}
	}


	/**
	 * Ajouter un nouvel item de film au <i>SocialNetwork</i> 
	 * 
	 * @param pseudo le pseudo du membre
	 * @param password le password du membre 
	 * @param titre le titre du film
	 * @param genre son genre (aventure, policier, etc.)
	 * @param realisateur le realisateur
	 * @param scenariste le scenariste
	 * @param duree sa duree en minutes
	 * 
	 * @throws BadEntry :
	 * <ul>
	 *  <li>  si le pseudo n'est pas instancie ou a moins de 1 caractere autre que des espaces .  </li>
	 *  <li>  si le password n'est pas instancie ou a moins de 4 caracteres autres que des leadings or trailing blanks. </li>
	 *  <li>  si le titre n'est pas instancie ou a moins de 1 caractere autre que des espaces.  </li>
	 *  <li>  si le genre n'est pas instancie. </li>
	 *  <li>  si le realisateur n'est pas instancie. </li>
	 *  <li>  si le scenariste n'est pas instancie. </li>
	 *  <li>  si la duree n'est pas positive.  </li>
	 * </ul><br>       
	 * @throws NotMember : si le pseudo n'est pas celui d'un membre ou si le pseudo et le password ne correspondent pas.
	 * @throws ItemFilmAlreadyExists : item film de meme titre  dejae present (meme titre : indifferent ae  la casse  et aux leadings et trailings blanks)
	 * 
	 */
	public void addItemFilm(String pseudo, String password, String titre, String genre, String realisateur, String scenariste, int duree) throws BadEntry, NotMember, ItemFilmAlreadyExists {
		ItemFilm film = new ItemFilm(titre, genre, realisateur, scenariste, duree);
		if(lesfilms.contains(film)) {
			throw new ItemFilmAlreadyExists();
		} else {
			Member membre = (Member) sno.retourneElement(new Member(pseudo, password, ""), lesmembers);
			if(membre == null) {
				throw new NotMember("Le membre n'existe pas dans liste");
			} else if(!membre.getPassword().equalsIgnoreCase(password)) {
				throw new NotMember("Le mot de passe du membre n'est pas celui enregistre dans liste");
			} else {
				lesfilms.add(film);
				sno.lienMembreItem(membre, film);
			}
		}
	}

	/**
	 * Ajouter un nouvel item de livre au <i>SocialNetwork</i> 
	 * 
	 * @param pseudo le pseudo du membre
	 * @param password le password du membre 
	 * @param titre le titre du livre
	 * @param genre son genre (roman, essai, etc.)
	 * @param auteur l'auteur
	 * @param nbPages le nombre de pages
	 * 
	 * @throws BadEntry :
	 * <ul>
	 *  <li>  si le pseudo n'est pas instancie ou a moins de 1 caractere autre que des espaces .  </li>
	 *  <li>  si le password n'est pas instancie ou a moins de 4 caracteres autres que des leadings or trailing blanks. </li>
	 *  <li>  si le titre n'est pas instancie ou a moins de 1 caractere autre que des espaces.  </li>
	 *  <li>  si le genre n'est pas instancie. </li>
	 *  <li>  si l'auteur n'est pas instancie. </li>
	 *  <li>  si le nombre de pages n'est pas positif.  </li>
	 * </ul><br>       
	 * @throws NotMember : si le pseudo n'est pas celui d'un membre ou si le pseudo et le password ne correspondent pas.
	 * @throws ItemBookAlreadyExists item livre de meme titre  dejae present (meme titre : indifferent ae la casse  et aux leadings et trailings blanks)
	 * 
	 * 
	 */
	public void addItemBook(String pseudo, String password, String titre, String genre, String auteur, int nbPages) throws  BadEntry, NotMember, ItemBookAlreadyExists{
		ItemBook livre = new ItemBook(titre, genre, auteur, nbPages);
		if(leslivres.contains(livre)) {
			throw new ItemBookAlreadyExists();
		} else {
			Member membre = (Member) sno.retourneElement(new Member(pseudo, password, ""), lesmembers);
			if(membre == null) {
				throw new NotMember("Le membre n'existe pas dans liste");
			} else if(!membre.getPassword().equalsIgnoreCase(password)) {
				throw new NotMember("Le mot de passe du membre n'est pas celui enregistre dans liste");
			} else {
				leslivres.add(livre);
				sno.lienMembreItem(membre, livre);
			}
		}
	}

	/**
	 * Consulter les items du <i>SocialNetwork</i> par nom
	 * 
	 * @param nom son nom (eg. titre d'un film, d'un livre, etc.)
	 * 
	 * @throws BadEntry : si le nom n'est pas instancie ou a moins de 1 caractere autre que des espaces.  </li>
	 * 
	 * @return LinkedList <String> : la liste des representations de tous les items ayant ce nom 
	 * Cette representation contiendra la note de l'item s'il a ete note.
	 * (une liste vide si aucun item ne correspond) 
	 */
	public LinkedList <String> consultItems(String nom) throws BadEntry {
		LinkedList <String> listesDesItemsAvecLeNom = new LinkedList <String> ();
		//Recherche des films avec ce nom
		ItemFilm film = (ItemFilm) sno.retourneElement(new ItemFilm(nom, "", "", "",1), lesfilms);
		if(film != null) {
			listesDesItemsAvecLeNom.add(film.getDescription());
		}
		//Recherche des films avec ce nom
		ItemBook livre = (ItemBook) sno.retourneElement(new ItemBook(nom, "", "",1), leslivres);
		if(livre != null) {
			listesDesItemsAvecLeNom.add(livre.getDescription());
		}
		return listesDesItemsAvecLeNom;
	}



	/**
	 * Donner son opinion sur un item film.
	 * Ajoute l'opinion de ce membre sur ce film au <i>SocialNetwork</i> 
	 * Si une opinion de ce membre sur ce film  preexiste, elle est mise ae jour avec ces nouvelles valeurs.
	 * 
	 * @param pseudo pseudo du membre emettant l'opinion
	 * @param password son mot de passe
	 * @param titre titre du film  concerne
	 * @param note la note qu'il donne au film 
	 * @param commentaire ses commentaires
	 * 
	 * @throws BadEntry :
	 * <ul>
	 *  <li>  si le pseudo n'est pas instancie ou a moins de 1 caractere autre que des espaces .  </li>
	 *  <li>  si le password n'est pas instancie ou a moins de 4 caracteres autres que des leadings or trailing blanks. </li>
	 *  <li>  si le titre n'est pas instancie ou a moins de 1 caractere autre que des espaces.  </li>
	 *  <li>  si la note n'est pas comprise entre 0.0 et 5.0. </li>
	 *  <li>  si le commentaire n'est pas instancie. </li>
	 * </ul><br>       
	 * @throws NotMember : si le pseudo n'est pas celui d'un membre ou si le pseudo et le password ne correspondent pas.
	 * @throws NotItem : si le titre n'est pas le titre d'un film.
	 * 
	 * @return la note moyenne des notes sur ce film  
	 */
	public float reviewItemFilm(String pseudo, String password, String titre, float note, String commentaire) throws BadEntry, NotMember, NotItem {
		float noteMoyenne = 0.0f;
		Member membre = (Member) sno.retourneElement(new Member(pseudo, password, ""), lesmembers);
		ItemFilm film = (ItemFilm) sno.retourneElement(new ItemFilm(titre, "", "", "",1), lesfilms);		
		if(film == null) {
			throw new NotItem("L'item n'existe pas dans liste");
		} else if(membre == null) {
			throw new NotMember("Le membre n'existe pas dans liste");
		} else if(!membre.getPassword().equalsIgnoreCase(password)) {
			throw new NotMember("Le mot de passe du membre n'est pas celui enregistre dans liste");
		} else {
			new Review(film, membre, note, commentaire);
		}
		noteMoyenne = film.calculNoteMoyenneActuelle();
		return noteMoyenne;
	}



	/**
	 * Donner son opinion sur un item livre.
	 * Ajoute l'opinion de ce membre sur ce livre au <i>SocialNetwork</i> 
	 * Si une opinion de ce membre sur ce livre  preexiste, elle est mise ae jour avec ces nouvelles valeurs.
	 * 
	 * @param pseudo pseudo du membre emettant l'opinion
	 * @param password son mot de passe
	 * @param titre titre du livre  concerne
	 * @param note la note qu'il donne au livre 
	 * @param commentaire ses commentaires
	 * 
	 * @throws BadEntry :
	 * <ul>
	 *  <li>  si le pseudo n'est pas instancie ou a moins de 1 caractere autre que des espaces .  </li>
	 *  <li>  si le password n'est pas instancie ou a moins de 4 caracteres autres que des leadings or trailing blanks. </li>
	 *  <li>  si le titre n'est pas instancie ou a moins de 1 caractere autre que des espaces.  </li>
	 *  <li>  si la note n'est pas comprise entre 0.0 et 5.0. </li>
	 *  <li>  si le commentaire n'est pas instancie. </li>
	 * </ul><br>       
	 * @throws NotMember : si le pseudo n'est pas celui d'un membre ou si le pseudo et le password ne correspondent pas.
	 * @throws NotItem : si le titre n'est pas le titre d'un livre.
	 * 
	 * @return la note moyenne des notes sur ce livre
	 */
	public float reviewItemBook(String pseudo, String password, String titre, float note, String commentaire) throws BadEntry, NotMember, NotItem {
		float noteMoyenne = 0.0f;
		Member membre = (Member) sno.retourneElement(new Member(pseudo, password, ""), lesmembers);
		ItemBook livre = (ItemBook) sno.retourneElement(new ItemBook(titre, "", "",1), leslivres);		
		if(livre == null) {
			throw new NotItem("L'item n'existe pas dans liste");
		} else if(membre == null) {
			throw new NotMember("Le membre n'existe pas dans liste");
		} else if(!membre.getPassword().equalsIgnoreCase(password)) {
			throw new NotMember("Le mot de passe du membre n'est pas celui enregistre dans liste");
		} else {
			new Review(livre, membre, note, commentaire);

		}
		noteMoyenne = livre.calculNoteMoyenneActuelle();
		return noteMoyenne;
	}

	//Noter un avis
	/**
	 * Donner son opinion sur l'opinion d'un autre membre.
	 * Ajoute l'opinion de ce membre au karma de l'autre membre du <i>SocialNetwork</i> 
	 * Si le karma de l'autre membre a déjà une valeur, il est mise à jour avec ces nouvelles valeurs.
	 * 
	 * @param pseudo pseudo du membre émettant l'opinion
	 * @param password son mot de passe
	 * @param review Opinion à noter
	 * @param note la note qu'il donne au membre 
	 * 
	 * @throws BadEntry :
	 * <ul>
	 *  <li>  si le pseudo n'est pas instancié ou a moins de 1 caractère autre que des espaces .  </li>
	 *  <li>  si le password n'est pas instancié ou a moins de 4 caractères autres que des leadings or trailing blanks. </li>
	 *  <li>  si la note n'est pas comprise entre 0.0 et 5.0. </li>
	 *  <li>  si le commentaire n'est pas instancié. </li>
	 * </ul><br>       
	 * @throws NotMember : si le pseudo n'est pas celui d'un membre ou si le pseudo et le password ne correspondent pas.
	 * 
	 * @return le karma du membre noté
	 */
	@Override
	public float reviewOpinion(String pseudo, String password, String pseudoNote, String passwordNote, String titre,
			String typeItem, float note) throws BadEntry, NotMember, NotItem {
		Item itemNote = null;
		Review review = null;
		Member membre = (Member) sno.retourneElement(new Member(pseudo, password, ""), lesmembers);
		//Le membre dont l'avis est notee
		Member membreNote = (Member) sno.retourneElement(new Member(pseudoNote, passwordNote, ""), lesmembers);
		if(typeItem.equals("livre")) {
			itemNote = (ItemBook) sno.retourneElement(new ItemBook(titre, "", "",1), leslivres);	
		} else if(typeItem.equals("film")){
			itemNote = (ItemFilm) sno.retourneElement(new ItemFilm(titre, "", "", "",1), lesfilms);
		}
		if(itemNote == null) {
			throw new NotItem("L'item n'existe pas dans liste");
		} else if(membre == null) {
			throw new NotMember("Le membre notant n'existe pas dans liste");
		} else if(membreNote == null) {
			throw new NotMember("Le membre note n'existe pas dans liste");
		} else {
			review = (Review) sno.retourneElement(new Review(itemNote, membre, 1, "commentaire"), membreNote.getNotations());
		}
		if(review == null) {
			throw new BadEntry("L'avis note n'a pu etre retrouve");
		}
		review.noterReview(membre, note);
		membreNote.calculerKarma();
		return membreNote.getKarma();
	}

	/**
	 * Obtenir une representation textuelle du <i>SocialNetwork</i>.
	 * 
	 * @return la chaine de caracteres representation textuelle du <i>SocialNetwork</i> 
	 */
	@Override
	public String toString() {
		String description;
		String liste_membre = "Liste des membres\n";
		String liste_film = "Liste des films\n";
		String liste_livre = "Liste des livres\n";
		for(Member m : lesmembers) {
			liste_membre += m+"\n";
		}
		for(ItemFilm f : lesfilms) {
			liste_film += f+"\n";
		}
		for(ItemBook l: leslivres) {
			liste_livre +=l+"\n";
		}
		description = liste_membre+"\n"+liste_film+"\n"+liste_livre+"\n";
		return description;
	}

	

}
