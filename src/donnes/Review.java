package donnes;

import java.util.ArrayList;
import java.util.List;

import exception.BadEntry;

public class Review {
	protected float note;
	protected Item item;
	protected Member member;
	protected String commentaire;
	protected static float MIN_NUMBER_REVIEW = 0.0f;
	protected static float MAX_NUMBER_REVIEW = 5.0f;
	
	//Liste d'avis pour la review
	protected List<AvisOpinion> listeAvis = new ArrayList <AvisOpinion>();
	//Moyenne de tous les avis sur la review
	protected float moyenneDesAvis;

	/**
	 * Constructeur de la classe Review 
	 * @param i : Item noté
	 * @param m : Membre attribuant la note
	 * @param n : note attribuée
	 * @param c : commentaire attribué
	 * 
	 * @throws BadEntry :
	 * <ul>
	 *  <li>  si le commentaire n'est pas instancie.</li>
	 *  <li>  si la note donnee est inferieure au minimun possible.</li>
	 *  <li>  si la note donnee est superieur au minimum possible</li>
	 * </ul>
	 * <br>
	 */
	public Review(Item i, Member m, float n, String c) throws BadEntry {
		verification(n,c);
		item = i;
		member = m;
		note = n;
		commentaire = c;
		setElements();
	}	

	/**
	 * Methode vérification 
	 * @param i : Item noté
	 * @param m : Membre attribuant la note
	 * @param n : note attribuée
	 * @param c : commentaire attribué
	 * 
	 * @throws BadEntry :
	 * <ul>
	 *  <li>  si le commentaire n'est pas instancie.</li>
	 *  <li>  si la note donnee est inferieure au minimun possible.</li>
	 *  <li>  si la note donnee est superieur au minimum possible</li>
	 * </ul>
	 * <br>
	 */
	private void verification(float noteReview, String commentaireReview)  throws BadEntry {
		if(commentaireReview == null) throw new BadEntry("Le commentaire n'est pas instancie");
		else if(noteReview < MIN_NUMBER_REVIEW) throw new BadEntry("La note donnee est inferieure a "+MIN_NUMBER_REVIEW);
		else if(noteReview > MAX_NUMBER_REVIEW) throw new BadEntry("La note donnee est superieur a "+MAX_NUMBER_REVIEW);
	}

	/**
	 * Retourne le membre de l'opinion
	 * 
	 * @return le membre  
	 */
	public Member getMember() {
		return this.member;
	}

	/**
	 * Retourne l'item de l'opinion
	 * 
	 * @return l'item  
	 */
	public Item getItem() {
		return this.item;
	}

	/**
	 * Retourne la note attribuée à l'opinion
	 * 
	 * @return la note attribuée  
	 */
	public float getNote() {
		return note;
	}

	/**
	 * Ajout de la review à l'item
	 * Atjout de la review au membre  
	 */
	public void setElements() {
		item.ajoutNouvelleNote(this);
		member.ajoutNouvelleNotationDuMembre(this);
	}
	
	@Override
	public boolean equals(Object o) {
		boolean bool = false;
		if (o instanceof Review) {
			Review r = (Review) o;
			bool = r.getMember().equals(member) && r.getItem().equals(item);
		}
		return bool;
	}
	
	/**
	 * Attribuer une note à la review (avis unique par membre)
	 * Si un avis a deja ete donne par ce membre, cet avis est modifie
	 * Enfin calcul de la nouvelle moyenne des avis
	 * 
	 * @param membre : Membre attribuant la note
	 * @param note : note attribuee
	 * @throws BadEntry 
	 */
	public void noterReview(Member membre, float note) throws BadEntry{
		if(note < MIN_NUMBER_REVIEW) throw new BadEntry("La note donnee est inferieure a "+MIN_NUMBER_REVIEW);
		if(note > MAX_NUMBER_REVIEW) throw new BadEntry("La note donnee est superieure a "+MAX_NUMBER_REVIEW);
		//Récupérer l'avis donne par ce membre sur cette review (s'il existe)
		AvisOpinion previousAvis = null; //Contiendra l'avis precedent donne par ce membre si celui avait deja donne son avis
		for(AvisOpinion avis : listeAvis){
			if(avis.getMember().equals(membre)){
				previousAvis = avis;
			}
		}
		
		//Si le membre a deja donne un avis sur cette review on modifie l'ancien avis
		if(previousAvis != null){
			previousAvis.setNote(note);
		}else{
			//Sinon on ajoute l'avis
			listeAvis.add(new AvisOpinion(membre, this, note));
		}
		calculerMoyenneAvis();
	}
	
	/**
	 * Calcul de la moyenne des avis sur cet avis  
	 */
	public void calculerMoyenneAvis(){
		float newMoyenne = 0.0f;
		for(AvisOpinion avis : listeAvis){
			newMoyenne += avis.getNote();
		}
		newMoyenne /= listeAvis.size();
		this.moyenneDesAvis = newMoyenne;
	}
	
	/**
	 * Recuperer la moyenne des avis
	 * 
	 * @return la note moyenne des avis sur cet avis  
	 */
	public float getMoyenne(){
		return this.moyenneDesAvis;
	}
}
