package donnes;

import exception.BadEntry;

/*
 *   CLASSE REPRESENTANT L'AVIS DONNER A UNE AVIS DE FILM ou de Livre (avis sur un avis) 
 * 
 */

public class AvisOpinion {
	protected Member membre; //Le membre ayant donné l'avis
	protected Review review; //L'avis qui est noté par l'objet AvisOpinion
	protected float note; //La note attribuée à l'avis
	
	/**
	 * Constructueur de la classe <i>AvisOpinion</i> 
	 * 
	 * @param membre Le membre ayant donné l'avis
	 * @param review L'avis qui est noté par l'objet AvisOpinion
	 * @param note La note attribuée à l'avis
	 * 
	 */
	public AvisOpinion(Member membre, Review review, float note){
		this.membre = membre;
		this.review = review;
		this.note = note;
	}
	
	/**
	 * Retourne la note de l'opinion
	 * 
	 * @return la note
	 */
	public float getNote(){
		return note;
	}
	
	public void setNote(float note){
		this.note = note;
	}
	
	/**
	 * Retourne le membre dont l'opinion est notee
	 * 
	 * @return le membre  
	 */
	public Member getMember(){
		return membre;
	}
	
	
}
