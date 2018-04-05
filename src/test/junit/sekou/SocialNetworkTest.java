/*	Fichier SocialNetworkTest.java
 * 
 *   Cette classe exécute tous les test de la classe SocialNetwork
 * 
 *   Dernière modificaiton: Sékou Traoré
 *   date: 19/11/2017
 */
package test.junit.sekou;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)

@SuiteClasses(
		{
			SocialNetworkTestMember.class,
			SocialNetworkTestItemFilm.class,
			SocialNetworkTestItemBook.class,
			SocialNetworkTestConsultItems.class
		}
)

public class SocialNetworkTest {

}