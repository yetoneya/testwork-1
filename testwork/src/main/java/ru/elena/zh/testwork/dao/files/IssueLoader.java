package ru.elena.zh.testwork.dao.files;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import ru.elena.zh.testwork.dao.DataLoader;
import ru.elena.zh.testwork.dao.IssueDao;
import ru.elena.zh.testwork.domain.Issue;

@Service("issueLoader")
public class IssueLoader implements DataLoader {

    private final Logger logger = Logger.getLogger(IssueLoader.class);
    private @Value("${csv.issue}")
    String FILE_NAME;
    private final String LOAD_FROM = "LOAD FROM ISSUE FILE";
    private final IssueDao issueDao;

    @Autowired
    public IssueLoader(IssueDao issueDao) {
        this.issueDao = issueDao;
    }

    @Override
    public void loadData() throws Exception {
        Path ISSUE_FILE = Paths.get(FILE_NAME);
        if (Files.exists(ISSUE_FILE) && Files.size(ISSUE_FILE) > 1) {
            try (CSVReader reader = new CSVReader(Files.newBufferedReader(ISSUE_FILE))) {
                CsvToBean<Issue> csv = new CsvToBean<>();
                csv.setMappingStrategy(Issue.getMappingStrategy());
                csv.setCsvReader(reader);
                List<Issue> issues = csv.parse();
                issueDao.load(issues);
                logger.info(LOAD_FROM);
            }
        }
    }

    @Override
    public void saveData() throws Exception {
        Path ISSUE_FILE = Paths.get(FILE_NAME);
        Files.deleteIfExists(ISSUE_FILE);
        try (CsvBeanWriter writer = new CsvBeanWriter(
                Files.newBufferedWriter(ISSUE_FILE, CREATE, APPEND), CsvPreference.STANDARD_PREFERENCE)) {
            for (Issue issue : issueDao.findAll()) {
                writer.write(issue, Issue.COLUMNS);
            }

        }
    }

}
