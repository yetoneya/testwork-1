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
import ru.elena.zh.testwork.constant.ProjectState;
import ru.elena.zh.testwork.dao.ProjectDao;
import ru.elena.zh.testwork.domain.Project;

@Service("projectDao")
@ConditionalOnProperty(name = "dao", havingValue = "database")
public class ProjectDaoJdbc implements ProjectDao {

    private final NamedParameterJdbcOperations jdbcOperations;

    public ProjectDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.jdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public boolean load(List<Project> list) throws Exception {
        String query = "insert into projects values (:id, :project_name, :issue_count, :error_free)";
        for (Project project : list) {
            final HashMap<String, Object> params = new HashMap<>(4);
            params.put("id", project.getId());
            params.put("project_name", project.getProject_name());
            params.put("issue_count", project.getIssue_count());
            params.put("error_free", project.getError_free().toString());
            jdbcOperations.update(query, params);
        }
        return true;
    }

    @Override
    public int merge(Project project) throws Exception {
        String query = "update projects set issue_count = :issue_count, error_free =:error_free where id = :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("issue_count", project.getIssue_count())
                .addValue("error_free", project.getError_free().toString())
                .addValue("id", project.getId());
        return jdbcOperations.update(query, namedParameters);
    }

    @Override
    public Project insert(Project project) throws Exception {
        String query = "insert into projects values (null,:project_name,:issue_count, :error_free)";
        final KeyHolder bKeyHolder = new GeneratedKeyHolder();
        final HashMap<String, Object> params = new HashMap<>(3);
        params.put("project_name", project.getProject_name());
        params.put("issue_count", project.getIssue_count());
        params.put("error_free", project.getError_free().toString());
        MapSqlParameterSource parameters = new MapSqlParameterSource().addValues(params);
        jdbcOperations.update(query, parameters, bKeyHolder);
        long id = bKeyHolder.getKey().longValue();
        project.setId((int) id);
        return project;
    }

    @Override
    public List<Project> findAll() {
        return jdbcOperations.query("select * from projects", new ProjectMapper());
    }

    @Override
    public Project findById(int id) {
        String query = "select*from projects where id = :id";
        final HashMap<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        Project project = jdbcOperations.queryForObject(query, params, new ProjectMapper());
        return project;
    }

    @Override
    public Project findByName(String project_name) {
        String query = "select*from projects where project_name = :project_name";
        final HashMap<String, Object> params = new HashMap<>(1);
        params.put("project_name", project_name);
        List<Project> list = jdbcOperations.query(query, params, new ProjectMapper());
        return list.isEmpty() ? null : list.get(0);

    }

    private static class ProjectMapper implements RowMapper<Project> {

        @Override
        public Project mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("id");
            String project_name = resultSet.getString("project_name");
            int issue_count = resultSet.getInt("issue_count");
            String error_free = resultSet.getString("error_free");
            return new Project(id, project_name, issue_count, ProjectState.valueOf(error_free.toUpperCase()));
        }
    }

}
