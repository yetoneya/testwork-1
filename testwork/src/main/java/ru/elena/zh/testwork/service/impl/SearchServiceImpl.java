package ru.elena.zh.testwork.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import ru.elena.zh.testwork.dao.IssueDao;
import ru.elena.zh.testwork.dao.ProjectDao;
import ru.elena.zh.testwork.dao.UserDao;
import ru.elena.zh.testwork.service.SearchService;
import ru.elena.zh.testwork.domain.Issue;
import ru.elena.zh.testwork.domain.Project;
import ru.elena.zh.testwork.domain.User;

@Service("searchService")
public class SearchServiceImpl implements SearchService {

    private final IssueDao issueDao;
    private final ProjectDao projectDao;
    private final UserDao userDao;

    public SearchServiceImpl(IssueDao issueDao, ProjectDao projectDao, UserDao userDao) {
        this.issueDao = issueDao;
        this.projectDao = projectDao;
        this.userDao = userDao;
    }

    @Override
    public Project findProject(int id) {
        try {
            return projectDao.findById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Project findProject(String project_name) {
        try {
            return projectDao.findByName(project_name);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public User findUser(int id) {
        try {
            return userDao.findById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public User findUser(String user_name) {
        try {
            return userDao.findByName(user_name);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Issue findIssue(int id) {
        try {
            return issueDao.findById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> getUsers() {
        try {
            return userDao.findAll();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Project> getProjects() {
        try {
            return projectDao.findAll();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Issue> getIssues() {
        try {
            return issueDao.findAll();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
