package ru.elena.zh.testwork.listener;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.elena.zh.testwork.service.DataService;

@Component
@ConditionalOnProperty(name = "dao", havingValue = "database")
public class StartApplicationListenerJdbc implements ApplicationListener<ContextRefreshedEvent> {

    private final static String CREATE_ISSUES = "CREATE TABLE USERS(id INT(10) NOT NULL AUTO_INCREMENT, user_name VARCHAR(20), PRIMARY KEY(id))";
    private final static String CREATE_PROJECTS = "CREATE TABLE PROJECTS(id INT(10) NOT NULL AUTO_INCREMENT, project_name VARCHAR(40), issue_count INT(10), error_free Varchar(5), PRIMARY KEY(id))";
    private final static String CREATE_USERS = "CREATE TABLE ISSUES(id INT(10) NOT NULL AUTO_INCREMENT, project_id INT(10), user_id INT(10), description VARCHAR(200),time_of_detection BIGINT(20), project_version_before BIGINT(20),project_version_after BIGINT(20), issue_priority VARCHAR(5), responsible_for_fixing VARCHAR(20), way_of_simulation VARCHAR(300), fixed VARCHAR(5), PRIMARY KEY(id))";

    private final DataService dataService;
    private final JdbcTemplate jdbcTemplate;

    public StartApplicationListenerJdbc(DataService dataService, JdbcTemplate jdbcTemplate) {
        this.dataService = dataService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent e) {
        try {
            jdbcTemplate.execute(CREATE_USERS);
            jdbcTemplate.execute(CREATE_PROJECTS);
            jdbcTemplate.execute(CREATE_ISSUES);
            dataService.loadData();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }

}
