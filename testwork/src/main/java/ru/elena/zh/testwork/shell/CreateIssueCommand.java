package ru.elena.zh.testwork.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.elena.zh.testwork.service.SearchService;
import ru.elena.zh.testwork.service.StoreService;
import ru.elena.zh.testwork.service.VerificationService;
import ru.elena.zh.testwork.domain.Issue;
import ru.elena.zh.testwork.constant.IssuePriority;
import ru.elena.zh.testwork.constant.IssueState;
import ru.elena.zh.testwork.constant.ProjectState;

import ru.elena.zh.testwork.domain.Project;
import ru.elena.zh.testwork.domain.User;

@ShellComponent
public class CreateIssueCommand {

    private final static String UNEXISTING_ISSUE = "unexisting issue";
    private final static String PROJECT_NOT_SAVED = "project has not been saved: ";
    private final static String UNEXISTING_PROJECT_ID = "unexisting project_id";
    private final static String USER_NOT_SAVED = "user has not been saved: ";
    private final static String UNEXISTING_USER_ID = "unexsisting user_id";
    private final static String UNEXISTING_PRIORITY = "unexisting priority";
    private final static String PROJECT_LACK = "project not specified";
    private final static String NOT_SAVED = "has not been saved: ";
    private final static String SAVED = "saved: ";
    private final static String CHANGE_PROJECT = "it is impossible to change project_id\n";
    private final static String CHANGE_USER = "it is impossible to change user_id\n";

    private final StoreService storeService;
    private final VerificationService verificationService;
    private final SearchService searchService;

    private Issue issue = new Issue();
    private Project project;

    @Autowired
    public CreateIssueCommand(StoreService storeService, VerificationService verificationService, SearchService searchService) {
        this.storeService = storeService;
        this.verificationService = verificationService;
        this.searchService = searchService;
    }

    @ShellMethod("New issue")
    public String create_new() {
        issue = new Issue();
        issue.setTime_of_detection(System.currentTimeMillis());
        return issue.toString();
    }

    @ShellMethod("Refresh issue")
    public String change_issue(@ShellOption int id) {
        issue = searchService.findIssue(id);
        if (issue != null) {
            return issue.toString();
        }
        return UNEXISTING_ISSUE;
    }

    @ShellMethod("Set project id")
    public String project_name(@ShellOption String projectName) {
        if (issue.getId() != 0) {
            return CHANGE_PROJECT + issue.toString();
        }
        project = searchService.findProject(projectName);
        if (project == null) {
            project = new Project(0, projectName, 1, ProjectState.ERROR);
        } else {
            project.setError_free(ProjectState.ERROR);
            project.setIssue_count(project.getIssue_count() + 1);
        }
        project = storeService.saveProject(project);
        issue.setProject_id(project.getId());
        return issue.toString();

    }

    @ShellMethod("Set project id")
    public String project_id(@ShellOption int id) {
        if (issue.getId() != 0) {
            return CHANGE_PROJECT + issue.toString();
        }
        project = searchService.findProject(id);
        if (project == null) {
            return UNEXISTING_PROJECT_ID;
        }
        project.setError_free(ProjectState.ERROR);
        project.setIssue_count(project.getIssue_count() + 1);
        project = storeService.saveProject(project);//null       
        if (project != null) {
            issue.setProject_id(id);
            return issue.toString();
        }
        return PROJECT_NOT_SAVED + issue.toString();
    }

    @ShellMethod("Set user id")
    public String user_name(@ShellOption String user_name) {
        if (issue.getId() != 0) {
            return CHANGE_USER + issue.toString();
        }
        User user = searchService.findUser(user_name);
        if (user == null) {
            user = storeService.saveUser(new User(0, user_name));
        }
        if (user != null) {
            issue.setUser_id(user.getId());
            return issue.toString();
        }
        return USER_NOT_SAVED + issue.toString();
    }

    @ShellMethod("Set user id")
    public String user_id(@ShellOption int id) {
        if (issue.getId() != 0) {
            return CHANGE_USER + issue.toString();
        }
        User user = searchService.findUser(id);
        if (user == null) {
            return UNEXISTING_USER_ID;
        }
        issue.setUser_id(id);
        return issue.toString();
    }

    @ShellMethod("Set description")
    public String description(@ShellOption String description) {
        issue.setDescription(description);
        return issue.toString();
    }

    @ShellMethod("Set responsible")
    public String responsible_for_fixing(@ShellOption String responsible) {
        issue.setResponsible_for_fixing(responsible);
        return issue.toString();
    }

    @ShellMethod("Set prioriry")
    public String priority(@ShellOption int priority) {
        switch (priority) {
            case 1:
                issue.setIssue_priority(IssuePriority.HIGH);
                break;
            case 2:
                issue.setIssue_priority(IssuePriority.MIDDLE);
                break;
            case 3:
                issue.setIssue_priority(IssuePriority.LOW);
                break;
            default:
                return UNEXISTING_PRIORITY;
        }
        return issue.toString();
    }

    @ShellMethod("Set projec version before fixing")
    public String project_version_before(@ShellOption long version) {
        issue.setProject_version_before(version);
        return issue.toString();
    }

    @ShellMethod("Set projec version before fixing")
    public String project_version_after(@ShellOption long version) {
        issue.setProject_version_after(version);
        return issue.toString();
    }

    @ShellMethod("Set way of simulation")
    public String way_of_simulation(@ShellOption String simulation) {
        issue.setWay_of_simulation(simulation);
        return issue.toString();
    }

    @ShellMethod("Set fixed")
    public String set_fixed(@ShellOption String fixed) {
        if (issue.getProject_id() == 0) {
            return PROJECT_LACK + issue.toString();
        }
        switch (fixed) {
            case "fixed":
                if (issue.getFixed() == IssueState.FIXED) {
                    return issue.toString();
                }
                refreshProject(issue.getProject_id(), -1);
                issue.setFixed(IssueState.FIXED);
                storeService.saveIssue(issue);
                break;
            case "new":
                if (issue.getFixed() == IssueState.NEW) {
                    return issue.toString();
                }
                refreshProject(issue.getProject_id(), 1);
                issue.setFixed(IssueState.NEW);
                storeService.saveIssue(issue);
                break;
            default:

        }
        return issue.toString();

    }

    private void refreshProject(int project_id, int dev) {
        project = searchService.findProject(project_id);
        project.setIssue_count(project.getIssue_count() + dev);
        if (project.getIssue_count() == 0) {
            project.setError_free(ProjectState.FREE);
        } else {
            project.setError_free(ProjectState.ERROR);
        }
        project = storeService.saveProject(project);

    }

    @ShellMethod("Save issue")
    public String save() {
        String result = verificationService.checkIssue(issue);
        if (result.equals("verified")) {
            issue = storeService.saveIssue(issue);
            if (issue != null) {
                return SAVED + issue.toString();
            }
            return NOT_SAVED + issue.toString();
        }
        return result + issue.toString();
    }

}
