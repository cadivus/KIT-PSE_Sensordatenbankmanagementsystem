package notificationsystem.controller;

/**
 * An instance of the CheckerUtil class always runs in the background. Its purpose is to regulary check if alerts
 * or reports have to be sent by the system. The class is designed with the singleton-pattern as only a single
 * instance should, for example, issue reports.
 */
public class CheckerUtil {

    private static CheckerUtil INSTANCE;
    private Controller controller;

    private CheckerUtil() {}

    public static CheckerUtil getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CheckerUtil();
        }
        return INSTANCE;
    }

    public void checkForSensorFailure() {

    }

    public void checkForReports() {

    }
}
