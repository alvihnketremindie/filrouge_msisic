/*
 *  Fichier SocialNetworkTestItemBook.java
 * 
 *  Cette classe test les fonctions relatives aux items de type livre (ItemBook)
 *  de l'objet SN
 * 
 *  Dernière modification: Sékou Traoré
 *  Date: 13/11/2017
 */

package test.junit.sekou;

import static org.hamcrest.CoreMatchers.is;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import avis.SN;
import avis.SocialNetwork;
import exception.BadEntry;
import exception.ItemBookAlreadyExists;
import exception.NotItem;
import exception.NotMember;

public class SocialNetworkTestItemBook {
	
	private static int nbBooks = 0;
	
	private static SN sn;
	
	@Rule
	public ErrorCollector collector = new ErrorCollector();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//On ajoute des membres au début du test, si cela échoue alors on arrete sans tester le addItemBook
		sn = new SocialNetwork();
	
		try{
		sn.addMember("pseudo", "password", "profil");
		sn.addMember("sekou", "motdepasse", "Etudiant en ISIC");
		sn.addMember("saikou", "courage", "Canadien");
		
		}catch(Exception e){
			System.out.println("ERROR 4.0: IMPOSSIBLE DE TESTER addItemBook CAR UN MEMBRE NE PEUT ETRE AJOUTEE");
		}
	}

	//Test de l'ajout d'un livre avec un pseudo de membre incorrecte
	@Test
	public void testAddItemBookWithBadPseudo(){
		
		//Test avec pseudo null
		nbBooks = sn.nbBooks();
		try {
			sn.addItemBook(null, "password", "titreLivre", "genre", "auteur", 110);	
			collector.addError(new Throwable("Erreur 4.1 :  l'ajout d'un livre dont le pseudo du membre n'est pas instancié est accepté "));
		}
		catch (BadEntry e) {
			if (sn.nbBooks() != nbBooks)
				collector.addError(new Throwable("Erreur 4.1 :  le nombre de livres après tentative d'ajout refusée a été modifié"));
		}
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 4.1, Exception non prévue : " + e));
			e.printStackTrace();
		}

		//test avec pseudo sans caractères autre que l'espace
		nbBooks = sn.nbBooks();
		try {
			sn.addItemBook("  ", "password", "titreLivre", "genre", "auteur",  110);	
			collector.addError(new Throwable("Erreur 4.2 :  l'ajout d'un livre avec un membre dont le pseudo ne contient pas un caractère, autre que des espaces, est accepté "));
		}
		catch (BadEntry e) {
			if (sn.nbBooks() != nbBooks)
				collector.addError(new Throwable("Erreur 4.2 :  le nombre de livres après tentative d'ajout refusée a été modifié"));
		}			
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 4.2, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		//Ajout d'un livre avec un membre dont le pseudo n'existe pas
		nbBooks = sn.nbBooks();
		try {
			sn.addItemBook("BadPseudo", "password", "Mort qui tue","Fantastique", "dqd", 120);
			collector.addError(new Throwable("Erreur 4.13 :  l'ajout d'un livre avec un membre inexistant est accepté"));
		}
		catch(NotMember e){
			if (sn.nbBooks() != nbBooks)
				collector.addError(new Throwable("Erreur 4.13 :  le nombre de livres après tentative d'ajout refusée a été modifié"));
		}
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 4.13, Exception non prévue : " + e));
			e.printStackTrace();
		}
	}
	
	//Test de l'ajout d'un livre avec un membre de mot de passe incorrect
	@Test
	public void testaddItemBookWithBadPassword(){
		
		//Test avec mot de passe null
		nbBooks = sn.nbBooks();
		try {
			sn.addItemBook("pseudo", null, "titreLivre", "genre", "auteur", 110);	
			collector.addError(new Throwable("Erreur 4.3 :  l'ajout d'un livre dont le password du membre n'est pas instancié est accepté "));
		}
		catch (BadEntry e) {
			if (sn.nbBooks() != nbBooks)
				collector.addError(new Throwable("Erreur 4.3 :  le nombre de membres après tentative d'ajout refusée a été modifié"));
		}
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 4.3, Exception non prévue : " + e));
			e.printStackTrace();
		}

		//Test avec mot de passe ne contenant pas au moins 4 caractères autre que des espaces en début et fin de ligne
		nbBooks = sn.nbBooks();
		try {
			sn.addItemBook("pseudo", "  qwd  ", "titre", "genre", "auteur",  110);	
			collector.addError(new Throwable("Erreur 4.4 :  l'ajout d'un livre par un membre dont le password ne contient pas au moins 4 caractères, autre que des espaces de début ou de fin, est accepté "));
		}
		catch (BadEntry e) {
			if (sn.nbBooks() != nbBooks)
				collector.addError(new Throwable("Erreur 4.4 :  le nombre de livres après tentative d'ajout refusée a été modifié"));
		}
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 4.4, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		//Ajout d'un livre avec un password ne correspondant pas
		nbBooks = sn.nbBooks();
		try {
			sn.addItemBook("pseudo", "badpassword", "Mort qui tue","Fantastique", "wan", 120);
			collector.addError(new Throwable("Erreur 4.13 :  l'ajout d'un livre avec un mauvais password de membre est accepté"));
		}
		catch(NotMember e){
			if (sn.nbBooks() != nbBooks)
				collector.addError(new Throwable("Erreur 4.13 :  le nombre de livres après tentative d'ajout refusée a été modifié"));
		}
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 4.13, Exception non prévue : " + e));
			e.printStackTrace();
		}
	}
		
		//Test de l'ajout d'un livre avec un mauvais titre
	@Test
	public void testAddItemWithBadTitle(){
		
		nbBooks = sn.nbBooks();
		try {
			sn.addItemBook("pseudo", "password", null, "genre", "auteur", 110);	
			collector.addError(new Throwable("Erreur 4.5 :  l'ajout d'un livre dont le titre n'est pas instancié est accepté."));
		}
		catch (BadEntry e) {
			if (sn.nbBooks() != nbBooks)
				collector.addError(new Throwable("Erreur 4.5 :  le nombre de livre après tentative d'ajout refusée a été modifié"));
		}
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 4.5, Exception non prévue : " + e));
			e.printStackTrace();
		}
			
			
		nbBooks = sn.nbBooks();
		try {
			sn.addItemBook("pseudo", "password", "    ", "genre", "auteur", 110);	
			collector.addError(new Throwable("Erreur 4.6 :  l'ajout d'un livre dont le titre ne contient pas au moins 1 caractère autre que espace est accepté."));
		}
		catch (BadEntry e) {
			if (sn.nbBooks() != nbBooks)
				collector.addError(new Throwable("Erreur 4.6 :  le nombre de livres après tentative d'ajout refusée a été modifié"));
		}
		catch (Exception e) {
			System.out.println("Erreur 4.6, Exception non prévue : " + e);
			e.printStackTrace();
		}
	}
	
	//Test de l'ajout d'un livre avec des infos nulles (genre, auteur)
	@Test
	public void testAddItemWithNullInfo(){
		
		nbBooks = sn.nbBooks();
		try {
			sn.addItemBook("pseudo", "password", "titre", null, "auteur", 110);	
			collector.addError(new Throwable("Erreur 4.7 :  l'ajout d'un livre dont le genre n'est pas instancié est accepté."));
		}
		catch (BadEntry e) {
			if (sn.nbBooks() != nbBooks)
				collector.addError(new Throwable("Erreur 4.7 :  le nombre de livres après tentative d'ajout refusée a été modifié"));
		}
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 4.7, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		nbBooks = sn.nbBooks();
		try {
			sn.addItemBook("pseudo", "password", "titre", "genre", null, 110);	
			collector.addError(new Throwable("Erreur 4.8 :  l'ajout d'un livre dont l'auteur n'est pas instancié est accepté."));
		}
		catch (BadEntry e) {
			if (sn.nbBooks() != nbBooks)
				collector.addError(new Throwable("Erreur 4.8 :  le nombre de livres après tentative d'ajout refusée a été modifié"));
		}
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 4.8, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
	}
	
	//test de l'ajout d'un livre avec un mauvais nombre de pages
	@Test
	public void testaddItemBookWithBadDuration(){
		
		nbBooks = sn.nbBooks();
		try {
			sn.addItemBook("pseudo", "password", "titre", "genre", "auteur",0);	
			collector.addError(new Throwable("Erreur 4.10 :  l'ajout d'un livre dont le nombre de pages est de 0 instancié est accepté."));
		}
		catch (BadEntry e) {
			if (sn.nbBooks() != nbBooks)
				collector.addError(new Throwable("Erreur 4.10 :  le nombre de livres après tentative d'ajout refusée a été modifié"));
		}
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 4.10, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		nbBooks = sn.nbBooks();
		try {
			sn.addItemBook("pseudo", "password", "titre", "genre", "auteur", -55);	
			collector.addError(new Throwable("Erreur 4.11 :  l'ajout d'un livre dont le nombre de pages est négative est accepté."));
		}
		catch (BadEntry e) {
			if (sn.nbBooks() != nbBooks)
				collector.addError(new Throwable("Erreur 4.11 :  le nombre de membres après tentative d'ajout refusée a été modifié"));
		}
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 4.11, Exception non prévue : " + e));
			e.printStackTrace();
		}
	}
	
	//test de l'ajout d'un livre 
	@Test
	public void testAdd3CorrectItemBook(){
		
		// ajout de 3 livres et test que le nombre de livres ajoutés vaut bien 3
		nbBooks = sn.nbBooks();
		try {
			sn.addItemBook("pseudo", "password", "Seigneur des Anneaux","Fantastique", "BigShow", 120);
			sn.addItemBook("pseudo", "password", "Le programmeur Teubreux","Drame", "Mysterious",  150);
			sn.addItemBook("pseudo", "password", "La mort sinistre","Tragédie", "mini", 63);
			
			if (sn.nbBooks()!= (nbBooks + 3)) 
				collector.addError(new Throwable("Erreur 4.12 :  le nombre de livres après ajout de 3 livres n'a pas augmenté de 3"));
			
		}
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 4.12, Exception non prévue : " + e));
			e.printStackTrace();
		}
	}
	
	//Test ajout de livre déjà existant (APPELER après testAdd3CoorectItemBook afin de faire les tests sur les livres ajoutés par eux ci)
	@Test
	public void testAddAlreadyExistBooks(){
		
		//Ajout d'un livre avec un titre déjà existant correspondant au premier livre ajouté
		nbBooks = sn.nbBooks();
		try {
			sn.addItemBook("pseudo", "password", "Seigneur des Anneaux","test", "dqd", 120);
			collector.addError(new Throwable("Erreur 4.14 :  l'ajout d'un livre avec un titre déjà existant est accepté"));
		}
		catch(ItemBookAlreadyExists e){
			if (sn.nbBooks() != nbBooks)
				collector.addError(new Throwable("Erreur 4.14:  le nombre de livres après tentative d'ajout refusée a été modifié"));
		}
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 4.14, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		//Ajout d'un livre avec un titre déjà existant correspondant au DERNIER livre ajouté
		nbBooks = sn.nbBooks();
		try {
			sn.addItemBook("pseudo", "password", "La mort sinistre","test", "dqd", 120);
			collector.addError(new Throwable("Erreur 4.16 :  l'ajout d'un livre avec un titre déjà existant est accepté"));
		}
		catch(ItemBookAlreadyExists e){
			if (sn.nbBooks() != nbBooks)
				collector.addError(new Throwable("Erreur 4.16:  le nombre de livres après tentative d'ajout refusée a été modifié"));
		}
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 4.16, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		
		//Ajout d'un titre de livre déjà existant en modifiant la casse  
		nbBooks = sn.nbBooks();
		try {
			sn.addItemBook("pseudo", "password", "Le prograMMeur Teubreux","test", "dqd", 120);
			collector.addError(new Throwable("Erreur 4.15 :  l'ajout d'un livre avec un titre déjà existant (casse version) est accepté"));
		}
		catch(ItemBookAlreadyExists e){
			if (sn.nbBooks() != nbBooks)
				collector.addError(new Throwable("Erreur 4.15:  le nombre de livres après tentative d'ajout refusée a été modifié"));
		}
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 4.15, Exception non prévue : " + e));
			e.printStackTrace();
		}
	}
	
	/*********************************************************************
	 * 
	 * 	Test d'ajout d'une opinion (Appelé après testAdd3CorrectItemBook afin de s'assurer que les livres qu'on souhaite commenter existe déjà) 
	 * 
	 *********************************************************************/
	@Test
	public void reviewItemBookTest(){
		
		//Test d'ajout d'un avis avec un pseudo null
		try {
			sn.reviewItemBook(null, "password", "Le programmeur Teubreux", 4, "Beau livre");	
			collector.addError(new Throwable("Erreur 4.17 :  l'ajout d'un avis par un membre dont le pseudo n'est pas instancié est accepté"));
		}
		catch (BadEntry e) {} //Le test est reussi si une exception BadEntry est catchée
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 4.17, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		//Test d'ajout d'un avis avec un pseudo constitué uniquement d'espaces
		try {
			sn.reviewItemBook("   ", "password", "Le programmeur Teubreux", 4, "Beau livre");	
			collector.addError(new Throwable("Erreur 4.18 :  l'ajout d'un avis par un membre dont le pseudo est constitué d'espaces est accepté"));
		}
		catch (BadEntry e) {} //Le test est reussi si une exception BadEntry est catchée
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 4.18, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		//Test d'ajout d'un avis avec un password nul
		try {
			sn.reviewItemBook("pseudo", null, "Le programmeur Teubreux", 4, "Beau livre");	
			collector.addError(new Throwable("Erreur 4.19 :  l'ajout d'un avis par un membre avec un password null est acceptée"));
		}
		catch (BadEntry e) {} //Le test est reussi si une exception BadEntry est catchée
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 4.19, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		//Test d'ajout d'un avis avec un password ne contenant pas au moins 4 caractères autres que des espaces en début et fin de lignes
		try {
			sn.reviewItemBook("pseudo", " pas ", "Le programmeur Teubreux", 4, "Beau livre");	
			collector.addError(new Throwable("Erreur 4.20 :  l'ajout d'un avis par un membre avec un password ne contenant pas au moins 4 caractères autre que des espaces en début et fin de ligne est accepté."));
		}
		catch (BadEntry e) {} //Le test est reussi si une exception BadEntry est catchée
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 4.20, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		//Test d'ajout d'un avis avec un titre de livre null
		try {
			sn.reviewItemBook("pseudo", "password", null, 4, "Beau livre");	
			collector.addError(new Throwable("Erreur 4.20 :  l'ajout d'un avis avec un titre de livre null est accepté."));
		}
		catch (BadEntry e) {} //Le test est reussi si une exception BadEntry est catchée
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 4.20, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		//Test d'ajout d'un avis avec un titre de livre ne contenant pas au moins 1 caractère autre que 'espace'
		try {
			sn.reviewItemBook("pseudo", "password", "   ", 4, "Beau livre");	
			collector.addError(new Throwable("Erreur 4.21 :  l'ajout d'un avis par un membre avec un password ne contenant pas au moins 1 caractère autre que 'espace' est accepté."));
		}
		catch (BadEntry e) {} //Le test est reussi si une exception BadEntry est catchée
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 4.21, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		//Test d'ajout d'un avis avec une note inférieur à 0
		try {
			sn.reviewItemBook("pseudo", "password", "Le programmeur Teubreux", -0.1f, "Beau livre");	
			collector.addError(new Throwable("Erreur 4.22 :  l'ajout d'un avis avec une note négative est accepté."));
		}
		catch (BadEntry e) {} //Le test est reussi si une exception BadEntry est catchée
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 4.22, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		//Test d'ajout d'un avis avec une note supérieure à 5
		try {
			sn.reviewItemBook("pseudo", "password", "Le programmeur Teubreux", 5.1f, "Beau livre");	
			collector.addError(new Throwable("Erreur 4.23 :  l'ajout d'un avis avec une note supérieure à 5 est accepté."));
		}
		catch (BadEntry e) {} //Le test est reussi si une exception BadEntry est catchée
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 4.23, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		//Test d'ajout d'un avis avec un commentaire null
		try {
			sn.reviewItemBook("pseudo", "password", "Le programmeur Teubreux", 4.0f, null);	
			collector.addError(new Throwable("Erreur 4.24 :  l'ajout d'un avis avec un commentaire null est accepté."));
		}
		catch (BadEntry e) {} //Le test est reussi si une exception BadEntry est catchée
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 4.24, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		//Test d'ajout d'un avis avec un pseudo de membre inexistant
		try {
			sn.reviewItemBook("pseudo2", "password", "Le programmeur Teubreux", 4.0f, null);	
			collector.addError(new Throwable("Erreur 4.25 :  l'ajout d'un avis avec un commentaire null est accepté."));
		}
		catch (NotMember e) {} //Le test est reussi si une exception NotMember est catchée
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 4.25, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		//Test d'ajout d'un avis avec un mot de passe ne correspondant pas au pseudo du membre
		try {
			sn.reviewItemBook("pseudo", "password2", "Le programmeur Teubreux", 4.0f, "Très beau livre");	
			collector.addError(new Throwable("Erreur 4.26 :  l'ajout d'un avis avec un commentaire null est accepté."));
		}
		catch (NotMember e) {} //Le test est reussi si une exception BadEntry est catchée
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 4.26, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		//Test d'ajout d'un avis avec un titre de livre erroné
		try {
			sn.reviewItemBook("pseudo", "password", "Vive le test logiciel", 0.5f, "livre mensonger ");	
			collector.addError(new Throwable("Erreur 4.27 :  l'ajout d'un avis sur un livre inexistant est accepté."));
		}
		catch (NotItem e) {} //Le test est reussi si une exception BadEntry est catchée
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 4.27, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		//Test d'ajout de 3 avis par 3 membres différents et verification de la moyenne calculée
		try {
			float note1 = 5f, note2=0f, note3 = 2.25f, moyenne;
			moyenne = sn.reviewItemBook("pseudo", "password", "Le prograMMeur Teubreux", note1, "livre parfait");	
			collector.checkThat("Moyenne incorrecte après ajout d'un 1er avis sur un livre",moyenne,is(note1));
			moyenne = sn.reviewItemBook("sekou", "motdepasse", "Le programmeur Teubreux", note2, "livre nul !");
			collector.checkThat("Moyenne incorrecte après ajout d'une 2eme de valeur nulle (d'avis) sur un livre",moyenne,is(note1/2));
			moyenne = sn.reviewItemBook("saikou", "courage", "Le programmeur Teubreux", note3, "livre moyen");
			collector.checkThat("Moyenne incorrecte après ajout d'un 3eme avis sur un livre",moyenne,is((note1+note2+note3)/3));
			System.out.println(moyenne);
			//Test d'ajout d'un avis par un même membre et vérification de la nouvelle moyenne avec ce nombre retourné
			float newNote2 = 4.5f;
			moyenne = sn.reviewItemBook("sekou", "motdepasse", "Le programmeur Teubreux", newNote2, "Je l'ai revu... en fait il est bien.");
			collector.checkThat("Moyenne incorrecte après le changement d'un avis par un membre",moyenne,is((note1+newNote2+note3)/3));
			System.out.println(moyenne);
			
		}
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 4.28, L'ajout de 3 avis correctes échouent. (Cela peut aussi etre du a des notes d'avis limites (0 ou 5)." + e));
			e.printStackTrace();
		}
		
	}

}
