package ru.elena.zh.testwork.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import ru.elena.zh.testwork.dao.IssueDao;
import ru.elena.zh.testwork.dao.ProjectDao;
import ru.elena.zh.testwork.dao.UserDao;
import ru.elena.zh.testwork.service.ViewService;
import ru.elena.zh.testwork.domain.Issue;
import ru.elena.zh.testwork.domain.Project;
import ru.elena.zh.testwork.domain.User;

@Service("viewService")
public class ViewServiceImpl implements ViewService {

    private final static String NOT_FOUND = "not found";

    private final IssueDao issueDao;
    private final ProjectDao projectDao;
    private final UserDao userDao;

    public ViewServiceImpl(IssueDao issueDao, ProjectDao projectDao, UserDao userDao) {
        this.issueDao = issueDao;
        this.projectDao = projectDao;
        this.userDao = userDao;
    }

    @Override
    public String showUsers() {
        try {
            StringBuilder builder = new StringBuilder();
            for (User user : userDao.findAll()) {
                builder.append(user).append("\n");
            }
            return builder.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return NOT_FOUND;
        }
    }

    @Override
    public String showProjects() {
        try {
            StringBuilder builder = new StringBuilder();
            for (Project project : projectDao.findAll()) {
                builder.append(project).append("\n");
            }
            return builder.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return NOT_FOUND;
        }
    }

    @Override
    public String showIssues() {
        try {
            return showIssues(issueDao.findAll());
        } catch (Exception ex) {
            ex.printStackTrace();
            return NOT_FOUND;
        }
    }

    @Override
    public String showIssues(List<Issue> list) {
        try {
            StringBuilder builder = new StringBuilder();
            for (Issue issue : list) {
                builder.append(issue.toShow()).append("\n");
            }
            return builder.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return NOT_FOUND;
        }
    }
}
