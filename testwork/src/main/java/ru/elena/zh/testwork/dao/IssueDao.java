package ru.elena.zh.testwork.dao;

import java.util.List;
import ru.elena.zh.testwork.domain.Issue;

public interface IssueDao { 
   
   Issue insert(Issue issue) throws Exception;
  
   int merge(Issue issue) throws Exception;
   
   boolean load(List<Issue>list) throws Exception;
   
   List<Issue> findAll() throws Exception;
   
   Issue findById(int id)throws Exception;
   
   
}
