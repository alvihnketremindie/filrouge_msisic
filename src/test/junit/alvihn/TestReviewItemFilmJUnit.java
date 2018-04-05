package test.junit.alvihn;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import avis.SN;
import avis.SocialNetwork;
import exception.BadEntry;
import exception.NotItem;
import exception.NotMember;

public class TestReviewItemFilmJUnit {

	private int nbFilms = 0;
	private int nbLivres = 0;
	private int nbMembres = 0;
	private SN sn;
	float noteTemoin = -1.0f;
	private static final float DELTA = 1e-15f;
	
	@Before
	public void setUp() throws Exception {
		sn = new SocialNetwork();
		nbFilms = sn.nbFilms();
		nbLivres = sn.nbBooks();
		nbMembres = sn.nbMembers();
		
		sn.addMember("Paul", "paul", "lecteur impulsif");
		sn.addMember("Antoine", "antoine", "grand amoureux de litterature");
		sn.addMember("Alice", "alice", "23 ans, sexy");
		
		sn.addItemFilm("Paul", "paul", "Thor: Ragnarok", "Action", "Taika Waititi", "Eric Pearson", 131);
		sn.addItemFilm("Antoine", "antoine", "Justice League", "Action", "Zack Snyder", "Joss Whedon", 170);
		sn.addItemFilm("Paul", "paul", "Happy Birthdead", "Horreur", "Christopher Landon", "Christopher Landon", 96);
		sn.addItemFilm("Alice", "alice", "Epouse-moi mon pote", "Comédie", "Tarek Boudali", "Tarek Boudali", 92);
		sn.addItemFilm("Paul", "paul", "Everything, Everything", "Amour", "Stella Meghie", "Nicola Yoon", 96);
		sn.addItemFilm("Antoine", "antoine", "Star Wars: Episode VIII", "Fantastique", "Rian Johnson", "Rian Johnson", 150);
		sn.addItemFilm("Paul", "paul", "Star Trek : Sans limites", "Fantastique", "Justin Lin", "Simon Pegg", 122);
	}

	@Test
	public void testInitialisation() {
		try {
			assertEquals(nbFilms, 0);
		} catch (Exception e) {
			fail("Erreur testInitialisation :  le nombre de livres aprés création du réseau est non nul");
		}
		try {
			assertEquals(nbLivres, 0);
			assertEquals(sn.nbBooks(), 0);
		} catch (Exception e) {
			fail("Erreur testInitialisation :  le nombre de livres aprés création du réseau est non nul");
		}
		try {
			assertEquals(nbMembres, 0);
		} catch (Exception e) {
			fail("Erreur testInitialisation :  le nombre de membres aprés création du réseau est non nul");
		}
		try {
			assertEquals(sn.nbMembers(), 3);
		} catch (Exception e) {
			fail("Erreur testInitialisation :  le nombre de membres aprés ajout de 3 membres est différents de 3");
		}
		try {
			assertEquals(sn.nbFilms(),nbFilms+7);
		} catch (Exception e) {
			fail("Erreur testInitialisation : le nombre de livres aprés ajout de 7 films n'a pas augmenté de 7");
		}
		nbMembres = sn.nbMembers(); //Mise a jour du nombre de Membres
		nbLivres = sn.nbBooks();	//Mise a jour du nombre de livres
	}
	
	@Test
	public void testReviewItemFilm() throws BadEntry, NotMember, NotItem {
		float note1;
		String toString;
		testInitialisation();
		//Note Thor: Ragnarok
		note1 = sn.reviewItemFilm("Paul", "paul", "Thor: Ragnarok", 5.0f, "Bon film");
		note1 = sn.reviewItemFilm("Antoine", "antoine", "Thor: Ragnarok", 4.0f, "Bon film");
		note1 = sn.reviewItemFilm("Alice", "alice", "Thor: Ragnarok", 3.0f, "Moyen film");
		try {
			assertEquals(note1, 4.0f, DELTA);
		} catch (Exception e) {
			fail("Erreur testReviewItemFilm : la moyenne doit etre 4.0");
		}
		//Note Thor: Ragnarok
		note1 = sn.reviewItemFilm("Alice", "alice", "Justice League", 0.0f, "Mauvais film");
		note1 = sn.reviewItemFilm("Paul", "paul", "Justice League", 0.0f, "Mauvais film");
		note1 = sn.reviewItemFilm("Alice", "alice", "Justice League", 1.0f, "Mauvais film");
		note1 = sn.reviewItemFilm("Paul", "paul", "Justice League", 1.0f, "Mauvais film");
		note1 = sn.reviewItemFilm("Alice", "alice", "Justice League", 2.0f, "Mauvais film");
		note1 = sn.reviewItemFilm("Paul", "paul", "Justice League", 2.0f, "Mauvais film");
		note1 = sn.reviewItemFilm("Antoine", "antoine", "Justice League", 4.0f, "Bon film");
		note1 = sn.reviewItemFilm("Paul", "paul", "Justice League", 4.0f, "Bon film");
		note1 = sn.reviewItemFilm("Antoine", "antoine", "Justice League", 4.0f, "Bon film");
		note1 = sn.reviewItemFilm("Alice", "alice", "Justice League", 4.0f, "Bon film");
		try {
			assertEquals(note1, 4.0f, DELTA);
		} catch (Exception e) {
			fail("Erreur testReviewItemFilm : la moyenne doit etre 2.2");
		}
		
		toString = sn.toString();
		try {
			assertTrue(toString.contains("Titre"));
			assertTrue(toString.contains("Note"));
			assertTrue(toString.contains("Genre"));
			assertTrue(toString.contains("Realisateur"));
			assertTrue(toString.contains("Scenariste"));
			assertTrue(toString.contains("Duree"));
			assertTrue(toString.contains("{"));
			assertTrue(toString.contains("}"));
			assertTrue(toString.contains("-->"));
		} catch (Exception e2) {
			fail("Erreur testReviewItemFilm : le toString  ne contient pas les éléments attendus");
		}
		
	}

	//Bad Entry Exception
	@Test
	public void testReviewItemFilmPseudoNull() {
		float note = noteTemoin;
		try {
			note = sn.reviewItemFilm(null, "password", "titre", 5.0f, "commentaire");
			fail("Erreur testReviewItemFilmPseudoNull :  la notation d'un film par un membre dont le pseudo n'est pas instancié a été accepté");
		} catch (BadEntry e) {
			try {
				assertEquals(note, noteTemoin, DELTA);
			} catch (Exception e1) {
				fail("Erreur testReviewItemFilmPseudoNull : la note temoin a été changé aprés une tentative de notation refusée");
			}
		} catch (Exception e) {
			fail("Erreur testReviewItemFilmPseudoNull : Exception non prévu");
		}
	}

	@Test
	public void testReviewItemFilmPseudoEmpty() {
		float note = noteTemoin;
		try {
			note = sn.reviewItemFilm("  ", "password", "titre", 5.0f, "commentaire");
			fail("Erreur testReviewItemFilmPseudoEmpty :   la notation d'un film par un membre dont le pseudo ne contient pas un caractére, autre que des espaces, est accepté");
		} catch (BadEntry e) {
			try {
				assertEquals(note, noteTemoin, DELTA);
			} catch (Exception e1) {
				fail("Erreur testReviewItemFilmPseudoEmpty : la note temoin a été changé aprés une tentative de notation refusée");
			}
		} catch (Exception e) {
			fail("Erreur testReviewItemFilmPseudoEmpty : Exception non prévu");
		}
	}

	@Test
	public void testReviewItemFilmPasswordNull() {
		float note = noteTemoin;
		try {
			note = sn.reviewItemFilm("pseudo", null, "titre", 5.0f, "commentaire");
			fail("Erreur testReviewItemFilmPasswordNull : la notation d'un film par un membre dont le password n'est pas instancié est accepté");
		} catch (BadEntry e) {
			try {
				assertEquals(note, noteTemoin, DELTA);
			} catch (Exception e1) {
				fail("Erreur testReviewItemFilmPasswordNull : la note temoin a été changé aprés une tentative de notation refusée");
			}
		} catch (Exception e) {
			fail("Erreur testReviewItemFilmPasswordNull : Exception non prévu");
		}
	}

	@Test
	public void testReviewItemFilmPasswordNotEnoughChar() {
		float note = noteTemoin;
		try {
			note = sn.reviewItemFilm("pseudo", "  qwd  ", "titre", 5.0f, "commentaire");
			fail("Erreur testReviewItemFilmPasswordNotEnoughChar : la notation d'un film par un membre dont le password ne contient pas plus de quatre caractéres, autre que des espaces est accepté");
		} catch (BadEntry e) {
			try {
				assertEquals(note, noteTemoin, DELTA);
			} catch (Exception e1) {
				fail("Erreur testReviewItemFilmPasswordNotEnoughChar : la note temoin a été changé aprés une tentative de notation refusée");
			}
		} catch (Exception e) {
			fail("Erreur testReviewItemFilmPasswordNotEnoughChar : Exception non prévu");
		}
	}

	@Test
	public void testReviewItemFilmTitleNull() {
		float note = noteTemoin;
		try {
			note = sn.reviewItemFilm("pseudo", "password", null, 5.0f, "commentaire");
			fail("Erreur testReviewItemFilmTitleNull : la notation d'un film dont le titre n'est pas instancié est accepté");
		} catch (BadEntry e) {
			try {
				assertEquals(note, noteTemoin, DELTA);
			} catch (Exception e1) {
				fail("Erreur testReviewItemFilmTitleNull : la note temoin a été changé aprés une tentative de notation refusée");
			}
		} catch (Exception e) {
			fail("Erreur testReviewItemFilmTitleNull : Exception non prévu");
		}
	}

	@Test
	public void testReviewItemFilmTitleEmpty() {
		float note = noteTemoin;
		try {
			note = sn.reviewItemFilm("pseudo", "password", "	", 5.0f, "commentaire");
			fail("Erreur testReviewItemFilmTitleEmpty : la notation d'un film dont le titre ne contient pas un caractére, autre que des blancs, est accepté");
		} catch (BadEntry e) {
			try {
				assertEquals(note, noteTemoin, DELTA);
			} catch (Exception e1) {
				fail("Erreur testReviewItemFilmTitleEmpty : la note temoin a été changé aprés une tentative de notation refusée");
			}
		} catch (Exception e) {
			fail("Erreur testReviewItemFilmTitleEmpty : Exception non prévu");
		}
	}

	@Test
	public void testReviewItemFilmCommentNull() {
		float note = noteTemoin;
		try {
			note = sn.reviewItemFilm("Alice", "alice", "Justice League", 5.0f, null);
			fail("Erreur testReviewItemFilmCommentNull : la notation d'un film dont le commentaire n'est pas instancié est accepté");
		} catch (BadEntry e) {
			try {
				assertEquals(note, noteTemoin, DELTA);
			} catch (Exception e1) {
				fail("Erreur testReviewItemFilmCommentNull :  la note temoin a été changé aprés une tentative de notation refusée");
			}
		} catch (Exception e) {
			fail("Erreur testReviewItemFilmCommentNull : Exception non prévu");
		}
	}

	@Test
	public void testReviewItemFilmNoteInferior() {
		float note = noteTemoin;
		try {
			note = sn.reviewItemFilm("Alice", "alice", "Justice League", -2.0f, "commentaire");
			fail("Erreur testReviewItemFilmNoteInferior : la notation d'un film dont la note est inferieure à  0.0 est accepté");
		} catch (BadEntry e) {
			try {
				assertEquals(note, noteTemoin, DELTA);
			} catch (Exception e1) {
				fail("Erreur testReviewItemFilmNoteInferior :  la note temoin a été changé aprés une tentative de notation refusée");
			}
		} catch (Exception e) {
			fail("Erreur testReviewItemFilmNoteInferior : Exception non prévu");
		}
	}

	@Test
	public void testReviewItemFilmNoteSuperior() {
		float note = noteTemoin;
		try {
			note = sn.reviewItemFilm("Alice", "alice", "Justice League", 8.0f, "commentaire");
			fail("Erreur testReviewItemFilmNoteSuperior : la notation d'un film dont la note est superieure à  5.0 est accepté");
		} catch (BadEntry e) {
			try {
				assertEquals(note, noteTemoin, DELTA);
			} catch (Exception e1) {
				fail("Erreur testReviewItemFilmNoteSuperior :  la note temoin a été changé aprés une tentative de notation refusée");
			}
		} catch (Exception e) {
			fail("Erreur testReviewItemFilmNoteSuperior : Exception non prévu");
		}
	}
	
	//Not Item Exception
	@Test
	public void testReviewItemFilmOnNonItem() {
		float note = noteTemoin;
		try {
			testInitialisation();
			note = sn.reviewItemFilm("Paul", "paul", "Un livre qui n'existe pas", 5.0f, "commentaire");
			fail("Erreur testReviewItemFilmPagesNegative : la notation d'un film inexistant est accepté");
		} catch (NotItem e) {
			try {
				assertEquals(note, noteTemoin, DELTA);
			} catch (Exception e1) {
				fail("Erreur testReviewItemFilmPagesNegative :  la note temoin a été changé aprés une tentative de notation refusée");
			}
		} catch (Exception e) {
			fail("Erreur testReviewItemFilmPagesNegative : Exception non prévu");
		}
	}

	//Not Member Exception
	@Test
	public void testReviewItemFilmByNonMember() {
		float note = noteTemoin;
		try {
			testInitialisation();
			note = sn.reviewItemFilm("pseudo", "password", "Thor: Ragnarok", 5.0f, "commentaire");
			fail("Erreur testReviewItemFilmByNonMember : la notation d'un film par un membre dont le pseudo n'existe pas est accepté");
		} catch (NotMember e) {
			try {
				assertEquals(note, noteTemoin, DELTA);
			} catch (Exception e1) {
				fail("Erreur testReviewItemFilmByNonMember :  la note temoin a été changé aprés une tentative de notation refusée");
			}
		} catch (Exception e) {
			fail("Erreur testReviewItemFilmByNonMember : Exception non prévu");
		}
	}

	@Test
	public void testReviewItemFilmWithBadPassword() {
		float note = noteTemoin;
		try {
			testInitialisation();
			note = sn.reviewItemFilm("Paul", "password", "Thor: Ragnarok", 5.0f, "commentaire");
			fail("Erreur testReviewItemFilmWithBadPassword : la notation d'un film par un membre dont le  pseudo et le password ne correspondent pas est accepté");
		} catch (NotMember e) {
			try {
				assertEquals(note, noteTemoin, DELTA);
			} catch (Exception e1) {
				fail("Erreur testReviewItemFilmWithBadPassword :  la note temoin a été changé aprés une tentative de notation refusée");
			}
		} catch (Exception e) {
			fail("Erreur testReviewItemFilmWithBadPassword : Exception non prévu");
		}
	}
}
