package ru.elena.zh.testwork.dao.list;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.elena.zh.testwork.dao.ProjectDao;
import ru.elena.zh.testwork.domain.Project;

@Service("projectDao")
@ConditionalOnProperty(name = "dao", havingValue = "memory")
public class ProjectDaoList implements ProjectDao {

    private final List<Project> projects = new ArrayList<>();

    @Override
    public boolean load(List<Project> list) {
        return projects.addAll(list);
    }

    @Override
    public List<Project> findAll() {
        return new ArrayList<>(projects);
    }

    @Override
    public Project findById(int id) {
        return projects
                .stream()
                .filter(p -> p.getId() == id)
                .findAny()
                .orElse(null);

    }

    @Override
    public Project findByName(String project_name) {
        return projects.stream()
                .filter(p -> p.getProject_name().equals(project_name))
                .findAny()
                .orElse(null);
    }

    @Override
    public Project insert(Project project) throws Exception {
        project.setId(projects.size() + 1);
        projects.add(project);
        return project;
    }

    @Override
    public int merge(Project project) throws Exception {
        projects.set(project.getId() - 1, project);
        return project.getId();
    }

}
