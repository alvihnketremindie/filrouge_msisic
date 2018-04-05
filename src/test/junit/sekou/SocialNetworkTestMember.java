/*
 *  Fichier SocialNetworkTestItemFilm.java
 * 
 *  Cette classe test les fonctions relatives aux membres de l'objet SN
 * 
 *  Dernière modification: Sékou Traoré
 *  Date: 13/11/2017
 */

package test.junit.sekou;

import static org.hamcrest.CoreMatchers.is;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import avis.SN;
import avis.SocialNetwork;
import exception.BadEntry;
import exception.MemberAlreadyExists;

public class SocialNetworkTestMember {
	
	private static int nbMembres = 0;
	
	private static SN sn;
	
	@Rule
	public ErrorCollector collector = new ErrorCollector();

	@Before
	public void setUp() throws Exception {
		
		//Création d'un nouvel objet SN en début de chaque test 
		sn = new SocialNetwork();
		
		//On met à jour les variables qui servent à vérifier que le nombre d'objets n'a pas changé après des échecs d'ajout
		nbMembres = sn.nbMembers();
	}
	
	//On vérifie que les nombre de membres, de livres et de films sont bien initialisés à 0
	@Test
	public void testInitialisation() {
	
		collector.checkThat("Erreur 1.1 :  le nombre de membres après création du réseau est non nul",sn.nbMembers(),is(0));
		collector.checkThat("Erreur 1.2 :  le nombre de livres après création du réseau est non nul",sn.nbBooks(),is(0));
		collector.checkThat("Erreur 1.3 :  le nombre de films après création du réseau est non nul",sn.nbFilms(),is(0));
	}
	
	/*************************************************************************************************************************
	 * 
	 * 			DEBUT DES TESTS DE ADD MEMBER
	 * 
	 ************************************************************************************************************************/
	
	/*
	 * 	Ce test vérifie que aucun membre n'est ajoutée lorsque le pseudo est mauvais
	 */
	@Test 
	public void testAddMemberWithBadPseudo(){
		
		nbMembres = sn.nbMembers();
		//On teste l'ajout d'un membre avec un pseudo nul
		try {
			sn.addMember(null, "qsdfgh", "");	
			collector.addError(new Throwable("Erreur 2.1 :  l'ajout d'un membre dont le pseudo n'est pas instancié est accepté"));
		}
		catch (BadEntry e) {
			if (sn.nbMembers() != nbMembres)
				collector.addError(new Throwable("Erreur 2.1 :  le nombre de membres après tentative d'ajout refusée a été modifié"));
		}
		catch (Exception e) {
			System.out.println("Erreur 2.1, Exception non prévue : " + e);
			e.printStackTrace();
		}
		
		//Test ajout d'un membre avec un pseudo ne contenant pas au moins 1 caractère autre que des espaces
		nbMembres = sn.nbMembers();
		try {
			sn.addMember("  ", "qsdfgh", "");	
			collector.addError(new Throwable("Erreur 2.2 :  l'ajout d'un membre dont le pseudo ne contient pas un caractère, autre que des espaces, est accepté"));
		}
		catch (BadEntry e) {
			if (sn.nbMembers() != nbMembres)
				collector.addError(new Throwable("Erreur 2.2 :  le nombre de membres après tentative d'ajout refusée a été modifié"));
		}			
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 2.2, Exception non prévue : " + e));
			e.printStackTrace();
		}
	}
	
	/*
	 * Test de l'ajout d'un membre avec un mauvais mot de passe
	 */
	@Test 
	public void testAddMemberWithBadPassword(){
		
		nbMembres = sn.nbMembers();
		//On teste l'ajout d'un membre avec un password nul
		try {
			sn.addMember("B", null, "");	
			collector.addError(new Throwable("Erreur 2.3 :  l'ajout d'un membre dont le mot de passe n'est pas instancié est accepté"));
		}
		catch (BadEntry e) {
			if (sn.nbMembers() != nbMembres)
				collector.addError(new Throwable("Erreur 2.3 :  le nombre de membres après tentative d'ajout refusée a été modifié"));
		}
		catch (Exception e) {
			System.out.println("Erreur 2.3, Exception non prévue : " + e);
			e.printStackTrace();
		}
		
		//Test ajout d'un membre avec un mot de passe ne contenant pas au moins 4 caractères autres que des espaces en début et fin de chaines
		nbMembres = sn.nbMembers();
		try {
			sn.addMember("B", "  qwd  ", "");	
			collector.addError(new Throwable("Erreur 2.4 :  l'ajout d'un membre dont le mdp ne contient pas au moins 4 caractère, autre que des espaces (début et fin de lignes), est accepté"));
		}
		catch (BadEntry e) {
			if (sn.nbMembers() != nbMembres)
				collector.addError(new Throwable("Erreur 2.4 :  le nombre de membres après tentative d'ajout refusée a été modifié"));
		}			
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 2.4, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
	}
	
	/*
	 * Test d'un membre ajouté avec un profil null
	 */
	@Test
	public void testAddMemberWithNullProfil(){
		
		nbMembres = sn.nbMembers();
		try {
			sn.addMember("BBBB", "bbbb", null);	
			collector.addError(new Throwable("Erreur 2.5 :  l'ajout d'un membre dont le profil n'est pas instancié est accepté"));
		}
		catch (BadEntry e) {
			if (sn.nbMembers() != nbMembres)
				collector.addError(new Throwable("Erreur 2.5 :  le nombre de membres après tentative d'ajout refusée a été modifié"));
		}
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 2.5, Exception non prévue : " + e));
			e.printStackTrace();
		}
	}
	
	/*
	 *  Test d'un ajout de 3 membres correctes
	 */
	@Test
	public void testAdd3CorrectMembers(){
		
		nbMembres = sn.nbMembers();
		try {
			sn.addMember("Paul", "paul", "lecteur impulsif");
			sn.addMember("Antoine", "antoine", "grand amoureux de littérature");
			sn.addMember("Alice", "alice", "23 ans, sexy");
			if (sn.nbMembers()!= (nbMembres + 3)) 
				collector.addError(new Throwable("Erreur 3.6 :  le nombre de membres après ajout de 3 membres n'a pas augmenté de 3."));
		}
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 2.6, Exception non prévue : " + e));
			e.printStackTrace();
		}
	}
	
	/*
	 * Test de l'ajout d'un membre déjà existant
	 */
	@Test
	public void testAddAlreadyExistMember(){
		try {
			sn.addMember("Paul", "paul", "lecteur impulsif");
			sn.addMember("Antoine", "antoine", "grand amoureux de littérature");
			sn.addMember("Alice", "alice", "23 ans, sexy");
			
			nbMembres = sn.nbMembers();
			//Tentative d'ajout d'un membre avec pseudo similaire au premier membre ajouté
			try{
				sn.addMember("Paul", "sdfqsdf", "0 impulsif");
				collector.addError(new Throwable("Erreur 2.7 : L'ajout d'un membre avec le même pseudo que le premier membre a reussi."));
			}catch (MemberAlreadyExists e) {
				if (sn.nbMembers() != nbMembres)
					collector.addError(new Throwable("Erreur 2.7 :  le nombre de membres après tentative d'ajout refusée a été modifié"));
			}
			catch(Exception e){
				collector.addError(new Throwable("Erreur 2.7, Exception non prévue : " + e));
				e.printStackTrace();
			}
			
			//Tentative d'ajout d'un membre avec un pseudo similaire au dernier membre ajouté
			try{
				sn.addMember("Alice", "qfdqsdfq", "Clahuteuse");
				collector.addError(new Throwable("Erreur 2.8 : L'ajout d'un membre avec le même pseudo que le dernier membre a reussi."));
			}catch (MemberAlreadyExists e) {
				if (sn.nbMembers() != nbMembres)
					collector.addError(new Throwable("Erreur 2.8 :  le nombre de membres après tentative d'ajout refusée a été modifié"));
			}
			catch(Exception e){
				collector.addError(new Throwable("Erreur 8, Exception non prévue : " + e));
				e.printStackTrace();
			}
			
			//Tentative d'ajout d'un membre avec un pseudo similaire (avec casse différente)
			nbMembres = sn.nbMembers();
			try {
				sn.addMember("anToine", "abcdefghij", "");	
				collector.addError(new Throwable("Erreur 2.9 :  l'ajout d'un membre avec un pseudo existant (avec casse différente) est accepté "));
			}
			catch (MemberAlreadyExists e) {
				if (sn.nbMembers() != nbMembres)
					collector.addError(new Throwable("Erreur 2.9 :  le nombre de membres après tentative d'ajout refusée a été modifié"));
			}
			catch (Exception e) {
				collector.addError(new Throwable("Erreur 2.9, Exception non prévue : " + e));
				e.printStackTrace();
			}
			
			//Tentative d'ajout d'un membre avec un pseudo existant (avec leading et trailing blanks)
			nbMembres = sn.nbMembers();
			try {
				sn.addMember("  Antoine  ", "abcdefghij", "");	
				collector.addError(new Throwable("Erreur 2.10 :  l'ajout d'un membre avec un pseudo existant (avec leading et trailing blanks) est accepté "));
			}
			catch (MemberAlreadyExists e) {
				if (sn.nbMembers() != nbMembres)
					collector.addError(new Throwable("Erreur 2.10 :  le nombre de membres après tentative d'ajout refusée a été modifié"));
			}
			catch (Exception e) {
				collector.addError(new Throwable("Erreur 3.10, Exception non prévue : " + e));
				e.printStackTrace();
			}
			
			//On vérifie que le nombre de livres et le nombre de films n'a pas augmenté
			collector.checkThat("Le nombre de films a augmenté avec l'ajout de membres", sn.nbFilms(), is(0));
			collector.checkThat("Le nombre de livres a augmenté avec l'ajout de membres", sn.nbBooks(), is(0));
			
		}
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 2.7, Impossible d'ajouter un membre correct donc impossibilité de test de l'ajout d'un membre doublon." + e));
			e.printStackTrace();
		}
		
	}

}
