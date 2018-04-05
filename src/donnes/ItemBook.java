package donnes;

import exception.BadEntry;

public class ItemBook extends Item {

	protected String auteur;
	protected int nbPages;

	/**
	 * Constructueur de la classe <i>ItemBook</i> 
	 * 
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
	 * 
	 * 
	 */
	public ItemBook(String titre, String genre, String auteur, int nbPages) throws  BadEntry{
		verification(titre, genre, auteur, nbPages);
		this.titre = titre.trim().toLowerCase();
		this.genre = genre.trim().toLowerCase();
		this.auteur = auteur.trim().toLowerCase();
		this.nbPages = Math.abs(nbPages);
		type = "Livre";
	}

	/**
	 * Verification des parametres du <i>Livre</i> 
	 * 
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
	 * </ul>
	 * <br>
	 * 
	 */
	private void verification(String titre, String genre, String auteur, int nbPages)  throws BadEntry {
		super.checkInfoItem(titre, genre);
		if(auteur == null) throw new BadEntry("L'auteur n'est pas instancie");
		else if(!(nbPages > Item.MIN_CHIFFRE_ITEM)) throw new BadEntry("Le nombre de pages n'est pas strictement positif");
	}

	@Override
	public String toString() {
		return "{"+"Titre --> "+titre
				+" | "+"Note --> "+calculNoteMoyenneActuelle()
				+" | "+"Genre --> "+genre
				+" | "+"Auteur --> "+auteur
				+" | "+"Nombre de pages --> "+nbPages+"}";
	}

	@Override
	public boolean equals(Object o) {
		boolean bool = false;
		if (o instanceof ItemBook) {
			bool = super.equals(o);
		}
		return bool;
	}
}
