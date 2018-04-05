package donnes;

import exception.BadEntry;

public class ItemFilm extends Item {

	protected String scenariste;
	protected String realisateur;
	protected int duree;

	/**
	 * Constructueur de la classe <i>ItemFilm</i> 
	 * 
	 * @param titre le titre du film
	 * @param genre son genre (aventure, policier, etc.)
	 * @param realisateur le realisateur
	 * @param scenariste le scenariste
	 * @param duree sa duree en minutes
	 * 
	 * @throws BadEntry :
	 * <ul>
	 *  <li>  si le titre n'est pas instancie ou a moins de 1 caractere autre que des espaces.  </li>
	 *  <li>  si le genre n'est pas instancie. </li>
	 *  <li>  si le realisateur n'est pas instancie. </li>
	 *  <li>  si le scenariste n'est pas instancie. </li>
	 *  <li>  si la duree n'est pas positive.  </li>
	 * </ul><br>
	 * 
	 */
	public ItemFilm(String titre, String genre, String realisateur, String scenariste, int duree) throws  BadEntry{
		verification(titre, genre, realisateur, scenariste, duree);
		this.titre = titre.trim().toLowerCase();
		this.genre = genre.trim().toLowerCase();
		this.realisateur = realisateur.trim().toLowerCase();
		this.scenariste = scenariste.trim().toLowerCase();
		this.duree = Math.abs(duree);
		type = "Film";
	}

	/**
	 * Verification des parametres du <i>Film</i> 
	 * 
	 * @param titre le titre du film
	 * @param genre son genre (aventure, policier, etc.)
	 * @param realisateur le realisateur
	 * @param scenariste le scenariste
	 * @param duree sa duree en minutes
	 * 
	 * @throws BadEntry :
	 * <ul>
	 *  <li>  si le titre n'est pas instancie ou a moins de 1 caractere autre que des espaces.  </li>
	 *  <li>  si le genre n'est pas instancie. </li>
	 *  <li>  si le realisateur n'est pas instancie. </li>
	 *  <li>  si le scenariste n'est pas instancie. </li>
	 *  <li>  si la duree n'est pas positive.  </li>
	 * </ul>
	 * <br>
	 * 
	 */
	private void verification(String titre, String genre, String realisateur, String scenariste, int duree)  throws BadEntry {
		super.checkInfoItem(titre, genre);
		if(realisateur == null) throw new BadEntry("Le realisateur n'est pas instancie");
		else if(scenariste == null) throw new BadEntry("Le scenariste n'est pas instancie");
		else if(!(duree > Item.MIN_CHIFFRE_ITEM)) throw new BadEntry("La duree n'est pas strictement positive");
	}

	@Override
	public String toString() {
		return "{"+"Titre --> "+titre
				+" | "+"Note --> "+calculNoteMoyenneActuelle()
				+" | "+"Genre --> "+genre
				+" | "+"Realisateur --> "+realisateur
				+" | "+"Scenariste --> "+scenariste
				+" | "+"Duree --> "+duree+"}";
	}

	@Override
	public boolean equals(Object o) {
		boolean bool = false;
		if (o instanceof ItemFilm) {
			bool = super.equals(o);
		}
		return bool;
	}
}
