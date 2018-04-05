/*
 *  Fichier SocialNetworkTestConsultItems.java
 * 
 *  Cette classe test les consultItems de la classe SocialNetwork
 *  ainsi que sa méthode toString (description textuelle du SN)
 * 
 *  Dernière modification: Sékou Traoré
 *  Date: 19/11/2017
 */

package test.junit.sekou;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import avis.SN;
import avis.SocialNetwork;
import exception.BadEntry;

public class SocialNetworkTestConsultItems {

	private static SN sn;
	
	@Rule
	public ErrorCollector collector = new ErrorCollector();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//On ajoute 1 membre au début du test ainsi que plusieurs items (livre et films) pour les tests
		sn = new SocialNetwork();
	
		try{
			
			sn.addMember("pseudo", "password", "profil");
			
			sn.addItemFilm("pseudo", "password", "mon film", "fantastique", "realisateur", "scenariste", 100);
			sn.addItemBook("pseudo", "password", "mon livre", "fantastique", "auteur", 30);
			
			sn.addItemFilm("pseudo", "password", "mon item", "fantastique", "realisateur", "scenariste", 100);
			sn.addItemBook("pseudo", "password", "mon item", "fantastique", "auteur", 42);
		
			//on rajoute deux items qui serviront aux tests de consulitems avec une casse différente
			sn.addItemFilm("pseudo", "password", "TON item", "fantastique", "realisateur", "scenariste", 100);
			sn.addItemBook("pseudo", "password", "ton item", "fantastique", "auteur", 42);			
		
		}catch(Exception e){
			System.out.println("ERROR 5.0: IMPOSSIBLE DE TESTER consultItems car impossible d'ajouter un membre ou un item (livre ou film)");
		}
	}
	
	//Test de consultation d'un item avec un titre null
	@Test(expected = BadEntry.class)
	public void testConsultItemWithNullTitle() throws BadEntry{
		sn.consultItems(null);
	}
	
	//Test de consultation d'un item avec un titre ne contenant pas au moins 1 caractère autre que des espaces
	@Test(expected = BadEntry.class)
	public void testConsultItemWithEmptyTitle() throws BadEntry{
		sn.consultItems("   ");
	}
	
	//On vérifie qu'une liste vide est retournée lorsqu'on appelle consultItem avec un titre inexistant
	@Test
	public void testConsultItemWithNonExistantTitle() throws BadEntry{
		List<String> listeItems = sn.consultItems("Titre non existant");
		if(listeItems == null) fail("Une valeur nulle est retournee lorsque l'on souhaite obtenir la liste des items");
		collector.checkThat("La liste retourné n'est pas nulle pour un mauvais titre", listeItems.size(), is(0));
	}
	
	//On vérifie qu'on a bien un unique resultat pour le film qu'on a ajouté 
	@Test
	public void testConsultItemWithFilmTitle() throws BadEntry{
		List<String> listeItems = sn.consultItems("mon film");
		if(listeItems == null) fail("Une valeur nulle est retournee lorsque l'on souhaite obtenir la liste des items");
		collector.checkThat("La liste retournée n'est pas égale à 1 pour un unique film du titre passé en paramètre", listeItems.size(), is(1));
		
		//On vérifie que la chaine retournée contient bien le titre du film recherché
		String consultString = listeItems.get(0);
		boolean resultat = consultString.toLowerCase().contains("mon film");
		collector.checkThat("La chaine de caractère retournée par consultItems ne contient pas le titre dans son contenu (pour un item film)", resultat, is(true));
	}
	
	//On vérifie qu'on a bien un unique resultat pour le film qu'on a ajouté 
	@Test
	public void testConsultItemWithBookTitle() throws BadEntry{
		List<String> listeItems = sn.consultItems("mon livre");
		if(listeItems == null) fail("Une valeur nulle est retournee lorsque l'on souhaite obtenir la liste des items");
		collector.checkThat("La liste retournée n'est pas égale à 1 pour un unique livre du titre passé en paramètre", listeItems.size(), is(1));
		
		//On vérifie que la chaine retournée contient bien le titre du film recherché
		String consultString = listeItems.get(0);
		boolean resultat = consultString.toLowerCase().contains("mon livre");
		collector.checkThat("La chaine de caractère retournée par consultItems ne contient pas le titre dans son contenu (pour un item livre)", resultat, is(true));
	}
	
	//On vérifie qu'on a bien deux resultats pour un livre et un film du meme titre
	@Test
	public void testConsultItemWithBookFilmTitle() throws BadEntry{
		//version avec casse exactement pareille
		List<String> listeItems = sn.consultItems("mon item");
		if(listeItems == null) fail("Une valeur nulle est retournee lorsque l'on souhaite obtenir la liste des items");
		collector.checkThat("La liste retournée n'est pas nulle pour un mauvais titre", listeItems.size(), is(2));
		
		//Version avec casse différente
		listeItems = sn.consultItems("ton item");
		if(listeItems == null) fail("Une valeur nulle est retournee lorsque l'on souhaite obtenir la liste des items");
		collector.checkThat("La liste retournée n'est pas nulle pour un mauvais titre", listeItems.size(), is(2));
	}
	
	/*
	 *   Test de la fonction toString de SN
	 *   Nous vérifions que certaines valeurs sont présentes dans la chaine retournée 
	 *   ( pseudo d'un membre, 2 titres de films et 2 titres de livre)
	 */
	@Test
	public void testSNToString(){
		Assert.assertThat("Certaines informations manquent dans la représentation textuelle de SN (Pseudo des membres ou titres des items)", 
				sn.toString(), CoreMatchers.allOf(
					      containsString("pseudo"),
					      containsString("mon film"),
					      containsString("mon livre"),
					      containsString("mon item"),
					      containsString("ton item")));
	}

}
