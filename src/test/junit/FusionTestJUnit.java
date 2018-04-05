package test.junit;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)

@SuiteClasses(
		{
			test.junit.alvihn.AllTestJUnit.class,
			test.junit.sekou.SocialNetworkTest.class
		}
)

public class FusionTestJUnit {

}