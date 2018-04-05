package test.junit.alvihn;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)

@SuiteClasses(
		{
			TestAddMemberJUnit.class,
			TestAddItemFilmJUnit.class,
			TestAddItemBookJUnit.class,
			TestConsultItemsJUnit.class,
			TestReviewItemFilmJUnit.class,
			TestReviewItemBookJUnit.class
		}
)

public class AllTestJUnit {

}