package ru.elena.zh.testwork.service;

import ru.elena.zh.testwork.domain.Issue;
import ru.elena.zh.testwork.domain.Project;
import ru.elena.zh.testwork.domain.User;

public interface StoreService {

    User saveUser(User user);

    Project saveProject(Project project);

    Issue saveIssue(Issue issue);

    boolean saveToFiles();
}
