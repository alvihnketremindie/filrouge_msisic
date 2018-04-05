package test.junit.alvihn;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import avis.SN;
import avis.SocialNetwork;
import exception.BadEntry;
import exception.MemberAlreadyExists;

public class TestAddMemberJUnit {

	private int nbFilms = 0;
	private int nbLivres = 0;
	private int nbMembres = 0;
	private SN sn;

	@Rule
	public ErrorCollector collector = new ErrorCollector();

	@Before
	public void setUp() throws Exception {
		sn = new SocialNetwork();
		nbFilms = sn.nbFilms();
		nbLivres = sn.nbBooks();
		nbMembres = sn.nbMembers();
	}

	@Test
	public void testInitialisation() {
		try {
			assertEquals(nbFilms, 0);
			assertEquals(sn.nbFilms(), 0);
		} catch (Exception e) {
			fail("Erreur testInitialisation :  le nombre de films apres creation du reseau est non nul");
		}
		try {
			assertEquals(nbLivres, 0);
			assertEquals(sn.nbBooks(), 0);
		} catch (Exception e) {
			fail("Erreur testInitialisation :  le nombre de livres apres creation du reseau est non nul");
		}
		try {
			assertEquals(nbMembres, 0);
			assertEquals(sn.nbMembers(), 0);
		} catch (Exception e) {
			fail("Erreur testInitialisation :  le nombre de membres apres creation du reseau est non nul");
		}
	}
	
	@Test
	public void testAddMember() throws BadEntry, MemberAlreadyExists {
		testInitialisation();
		sn.addMember("Paul", "paul", "lecteur impulsif");
		sn.addMember("Antoine", "antoine", "grand amoureux de litterature");
		sn.addMember("Alice", "alice", "23 ans, sexy");
		try {
			assertEquals(sn.nbMembers(),nbMembres+3);
		} catch (Exception e) {
			fail("Erreur testAddMember : le nombre de membres apres ajout de 3 membres n'a pas augmente de 3");
		}
		try {
			assertEquals(sn.nbBooks(),nbLivres);
		} catch (Exception e) {
			fail("Erreur testAddMember : le nombre de livres apres utilisation de addMember a ete modifie");
		}
		try {
			assertEquals(sn.nbFilms(),nbFilms);
		} catch (Exception e) {
			fail("Erreur testAddMember : le nombre de films apres utilisation de addMember a ete modifie");
		}
		String toString = sn.toString();
		try {
			assertTrue(toString.contains("Pseudo"));
			assertTrue(toString.contains("Password"));
			assertTrue(toString.contains("Profil"));
			assertTrue(toString.contains("{"));
			assertTrue(toString.contains("}"));
			assertTrue(toString.contains("-->"));
			assertTrue(toString.contains("|"));
			assertTrue(toString.contains("\n"));
		} catch (Exception e2) {
			fail("Erreur testAddMember : le toString  ne contient pas les elements attendus");
		}
		nbMembres = sn.nbMembers();	//Mise a jour du nombre de membres
	}
	
	@Test
	public void testAddMemberPseudoNull() {
		try {
			sn.addMember(null, "qsdfgh", "");
			fail("Erreur testAddMemberPseudoNull :  l'ajout d'un membre dont le pseudo n'est pas instancie est accepte");
		} catch (BadEntry e) {
			try {
				assertEquals(nbMembres, sn.nbMembers());
			} catch (Exception e1) {
				fail("Erreur testAddMemberPseudoNull :  le nombre de membres apres tentative d'ajout refusee a ete modifie");
			}
		} catch (Exception e) {
			fail("Erreur testAddMemberPseudoNull :  Exception non prevue");
		}
	}

	@Test
	public void testAddMemberPseudoVide() {
		try {
			sn.addMember("     ", "qsdfgh", "");
			fail("Erreur testAddMemberPseudoVide :  l'ajout d'un membre dont le pseudo ne contient pas un caractere, autre que des espaces, est accepte");
		} catch (BadEntry e) {
			try {
				assertEquals(nbMembres, sn.nbMembers());
			} catch (Exception e2) {
				fail("Erreur testAddMemberPseudoVide :  le nombre de membres apres tentative d'ajout refusee a ete modifie");
			}
		} catch (Exception e) {
			fail("Erreur testAddMemberPseudoVide :  Exception non prevue");
		}
	}

	@Test
	public void testAddMemberPasswordNull() {
		try {
			sn.addMember("B", null, "");
			fail("Erreur testAddMemberPasswordNull :  l'ajout d'un membre dont le password n'est pas instancie est accepte");
		} catch (BadEntry e) {
			try {
				assertEquals(nbMembres, sn.nbMembers());
			} catch (Exception e2) {
				fail("Erreur testAddMemberPasswordNull :  le nombre de membres apres tentative d'ajout refusee a ete modifie");
			}
		} catch (Exception e) {
			fail("Erreur testAddMemberPasswordNull :  Exception non prevue");
		}
	}

	@Test
	public void testAddMemberPasswordNotEnoughChar() {
		try {
			sn.addMember("B", "  qwd  ", "");
			fail("Erreur testAddMemberPasswordNotEnoughChar :  l'ajout d'un membre dont le password ne contient pas au moins 4 caracteres, autre que des espaces de debut ou de fin, est accepte");
		} catch (BadEntry e) {
			try {
				assertEquals(nbMembres, sn.nbMembers());
			} catch (Exception e2) {
				fail("Erreur testAddMemberPasswordNotEnoughChar :  le nombre de membres apres tentative d'ajout refusee a ete modifie");
			}
		} catch (Exception e) {
			fail("Erreur testAddMemberPasswordNotEnoughChar :  Exception non prevue");
		}
	}

	@Test
	public void testAddMemberProfilNull() {
		try {
			sn.addMember("BBBB", "bbbb", null);	
			fail("Erreur testAddMemberProfilNull :  l'ajout d'un membre dont le profil n'est pas instancie est accepte");
		} catch (BadEntry e) {
			try {
				assertEquals(nbMembres, sn.nbMembers());
			} catch (Exception e2) {
				fail("Erreur testAddMemberProfilNull : le nombre de membres apres tentative d'ajout refusee a ete modifie");
			}
		} catch (Exception e) {
			fail("Erreur testAddMemberProfilNull : Exception non prevue");
		}
	}

	@Test
	public void testAddExistingMemberWithOtherPassword() {
		try {
			testAddMember();
			sn.addMember("Paul", "abcdefghij", "");	
		} catch (MemberAlreadyExists e) {
			try {
				assertEquals(nbMembres, sn.nbMembers());
			} catch (Exception e2) {
				fail("Erreur testAddExistingMemberWithOtherPassword : l'ajout d'un membre existant deje avec un mot de passe different a ete accepte");
			}
		} catch (Exception e) {
			fail("Erreur testAddExistingMemberWithOtherPassword : Exception non prevue");
		}
	}

	@Test
	public void testAddExistingMemberWithOtherPseudoCase() {
		try {
			testAddMember();
			sn.addMember("paUl", "paul", "lecteur impulsif");	
		} catch (MemberAlreadyExists e) {
			try {
				assertEquals(nbMembres, sn.nbMembers());
			} catch (Exception e2) {
				fail("Erreur testAddExistingMemberWithOtherPseudoCase : l'ajout d'un membre existant deje avec une case differente a ete accepte");
			}
		} catch (Exception e) {
			fail("Erreur testAddExistingMemberWithOtherPseudoCase : Exception non prevue");
		}
	}

	@Test
	public void testAddExistingMemberWithBlankInPseudo() {
		try {
			testAddMember();
			sn.addMember("     Paul              	", "abcdefghij", "");	
		} catch (MemberAlreadyExists e) {
			try {
				assertEquals(nbMembres, sn.nbMembers());
			} catch (Exception e2) {
				fail("Erreur testAddExistingMemberWithBlankInPseudo : l'ajout d'un membre existant deje avec des blancs dans le pseudo a ete accepte");
			}
		} catch (Exception e) {
			fail("Erreur testAddExistingMemberWithBlankInPseudo : Exception non prevue");
		}
	}
}
