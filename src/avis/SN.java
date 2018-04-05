package avis;

import java.util.LinkedList;

import donnes.Review;
import exception.BadEntry;
import exception.ItemFilmAlreadyExists;
import exception.ItemBookAlreadyExists;
import exception.MemberAlreadyExists;
import exception.NotItem;
import exception.NotMember;

/** 
 * <p>
 * <b>Syst�me de mutualisation d'opinions portant sur des domaines
 * vari�s (litt�rature, cin�ma, art, gastronomie, etc.) et non limit�s.</b>
 * </p>
 * <p>
 * L'acc�s aux items et aux opinions qui leurs sont associ�es
 * est public. La cr�ation d'item et le d�p�t d'opinion n�cessite en revanche 
 * que l'utilisateur cr�e son profil au pr�alable.
 * </p>
 * <p>
 * Lorsqu'une m�thode peut lever deux types d'exception, et que les conditions sont r�unies 
 * pour lever l'une et l'autre, rien ne permet de dire laquelle des deux sera effectivement lev�e.
 * </p>
 * <p>
 * Dans une version avanc�e (version 2), une opinion peut �galement
 * �tre �valu�e. Chaque membre se voit dans cette version d�cerner un "karma" qui mesure
 * la moyenne des notes portant sur les opinions qu'il a �mises.
 * L'impact des opinions entrant dans le calcul de la note moyenne attribu�e � un item
 * est pond�r� par le karma des membres qui les �mettent.
 * </p>
 */
public interface SN {

	/**
	 * Obtenir le nombre de membres du <i>SocialNetwork</i>
	 * 
	 * @return le nombre de membres
	 */
	public int nbMembers();


	/**
	 * Obtenir le nombre de films du <i>SocialNetwork</i>
	 * 
	 * @return le nombre de films
	 */
	public int nbFilms();

	/**
	 * Obtenir le nombre de livres du <i>SocialNetwork</i>
	 * 
	 * @return le nombre de livres
	 */
	public int nbBooks() ;



	/**
	 * Ajouter un nouveau membre au <i>SocialNetwork</i>
	 * 
	 * @param pseudo son pseudo
	 * @param password son mot de passe 
	 * @param profil un slogan choisi par le membre pour se d�finir
	 * 
	 * @throws BadEntry :
	 * <ul>
	 *  <li>  si le pseudo n'est pas instanci� ou a moins de 1 caract�re autre que des espaces .  </li>
	 *  <li>  si le password n'est pas instanci� ou a moins de 4 caract�res autres que des leadings or trailing blanks. </li>
	 *  <li>  si le profil n'est pas instanci�.  </li>
	 * </ul><br>       
	 * 
	 * @throws MemberAlreadyExists membre de m�me pseudo d�j� pr�sent dans le <i>SocialNetwork</i> (m�me pseudo : indiff�rent �  la casse  et aux leadings et trailings blanks)
	 * 
	 */
	public void addMember(String pseudo, String password, String profil) throws BadEntry, MemberAlreadyExists;



	/**
	 * Ajouter un nouvel item de film au <i>SocialNetwork</i> 
	 * 
	 * @param pseudo le pseudo du membre
	 * @param password le password du membre 
	 * @param titre le titre du film
	 * @param genre son genre (aventure, policier, etc.)
	 * @param realisateur le r�alisateur
	 * @param scenariste le sc�nariste
	 * @param duree sa dur�e en minutes
	 * 
	 * @throws BadEntry :
	 * <ul>
	 *  <li>  si le pseudo n'est pas instanci� ou a moins de 1 caract�re autre que des espaces .  </li>
	 *  <li>  si le password n'est pas instanci� ou a moins de 4 caract�res autres que des leadings or trailing blanks. </li>
	 *  <li>  si le titre n'est pas instanci� ou a moins de 1 caract�re autre que des espaces.  </li>
	 *  <li>  si le genre n'est pas instanci�. </li>
	 *  <li>  si le r�alisateur n'est pas instanci�. </li>
	 *  <li>  si le sc�nariste n'est pas instanci�. </li>
	 *  <li>  si la dur�e n'est pas strictement positive.  </li>
	 * </ul><br>       
	 * @throws NotMember : si le pseudo n'est pas celui d'un membre ou si le pseudo et le password ne correspondent pas.
	 * @throws ItemFilmAlreadyExists : item film de m�me titre  d�j� pr�sent (m�me titre : indiff�rent �  la casse  et aux leadings et trailings blanks)
	 * 
	 */
	public void addItemFilm(String pseudo, String password, String titre, String genre, String realisateur, String scenariste, int duree) throws BadEntry, NotMember, ItemFilmAlreadyExists;



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
	 *  <li>  si le pseudo n'est pas instanci� ou a moins de 1 caract�re autre que des espaces .  </li>
	 *  <li>  si le password n'est pas instanci� ou a moins de 4 caract�res autres que des leadings or trailing blanks. </li>
	 *  <li>  si le titre n'est pas instanci� ou a moins de 1 caract�re autre que des espaces.  </li>
	 *  <li>  si le genre n'est pas instanci�. </li>
	 *  <li>  si l'auteur n'est pas instanci�. </li>
	 *  <li>  si le nombre de pages n'est pas positif.  </li>
	 * </ul><br>       
	 * @throws NotMember : si le pseudo n'est pas celui d'un membre ou si le pseudo et le password ne correspondent pas.
	 * @throws ItemBookAlreadyExists item livre de m�me titre  d�j� pr�sent (m�me titre : indiff�rent � la casse  et aux leadings et trailings blanks)
	 * 
	 * 
	 */
	public void addItemBook(String pseudo, String password, String titre, String genre, String auteur, int nbPages) throws  BadEntry, NotMember, ItemBookAlreadyExists;



	/**
	 * Consulter les items du <i>SocialNetwork</i> par nom
	 * 
	 * @param nom son nom (eg. titre d'un film, d'un livre, etc.)
	 * 
	 * @throws BadEntry : si le nom n'est pas instanci� ou a moins de 1 caract�re autre que des espaces.  </li>
	 * 
	 * @return LinkedList <String> : la liste des repr�sentations textuelles de tous les items ayant ce titre. 
	 * Cette repr�sentation contiendra le titre, le genre, etc. et la note de l'item si celui-ci a �t� not�. 
	 * Retourne une liste vide si aucun item ne correspond.
	 */
	public LinkedList <String> consultItems(String nom) throws BadEntry;


	/**
	 * Donner son opinion sur un item film.
	 * Ajoute l'opinion de ce membre sur ce film au <i>SocialNetwork</i> 
	 * Si une opinion de ce membre sur ce film  pr�existe, elle est mise � jour avec ces nouvelles valeurs.
	 * 
	 * @param pseudo pseudo du membre �mettant l'opinion
	 * @param password son mot de passe
	 * @param titre titre du film  concern�
	 * @param note la note qu'il donne au film 
	 * @param commentaire ses commentaires
	 * 
	 * @throws BadEntry :
	 * <ul>
	 *  <li>  si le pseudo n'est pas instanci� ou a moins de 1 caract�re autre que des espaces .  </li>
	 *  <li>  si le password n'est pas instanci� ou a moins de 4 caract�res autres que des leadings or trailing blanks. </li>
	 *  <li>  si le titre n'est pas instanci� ou a moins de 1 caract�re autre que des espaces.  </li>
	 *  <li>  si la note n'est pas comprise entre 0.0 et 5.0. </li>
	 *  <li>  si le commentaire n'est pas instanci�. </li>
	 * </ul><br>       
	 * @throws NotMember : si le pseudo n'est pas celui d'un membre ou si le pseudo et le password ne correspondent pas.
	 * @throws NotItem : si le titre n'est pas le titre d'un film.
	 * 
	 * @return la note moyenne des notes de ce film
	 */
	public float reviewItemFilm(String pseudo, String password, String titre, float note, String commentaire) throws BadEntry, NotMember, NotItem;

	/**
	 * Donner son opinion sur un item livre.
	 * Ajoute l'opinion de ce membre sur ce livre au <i>SocialNetwork</i> 
	 * Si une opinion de ce membre sur ce livre  pr�existe, elle est mise � jour avec ces nouvelles valeurs.
	 * 
	 * @param pseudo pseudo du membre �mettant l'opinion
	 * @param password son mot de passe
	 * @param titre titre du livre  concern�
	 * @param note la note qu'il donne au livre 
	 * @param commentaire ses commentaires
	 * 
	 * @throws BadEntry :
	 * <ul>
	 *  <li>  si le pseudo n'est pas instanci� ou a moins de 1 caract�re autre que des espaces .  </li>
	 *  <li>  si le password n'est pas instanci� ou a moins de 4 caract�res autres que des leadings or trailing blanks. </li>
	 *  <li>  si le titre n'est pas instanci� ou a moins de 1 caract�re autre que des espaces.  </li>
	 *  <li>  si la note n'est pas comprise entre 0.0 et 5.0. </li>
	 *  <li>  si le commentaire n'est pas instanci�. </li>
	 * </ul><br>       
	 * @throws NotMember : si le pseudo n'est pas celui d'un membre ou si le pseudo et le password ne correspondent pas.
	 * @throws NotItem : si le titre n'est pas le titre d'un livre.
	 * 
	 * @return la note moyenne des notes de ce livre
	 */
	public float reviewItemBook(String pseudo, String password, String titre, float note, String commentaire) throws BadEntry, NotMember, NotItem;

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
	 *  <li>  si le pseudo n'est pas instancié ou a moins de 1 caractère autre que des espaces.</li>
	 *  <li>  si le password n'est pas instancié ou a moins de 4 caractères autres que des leadings or trailing blanks.</li>
	 *  <li>  si la note n'est pas comprise entre 0.0 et 5.0.</li>
	 *  <li>  si le commentaire n'est pas instancié.</li>
	 * </ul><br>       
	 * @throws NotMember : si le pseudo n'est pas celui d'un membre ou si le pseudo et le password ne correspondent pas.
	 * 
	 * @return le karma du membre noté
	 */
	public float reviewOpinion(String pseudo, String password, String pseudoNote, String passwordNote, String titre, String typeItem, float note) throws BadEntry, NotMember, NotItem;

	/**
	 * Obtenir une repr�sentation textuelle du <i>SocialNetwork</i>.
	 * 
	 * @return la cha�ne de caract�res repr�sentation textuelle du <i>SocialNetwork</i> 
	 */
	public String toString();
	
}