package ru.elena.zh.testwork.dao;

import java.util.List;
import ru.elena.zh.testwork.domain.Issue;
import ru.elena.zh.testwork.domain.User;

public interface UserDao {

    User insert(User user) throws Exception;
    
    boolean load(List<User>list) throws Exception;

    List<User> findAll() throws Exception;

    User findById(int id)throws Exception;

    User findByName(String name)throws Exception;

}
