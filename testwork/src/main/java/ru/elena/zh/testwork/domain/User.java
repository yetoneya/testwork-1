package ru.elena.zh.testwork.domain;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    public static final long serialVersionUID = 1L;
    public static final String[] COLUMNS = new String[]{"id", "user_name"};
    private int id;
    private String user_name = "";

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static ColumnPositionMappingStrategy getMappingStrategy() {
        ColumnPositionMappingStrategy<User> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(User.class);
        strategy.setColumnMapping(COLUMNS);
        return strategy;
    }

}
