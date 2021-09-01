package notificationsystem.model;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SubscriptionDAOTests {

    @Autowired
    SubscriptionDAO subscriptionDAO;
    @MockBean
    SubscriptionRepository subscriptionRepository;

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

    @Test
    public void testGetByMailAndId() {
        List<Subscription> subscriptions = setupAll();
        Mockito.when(subscriptionRepository.findAll()).thenReturn(subscriptions);

        Subscription toTest = subscriptionDAO.get(subscriptions.get(0).getSubscriberAddress(), subscriptions.get(0).getSensorId());

        assertNotNull(toTest);
        assertEquals(subscriptions.get(0).getSubscriberAddress(), toTest.getSubscriberAddress());
        assertEquals(subscriptions.get(0).getSensorId(), toTest.getSensorId());
        assertEquals(subscriptions.get(0).getSubTime(), toTest.getSubTime());
        assertEquals(subscriptions.get(0).getReportInterval(), toTest.getReportInterval());
        assertEquals(subscriptions.get(0).isToggleAlert(), toTest.isToggleAlert());
    }

    @Test
    public void testGetByMailAndIdReturnNull() {
        Subscription subscription = getTestSub();
        Subscription subscription2 = getTestSub2();
        LinkedList<Subscription> subs = new LinkedList<>();
        subs.add(subscription2);
        Mockito.when(subscriptionRepository.findAll()).thenReturn(subs);

        Subscription toTest = subscriptionDAO.get(subscription.getSubscriberAddress(), subscription.getSensorId());

        assertNull(toTest);
    }

    @Test
    public void testGetAll() {
        List<Subscription> subscriptions = setupAll();
        Mockito.when(subscriptionRepository.findAll()).thenReturn(subscriptions);

        List<Subscription> subs = subscriptionDAO.getAll();

        assertEquals(subscriptions.get(0), subs.get(0));
        assertEquals(subscriptions.get(1), subs.get(1));
        assertEquals(subscriptions.get(2), subs.get(2));
        assertEquals(3, subs.size());
    }

    @Test
    public void testGetAllSubscribers() {
        List<Subscription> subscriptions = setupAll();
        Mockito.when(subscriptionRepository.findAll()).thenReturn(subscriptions);

        List<String> allSubs = (subscriptionDAO.getAllSubscribers(subscriptions.get(0).getSensorId()));

        assertEquals(subscriptions.get(0).getSubscriberAddress(), allSubs.get(0));
        assertEquals(subscriptions.get(2).getSubscriberAddress(), allSubs.get(1));
    }

    @Test
    public void testGetAllSensors() {
        List<Subscription> subscriptions = setupAll();
        Mockito.when(subscriptionRepository.findAll()).thenReturn(new ArrayList<>());

        List<String> allSensors = (subscriptionDAO.getAllSensors(subscriptions.get(0).getSubscriberAddress()));

        assertNotNull(allSensors);
    }

    @Test
    public void testSave() {
        Subscription subscription = getTestSub();
        Mockito.when(subscriptionRepository.save(subscription)).thenReturn(subscription);

        subscriptionDAO.save(subscription);

        verify(subscriptionRepository).save(subscription);
    }

    @Test
    public void testDelete() {
        Subscription subscription = getTestSub();

        subscriptionDAO.delete(subscription);

        verify(subscriptionRepository).delete(subscription);
    }

    @Configuration
    @Import(SubscriptionDAO.class)
    static class TestConfig {
        @Bean
        SubscriptionRepository subscriptionRepository() {
            return mock(SubscriptionRepository.class);
        }
    }

    }
