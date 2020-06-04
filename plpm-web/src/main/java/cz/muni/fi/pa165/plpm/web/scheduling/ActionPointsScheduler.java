package cz.muni.fi.pa165.plpm.web.scheduling;

import cz.muni.fi.pa165.plpm.service.facade.TrainerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ActionPointsScheduler {

    private final TrainerFacade trainerFacade;

    @Autowired
    public ActionPointsScheduler(TrainerFacade trainerFacade) {
        this.trainerFacade = trainerFacade;
    }

//    @Scheduled(cron = "0 0 12 * * ?") //every day at 12pm
//    @Scheduled(cron = "0 0/2 * * * ?") //every 2 minutes
    @Scheduled(cron = "0/10 0/2 * * * ?") //every 10 sec
    public void addActionPointsToEveryTrainer() {
        trainerFacade.addActionPointsToEveryTrainer();
    }
}
