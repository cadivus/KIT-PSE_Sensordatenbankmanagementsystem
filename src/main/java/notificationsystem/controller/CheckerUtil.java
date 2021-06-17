package notificationsystem.controller;

//@autowired erstellt auch singleton, @Component
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
