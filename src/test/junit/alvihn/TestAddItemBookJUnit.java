package test.junit.alvihn;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import avis.SN;
import avis.SocialNetwork;
import exception.BadEntry;
import exception.ItemBookAlreadyExists;
import exception.NotMember;

public class TestAddItemBookJUnit {

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
		sn.addMember("Antoine", "antoine", "grand amoureux de litterature");
		sn.addMember("Alice", "alice", "23 ans, sexy");
	}

	@Test
	public void testInitialisation() {
		try {
			assertEquals(nbFilms, 0);
			assertEquals(sn.nbFilms(), 0);
		} catch (Exception e) {
			fail("Erreur testInitialisation :  le nombre de livres apres creation du reseau est non nul");
		}
		try {
			assertEquals(nbLivres, 0);
			assertEquals(sn.nbBooks(), 0);
		} catch (Exception e) {
			fail("Erreur testInitialisation :  le nombre de livres apres creation du reseau est non nul");
		}
		try {
			assertEquals(nbMembres, 0);
		} catch (Exception e) {
			fail("Erreur testInitialisation :  le nombre de membres apres creation du reseau est non nul");
		}
		try {
			assertEquals(sn.nbMembers(), 3);
		} catch (Exception e) {
			fail("Erreur testInitialisation :  le nombre de membres apres ajout de 3 membres est differents de 3");
		}
		nbMembres = sn.nbMembers(); //Mise a jour du nombre de Membres
	}
	
	@Test
	public void testAddItemBook() throws BadEntry, NotMember, ItemBookAlreadyExists {
		testInitialisation();
		sn.addItemBook("Paul", "paul", "L'Enfant noir", "Roman", "Camara Laye", 224);
		sn.addItemBook("Antoine", "antoine", "L'affaire du silure", "Roman", "Guy Menga", 176);
		sn.addItemBook("Paul", "paul", "Le Cid", "Theatre", "Corneille", 192);
		sn.addItemBook("Alice", "alice", "Ethiopiques", "Poesie", "Leopold Sedar SENGHOR", 164);
		sn.addItemBook("Paul", "paul", "Everything, Everything", "Nouvelle", "Stella Meghie", 296);
		sn.addItemBook("Antoine", "antoine", "Star Wars: Rebels", "Science-Fiction", "Walt Disney", 150);
		sn.addItemBook("Paul", "paul", "Zelda", "Fantastique", "Nittendo", 122);
		try {
			assertEquals(sn.nbBooks(),nbLivres+7);
		} catch (Exception e) {
			fail("Erreur testAddItemBook : le nombre de livres apres ajout de 7 films n'a pas augmente de 7");
		}
		try {
			assertEquals(sn.nbMembers(),nbMembres);
		} catch (Exception e) {
			fail("Erreur testAddItemBook : le nombre de membres apres utilisation de addMember a ete modifie");
		}
		try {
			assertEquals(sn.nbFilms(),nbFilms);
		} catch (Exception e) {
			fail("Erreur testAddItemBook : le nombre de livres apres utilisation de addMember a ete modifie");
		}
		String toString = sn.toString();
		try {
			assertTrue(toString.contains("Titre"));
			assertTrue(toString.contains("Genre"));
			assertTrue(toString.contains("Auteur"));
			assertTrue(toString.contains("Nombre de pages"));
			assertTrue(toString.contains("{"));
			assertTrue(toString.contains("}"));
			assertTrue(toString.contains("-->"));
		} catch (Exception e2) {
			fail("Erreur testAddItemBook : le toString  ne contient pas les elements attendus");
		}
		nbLivres = sn.nbBooks();	//Mise a jour du nombre de livres
	}

	@Test
	public void testAddItemBookPseudoNull() {
		try {
			sn.addItemBook(null, "password", "titre", "genre", "auteur", 245);
			fail("Erreur testAddItemBookPseudoNull :  l'ajout d'un livre par un membre dont le pseudo n'est pas instancie a ete accepte");
		} catch (BadEntry e) {
			try {
				assertEquals(nbLivres, sn.nbBooks());
			} catch (Exception e1) {
				fail("Erreur testAddItemBookPseudoNull :  le nombre de livres apres tentative d'ajout refusee a ete modifie");
			}
		} catch (Exception e) {
			fail("Erreur testAddItemBookPseudoNull : Exception non prevu");
		}
	}

	@Test
	public void testAddItemBookPseudoEmpty() {
		try {
			sn.addItemBook("  ", "password", "titre", "genre", "auteur", 245);
			fail("Erreur testAddItemBookPseudoEmpty :  l'ajout d'un livre par un membre dont le pseudo ne contient pas un caractere, autre que des espaces, est accepte");
		} catch (BadEntry e) {
			try {
				assertEquals(nbLivres, sn.nbBooks());
			} catch (Exception e1) {
				fail("Erreur testAddItemBookPseudoEmpty :  le nombre de livres apres tentative d'ajout refusee a ete modifie");
			}
		} catch (Exception e) {
			fail("Erreur testAddItemBookPseudoEmpty : Exception non prevu");
		}
	}

	@Test
	public void testAddItemBookPasswordNull() {
		try {
			sn.addItemBook("pseudo", null, "titre", "genre", "auteur", 245);
			fail("Erreur testAddItemBookPasswordNull : l'ajout d'un livre par un membre dont le password n'est pas instancie est accepte");
		} catch (BadEntry e) {
			try {
				assertEquals(nbLivres, sn.nbBooks());
			} catch (Exception e1) {
				fail("Erreur testAddItemBookPasswordNull :  le nombre de livres apres tentative d'ajout refusee a ete modifie");
			}
		} catch (Exception e) {
			fail("Erreur testAddItemBookPasswordNull : Exception non prevu");
		}
	}

	@Test
	public void testAddItemBookPasswordNotEnoughChar() {
		try {
			sn.addItemBook("pseudo", "  qwd  ", "titre", "genre", "auteur", 245);
			fail("Erreur testAddItemBookPasswordNotEnoughChar : l'ajout d'un livre par un membre dont le password ne contient pas plus de quatre caracteres, autre que des espaces est accepte");
		} catch (BadEntry e) {
			try {
				assertEquals(nbLivres, sn.nbBooks());
			} catch (Exception e1) {
				fail("Erreur testAddItemBookPasswordNotEnoughChar :  le nombre de livres apres tentative d'ajout refusee a ete modifie");
			}
		} catch (Exception e) {
			fail("Erreur testAddItemBookPasswordNotEnoughChar : Exception non prevu");
		}
	}

	@Test
	public void testAddItemBookTitleNull() {
		try {
			sn.addItemBook("pseudo", "password", null, "genre", "auteur", 245);
			fail("Erreur testAddItemBookTitleNull : l'ajout d'un livre dont le titre n'est pas instancie est accepte");
		} catch (BadEntry e) {
			try {
				assertEquals(nbLivres, sn.nbBooks());
			} catch (Exception e1) {
				fail("Erreur testAddItemBookTitleNull :  le nombre de livres apres tentative d'ajout refusee a ete modifie");
			}
		} catch (Exception e) {
			fail("Erreur testAddItemBookTitleNull : Exception non prevu");
		}
	}

	@Test
	public void testAddItemBookTitleEmpty() {
		try {
			sn.addItemBook("pseudo", "password", "  	", "genre", "auteur", 245);
			fail("Erreur testAddItemBookTitleEmpty : l'ajout d'un livre dont le titre ne contient pas un caractere, autre que des blancs, est accepte");
		} catch (BadEntry e) {
			try {
				assertEquals(nbLivres, sn.nbBooks());
			} catch (Exception e1) {
				fail("Erreur testAddItemBookTitleEmpty :  le nombre de livres apres tentative d'ajout refusee a ete modifie");
			}
		} catch (Exception e) {
			fail("Erreur testAddItemBookTitleEmpty : Exception non prevu");
		}
	}

	@Test
	public void testAddItemBookGenreNull() {
		try {
			sn.addItemBook("pseudo", "password", "titre", null, "auteur", 245);
			fail("Erreur testAddItemBookGenreNull : l'ajout d'un livre dont le genre n'est pas instancie est accepte");
		} catch (BadEntry e) {
			try {
				assertEquals(nbLivres, sn.nbBooks());
			} catch (Exception e1) {
				fail("Erreur testAddItemBookGenreNull :  le nombre de livres apres tentative d'ajout refusee a ete modifie");
			}
		} catch (Exception e) {
			fail("Erreur testAddItemBookGenreNull : Exception non prevu");
		}
	}

	@Test
	public void testAddItemBookAuteurNull() {
		try {
			sn.addItemBook("pseudo", "password", "titre", "genre", null, 245);
			fail("Erreur testAddItemBookAuteurNull : l'ajout d'un livre dont l'auteur n'est pas instancie est accepte");
		} catch (BadEntry e) {
			try {
				assertEquals(nbLivres, sn.nbBooks());
			} catch (Exception e1) {
				fail("Erreur testAddItemBookAuteurNull :  le nombre de livres apres tentative d'ajout refusee a ete modifie");
			}
		} catch (Exception e) {
			fail("Erreur testAddItemBookAuteurNull : Exception non prevu");
		}
	}

	@Test
	public void testAddItemBookPagesNull() {
		try {
			sn.addItemBook("pseudo", "password", "titre", "genre", "auteur", 0);
			fail("Erreur testAddItemBookPagesNull :l'ajout d'un livre dont la duree est nulle est accepte");
		} catch (BadEntry e) {
			try {
				assertEquals(nbLivres, sn.nbBooks());
			} catch (Exception e1) {
				fail("Erreur testAddItemBookPagesNull :  le nombre de livres apres tentative d'ajout refusee a ete modifie");
			}
		} catch (Exception e) {
			fail("Erreur testAddItemBookPagesNull : Exception non prevu");
		}
	}

	@Test
	public void testAddItemBookPagesNegative() {
		try {
			sn.addItemBook("pseudo", "password", "titre", "genre", "auteur", -89);
			fail("Erreur testAddItemBookPagesNegative :l'ajout d'un livre dont la duree est nulle est accepte");
		} catch (BadEntry e) {
			try {
				assertEquals(nbLivres, sn.nbBooks());
			} catch (Exception e1) {
				fail("Erreur testAddItemBookPagesNegative :  le nombre de livres apres tentative d'ajout refusee a ete modifie");
			}
		} catch (Exception e) {
			fail("Erreur testAddItemBookPagesNegative : Exception non prevu");
		}
	}

	@Test
	public void testAddItemBookByNonMember() {
		try {
			testInitialisation();
			sn.addItemBook("pseudo", "password", "Thor: Ragnarok", "Action, Adventure", "Taika Waititi", 131);
			fail("Erreur testAddItemBookByNonMember :l'ajout d'un livre dont le  pseudo n'existe pas est accepte");
		} catch (NotMember e) {
			try {
				assertEquals(nbLivres, sn.nbBooks());
			} catch (Exception e1) {
				fail("Erreur testAddItemBookByNonMember :  le nombre de livres apres tentative d'ajout refusee a ete modifie");
			}
		} catch (Exception e) {
			fail("Erreur testAddItemBookByNonMember : Exception non prevu");
		}
	}

	@Test
	public void testAddItemBookWithBadPassword() {
		try {
			testInitialisation();
			sn.addItemBook("Paul", "password", "Thor: Ragnarok", "Action, Adventure", "Taika Waititi", 131);
			fail("Erreur testAddItemBookWithBadPassword : l'ajout d'un livre dont le  pseudo et le password ne correspondent pas est accepte");
		} catch (NotMember e) {
			try {
				assertEquals(nbLivres, sn.nbBooks());
			} catch (Exception e1) {
				fail("Erreur testAddItemBookWithBadPassword :  le nombre de livres apres tentative d'ajout refusee a ete modifie");
			}
		} catch (Exception e) {
			fail("Erreur testAddItemBookWithBadPassword : Exception non prevu");
		}
	}

	@Test
	public void testAddItemBookWithExistingTitle() {
		try {
			testAddItemBook();
			sn.addItemBook("Antoine", "antoine", "L'Enfant noir", "Fantastique", "Justin Lin", 122);
			fail("Erreur testAddItemBookWithExistingTitle :l'ajout d'un livre avec un titre existant est accepte");
		} catch (ItemBookAlreadyExists e) {
			try {
				assertEquals(nbLivres, sn.nbBooks());
			} catch (Exception e1) {
				fail("Erreur testAddItemBookWithExistingTitle :  le nombre de livres apres tentative d'ajout refusee a ete modifie");
			}
		} catch (Exception e) {
			fail("Erreur testAddItemBookWithExistingTitle : Exception non prevu");
		}
	}

	@Test
	public void testAddItemBookWithExistingTitleDifferentCasse() {
		try {
			testAddItemBook();
			sn.addItemBook("Antoine", "antoine", "L'enfAnt nOir", "Comedie", "Tarek Boudali", 92);
			fail("Erreur testAddItemBookWithExistingTitleDifferentCasse :l'ajout d'un livre avec un titre existant 'casse diferente' est accepte");
		} catch (ItemBookAlreadyExists e) {
			try {
				assertEquals(nbLivres, sn.nbBooks());
			} catch (Exception e1) {
				fail("Erreur testAddItemBookWithExistingTitleDifferentCasse :  le nombre de livres apres tentative d'ajout refusee a ete modifie");
			}
		} catch (Exception e) {
			fail("Erreur testAddItemBookWithExistingTitleDifferentCasse : Exception non prevu");
		}
	}

	@Test
	public void testAddItemBookWithExistingTitleWithBlank() {
		try {
			testAddItemBook();
			sn.addItemBook("Antoine", "antoine", "  	L'Enfant noir			", "Comedie", "Tarek Boudali", 92);
			fail("Erreur testAddItemBookWithExistingTitleWithBlank :l'ajout d'un livre avec un titre existant 'blancs en plus' est accepte");
		} catch (ItemBookAlreadyExists e) {
			try {
				assertEquals(nbLivres, sn.nbBooks());
			} catch (Exception e1) {
				fail("Erreur testAddItemBookWithExistingTitleWithBlank :  le nombre de livres apres tentative d'ajout refusee a ete modifie");
			}
		} catch (Exception e) {
			fail("Erreur testAddItemBookWithExistingTitleWithBlank : Exception non prevu");
		}
	}

}
