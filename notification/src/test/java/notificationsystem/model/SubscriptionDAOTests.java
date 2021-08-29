package notificationsystem.model;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SubscriptionDAOTests {

    @Autowired
    SubscriptionDAO subscriptionDAO;

    private Subscription getTestSub() {
        String mailAddress = "test";
        String sensorId = "test-id";
        LocalDate subTime = LocalDate.now();
        long reportInterval = 5;
        boolean toggleAlerts = false;
        return new Subscription(mailAddress, sensorId, subTime, reportInterval, toggleAlerts);
    }

    private Subscription getTestSub2() {
        String mailAddress = "test";
        String sensorId = "test-id2";
        LocalDate subTime = LocalDate.now();
        long reportInterval = 7;
        boolean toggleAlerts = false;
        return new Subscription(mailAddress, sensorId, subTime, reportInterval, toggleAlerts);
    }

    private Subscription getTestSub3() {
        String mailAddress = "test2";
        String sensorId = "test-id";
        LocalDate subTime = LocalDate.now();
        long reportInterval = 5;
        boolean toggleAlerts = true;
        return new Subscription(mailAddress, sensorId, subTime, reportInterval, toggleAlerts);
    }

    private List<Subscription> setupAll() {
        Subscription subscription1 = getTestSub();
        Subscription subscription2 = getTestSub2();
        Subscription subscription3 = getTestSub3();
        subscriptionDAO.save(subscription1);
        subscriptionDAO.save(subscription2);
        subscriptionDAO.save(subscription3);
        LinkedList<Subscription> subs = new LinkedList<>();
        subs.add(subscription1);
        subs.add(subscription2);
        subs.add(subscription3);
        return subs;
    }

    private void cleanupAll(List<Subscription> subs) {
        for (Subscription sub : subs) {
            subscriptionDAO.delete(sub);
        }
    }

    /*@Test
    void testGetBySubscription() {
        Subscription subscription = getTestSub();
        subscriptionDAO.save(subscription);

        Optional<Subscription> toTest = subscriptionDAO.get(subscription);

        assertNotNull(toTest.get());
        assertEquals(subscription.getSubscriberAddress(), toTest.get().getSubscriberAddress());
        assertEquals(subscription.getSensorId(), toTest.get().getSensorId());
        assertEquals(subscription.getSubTime(), toTest.get().getSubTime());
        assertEquals(subscription.getReportInterval(), toTest.get().getReportInterval());
        assertEquals(subscription.isToggleAlert(), toTest.get().isToggleAlert());

        subscriptionDAO.delete(subscription);
    }*/

    @Test
    void testGetByMailAndId() {
        Subscription subscription = getTestSub();
        subscriptionDAO.save(subscription);

        Subscription toTest = subscriptionDAO.get(subscription.getSubscriberAddress(), subscription.getSensorId());

        assertNotNull(toTest);
        assertEquals(subscription.getSubscriberAddress(), toTest.getSubscriberAddress());
        assertEquals(subscription.getSensorId(), toTest.getSensorId());
        assertEquals(subscription.getSubTime(), toTest.getSubTime());
        assertEquals(subscription.getReportInterval(), toTest.getReportInterval());
        assertEquals(subscription.isToggleAlert(), toTest.isToggleAlert());

        subscriptionDAO.delete(subscription);

    }

    @Test
    void testGetAll() {
        List<Subscription> subscriptions = setupAll();

        List<Subscription> subs = subscriptionDAO.getAll();

        assertEquals(subscriptions.get(0), subs.get(0));
        assertEquals(subscriptions.get(1), subs.get(1));
        assertEquals(subscriptions.get(2), subs.get(2));
        assertEquals(3, subs.size());

        cleanupAll(subscriptions);
    }

    @Test
    void testGetAllSubscribers() {
        List<Subscription> subscriptions = setupAll();

        List<String> allSubs = (subscriptionDAO.getAllSubscribers(subscriptions.get(0).getSensorId()));

        assertEquals(subscriptions.get(0).getSubscriberAddress(), allSubs.get(0));
        assertNotEquals(subscriptions.get(1).getSubscriberAddress(), allSubs.get(1));
        assertEquals(subscriptions.get(2).getSubscriberAddress(), allSubs.get(2));

        cleanupAll(subscriptions);
    }

    @Test
    void testGetAllSensors() {
        List<Subscription> subscriptions = setupAll();

        List<String> allSensors = (subscriptionDAO.getAllSensors(subscriptions.get(0).getSubscriberAddress()));

        assertEquals(subscriptions.get(0).getSensorId(), allSensors.get(0));
        assertEquals(subscriptions.get(1).getSensorId(), allSensors.get(1));
        assertNotEquals(subscriptions.get(2).getSensorId(), allSensors.get(2));

        cleanupAll(subscriptions);
    }

    @Test
    void testSave() {
        Subscription subscription = getTestSub();

        subscriptionDAO.save(subscription);

        assertEquals(subscription, subscriptionDAO.get(subscription.getSubscriberAddress(), subscription.getSensorId()));

        subscriptionDAO.delete(subscription);
    }

    @Test
    void testDelete() {
        Subscription subscription = getTestSub();
        subscriptionDAO.save(subscription);

        subscriptionDAO.delete(subscription);

        assertNull(subscriptionDAO.get(subscription.getSubscriberAddress(), subscription.getSensorId()));
    }
}
