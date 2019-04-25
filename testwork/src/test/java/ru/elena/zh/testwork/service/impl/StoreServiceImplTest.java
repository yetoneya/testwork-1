package ru.elena.zh.testwork.service.impl;

import static org.junit.Assert.assertNotEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.elena.zh.testwork.constant.IssuePriority;
import ru.elena.zh.testwork.constant.IssueState;
import ru.elena.zh.testwork.constant.ProjectState;
import ru.elena.zh.testwork.domain.Issue;
import ru.elena.zh.testwork.domain.Project;
import ru.elena.zh.testwork.domain.User;
import ru.elena.zh.testwork.service.StoreService;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
    ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"})
public class StoreServiceImplTest {

    @Autowired
    private StoreService storeService;

    @Test
    public void testSaveUser() {
        System.out.println("saveUser");
        User user = new User(0, "pyotr");
        user = storeService.saveUser(user);
        assertNotEquals(user.getId(), 0);

    }

    @Test
    public void testSaveProject() {
        System.out.println("saveProject");
        Project project = new Project(0, "four", 1, ProjectState.ERROR);
        project = storeService.saveProject(project);
        assertNotEquals(project.getId(), 0);

    }

    @Test
    public void testSaveIssue() {
        System.out.println("saveIssue");
        Issue issue = new Issue(0, 3, 6, "meow", 1556218489123L, 33, 0, IssuePriority.LOW, "meow", "meow", IssueState.FIXED);
        issue = storeService.saveIssue(issue);
        assertNotEquals(issue.getId(), 0);
    }

}
