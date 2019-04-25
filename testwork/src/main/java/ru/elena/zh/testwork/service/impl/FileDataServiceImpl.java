package ru.elena.zh.testwork.service.impl;

import org.springframework.stereotype.Service;
import ru.elena.zh.testwork.service.DataService;
import ru.elena.zh.testwork.dao.files.IssueLoader;
import ru.elena.zh.testwork.dao.files.ProjectLoader;
import ru.elena.zh.testwork.dao.files.UserLoader;

@Service("dataService")
public class FileDataServiceImpl implements DataService {

    private final IssueLoader issueLoader;
    private final ProjectLoader projectLoader;
    private final UserLoader userLoader;

    public FileDataServiceImpl(IssueLoader issueLoader, ProjectLoader projectLoader, UserLoader userLoader) {
        this.issueLoader = issueLoader;
        this.projectLoader = projectLoader;
        this.userLoader = userLoader;
    }

    @Override
    public void loadData() throws Exception {
        issueLoader.loadData();
        projectLoader.loadData();
        userLoader.loadData();

    }

    @Override
    public void saveData() throws Exception {
        issueLoader.saveData();
        projectLoader.saveData();
        userLoader.saveData();
    }

}
