package ru.elena.zh.testwork.dao.list;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.elena.zh.testwork.dao.UserDao;
import ru.elena.zh.testwork.domain.User;

@Service("userDao")
@ConditionalOnProperty(name = "dao", havingValue = "memory")
public class UserDaoList implements UserDao {

    private final List<User> users = new ArrayList<>();


    @Override
    public boolean load(List<User> list) {
        return users.addAll(list);
    }

    @Override
    public List<User> findAll() throws Exception {
        return new ArrayList<>(users);
    }

    @Override
    public User findById(int id) {
        return users
                .stream()
                .filter(u -> u.getId() == id)
                .findAny()
                .orElse(null);
    }

    @Override
    public User findByName(String user_name) {
        return users.stream()
                .filter(u -> u.getUser_name().equals(user_name))
                .findAny()
                .orElse(null);
    }

    @Override
    public User insert(User user) throws Exception {
        user.setId(users.size() + 1);
        users.add(user);
        return user;

    }

}
/*    
        }*/
