package ru.elena.zh.testwork.dao;

import java.util.List;
import ru.elena.zh.testwork.domain.Project;

public interface ProjectDao {

    
    Project insert(Project project) throws Exception;
    
    int merge(Project project)throws Exception;
    
    boolean load(List<Project>list) throws Exception;
    
    List<Project> findAll()throws Exception;
    
    Project findById(int id)throws Exception;
    
    Project findByName(String project_name)throws Exception;
    
            
}
