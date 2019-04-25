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
import ru.elena.zh.testwork.dao.UserDao;
import ru.elena.zh.testwork.domain.User;

@Service("userLoader")
public class UserLoader implements DataLoader {

    private final Logger logger = Logger.getLogger(UserLoader.class);
    private @Value("${csv.user}")
    String FILE_NAME;
    private final String LOAD_FROM = "LOAD FROM USER FILE";
    private final UserDao userDao;

    @Autowired
    public UserLoader(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void loadData() throws Exception {
        Path USER_FILE = Paths.get(FILE_NAME);
        if (!Files.exists(USER_FILE) || Files.size(USER_FILE) < 2) {
            userDao.insert(new User(0, "undefined"));
            return;
        }
        try (CSVReader reader = new CSVReader(Files.newBufferedReader(USER_FILE))) {
            CsvToBean<User> csv = new CsvToBean<>();
            csv.setMappingStrategy(User.getMappingStrategy());
            csv.setCsvReader(reader);
            List<User> users = csv.parse();
            userDao.load(users);
            logger.info(LOAD_FROM);
        }

    }

    @Override
    public void saveData() throws Exception {
        Path USER_FILE = Paths.get(FILE_NAME);
        Files.deleteIfExists(USER_FILE);
        try (CsvBeanWriter writer = new CsvBeanWriter(
                Files.newBufferedWriter(USER_FILE, CREATE, APPEND), CsvPreference.STANDARD_PREFERENCE)) {
            List<User> users = userDao.findAll();
            for (User user : users) {
                writer.write(user, User.COLUMNS);
            }
        }
    }
}
