package ru.elena.zh.testwork.dao.list;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.elena.zh.testwork.dao.IssueDao;
import ru.elena.zh.testwork.domain.Issue;

@Service("issueDao")
@ConditionalOnProperty(name = "dao", havingValue = "memory")
public class IssueDaoList implements IssueDao {

    private final List<Issue> issues = new ArrayList<>();

    @Override
    public boolean load(List<Issue> list) {
        return issues.addAll(list);
    }

    @Override
    public List<Issue> findAll() throws Exception {
        return new ArrayList(issues);
    }

    @Override
    public Issue findById(int id) {
        return issues
                .stream()
                .filter(p -> p.getId() == id)
                .findAny()
                .orElse(null);
    }

    @Override
    public Issue insert(Issue issue) throws Exception {
        issue.setId(issues.size() + 1);
        issues.add(issue);
        return issue;
    }

    @Override
    public int merge(Issue issue) throws Exception {
        issues.set(issue.getId() - 1, issue);
        return issue.getId();
    }

}
