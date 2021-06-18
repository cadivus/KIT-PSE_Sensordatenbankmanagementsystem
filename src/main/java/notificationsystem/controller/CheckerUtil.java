package notificationsystem.controller;

/**
 * An instance of the CheckerUtil class always runs in the background. Its purpose is to regulary check if alerts
 * or reports have to be sent by the system. The class is designed with the singleton-pattern as only a single
 * instance should, for example, issue reports.
 */
public class CheckerUtil {

    private static CheckerUtil INSTANCE;
    private Controller controller;

    /**
     * Private constructer only used by the getInstance method.
     */
    private CheckerUtil() {}

    /**
     * Returns the instance of CheckerUtil if it exists, creates it first if it doesn't.
     * @return CheckerUtil instance.
     */
    public static CheckerUtil getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CheckerUtil();
        }
        return INSTANCE;
    }

    /**
     * Periodically checks if a sensor has malfunctioned by communicating with the project API.
     * If one or multiple sensor failures occurred, the method calls the controller to send an alert to the subscribers
     * of these sensors.
     */
    public void checkForSensorFailure() {

    }

    /**
     * Keeps track of the reports which have to be periodically sent to subscribers of sensors and the time intervals
     * between these reports.
     * Calls the controller to send such a report to a subscriber if necessary.
     */
    public void checkForReports() {

    }
}
