package ru.elena.zh.testwork.shell;

import java.util.List;
import static java.util.stream.Collectors.toList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.elena.zh.testwork.service.SearchService;
import ru.elena.zh.testwork.service.ViewService;

import ru.elena.zh.testwork.domain.Issue;
import ru.elena.zh.testwork.domain.Project;
import ru.elena.zh.testwork.domain.User;

@ShellComponent
public class DataViewCommand {

    final private static String NOT_FOUND = "not found";
    final private ViewService viewService;
    final private SearchService searchService;

    @Autowired
    public DataViewCommand(ViewService viewService, SearchService searchService) {
        this.viewService = viewService;
        this.searchService = searchService;
    }

    @ShellMethod("Show users")
    public String show_users() {
        return viewService.showUsers();
    }

    @ShellMethod("Show projects")
    public String show_projects() {
        return viewService.showProjects();
    }

    @ShellMethod("Show issues")
    public String show_issues() {
        return viewService.showIssues();
    }

    @ShellMethod("Show some issues")
    public String find_by_ids(@ShellOption int project_id, @ShellOption int user_id) {
        List<Issue> issues = searchService
                .getIssues()
                .stream()
                .filter(i -> i.getProject_id() == project_id && i.getUser_id() == user_id)
                .collect(toList());
        return issues.size() != 0 ? viewService.showIssues(issues) : NOT_FOUND;
    }

    @ShellMethod("Show some issues")
    public String find_by_names(@ShellOption String project_name, @ShellOption String user_name) {
        Project project = searchService
                .getProjects()
                .stream()
                .filter(p -> p.getProject_name().equalsIgnoreCase(project_name)).findAny().orElse(null);
        User user = searchService
                .getUsers()
                .stream()
                .filter(u -> u.getUser_name().equalsIgnoreCase(user_name)).findAny().orElse(null);
        if (user != null && project != null) {
            List<Issue> issues = searchService
                    .getIssues()
                    .stream()
                    .filter(i -> i.getProject_id() == project.getId() && i.getUser_id() == user.getId())
                    .collect(toList());
            return issues.size() != 0 ? viewService.showIssues(issues) : NOT_FOUND;
        }
        return NOT_FOUND;
    }
}
