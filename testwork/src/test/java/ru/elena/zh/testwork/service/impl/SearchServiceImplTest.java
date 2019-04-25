package ru.elena.zh.testwork.service.impl;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
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
import ru.elena.zh.testwork.service.SearchService;
import ru.elena.zh.testwork.service.StoreService;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
    ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"})
public class SearchServiceImplTest {

    @Autowired
    private SearchService searchService;
    @Autowired
    private StoreService storeService;

    @Test
    public void testFindProject_int() {
        Project project = new Project(0, "one", 1, ProjectState.ERROR);
        project = storeService.saveProject(project);
        Project retv = searchService.findProject(project.getId());
        assertEquals(project, retv);
    }

    @Test
    public void testFindProject_String() {
        System.out.println("findProject");
        Project project = new Project(0, "two", 1, ProjectState.ERROR);
        project = storeService.saveProject(project);
        Project retv = searchService.findProject(project.getProject_name());
        assertEquals(project, retv);
    }

    @Test
    public void testFindUser_int() {
        System.out.println("findUser");
        User user = new User(0, "yury");        
        user = storeService.saveUser(user);      
        User retv = searchService.findUser(user.getId());
        assertEquals(user, retv);
    }

    @Test
    public void testFindUser_String() {
        System.out.println("findUser");
        User user = new User(0, "yakov");
        user = storeService.saveUser(user);
        User retv = searchService.findUser(user.getUser_name());
        assertEquals(user, retv);
    }

    @Test
    public void testFindIssue() {
        Issue issue = new Issue(0, 5, 6, "meow", 1556218489123L, 33, 0, IssuePriority.LOW, "meow", "meow", IssueState.FIXED);
        issue = storeService.saveIssue(issue);
        Issue retv = searchService.findIssue(issue.getId());
        assertEquals(issue, retv);
    }

    @Test
    public void testGetUsers() {
        System.out.println("getUsers");
        User user = new User(0, "ivan");
        user = storeService.saveUser(user);
        List<User> list = searchService.getUsers();
        assertThat(!list.isEmpty());
    }

    @Test
    public void testGetProjects() {
        System.out.println("getProjects");
        Project project = new Project(0, "three", 1, ProjectState.ERROR);
        project = storeService.saveProject(project);
        List<Project> list = searchService.getProjects();
        assertThat(!list.isEmpty());
    }

    @Test
    public void testGetIssues() {
        System.out.println("getIssues");
        Issue issue = new Issue(0, 6, 6, "wow", 1556218489123L, 33, 0, IssuePriority.LOW, "meow", "meow", IssueState.FIXED);
        issue = storeService.saveIssue(issue);
        List<Issue> list = searchService.getIssues();
        assertThat(!list.isEmpty());
    }

}
