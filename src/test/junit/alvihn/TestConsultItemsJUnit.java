package test.junit.alvihn;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import avis.SN;
import avis.SocialNetwork;
import exception.BadEntry;

public class TestConsultItemsJUnit {

	private SN sn;
	private int nbFilms = 0;
	private int nbLivres = 0;
	private int nbMembres = 0;

	@Before
	public void setUp() throws Exception {
		sn = new SocialNetwork();

		sn.addMember("Paul", "paul", "lecteur impulsif");
		sn.addMember("Antoine", "antoine", "grand amoureux de litterature");
		sn.addMember("Alice", "alice", "23 ans, sexy");

		sn.addItemBook("Paul", "paul", "L'Enfant noir", "Roman", "Camara Laye", 224);
		sn.addItemBook("Antoine", "antoine", "L'affaire du silure", "Roman", "Guy Menga", 176);
		sn.addItemBook("Paul", "paul", "Le Cid", "Theatre", "Corneille", 192);
		sn.addItemBook("Alice", "alice", "Ethiopiques", "Poesie", "Leopold Sedar SENGHOR", 164);
		sn.addItemBook("Paul", "paul", "Everything, Everything", "Nouvelle", "Stella Meghie", 296);
		sn.addItemBook("Antoine", "antoine", "Star Wars: Rebels", "Science-Fiction", "Walt Disney", 150);
		sn.addItemBook("Paul", "paul", "Zelda", "Fantastique", "Nittendo", 122);

		sn.addItemFilm("Paul", "paul", "Thor: Ragnarok", "Action", "Taika Waititi", "Eric Pearson", 131);
		sn.addItemFilm("Antoine", "antoine", "Justice League", "Action", "Zack Snyder", "Joss Whedon", 170);
		sn.addItemFilm("Paul", "paul", "Happy Birthdead", "Horreur", "Christopher Landon", "Christopher Landon", 96);
		sn.addItemFilm("Alice", "alice", "Epouse-moi mon pote", "Comedie", "Tarek Boudali", "Tarek Boudali", 92);
		sn.addItemFilm("Paul", "paul", "Everything, Everything", "Amour", "Stella Meghie", "Nicola Yoon", 96);
		sn.addItemFilm("Antoine", "antoine", "Star Wars: Episode VIII", "Fantastique", "Rian Johnson", "Rian Johnson", 150);
		sn.addItemFilm("Paul", "paul", "Star Trek : Sans limites", "Fantastique", "Justin Lin", "Simon Pegg", 122);

		nbFilms = sn.nbFilms();
		nbLivres = sn.nbBooks();
		nbMembres = sn.nbMembers();
	}

	@Test
	public void testInitialisation() {
		try {
			assertEquals(nbLivres,7);
		} catch (Exception e) {
			fail("Erreur testInitialisation : le nombre de livres apres ajout de 7 films n'a pas augmente de 7");
		}
		try {
			assertEquals(nbMembres,3);
		} catch (Exception e) {
			fail("Erreur testInitialisation : le nombre de membres apres ajout de 3 membres n'a pas augmente de 3");
		}
		try {
			assertEquals(nbFilms,7);
		} catch (Exception e) {
			fail("Erreur testInitialisation : le nombre de films apres ajout de 7 films n'a pas augmente de 7");
		}
	}

	@Test
	public void testConsultItemsNameNull() {
		try {
			LinkedList <String> research = sn.consultItems(null);
			fail("Erreur testConsultItemsNameNull :  la recherche d'un item dont le nom n'est pas instancie a ete accepte");
		} catch (BadEntry e) {

		}
	}

	@Test
	public void testConsultItemsNameEmpty() {
		try {
			LinkedList <String> research = sn.consultItems("  		    ");
			fail("Erreur testConsultItemsNameEmpty :  la recherche d'un item dont le nom ne contient pas un caractere, autre que des espaces, est accepte");
		} catch (BadEntry e) {

		}
	}

	@Test
	public void testConsultItemsNameNotExist() throws BadEntry {
		LinkedList <String> research = sn.consultItems("aladin");
		try {
			assertEquals(research.size(), 0);
		} catch (Exception e) {
			fail("Erreur testConsultItemsNameNotExist :  la recherche d'un item dont le nom n'est pas dans le reseau a retourne un resultat");
		}
	}


	@Test
	public void testConsultItems() throws BadEntry {
		LinkedList <String> research;
		LinkedList<String> noms = new LinkedList<String>();
		Collections.addAll(noms, "Everything, Everything", "L'affaire du silure", "Thor: Ragnarok", "Justice League");
		for (String nom : noms) {
			research = sn.consultItems(nom);
			try {
				assertNotNull(research);
			} catch (Exception e) {
				fail("Erreur testConsultItemsFilmNameExist :  la recherche avec un nom existant n'a pas retourne de resultats");
			}
			try {
				assertTrue(research.size() > 0);
			} catch (Exception e) {
				fail("Erreur testConsultItemsFilmNameExist :  la recherche avec un nom existant a retourne un resultat vide");
			}
			for(String toString : research) {
				try {
					assertTrue(toString.contains(nom.toLowerCase().trim()));
				} catch (Exception e) {
					fail("Erreur testConsultItemsFilmNameExist :  la recherche avec un nom existant a retourne un resultat ne contenant pas le nom");
				}
			}
		}
	}
}
