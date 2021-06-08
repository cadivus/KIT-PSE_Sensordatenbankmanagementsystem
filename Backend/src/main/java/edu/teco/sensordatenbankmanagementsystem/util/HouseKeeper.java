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
public class HouseKeeper {

    Boolean alreadyStarted = false;
    @EventListener(ContextRefreshedEvent.class)
    public void onStartup(){


        alreadyStarted = true;
    }

    @Scheduled(cron="*/300 * * * * MON-FRI")
    private void doPeriodically(){

    }
}
