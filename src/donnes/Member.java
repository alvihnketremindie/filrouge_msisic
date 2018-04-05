package donnes;

import java.util.LinkedList;

import exception.BadEntry;

public class Member {

	protected String pseudo;
	protected String password;
	protected String profil;

	protected static int MIN_CHAR_PSEUDO = 1;
	protected static int MIN_CHAR_PASSWD = 4;

	
	protected LinkedList <Item> sesitems = new LinkedList <Item>();
	protected LinkedList <Review> sesNotationsSurDesItem = new LinkedList <Review>();
	
	protected float karma; //Représente le Karma d'un membre
	/**
	 * Constructeur de la classe <i>Member</i> 
	 * 
	 * @param pseudo le pseudo du membre
	 * @param password le password du membre 
	 * @param profil le profil du membre
	 * 
	 * @throws BadEntry :
	 * <ul>
	 *  <li>  si le pseudo n'est pas instancie ou a moins de 1 caractere autre que des espaces .  </li>
	 *  <li>  si le password n'est pas instancie ou a moins de 4 caracteres autres que des leadings or trailing blanks. </li>
	 *  <li>  si le profil n'est pas instancie.  </li>
	 * </ul><br>
	 * 
	 */
	public Member(String pseudo, String password, String profil) throws BadEntry {
		verification(pseudo, password, profil);
		this.pseudo = pseudo.trim().toLowerCase();
		this.profil = profil.trim().toLowerCase();
		this.password = password.trim();
		
		//Initialisation du karma à 1
		karma = 1.0f;
	}

	/**
	 * Verification des parametres du <i>Membre</i> 
	 * 
	 * @param pseudo le pseudo du membre
	 * @param password le password du membre 
	 * @param profil le profil du membre
	 * 
	 * @throws BadEntry :
	 * <ul>
	 *  <li>  si le pseudo n'est pas instancie ou a moins de 1 caractere autre que des espaces .  </li>
	 *  <li>  si le password n'est pas instancie ou a moins de 4 caracteres autres que des leadings or trailing blanks. </li>
	 *  <li>  si le profil n'est pas instancie.  </li>
	 * </ul><br>
	 * 
	 */
	private void verification(String pseudo, String password, String profil)  throws BadEntry {
		if(pseudo == null) throw new BadEntry("Le pseudo n'est pas instancie");
		else if(pseudo.trim().length() < MIN_CHAR_PSEUDO) throw new BadEntry("Le pseudo a moins de "+MIN_CHAR_PSEUDO+" caractere(s) autre que des espaces");
		else if(password == null) throw new BadEntry("Le password n'est pas instancie");
		else if(password.trim().length() < MIN_CHAR_PASSWD) throw new BadEntry("Le pseudo a moins de "+MIN_CHAR_PASSWD+" caractere(s) autre que des espaces");
		else if(profil == null) throw new BadEntry("Le profil n'est pas instancie");
	}

	@Override
	public boolean equals(Object o) {
		boolean bool = false;
		if (o instanceof Member) {
			Member m = (Member) o;
			bool = m.getPseudo().equalsIgnoreCase(pseudo);
		}
		return bool;
	}

	/**
	 * Retourne le pseudo du membre
	 * 
	 * @return le pseudo  
	 */
	public String getPseudo() {
		return this.pseudo.trim().toLowerCase();
	}

	/**
	 * Retourne le mot de passe du membre
	 * 
	 * @return le password  
	 */
	public String getPassword() {
		return this.password.trim().toLowerCase();
	}
	
	public LinkedList <Review> getNotations() {
		return sesNotationsSurDesItem;
	}

	/**
	 * Ajoute l'item dans le reseau et indique le membre comme celui ayant ajouter l'item  
	 */
	public void ajoutItemDansLeSN(Item item) {
		sesitems.add(item);
	}

	/**
	 * Ajoute la notation à l'item par le membre  
	 */
	public void ajoutNouvelleNotationDuMembre(Review review) {
		sesNotationsSurDesItem.add(review);
	}
	
	@Override
	public String toString() {
		return "{"+"Pseudo --> "+pseudo
				+" | "+"Password --> "+password
				+" | "+"Profil --> "+profil+"}";
	}
	
	//Calculer la valeur du karma
	/**
	 * Calcul le karma du membre  
	 */
	public void calculerKarma(){

		if(sesNotationsSurDesItem.size() <= 0) return;
		
		float newKarma = 0.0f;
		for(Review notation : sesNotationsSurDesItem){
			newKarma += notation.getMoyenne();
		}
		newKarma /= sesNotationsSurDesItem.size();
		
		karma = newKarma;
	}
	
	//Retourne la valeur du Karma
	/**
	 * Retourne la valeur du Karma du membre
	 * 
	 * @return le Karma  
	 */
	public float getKarma(){
		return karma;
	}
}
