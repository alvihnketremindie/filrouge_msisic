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

public class TestReviewItemBookJUnit {

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
		
		sn.addItemBook("Paul", "paul", "L'Enfant noir", "Roman", "Camara Laye", 224);
		sn.addItemBook("Antoine", "antoine", "L'affaire du silure", "Roman", "Guy Menga", 176);
		sn.addItemBook("Paul", "paul", "Le Cid", "Theatre", "Corneille", 192);
		sn.addItemBook("Alice", "alice", "Ethiopiques", "Poesie", "Leopold Sedar SENGHOR", 164);
		sn.addItemBook("Paul", "paul", "Everything, Everything", "Nouvelle", "Stella Meghie", 296);
		sn.addItemBook("Antoine", "antoine", "Star Wars: Rebels", "Science-Fiction", "Walt Disney", 150);
		sn.addItemBook("Paul", "paul", "Zelda", "Fantastique", "Nittendo", 122);
	}

	@Test
	public void testInitialisation() {
		try {
			assertEquals(nbFilms, 0);
			assertEquals(sn.nbFilms(), 0);
		} catch (Exception e) {
			fail("Erreur testInitialisation :  le nombre de livres après création du réseau est non nul");
		}
		try {
			assertEquals(nbLivres, 0);
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
			fail("Erreur testInitialisation :  le nombre de membres après ajout de 3 membres est diff�rents de 3");
		}
		try {
			assertEquals(sn.nbBooks(),nbLivres+7);
		} catch (Exception e) {
			fail("Erreur testInitialisation : le nombre de livres après ajout de 7 films n'a pas augment� de 7");
		}
		nbMembres = sn.nbMembers(); //Mise a jour du nombre de Membres
		nbLivres = sn.nbBooks();	//Mise a jour du nombre de livres
	}
	
	@Test
	public void testReviewItemBook() throws BadEntry, NotMember, NotItem {
		float note1;
		String toString;
		testInitialisation();
		//Note L'Enfant Noir
		note1 = sn.reviewItemBook("Paul", "paul", "L'Enfant Noir", 5.0f, "Bon roman");
		note1 = sn.reviewItemBook("Antoine", "antoine", "L'Enfant Noir", 4.0f, "Bon roman");
		note1 = sn.reviewItemBook("Alice", "alice", "L'Enfant Noir", 3.0f, "Roman Moyen");
		try {
			assertEquals(note1, 4.0, DELTA);
		} catch (Exception e) {
			fail("Erreur testReviewItemBook : la moyenne doit etre 4.0");
		}
		//Note L'Enfant Noir
		note1 = sn.reviewItemBook("Alice", "alice", "L'affaire du silure", 0.0f, "Mauvais roman");
		note1 = sn.reviewItemBook("Paul", "paul", "L'affaire du silure", 0.0f, "Mauvais roman");
		note1 = sn.reviewItemBook("Alice", "alice", "L'affaire du silure", 1.0f, "Mauvais roman");
		note1 = sn.reviewItemBook("Paul", "paul", "L'affaire du silure", 1.0f, "Mauvais roman");
		note1 = sn.reviewItemBook("Alice", "alice", "L'affaire du silure", 2.0f, "Mauvais roman");
		note1 = sn.reviewItemBook("Paul", "paul", "L'affaire du silure", 2.0f, "Mauvais roman");
		note1 = sn.reviewItemBook("Antoine", "antoine", "L'affaire du silure", 4.0f, "Bon roman");
		note1 = sn.reviewItemBook("Paul", "paul", "L'affaire du silure", 4.0f, "Bon roman");
		note1 = sn.reviewItemBook("Antoine", "antoine", "L'affaire du silure", 4.0f, "Bon roman");
		note1 = sn.reviewItemBook("Alice", "alice", "L'affaire du silure", 4.0f, "Bon roman");
		try {
			System.out.println(note1);
			assertEquals(note1, 4.0f, DELTA);
		} catch (Exception e) {
			fail("Erreur testReviewItemBook : la moyenne doit etre 4.0");
		}
		//Test toString
		toString = sn.toString();
		try {
			assertTrue(toString.contains("Titre"));
			assertTrue(toString.contains("Note"));
			assertTrue(toString.contains("Genre"));
			assertTrue(toString.contains("Auteur"));
			assertTrue(toString.contains("Nombre de pages"));
			assertTrue(toString.contains("{"));
			assertTrue(toString.contains("}"));
			assertTrue(toString.contains("-->"));
		} catch (Exception e2) {
			fail("Erreur testReviewItemBook : le toString  ne contient pas les �l�ments attendus");
		}
	}

	//Bad Entry Exception
	@Test
	public void testReviewItemBookPseudoNull() {
		float note = noteTemoin;
		try {
			note = sn.reviewItemBook(null, "password", "titre", 5.0f, "commentaire");
			fail("Erreur testReviewItemBookPseudoNull :  la notation d'un livre par un membre dont le pseudo n'est pas instanci� a été accept�");
		} catch (BadEntry e) {
			try {
				assertEquals(note, noteTemoin, DELTA);
			} catch (Exception e1) {
				fail("Erreur testReviewItemBookPseudoNull : la note temoin a été chang� après une tentative de notation refus�e");
			}
		} catch (Exception e) {
			fail("Erreur testReviewItemBookPseudoNull : Exception non pr�vu");
		}
	}

	@Test
	public void testReviewItemBookPseudoEmpty() {
		float note = noteTemoin;
		try {
			note = sn.reviewItemBook("  ", "password", "titre", 5.0f, "commentaire");
			fail("Erreur testReviewItemBookPseudoEmpty :   la notation d'un livre par un membre dont le pseudo ne contient pas un caract�re, autre que des espaces, est accept�");
		} catch (BadEntry e) {
			try {
				assertEquals(note, noteTemoin, DELTA);
			} catch (Exception e1) {
				fail("Erreur testReviewItemBookPseudoEmpty : la note temoin a été chang� après une tentative de notation refus�e");
			}
		} catch (Exception e) {
			fail("Erreur testReviewItemBookPseudoEmpty : Exception non pr�vu");
		}
	}

	@Test
	public void testReviewItemBookPasswordNull() {
		float note = noteTemoin;
		try {
			note = sn.reviewItemBook("pseudo", null, "titre", 5.0f, "commentaire");
			fail("Erreur testReviewItemBookPasswordNull : la notation d'un livre par un membre dont le password n'est pas instanci� est accept�");
		} catch (BadEntry e) {
			try {
				assertEquals(note, noteTemoin, DELTA);
			} catch (Exception e1) {
				fail("Erreur testReviewItemBookPasswordNull : la note temoin a été chang� après une tentative de notation refus�e");
			}
		} catch (Exception e) {
			fail("Erreur testReviewItemBookPasswordNull : Exception non pr�vu");
		}
	}

	@Test
	public void testReviewItemBookPasswordNotEnoughChar() {
		float note = noteTemoin;
		try {
			note = sn.reviewItemBook("pseudo", "  qwd  ", "titre", 5.0f, "commentaire");
			fail("Erreur testReviewItemBookPasswordNotEnoughChar : la notation d'un livre par un membre dont le password ne contient pas plus de quatre caract�res, autre que des espaces est accept�");
		} catch (BadEntry e) {
			try {
				assertEquals(note, noteTemoin, DELTA);
			} catch (Exception e1) {
				fail("Erreur testReviewItemBookPasswordNotEnoughChar : la note temoin a été chang� après une tentative de notation refus�e");
			}
		} catch (Exception e) {
			fail("Erreur testReviewItemBookPasswordNotEnoughChar : Exception non pr�vu");
		}
	}

	@Test
	public void testReviewItemBookTitleNull() {
		float note = noteTemoin;
		try {
			note = sn.reviewItemBook("pseudo", "password", null, 5.0f, "commentaire");
			fail("Erreur testReviewItemBookTitleNull : la notation d'un livre dont le titre n'est pas instanci� est accept�");
		} catch (BadEntry e) {
			try {
				assertEquals(note, noteTemoin, DELTA);
			} catch (Exception e1) {
				fail("Erreur testReviewItemBookTitleNull : la note temoin a été chang� après une tentative de notation refus�e");
			}
		} catch (Exception e) {
			fail("Erreur testReviewItemBookTitleNull : Exception non pr�vu");
		}
	}

	@Test
	public void testReviewItemBookTitleEmpty() {
		float note = noteTemoin;
		try {
			note = sn.reviewItemBook("pseudo", "password", "	", 5.0f, "commentaire");
			fail("Erreur testReviewItemBookTitleEmpty : la notation d'un livre dont le titre ne contient pas un caract�re, autre que des blancs, est accept�");
		} catch (BadEntry e) {
			try {
				assertEquals(note, noteTemoin, DELTA);
			} catch (Exception e1) {
				fail("Erreur testReviewItemBookTitleEmpty : la note temoin a été chang� après une tentative de notation refus�e");
			}
		} catch (Exception e) {
			fail("Erreur testReviewItemBookTitleEmpty : Exception non pr�vu");
		}
	}

	@Test
	public void testReviewItemBookCommentNull() {
		float note = noteTemoin;
		try {
			note = sn.reviewItemBook("Alice", "alice", "L'affaire du silure", 5.0f, null);
			fail("Erreur testReviewItemBookCommentNull : la notation d'un livre dont le commentaire n'est pas instanci� est accept�");
		} catch (BadEntry e) {
			try {
				assertEquals(note, noteTemoin, DELTA);
			} catch (Exception e1) {
				fail("Erreur testReviewItemBookCommentNull :  la note temoin a été chang� après une tentative de notation refus�e");
			}
		} catch (Exception e) {
			fail("Erreur testReviewItemBookCommentNull : Exception non pr�vu");
		}
	}

	@Test
	public void testReviewItemBookNoteInferior() {
		float note = noteTemoin;
		try {
			note = sn.reviewItemBook("Alice", "alice", "L'affaire du silure", -2.0f, "commentaire");
			fail("Erreur testReviewItemBookNoteInferior : la notation d'un livre dont la note est inferieure � 0.0 est accept�");
		} catch (BadEntry e) {
			try {
				assertEquals(note, noteTemoin, DELTA);
			} catch (Exception e1) {
				fail("Erreur testReviewItemBookNoteInferior :  la note temoin a été chang� après une tentative de notation refus�e");
			}
		} catch (Exception e) {
			fail("Erreur testReviewItemBookNoteInferior : Exception non pr�vu");
		}
	}

	@Test
	public void testReviewItemBookNoteSuperior() {
		float note = noteTemoin;
		try {
			note = sn.reviewItemBook("Alice", "alice", "L'affaire du silure", 8.0f, "commentaire");
			fail("Erreur testReviewItemBookNoteSuperior : la notation d'un livre dont la note est superieure � 5.0 est accept�");
		} catch (BadEntry e) {
			try {
				assertEquals(note, noteTemoin, DELTA);
			} catch (Exception e1) {
				fail("Erreur testReviewItemBookNoteSuperior :  la note temoin a été chang� après une tentative de notation refus�e");
			}
		} catch (Exception e) {
			fail("Erreur testReviewItemBookNoteSuperior : Exception non pr�vu");
		}
	}
	
	//Not Item Exception
	@Test
	public void testReviewItemBookOnNonItem() {
		float note = noteTemoin;
		try {
			testInitialisation();
			note = sn.reviewItemBook("Paul", "paul", "Un livre qui n'existe pas", 5.0f, "commentaire");
			fail("Erreur testReviewItemBookPagesNegative : la notation d'un livre inexistant est accept�");
		} catch (NotItem e) {
			try {
				assertEquals(note, noteTemoin, DELTA);
			} catch (Exception e1) {
				fail("Erreur testReviewItemBookPagesNegative :  la note temoin a été chang� après une tentative de notation refus�e");
			}
		} catch (Exception e) {
			fail("Erreur testReviewItemBookPagesNegative : Exception non pr�vu");
		}
	}

	//Not Member Exception
	@Test
	public void testReviewItemBookByNonMember() {
		float note = noteTemoin;
		try {
			testInitialisation();
			note = sn.reviewItemBook("pseudo", "password", "L'Enfant Noir", 5.0f, "commentaire");
			fail("Erreur testReviewItemBookByNonMember : la notation d'un livre par un membre dont le pseudo n'existe pas est accept�");
		} catch (NotMember e) {
			try {
				assertEquals(note, noteTemoin, DELTA);
			} catch (Exception e1) {
				fail("Erreur testReviewItemBookByNonMember :  la note temoin a été chang� après une tentative de notation refus�e");
			}
		} catch (Exception e) {
			fail("Erreur testReviewItemBookByNonMember : Exception non prévu");
		}
	}

	@Test
	public void testReviewItemBookWithBadPassword() {
		float note = noteTemoin;
		try {
			testInitialisation();
			note = sn.reviewItemBook("Paul", "password", "L'Enfant Noir", 5.0f, "commentaire");
			fail("Erreur testReviewItemBookWithBadPassword : la notation d'un livre par un membre dont le  pseudo et le password ne correspondent pas est accept�");
		} catch (NotMember e) {
			try {
				assertEquals(note, noteTemoin, DELTA);
			} catch (Exception e1) {
				fail("Erreur testReviewItemBookWithBadPassword :  la note temoin a été chang� après une tentative de notation refus�e");
			}
		} catch (Exception e) {
			fail("Erreur testReviewItemBookWithBadPassword : Exception non pr�vu");
		}
	}
}
