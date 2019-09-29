import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        MyTests.testSnake.class,
        MyTests.testCricket.class,
        MyTests.testVulture.class,
        MyTests.testTrampoline.class,
        MyTests.testGameWinner.class,
        MyTests.testSerialization.class
})

public class JunitTestSuite { }
