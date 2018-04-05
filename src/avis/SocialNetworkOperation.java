package avis;

import java.util.LinkedList;

import donnes.Item;
import donnes.Member;
public class SocialNetworkOperation {

	public Object retourneElement(Object o, LinkedList<?> l) {
		Object retour = null;
		int index = l.indexOf(o);
		o = null;
		if(index != -1) {
			retour = l.get(index);
		}
		return retour;
	}

	public void lienMembreItem(Member m, Item i) {
		//Indiquer l'item comme ayant �t� ajout� par le membre
		i.setProprietaire(m);
		//Ajouter l'item dans la liste des items du membre
		m.ajoutItemDansLeSN(i);
	}
}
