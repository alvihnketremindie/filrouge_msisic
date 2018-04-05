package test.junit.alvihn;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import avis.SN;
import avis.SocialNetwork;
import exception.BadEntry;
import exception.ItemFilmAlreadyExists;
import exception.NotMember;

public class TestAddItemFilmJUnit {
	private int nbFilms = 0;
	private int nbLivres = 0;
	private int nbMembres = 0;
	private SN sn;
	
	@Before
	public void setUp() throws Exception {
		sn = new SocialNetwork();
		nbFilms = sn.nbFilms();
		nbLivres = sn.nbBooks();
		nbMembres = sn.nbMembers();
		sn.addMember("Paul", "paul", "lecteur impulsif");
		sn.addMember("Antoine", "antoine", "grand amoureux de littérature");
		sn.addMember("Alice", "alice", "23 ans, sexy");
	}

	@Test
	public void testInitialisation() {
		try {
			assertEquals(nbFilms, 0);
			assertEquals(sn.nbFilms(), 0);
		} catch (Exception e) {
			fail("Erreur testInitialisation :  le nombre de films après création du réseau est non nul");
		}
		try {
			assertEquals(nbLivres, 0);
			assertEquals(sn.nbBooks(), 0);
		} catch (Exception e) {
			fail("Erreur testInitialisation :  le nombre de livres après création du réseau est non nul");
		}
		try {
			assertEquals(nbMembres, 0);
		} catch (Exception e) {
			fail("Erreur testInitialisation :  le nombre de membres après création du réseau est non nul");
		}
		try {
			assertEquals(sn.nbMembers(), 3);
		} catch (Exception e) {
			fail("Erreur testInitialisation :  le nombre de membres après ajout de 3 membres est différents de 3");
		}
		nbMembres = sn.nbMembers(); //Mise a jour du nombre de Membres
	}
	
	@Test
	public void testAddItemFilm() throws BadEntry, NotMember, ItemFilmAlreadyExists {
		testInitialisation();
		sn.addItemFilm("Paul", "paul", "Thor: Ragnarok", "Action", "Taika Waititi", "Eric Pearson", 131);
		sn.addItemFilm("Antoine", "antoine", "Justice League", "Action", "Zack Snyder", "Joss Whedon", 170);
		sn.addItemFilm("Paul", "paul", "Happy Birthdead", "Horreur", "Christopher Landon", "Christopher Landon", 96);
		sn.addItemFilm("Alice", "alice", "Epouse-moi mon pote", "Comédie", "Tarek Boudali", "Tarek Boudali", 92);
		sn.addItemFilm("Paul", "paul", "Everything, Everything", "Amour", "Stella Meghie", "Nicola Yoon", 96);
		sn.addItemFilm("Antoine", "antoine", "Star Wars: Episode VIII", "Fantastique", "Rian Johnson", "Rian Johnson", 150);
		sn.addItemFilm("Paul", "paul", "Star Trek : Sans limites", "Fantastique", "Justin Lin", "Simon Pegg", 122);
		try {
			assertEquals(sn.nbFilms(),nbFilms+7);
		} catch (Exception e) {
			fail("Erreur testAddItemFilm : le nombre de films après ajout de 7 films n'a pas augmenté de 7");
		}
		try {
			assertEquals(sn.nbMembers(),nbMembres);
		} catch (Exception e) {
			fail("Erreur testAddItemFilm : le nombre de membres après utilisation de addMember a été modifié");
		}
		try {
			assertEquals(sn.nbBooks(),nbLivres);
		} catch (Exception e) {
			fail("Erreur testAddItemFilm : le nombre de livres après utilisation de addMember a été modifié");
		}
		String toString = sn.toString();
		try {
			assertTrue(toString.contains("Titre"));
			assertTrue(toString.contains("Genre"));
			assertTrue(toString.contains("Realisateur"));
			assertTrue(toString.contains("Scenariste"));
			assertTrue(toString.contains("Duree"));
			assertTrue(toString.contains("{"));
			assertTrue(toString.contains("}"));
			assertTrue(toString.contains("-->"));
		} catch (Exception e2) {
			fail("Erreur testAddItemFilm : le toString  ne contient pas les éléments attendus");
		}
		nbFilms = sn.nbFilms();	//Mise a jour du nombre de films
	}

	@Test
	public void testAddItemFilmPseudoNull() {
		try {
			sn.addItemFilm(null, "password", "titre", "genre", "realisateur", "scenariste", 245);
			fail("Erreur testAddItemFilmPseudoNull :  l'ajout d'un film par un membre dont le pseudo n'est pas instancié a été accepté");
		} catch (BadEntry e) {
			try {
				assertEquals(nbFilms, sn.nbFilms());
			} catch (Exception e1) {
				fail("Erreur testAddItemFilmPseudoNull :  le nombre de films après tentative d'ajout refusée a été modifié");
			}
		} catch (Exception e) {
			fail("Erreur testAddItemFilmPseudoNull : Exception non prévu");
		}
	}

	@Test
	public void testAddItemFilmPseudoEmpty() {
		try {
			sn.addItemFilm("  ", "password", "titre", "genre", "realisateur", "scenariste", 245);
			fail("Erreur testAddItemFilmPseudoEmpty :  l'ajout d'un film par un membre dont le pseudo ne contient pas un caractére, autre que des espaces, est accepté");
		} catch (BadEntry e) {
			try {
				assertEquals(nbFilms, sn.nbFilms());
			} catch (Exception e1) {
				fail("Erreur testAddItemFilmPseudoEmpty :  le nombre de films après tentative d'ajout refusée a été modifié");
			}
		} catch (Exception e) {
			fail("Erreur testAddItemFilmPseudoEmpty : Exception non prévu");
		}
	}

	@Test
	public void testAddItemFilmPasswordNull() {
		try {
			sn.addItemFilm("pseudo", null, "titre", "genre", "realisateur", "scenariste", 245);
			fail("Erreur testAddItemFilmPasswordNull : l'ajout d'un film par un membre dont le password n'est pas instancié est accepté");
		} catch (BadEntry e) {
			try {
				assertEquals(nbFilms, sn.nbFilms());
			} catch (Exception e1) {
				fail("Erreur testAddItemFilmPasswordNull :  le nombre de films après tentative d'ajout refusée a été modifié");
			}
		} catch (Exception e) {
			fail("Erreur testAddItemFilmPasswordNull : Exception non prévu");
		}
	}

	@Test
	public void testAddItemFilmPasswordNotEnoughChar() {
		try {
			sn.addItemFilm("pseudo", "  qwd  ", "titre", "genre", "realisateur", "scenariste", 245);
			fail("Erreur testAddItemFilmPasswordNotEnoughChar : l'ajout d'un film par un membre dont le password n'est pas instancié est accepté");
		} catch (BadEntry e) {
			try {
				assertEquals(nbFilms, sn.nbFilms());
			} catch (Exception e1) {
				fail("Erreur testAddItemFilmPasswordNotEnoughChar :  le nombre de films après tentative d'ajout refusée a été modifié");
			}
		} catch (Exception e) {
			fail("Erreur testAddItemFilmPasswordNotEnoughChar : Exception non prévu");
		}
	}

	@Test
	public void testAddItemFilmTitleNull() {
		try {
			sn.addItemFilm("pseudo", "password", null, "genre", "realisateur", "scenariste", 245);
			fail("Erreur testAddItemFilmTitleNull : l'ajout d'un film dont le titre n'est pas instancié est accepté");
		} catch (BadEntry e) {
			try {
				assertEquals(nbFilms, sn.nbFilms());
			} catch (Exception e1) {
				fail("Erreur testAddItemFilmTitleNull :  le nombre de films après tentative d'ajout refusée a été modifié");
			}
		} catch (Exception e) {
			fail("Erreur testAddItemFilmTitleNull : Exception non prévu");
		}
	}

	@Test
	public void testAddItemFilmTitleEmpty() {
		try {
			sn.addItemFilm("pseudo", "password", "  	", "genre", "realisateur", "scenariste", 245);
			fail("Erreur testAddItemFilmTitleNull : l'ajout d'un film dont le titre ne contient pas un caractére, autre que des blancs, est accepté");
		} catch (BadEntry e) {
			try {
				assertEquals(nbFilms, sn.nbFilms());
			} catch (Exception e1) {
				fail("Erreur testAddItemFilmTitleNull :  le nombre de films après tentative d'ajout refusée a été modifié");
			}
		} catch (Exception e) {
			fail("Erreur testAddItemFilmTitleNull : Exception non prévu");
		}
	}

	@Test
	public void testAddItemFilmGenreNull() {
		try {
			sn.addItemFilm("pseudo", "password", "titre", null, "realisateur", "scenariste", 245);
			fail("Erreur testAddItemFilmGenreNull : l'ajout d'un film dont le genre n'est pas instancié est accepté");
		} catch (BadEntry e) {
			try {
				assertEquals(nbFilms, sn.nbFilms());
			} catch (Exception e1) {
				fail("Erreur testAddItemFilmGenreNull :  le nombre de films après tentative d'ajout refusée a été modifié");
			}
		} catch (Exception e) {
			fail("Erreur testAddItemFilmGenreNull : Exception non prévu");
		}
	}

	@Test
	public void testAddItemFilmRealisateurNull() {
		try {
			sn.addItemFilm("pseudo", "password", "titre", "genre", null, "scenariste", 245);
			fail("Erreur testAddItemFilmRealisateurNull : l'ajout d'un film dont le realisateur n'est pas instancié est accepté");
		} catch (BadEntry e) {
			try {
				assertEquals(nbFilms, sn.nbFilms());
			} catch (Exception e1) {
				fail("Erreur testAddItemFilmRealisateurNull :  le nombre de films après tentative d'ajout refusée a été modifié");
			}
		} catch (Exception e) {
			fail("Erreur testAddItemFilmRealisateurNull : Exception non prévu");
		}
	}

	@Test
	public void testAddItemFilmScenaristeNull() {
		try {
			sn.addItemFilm("pseudo", "password", "titre", "genre", "realisateur", null, 245);
			fail("Erreur testAddItemFilmScenaristeNull : l'ajout d'un film dont le scenariste n'est pas instancié est accepté");
		} catch (BadEntry e) {
			try {
				assertEquals(nbFilms, sn.nbFilms());
			} catch (Exception e1) {
				fail("Erreur testAddItemFilmScenaristeNull :  le nombre de films après tentative d'ajout refusée a été modifié");
			}
		} catch (Exception e) {
			fail("Erreur testAddItemFilmScenaristeNull : Exception non prévu");
		}
	}

	@Test
	public void testAddItemFilmDureeNull() {
		try {
			sn.addItemFilm("pseudo", "password", "titre", "genre", "realisateur", "scenariste", 0);
			fail("Erreur testAddItemFilmDureeNull :l'ajout d'un film dont la durée est nulle est accepté");
		} catch (BadEntry e) {
			try {
				assertEquals(nbFilms, sn.nbFilms());
			} catch (Exception e1) {
				fail("Erreur testAddItemFilmDureeNull :  le nombre de films après tentative d'ajout refusée a été modifié");
			}
		} catch (Exception e) {
			fail("Erreur testAddItemFilmDureeNull : Exception non prévu");
		}
	}

	@Test
	public void testAddItemFilmDureeNegative() {
		try {
			sn.addItemFilm("pseudo", "password", "titre", "genre", "realisateur", "scenariste", -89);
			fail("Erreur testAddItemFilmDureeNegative :l'ajout d'un film dont la durée est nulle est accepté");
		} catch (BadEntry e) {
			try {
				assertEquals(nbFilms, sn.nbFilms());
			} catch (Exception e1) {
				fail("Erreur testAddItemFilmDureeNegative :  le nombre de films après tentative d'ajout refusée a été modifié");
			}
		} catch (Exception e) {
			fail("Erreur testAddItemFilmDureeNegative : Exception non prévu");
		}
	}

	@Test
	public void testAddItemFilmByNonMember() {
		try {
			testInitialisation();
			sn.addItemFilm("pseudo", "password", "Thor: Ragnarok", "Action, Adventure", "Taika Waititi", "Eric Pearson", 131);
			fail("Erreur testAddItemFilmByNonMember :l'ajout d'un film dont le  pseudo n'existe pas est accepté");
		} catch (NotMember e) {
			try {
				assertEquals(nbFilms, sn.nbFilms());
			} catch (Exception e1) {
				fail("Erreur testAddItemFilmByNonMember :  le nombre de films après tentative d'ajout refusée a été modifié");
			}
		} catch (Exception e) {
			fail("Erreur testAddItemFilmByNonMember : Exception non prévu");
		}
	}

	@Test
	public void testAddItemFilmWithBadPassword() {
		try {
			testInitialisation();
			sn.addItemFilm("Paul", "password", "Thor: Ragnarok", "Action, Adventure", "Taika Waititi", "Eric Pearson", 131);
			fail("Erreur testAddItemFilmWithBadPassword : l'ajout d'un film dont le  pseudo et le password ne correspondent pas est accepté");
		} catch (NotMember e) {
			try {
				assertEquals(nbFilms, sn.nbFilms());
			} catch (Exception e1) {
				fail("Erreur testAddItemFilmWithBadPassword :  le nombre de films après tentative d'ajout refusée a été modifié");
			}
		} catch (Exception e) {
			fail("Erreur testAddItemFilmWithBadPassword : Exception non prévu");
		}
	}

	@Test
	public void testAddItemFilmWithExistingTitle() {
		try {
			testAddItemFilm();
			sn.addItemFilm("Antoine", "antoine", "Star Trek : Sans limites", "Fantastique", "Justin Lin", "Simon Pegg", 122);
			fail("Erreur testAddItemFilmWithExistingTitle :l'ajout d'un film avec un titre existant est accepté");
		} catch (ItemFilmAlreadyExists e) {
			try {
				assertEquals(nbFilms, sn.nbFilms());
			} catch (Exception e1) {
				fail("Erreur testAddItemFilmWithExistingTitle :  le nombre de films après tentative d'ajout refusée a été modifié");
			}
		} catch (Exception e) {
			fail("Erreur testAddItemFilmWithExistingTitle : Exception non prévu");
		}
	}

	@Test
	public void testAddItemFilmWithExistingTitleDifferentCasse() {
		try {
			testAddItemFilm();
			sn.addItemFilm("Antoine", "antoine", "epouse-Moi mon pOte", "Comédie", "Tarek Boudali", "Tarek Boudali", 92);
			fail("Erreur testAddItemFilmWithExistingTitleDifferentCasse :l'ajout d'un film avec un titre existant 'casse diférente' est accepté");
		} catch (ItemFilmAlreadyExists e) {
			try {
				assertEquals(nbFilms, sn.nbFilms());
			} catch (Exception e1) {
				fail("Erreur testAddItemFilmWithExistingTitleDifferentCasse :  le nombre de films après tentative d'ajout refusée a été modifié");
			}
		} catch (Exception e) {
			fail("Erreur testAddItemFilmWithExistingTitleDifferentCasse : Exception non prévu");
		}
	}

	@Test
	public void testAddItemFilmWithExistingTitleWithBlank() {
		try {
			testAddItemFilm();
			sn.addItemFilm("Antoine", "antoine", "			epouse-Moi mon pOte", "Comédie", "Tarek Boudali", "Tarek Boudali", 92);
			fail("Erreur testAddItemFilmWithExistingTitleWithBlank :l'ajout d'un film avec un titre existant 'blancs en plus' est accepté");
		} catch (ItemFilmAlreadyExists e) {
			try {
				assertEquals(nbFilms, sn.nbFilms());
			} catch (Exception e1) {
				fail("Erreur testAddItemFilmWithExistingTitleWithBlank :  le nombre de films après tentative d'ajout refusée a été modifié");
			}
		} catch (Exception e) {
			fail("Erreur testAddItemFilmWithExistingTitleWithBlank : Exception non prévu");
		}
	}
}
