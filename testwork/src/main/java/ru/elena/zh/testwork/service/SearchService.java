package ru.elena.zh.testwork.service;

import java.util.List;
import ru.elena.zh.testwork.domain.Issue;
import ru.elena.zh.testwork.domain.Project;
import ru.elena.zh.testwork.domain.User;

public interface SearchService {

    Project findProject(int id);

    Project findProject(String project_name);

    User findUser(int id);

    User findUser(String user_name);

    Issue findIssue(int id);

    List<User> getUsers();

    List<Project> getProjects();

    List<Issue> getIssues();
}
