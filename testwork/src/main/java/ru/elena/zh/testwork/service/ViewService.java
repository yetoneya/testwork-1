package ru.elena.zh.testwork.service;

import java.util.List;
import ru.elena.zh.testwork.domain.Issue;

public interface ViewService {

    String showUsers();

    String showProjects();

    String showIssues();

    String showIssues(List<Issue> list);

}
