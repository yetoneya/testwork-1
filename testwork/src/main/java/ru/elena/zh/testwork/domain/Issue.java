package ru.elena.zh.testwork.domain;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import java.io.Serializable;
import java.time.Instant;
import lombok.AllArgsConstructor;
import ru.elena.zh.testwork.constant.IssuePriority;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.elena.zh.testwork.constant.IssueState;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Issue implements Serializable {

    public static final long serialVersionUID = 1L;
    public static final String[] COLUMNS = new String[]{"id", "project_id",
        "user_id", "description", "time_of_detection",
        "project_version_before", "project_version_after",
        "issue_priority", "responsible_for_fixing", "way_of_simulation", "fixed"};

    private int id;
    private int project_id;
    private int user_id;
    private String description = "";
    private long time_of_detection;
    private long project_version_before;
    private long project_version_after;
    private IssuePriority issue_priority = IssuePriority.LOW;
    private String responsible_for_fixing = "";
    private String way_of_simulation = "";
    private IssueState fixed = IssueState.NEW;

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static ColumnPositionMappingStrategy getMappingStrategy() {
        ColumnPositionMappingStrategy<Issue> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(Issue.class);
        strategy.setColumnMapping(COLUMNS);
        return strategy;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("id = ").append(id)
                .append(", project_id = ").append(project_id)
                .append(", user_id = ").append(user_id)
                .append(", description = ").append(description.substring(0, description.length() > 5 ? 5 : description.length()).concat("..."))
                .append(", time_of_detection = ").append(Instant.ofEpochMilli(time_of_detection))
                .append(", project_version_before = ").append(project_version_before)
                .append(", project_version_after = ").append(project_version_after)
                .append(", issue_priority = ").append(issue_priority)
                .append(", responsible_for_fixing = ").append(responsible_for_fixing)
                .append(", way_of_simulation = ").append(way_of_simulation.substring(0, way_of_simulation.length() > 5 ? 5 : way_of_simulation.length()).concat("..."))
                .append(", fixed = ").append(fixed);
        return builder.toString();
    }

    public String toShow() {
        StringBuilder builder = new StringBuilder();
        builder.append("id = ").append(id)
                .append(", project_id = ").append(project_id)
                .append(", user_id = ").append(user_id).append("\n")
                .append("description = ").append(description).append("\n")
                .append("time_of_detection = ").append(Instant.ofEpochMilli(time_of_detection))
                .append(", project_version_before = ").append(project_version_before)
                .append(", project_version_after = ").append(project_version_after)
                .append(", issue_priority = ").append(issue_priority)
                .append(", responsible_for_fixing = ").append(responsible_for_fixing).append("\n")
                .append("way_of_simulation = ").append(way_of_simulation).append("\n")
                .append("fixed = ").append(fixed)
                .append("\n\n");

        return builder.toString();
    }

}
