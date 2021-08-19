package notificationsystem.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SubscriptionTests {

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

    @Test
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
    }

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
        Subscription subscription1 = getTestSub();
        Subscription subscription2 = getTestSub2();
        Subscription subscription3 = getTestSub3();
        subscriptionDAO.save(subscription1);
        subscriptionDAO.save(subscription2);
        subscriptionDAO.save(subscription3);

        List<Subscription> subs = subscriptionDAO.getAll();

        assertEquals(subscription1, subs.get(0));
        assertEquals(subscription2, subs.get(1));
        assertEquals(subscription3, subs.get(2));
        assertEquals(3, subs.size());

        subscriptionDAO.delete(subscription1);
        subscriptionDAO.delete(subscription2);
        subscriptionDAO.delete(subscription3);
    }

    @Test
    void testGetAllSubscribers() {}

    @Test
    void testGetAllSensors() {}

    @Test
    void testSave() {}

    @Test
    void testDelete() {}
}
