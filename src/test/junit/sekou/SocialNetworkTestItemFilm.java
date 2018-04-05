/*
 *  Fichier SocialNetworkTestItemFilm.java
 * 
 *  Cette classe test les fonctions relatives aux items de type film (ItemFilm)
 *  de l'objet SN
 * 
 *  Dernière modification: Sékou Traoré
 *  Date: 16/11/2017
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
import exception.ItemFilmAlreadyExists;
import exception.NotItem;
import exception.NotMember;

public class SocialNetworkTestItemFilm {
	
	private static int nbFilms = 0;
	
	private static SN sn;
	
	@Rule
	public ErrorCollector collector = new ErrorCollector();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//On ajoute des membres au début du test, si cela échoue alors on arrete sans tester le addItemFilm
		sn = new SocialNetwork();
	
		try{
		sn.addMember("pseudo", "password", "profil");
		sn.addMember("sekou", "motdepasse", "Etudiant en ISIC");
		sn.addMember("saikou", "courage", "Canadien");
		
		}catch(Exception e){
			System.out.println("ERROR 3.0: IMPOSSIBLE DE TESTER ADDITEMFILM CAR UN MEMBRE NE PEUT ETRE AJOUTEE");
		}
	}

	//Test de l'ajout d'un film avec un pseudo de membre incorrecte
	@Test
	public void testAddItemFilmWithBadPseudo(){
		
		//Test avec pseudo null
		nbFilms = sn.nbFilms();
		try {
			sn.addItemFilm(null, "password", "titreLivre", "genre", "realisateur", "scenariste", 110);	
			collector.addError(new Throwable("Erreur 3.1 :  l'ajout d'un film dont le pseudo du membre n'est pas instancié est accepté "));
		}
		catch (BadEntry e) {
			if (sn.nbFilms() != nbFilms)
				collector.addError(new Throwable("Erreur 3.1 :  le nombre de films après tentative d'ajout refusée a été modifié"));
		}
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 3.1, Exception non prévue : " + e));
			e.printStackTrace();
		}

		//test avec pseudo sans caractères autre que l'espace
		nbFilms = sn.nbFilms();
		try {
			sn.addItemFilm("  ", "password", "titreLivre", "genre", "realisateur", "scenariste", 110);	
			collector.addError(new Throwable("Erreur 3.2 :  l'ajout d'un film avec un membre dont le pseudo ne contient pas un caractère, autre que des espaces, est accepté "));
		}
		catch (BadEntry e) {
			if (sn.nbFilms() != nbFilms)
				collector.addError(new Throwable("Erreur 3.2 :  le nombre de films après tentative d'ajout refusée a été modifié"));
		}			
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 3.2, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		//Ajout d'un film avec un membre dont le pseudo n'existe pas
		nbFilms = sn.nbFilms();
		try {
			sn.addItemFilm("BadPseudo", "password", "Mort qui tue","Fantastique", "wan", "dqd", 120);
			collector.addError(new Throwable("Erreur 3.13 :  l'ajout d'un film avec un membre inexistant est accepté"));
		}
		catch(NotMember e){
			if (sn.nbFilms() != nbFilms)
				collector.addError(new Throwable("Erreur 3.13 :  le nombre de films après tentative d'ajout refusée a été modifié"));
		}
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 3.13, Exception non prévue : " + e));
			e.printStackTrace();
		}
	}
	
	//Test de l'ajout d'un film avec un membre de mot de passe incorrect
	@Test
	public void testAddItemFilmWithBadPassword(){
		
		//Test avec mot de passe null
		nbFilms = sn.nbFilms();
		try {
			sn.addItemFilm("pseudo", null, "titreLivre", "genre", "realisateur", "scenariste", 110);	
			collector.addError(new Throwable("Erreur 3.3 :  l'ajout d'un film dont le password du membre n'est pas instancié est accepté "));
		}
		catch (BadEntry e) {
			if (sn.nbFilms() != nbFilms)
				collector.addError(new Throwable("Erreur 3.3 :  le nombre de membres après tentative d'ajout refusée a été modifié"));
		}
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 3.3, Exception non prévue : " + e));
			e.printStackTrace();
		}

		//Test avec mot de passe ne contenant pas au moins 4 caractères autre que des espaces en début et fin de ligne
		nbFilms = sn.nbFilms();
		try {
			sn.addItemFilm("pseudo", "  qwd  ", "titre", "genre", "realisateur", "scenariste", 110);	
			collector.addError(new Throwable("Erreur 3.4 :  l'ajout d'un livre par un membre dont le password ne contient pas au moins 4 caractères, autre que des espaces de début ou de fin, est accepté "));
		}
		catch (BadEntry e) {
			if (sn.nbFilms() != nbFilms)
				collector.addError(new Throwable("Erreur 3.4 :  le nombre de films après tentative d'ajout refusée a été modifié"));
		}
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 3.4, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		//Ajout d'un film avec un password ne correspondant pas
		nbFilms = sn.nbFilms();
		try {
			sn.addItemFilm("pseudo", "badpassword", "Mort qui tue","Fantastique", "wan", "dqd", 120);
			collector.addError(new Throwable("Erreur 3.13 :  l'ajout d'un film avec un mauvais password de membre est accepté"));
		}
		catch(NotMember e){
			if (sn.nbFilms() != nbFilms)
				collector.addError(new Throwable("Erreur 3.13 :  le nombre de films après tentative d'ajout refusée a été modifié"));
		}
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 3.13, Exception non prévue : " + e));
			e.printStackTrace();
		}
	}
		
		//Test de l'ajout d'un film avec un mauvais titre
	@Test
	public void testAddItemWithBadTitle(){
		
		nbFilms = sn.nbFilms();
		try {
			sn.addItemFilm("pseudo", "password", null, "genre", "realisateur", "scenariste", 110);	
			collector.addError(new Throwable("Erreur 3.5 :  l'ajout d'un livre dont le titre n'est pas instancié est accepté."));
		}
		catch (BadEntry e) {
			if (sn.nbFilms() != nbFilms)
				collector.addError(new Throwable("Erreur 3.5 :  le nombre de films après tentative d'ajout refusée a été modifié"));
		}
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 3.5, Exception non prévue : " + e));
			e.printStackTrace();
		}
			
			
		nbFilms = sn.nbFilms();
		try {
			sn.addItemFilm("pseudo", "password", "    ", "genre", "realisateur", "scenariste", 110);	
			collector.addError(new Throwable("Erreur 3.6 :  l'ajout d'un livre dont le titre ne contient pas au moins 1 caractère autre que espace est accepté."));
		}
		catch (BadEntry e) {
			if (sn.nbFilms() != nbFilms)
				collector.addError(new Throwable("Erreur 3.6 :  le nombre de films après tentative d'ajout refusée a été modifié"));
		}
		catch (Exception e) {
			System.out.println("Erreur 3.6, Exception non prévue : " + e);
			e.printStackTrace();
		}
	}
	
	//Test de l'ajout d'un film avec des infos nulles (genre, realisateur, scenariste)
	@Test
	public void testAddItemWithNullInfo(){
		
		nbFilms = sn.nbFilms();
		try {
			sn.addItemFilm("pseudo", "password", "titre", null, "realisateur", "scenariste", 110);	
			collector.addError(new Throwable("Erreur 3.7 :  l'ajout d'un livre dont le genre n'est pas instancié est accepté."));
		}
		catch (BadEntry e) {
			if (sn.nbFilms() != nbFilms)
				collector.addError(new Throwable("Erreur 3.7 :  le nombre de films après tentative d'ajout refusée a été modifié"));
		}
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 3.7, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		nbFilms = sn.nbFilms();
		try {
			sn.addItemFilm("pseudo", "password", "titre", "genre", null, "scenariste", 110);	
			collector.addError(new Throwable("Erreur 3.8 :  l'ajout d'un livre dont le realisateur n'est pas instancié est accepté."));
		}
		catch (BadEntry e) {
			if (sn.nbFilms() != nbFilms)
				collector.addError(new Throwable("Erreur 3.8 :  le nombre de films après tentative d'ajout refusée a été modifié"));
		}
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 3.8, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		nbFilms = sn.nbFilms();
		try {
			sn.addItemFilm("pseudo", "password", "titre", "genre", "realisateur", null, 110);	
			collector.addError(new Throwable("Erreur 3.9 :  l'ajout d'un livre dont le scenariste n'est pas instancié est accepté."));
		}
		catch (BadEntry e) {
			if (sn.nbFilms() != nbFilms)
				collector.addError(new Throwable("Erreur 3.9 :  le nombre de films après tentative d'ajout refusée a été modifié"));
		}
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 3.9, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
	}
	
	//test de l'ajout d'un film avec une mauvaise durée
	@Test
	public void testAddItemFilmWithBadDuration(){
		
		nbFilms = sn.nbFilms();
		try {
			sn.addItemFilm("pseudo", "password", "titre", "genre", "realisateur", "scenariste", 0);	
			collector.addError(new Throwable("Erreur 3.10 :  l'ajout d'un film dont la durée est de 0 instancié est accepté."));
		}
		catch (BadEntry e) {
			if (sn.nbFilms() != nbFilms)
				collector.addError(new Throwable("Erreur 3.10 :  le nombre de films après tentative d'ajout refusée a été modifié"));
		}
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 3.10, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		nbFilms = sn.nbFilms();
		try {
			sn.addItemFilm("pseudo", "password", "titre", "genre", "realisateur", "scenariste", -55);	
			collector.addError(new Throwable("Erreur 3.11 :  l'ajout d'un film dont la durée est négative est accepté."));
		}
		catch (BadEntry e) {
			if (sn.nbFilms() != nbFilms)
				collector.addError(new Throwable("Erreur 3.11 :  le nombre de membres après tentative d'ajout refusée a été modifié"));
		}
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 3.11, Exception non prévue : " + e));
			e.printStackTrace();
		}
	}
	
	//test de l'ajout d'un film 
	@Test
	public void testAdd3CorrectItemFilm(){
		
		// ajout de 3 films et test que le nombre de films ajoutés vaut bien 3
		nbFilms = sn.nbFilms();
		try {
			sn.addItemFilm("pseudo", "password", "Seigneur des Anneaux","Fantastique", "BigShow", "Undertaker", 120);
			sn.addItemFilm("pseudo", "password", "Le programmeur Teubreux","Drame", "Mysterious", "TripleH", 150);
			sn.addItemFilm("pseudo", "password", "La mort sinistre","Tragédie", "mini", "max", 63);
			
			if (sn.nbFilms()!= (nbFilms + 3)) 
				collector.addError(new Throwable("Erreur 3.12 :  le nombre de films après ajout de 3 films n'a pas augmenté de 3"));
			
			//testAddAlreadyExistFilms();
		}
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 3.12, Exception non prévue : " + e));
			e.printStackTrace();
		}
	}
	
	//Test ajout de film déjà existant (APPELER après testAdd3CoorectItemFilm afin de faire les tests sur les films ajoutés par eux ci)
	@Test
	public void testAddAlreadyExistFilms(){
		
		//Ajout d'un film avec un titre déjà existant correspondant au premier film ajouté
		nbFilms = sn.nbFilms();
		try {
			sn.addItemFilm("pseudo", "password", "Seigneur des Anneaux","test", "wan", "dqd", 120);
			collector.addError(new Throwable("Erreur 3.14 :  l'ajout d'un film avec un titre déjà existant est accepté"));
		}
		catch(ItemFilmAlreadyExists e){
			if (sn.nbFilms() != nbFilms)
				collector.addError(new Throwable("Erreur 3.14:  le nombre de films après tentative d'ajout refusée a été modifié"));
		}
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 3.14, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		//Ajout d'un film avec un titre déjà existant correspondant au DERNIER film ajouté
		nbFilms = sn.nbFilms();
		try {
			sn.addItemFilm("pseudo", "password", "La mort sinistre","test", "wan", "dqd", 120);
			collector.addError(new Throwable("Erreur 3.16 :  l'ajout d'un film avec un titre déjà existant est accepté"));
		}
		catch(ItemFilmAlreadyExists e){
			if (sn.nbFilms() != nbFilms)
				collector.addError(new Throwable("Erreur 3.16:  le nombre de films après tentative d'ajout refusée a été modifié"));
		}
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 3.16, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		
		//Ajout d'un titre de film déjà existant en modifiant la casse  
		nbFilms = sn.nbFilms();
		try {
			sn.addItemFilm("pseudo", "password", "Le prograMMeur Teubreux","test", "wan", "dqd", 120);
			collector.addError(new Throwable("Erreur 3.15 :  l'ajout d'un film avec un titre déjà existant (casse version) est accepté"));
		}
		catch(ItemFilmAlreadyExists e){
			if (sn.nbFilms() != nbFilms)
				collector.addError(new Throwable("Erreur 3.15:  le nombre de films après tentative d'ajout refusée a été modifié"));
		}
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 3.15, Exception non prévue : " + e));
			e.printStackTrace();
		}
	}
	
	/*********************************************************************
	 * 
	 * 	Test d'ajout d'une opinion (Appelé après testAdd3CorrectItemFilm afin de s'assurer que les films qu'on souhaite commenter existe déjà) 
	 * 
	 *********************************************************************/
	@Test
	public void reviewItemFilmTest(){
		
		//Test d'ajout d'un avis avec un pseudo null
		try {
			sn.reviewItemFilm(null, "password", "Le programmeur Teubreux", 4, "Beau film");	
			collector.addError(new Throwable("Erreur 3.17 :  l'ajout d'un avis par un membre dont le pseudo n'est pas instancié est accepté"));
		}
		catch (BadEntry e) {} //Le test est reussi si une exception BadEntry est catchée
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 3.17, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		//Test d'ajout d'un avis avec un pseudo constitué uniquement d'espaces
		try {
			sn.reviewItemFilm("   ", "password", "Le programmeur Teubreux", 4, "Beau film");	
			collector.addError(new Throwable("Erreur 3.18 :  l'ajout d'un avis par un membre dont le pseudo est constitué d'espaces est accepté"));
		}
		catch (BadEntry e) {} //Le test est reussi si une exception BadEntry est catchée
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 3.18, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		//Test d'ajout d'un avis avec un password nul
		try {
			sn.reviewItemFilm("pseudo", null, "Le programmeur Teubreux", 4, "Beau film");	
			collector.addError(new Throwable("Erreur 3.19 :  l'ajout d'un avis par un membre avec un password null est acceptée"));
		}
		catch (BadEntry e) {} //Le test est reussi si une exception BadEntry est catchée
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 3.19, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		//Test d'ajout d'un avis avec un password ne contenant pas au moins 4 caractères autres que des espaces en début et fin de lignes
		try {
			sn.reviewItemFilm("pseudo", " pas ", "Le programmeur Teubreux", 4, "Beau film");	
			collector.addError(new Throwable("Erreur 3.20 :  l'ajout d'un avis par un membre avec un password ne contenant pas au moins 4 caractères autre que des espaces en début et fin de ligne est accepté."));
		}
		catch (BadEntry e) {} //Le test est reussi si une exception BadEntry est catchée
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 3.20, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		//Test d'ajout d'un avis avec un titre de film null
		try {
			sn.reviewItemFilm("pseudo", "password", null, 4, "Beau film");	
			collector.addError(new Throwable("Erreur 3.20 :  l'ajout d'un avis avec un titre de film null est accepté."));
		}
		catch (BadEntry e) {} //Le test est reussi si une exception BadEntry est catchée
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 3.20, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		//Test d'ajout d'un avis avec un titre de film ne contenant pas au moins 1 caractère autre que 'espace'
		try {
			sn.reviewItemFilm("pseudo", "password", "   ", 4, "Beau film");	
			collector.addError(new Throwable("Erreur 3.21 :  l'ajout d'un avis par un membre avec un password ne contenant pas au moins 1 caractère autre que 'espace' est accepté."));
		}
		catch (BadEntry e) {} //Le test est reussi si une exception BadEntry est catchée
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 3.21, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		//Test d'ajout d'un avis avec une note inférieur à 0
		try {
			sn.reviewItemFilm("pseudo", "password", "Le programmeur Teubreux", -0.1f, "Beau film");	
			collector.addError(new Throwable("Erreur 3.22 :  l'ajout d'un avis avec une note négative est accepté."));
		}
		catch (BadEntry e) {} //Le test est reussi si une exception BadEntry est catchée
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 3.22, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		//Test d'ajout d'un avis avec une note supérieure à 5
		try {
			sn.reviewItemFilm("pseudo", "password", "Le programmeur Teubreux", 5.1f, "Beau film");	
			collector.addError(new Throwable("Erreur 3.23 :  l'ajout d'un avis avec une note supérieure à 5 est accepté."));
		}
		catch (BadEntry e) {} //Le test est reussi si une exception BadEntry est catchée
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 3.23, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		//Test d'ajout d'un avis avec un commentaire null
		try {
			sn.reviewItemFilm("pseudo", "password", "Le programmeur Teubreux", 4.0f, null);	
			collector.addError(new Throwable("Erreur 3.24 :  l'ajout d'un avis avec un commentaire null est accepté."));
		}
		catch (BadEntry e) {} //Le test est reussi si une exception BadEntry est catchée
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 3.24, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		//Test d'ajout d'un avis avec un pseudo de membre inexistant
		try {
			sn.reviewItemFilm("pseudo2", "password", "Le programmeur Teubreux", 4.0f, null);	
			collector.addError(new Throwable("Erreur 3.25 :  l'ajout d'un avis avec un commentaire null est accepté."));
		}
		catch (NotMember e) {} //Le test est reussi si une exception NotMember est catchée
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 3.25, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		//Test d'ajout d'un avis avec un mot de passe ne correspondant pas au pseudo du membre
		try {
			sn.reviewItemFilm("pseudo", "password2", "Le programmeur Teubreux", 4.0f, "Très beau film");	
			collector.addError(new Throwable("Erreur 3.26 :  l'ajout d'un avis avec un commentaire null est accepté."));
		}
		catch (NotMember e) {} //Le test est reussi si une exception NotMember est catchée
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 3.26, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		//Test d'ajout d'un avis avec un titre de film erroné
		try {
			sn.reviewItemFilm("pseudo", "password", "Vive le test logiciel", 0.5f, "Film mensonger ");	
			collector.addError(new Throwable("Erreur 3.27 :  l'ajout d'un avis sur un film inexistant est accepté."));
		}
		catch (NotItem e) {} //Le test est reussi si une exception BadEntry est catchée
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 3.27, Exception non prévue : " + e));
			e.printStackTrace();
		}
		
		//Test d'ajout de 3 avis par 3 membres différents et verification de la moyenne calculée
		try {
			float note1 = 5f, note2=0f, note3 = 2.25f, moyenne;
			moyenne = sn.reviewItemFilm("pseudo", "password", "Le prograMMeur Teubreux", note1, "Film parfait");	
			collector.checkThat("Moyenne incorrecte après ajout d'un 1er avis sur un film",moyenne,is(note1));
			moyenne = sn.reviewItemFilm("sekou", "motdepasse", "Le programmeur Teubreux", note2, "Film nul !");
			collector.checkThat("Moyenne incorrecte après ajout d'une 2eme de valeur nulle (d'avis) sur un film",moyenne,is(note1/2));
			moyenne = sn.reviewItemFilm("saikou", "courage", "Le programmeur Teubreux", note3, "Film moyen");
			collector.checkThat("Moyenne incorrecte après ajout d'un 3eme avis sur un film",moyenne,is((note1+note2+note3)/3));
			
			//Test d'ajout d'un avis par un même membre et vérification de la nouvelle moyenne avec ce nombre retourné
			float newNote2 = 4.5f;
			moyenne = sn.reviewItemFilm("sekou", "motdepasse", "Le programmeur Teubreux", newNote2, "Je l'ai revu... en fait il est bien.");
			collector.checkThat("Moyenne incorrecte après le changement d'un avis par un membre",moyenne,is((note1+newNote2+note3)/3));
			
		}
		catch (Exception e) {
			collector.addError(new Throwable("Erreur 3.28, L'ajout de 3 avis correctes échouent. (Cela peut aussi etre du a des notes d'avis limites (0 ou 5)." + e));
			e.printStackTrace();
		}
		
	}

}
