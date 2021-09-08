package testutil;

import static org.junit.jupiter.api.Assertions.fail;

public final class TestUtils {

    private static final double tolerance = .01;

    private TestUtils(){
    }


    public static String createURLWithPort(Integer port, String uri) {
      return "http://localhost:" + port + uri;
    }

    public static void assertAlmost(double a, double b) {
        if (a + b == 0) {
            return;
        }
        if ((a - b) / ((a + b) * .5) > tolerance) {
            fail("that's not even almost the same");
        }
    }
}
