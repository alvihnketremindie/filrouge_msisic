package donnes;

import java.util.LinkedList;

import exception.BadEntry;

public abstract class Item {
	protected String titre;
	protected String genre;
	protected String type;
	protected static int MIN_CHAR_ITEM = 1;
	protected static int MIN_CHIFFRE_ITEM = 0;
	protected LinkedList <Review> lesNotationsDeLItem = new LinkedList <Review>();

	protected Member proprietaire;

	@Override
	public boolean equals(Object o) {
		boolean bool = false;
		if (o instanceof Item) {
			Item item = (Item) o;
			bool = item.getTitre().equalsIgnoreCase(titre);
		}
		return bool;
	}

	public void checkInfoItem(String titre, String genre) throws  BadEntry{
		if(titre == null) throw new BadEntry("Le titre n'est pas instancie");
		else if(titre.trim().length() < Item.MIN_CHAR_ITEM) throw new BadEntry("Le titre a moins de "+Item.MIN_CHAR_ITEM+" caractere(s) autre que des espaces");
		else if(genre == null) throw new BadEntry("Le genre n'est pas instancie");
	}

	public String getTitre() {
		return titre.trim().toLowerCase();
	}

	public String getType() {
		return type;
	}

	public String getDescription() {
		return getType()+"\t"+getTitre()+"\t"+calculNoteMoyenneActuelle();
	}
	public void setProprietaire(Member m) {
		proprietaire = m;
	}

	public void ajoutNouvelleNote(Review r) {
		//Retrait de l'ancienne note accorde (si elle existe) par un membre
		lesNotationsDeLItem.remove(r);
		//Ajout de la nouvelle note accorde par un membre
		lesNotationsDeLItem.add(r);
		//System.out.println(r.getNote()+"  "+r.getItem()+"  "+r.getMember());
	}

	/**
	 * Calcule de la note moyenne de l'Item
	 * @return : la note de l'Item
	 */
	public float calculNoteMoyenneActuelle() {
		float note = 0.0f;
		float numerateur = 0.0f;
		float denominateur = 0.0f;
		float karmaMembre;
		for(Review laNotationsDeLItem : lesNotationsDeLItem) {
			karmaMembre = laNotationsDeLItem.getMember().getKarma();
			numerateur += laNotationsDeLItem.getNote() * karmaMembre;
			denominateur += karmaMembre;
		}
		if(denominateur > 0) {
			note = numerateur / denominateur;
		}
		return note;
	}

}
