package ru.elena.zh.testwork.listener;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ru.elena.zh.testwork.service.DataService;

@Component
@ConditionalOnProperty(name = "dao", havingValue = "memory")
public class StartApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private final DataService dataService;

    public StartApplicationListener(DataService dataService) {
        this.dataService = dataService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        
        try {
            dataService.loadData();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }
}
