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
import ru.elena.zh.testwork.dao.ProjectDao;
import ru.elena.zh.testwork.domain.Project;

@Service("projectLoader")
public class ProjectLoader implements DataLoader {

    private final Logger logger = Logger.getLogger(ProjectLoader.class);
    private @Value("${csv.project}")
    String FILE_NAME;
    private final String LOAD_FROM = "LOAD FROM PROJECT FILE";

    private final ProjectDao projectDao;

    @Autowired
    public ProjectLoader(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    public void loadData() throws Exception {
        Path PROJECT_FILE = Paths.get(FILE_NAME);
        if (Files.exists(PROJECT_FILE) && Files.size(PROJECT_FILE) > 1) {

            try (CSVReader reader = new CSVReader(Files.newBufferedReader(PROJECT_FILE))) {
                CsvToBean<Project> csv = new CsvToBean<>();
                csv.setMappingStrategy(Project.getMappingStrategy());
                csv.setCsvReader(reader);
                List<Project> projects = csv.parse();
                projectDao.load(projects);
                logger.info(LOAD_FROM);
            }
        }
    }

    @Override
    public void saveData() throws Exception {
        Path PROJECT_FILE = Paths.get(FILE_NAME);
        Files.deleteIfExists(PROJECT_FILE);
        try (CsvBeanWriter writer = new CsvBeanWriter(
                Files.newBufferedWriter(PROJECT_FILE, CREATE, APPEND), CsvPreference.STANDARD_PREFERENCE)) {
            for (Project project : projectDao.findAll()) {
                writer.write(project, Project.COLUMNS);
            }
        }
    }
}
