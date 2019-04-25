package ru.elena.zh.testwork.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.elena.zh.testwork.dao.IssueDao;
import ru.elena.zh.testwork.dao.ProjectDao;
import ru.elena.zh.testwork.dao.UserDao;
import ru.elena.zh.testwork.service.DataService;
import ru.elena.zh.testwork.service.StoreService;

import ru.elena.zh.testwork.domain.Issue;
import ru.elena.zh.testwork.domain.Project;
import ru.elena.zh.testwork.domain.User;

@Service("storeService")
public class StoreServiceImpl implements StoreService {

    private final IssueDao issueDao;
    private final ProjectDao projectDao;
    private final UserDao userDao;
    private final DataService dataService;

    @Autowired
    public StoreServiceImpl(IssueDao issueDao, ProjectDao projectDao, UserDao userDao, DataService dataService) {
        this.issueDao = issueDao;
        this.projectDao = projectDao;
        this.userDao = userDao;
        this.dataService = dataService;
    }

    @Override
    public User saveUser(User user) {
        try {
            user = userDao.insert(user);
            return user;
        } catch (Exception ioe) {
            ioe.printStackTrace();
            return null;
        }
    }

    @Override
    public Project saveProject(Project project) {
        try {
            if (project.getId() == 0) {
                project = projectDao.insert(project);
            } else {
                projectDao.merge(project);
            }
            return project;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Issue saveIssue(Issue issue) {
        try {
            if (issue.getUser_id() == 0) {
                issue.setUser_id(1);
            }
            if (issue.getId() == 0) {
                issueDao.insert(issue);
            } else {
                issueDao.merge(issue);
            }
            return issue;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean saveToFiles() {
        try {
            dataService.saveData();
            return true;
        } catch (Exception ioe) {
            ioe.printStackTrace();
            return false;
        }
    }

}
