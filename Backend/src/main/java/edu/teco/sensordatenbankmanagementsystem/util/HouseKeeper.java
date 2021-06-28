package edu.teco.sensordatenbankmanagementsystem.util;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
@EnableScheduling
/**
 * The housekeeper is used to make sure that any unwanted behaviour, that has not been handled by anything else is stopped
 */
public class HouseKeeper {

    /**
     * This variable is set after the onStartup method was successfully completed
     */
    Boolean alreadyStarted = false;
    @EventListener(ContextRefreshedEvent.class)
    /**
     * This method will be called once after the Springbootapplication has started completely and will handle anything
     * that needs to be done on startup. E.g. reload the database and check for new Sensors, create missing Metadata
     */
    private void onStartup(){


        alreadyStarted = true;
    }

    @Scheduled(cron="*/300 * * * * MON-FRI")
    /**
     * This method will be called periodically (At the moment every 5 minutes Monday until Friday) to check up the
     * application status
     */
    private void doPeriodically(){

    }
}
