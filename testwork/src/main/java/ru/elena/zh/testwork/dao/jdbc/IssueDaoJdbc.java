package ru.elena.zh.testwork.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import ru.elena.zh.testwork.dao.IssueDao;
import ru.elena.zh.testwork.constant.IssuePriority;
import ru.elena.zh.testwork.constant.IssueState;
import ru.elena.zh.testwork.domain.Issue;

@Service("issueDao")
@ConditionalOnProperty(name = "dao", havingValue = "database")
public class IssueDaoJdbc implements IssueDao {

    private final NamedParameterJdbcOperations jdbcOperations;

    public IssueDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.jdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public boolean load(List<Issue> list) throws Exception {
        String query = "insert into issues values ("
                + ":id, :project_id, :user_id, :description, :time_of_detection, :project_version_before,"
                + ":project_version_after, :issue_priority, :responsible_for_fixing, :way_of_simulation, :fixed)";
        for (Issue issue : list) {
            final HashMap<String, Object> params = new HashMap<>(11);
            params.put("id", issue.getId());
            params.put("project_id", issue.getProject_id());
            params.put("user_id", issue.getUser_id());
            params.put("description", issue.getDescription());
            params.put("time_of_detection", issue.getTime_of_detection());
            params.put("project_version_before", issue.getProject_version_before());
            params.put("project_version_after", issue.getProject_version_after());
            params.put("issue_priority", issue.getIssue_priority().toString());
            params.put("responsible_for_fixing", issue.getResponsible_for_fixing());
            params.put("way_of_simulation", issue.getWay_of_simulation());
            params.put("fixed", issue.getFixed().toString());
            jdbcOperations.update(query, params);
        }
        return true;
    }

    @Override
    public int merge(Issue issue) throws Exception {
        String query = "update issues set description = :description,"
                + " project_version_after = :project_version_after,"
                + " issue_priority = :issue_priority,"
                + " responsible_for_fixing = :responsible_for_fixing,"
                + " way_of_simulation = :way_of_simulation,"
                + " fixed = :fixed where id = :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("description", issue.getDescription())
                .addValue("project_version_after", issue.getProject_version_after())
                .addValue("issue_priority", issue.getIssue_priority().toString())
                .addValue("responsible_for_fixing", issue.getResponsible_for_fixing())
                .addValue("way_of_simulation", issue.getWay_of_simulation())
                .addValue("fixed", issue.getFixed().toString())
                .addValue("id", issue.getId());
        return jdbcOperations.update(query, namedParameters);

    }

    @Override
    public Issue insert(Issue issue) throws Exception {
        String query = "insert into issues values ("
                + "null, :project_id, :user_id, :description, :time_of_detection, :project_version_before,"
                + ":project_version_after, :issue_priority, :responsible_for_fixing, :way_of_simulation, :fixed)";
        final KeyHolder bKeyHolder = new GeneratedKeyHolder();
        final HashMap<String, Object> params = new HashMap<>(10);
        params.put("project_id", issue.getProject_id());
        params.put("user_id", issue.getUser_id());
        params.put("description", issue.getDescription());
        params.put("time_of_detection", issue.getTime_of_detection());
        params.put("project_version_before", issue.getProject_version_before());
        params.put("project_version_after", issue.getProject_version_after());
        params.put("issue_priority", issue.getIssue_priority().toString());
        params.put("responsible_for_fixing", issue.getResponsible_for_fixing());
        params.put("way_of_simulation", issue.getWay_of_simulation());
        params.put("fixed", issue.getFixed().toString());
        MapSqlParameterSource parameters = new MapSqlParameterSource().addValues(params);
        jdbcOperations.update(query, parameters, bKeyHolder);
        long id = bKeyHolder.getKey().longValue();
        issue.setId((int) id);
        return issue;
    }

    @Override
    public List<Issue> findAll() throws Exception {
        return jdbcOperations.query("select * from issues", new IssueMapper());
    }

    @Override
    public Issue findById(int id) {
        String query = "select*from issues where id = :id";
        final HashMap<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        Issue issue = jdbcOperations.queryForObject(query, params, new IssueMapper());
        return issue;
    }

    private static class IssueMapper implements RowMapper<Issue> {

        @Override
        public Issue mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("id");
            int project_id = resultSet.getInt("project_id");
            int user_id = resultSet.getInt("user_id");
            String description = resultSet.getString("description");
            long time_of_detection = resultSet.getLong("time_of_detection");
            long project_version_before = resultSet.getLong("project_version_before");
            long project_version_after = resultSet.getLong("project_version_after");
            String issue_priority = resultSet.getString("issue_priority");
            String responsible_for_fixing = resultSet.getString("responsible_for_fixing");
            String way_of_simulation = resultSet.getString("way_of_simulation");
            String fixed = resultSet.getString("fixed");
            return new Issue(id, project_id, user_id, description, time_of_detection,
                    project_version_before, project_version_after,
                    IssuePriority.valueOf(issue_priority.toUpperCase()), responsible_for_fixing,
                    way_of_simulation, IssueState.valueOf(fixed.toUpperCase()));
        }
    }
}
