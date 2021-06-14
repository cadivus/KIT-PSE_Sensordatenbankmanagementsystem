package notificationsystem.controller;

public class CheckerUtil {

    private static CheckerUtil INSTANCE;
    private MailManager mailManager;

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
