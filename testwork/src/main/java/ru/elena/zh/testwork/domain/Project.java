package ru.elena.zh.testwork.domain;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.elena.zh.testwork.constant.ProjectState;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project implements Serializable {

    public static final long serialVersionUID = 1L;
    public static final String[] COLUMNS = new String[]{"id", "project_name", "issue_count", "error_free"};
    private int id; 
    private String project_name = "";
    private int issue_count;
    private ProjectState error_free = ProjectState.ERROR;

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static ColumnPositionMappingStrategy getMappingStrategy() {
        ColumnPositionMappingStrategy<Project> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(Project.class);
        strategy.setColumnMapping(COLUMNS);
        return strategy;
    }
}
