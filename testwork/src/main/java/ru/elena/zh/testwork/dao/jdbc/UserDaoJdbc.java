package ru.elena.zh.testwork.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import ru.elena.zh.testwork.dao.UserDao;
import ru.elena.zh.testwork.domain.User;

@Service("userDao")
@ConditionalOnProperty(name = "dao", havingValue = "database")
public class UserDaoJdbc implements UserDao {

    private final NamedParameterJdbcOperations jdbcOperations;

    public UserDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.jdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public boolean load(List<User> list) throws Exception {
        String query = "insert into users values (:id,:user_name)";
        for (User user : list) {
            final HashMap<String, Object> userParams = new HashMap<>(2);
            userParams.put("id", user.getId());
            userParams.put("user_name", user.getUser_name());
            jdbcOperations.update(query, userParams);
        }
        return true;
    }

    @Override
    public User insert(User user) throws Exception {
        String query = "insert into users values (null,:user_name)";
        final KeyHolder bKeyHolder = new GeneratedKeyHolder();
        final HashMap<String, Object> userParams = new HashMap<>(1);
        userParams.put("user_name", user.getUser_name());
        MapSqlParameterSource parameters = new MapSqlParameterSource().addValues(userParams);
        jdbcOperations.update(query, parameters, bKeyHolder);
        long id = bKeyHolder.getKey().longValue();
        user.setId((int) id);
        return user;
    }

    @Override
    public List<User> findAll() throws Exception {
        return jdbcOperations.query("select * from users", new UserMapper());

    }

    @Override
    public User findById(int id) throws Exception {
        String query = "select*from users where id = :id";
        final HashMap<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        User user = jdbcOperations.queryForObject(query, params, new UserMapper());
        return user;
    }

    @Override
    public User findByName(String user_name) throws Exception {
        String query = "select*from users where user_name = :user_name";
        final HashMap<String, Object> params = new HashMap<>(1);
        params.put("user_name", user_name);
        List<User> list = jdbcOperations.query(query, params, new UserMapper());
        return list.isEmpty() ? null : list.get(0);
    }

    private static class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("id");
            String user_name = resultSet.getString("user_name");
            return new User(id, user_name);
        }
    }

}
