package fazlastoks;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ MasterTest.class, TestPro.class, HomeTest.class,
		LoginTest.class, RegisterTest.class, SearchTest.class })
public class AllTests {

}
