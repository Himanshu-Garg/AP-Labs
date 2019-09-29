import org.junit.runner.*;
import org.junit.runner.notification.Failure;

public class TestRunner {
    public static void main(String[] args) {

        Result r = JUnitCore.runClasses(JunitTestSuite.class);
        for(Failure f : r.getFailures()) {
            System.out.println(f.toString());
        }

        System.out.println("isSuccessful - " + r.wasSuccessful());
    }

}
