package ru.elena.zh.testwork.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.elena.zh.testwork.service.StoreService;

@ShellComponent
public class SaveToFilesCommand {
    
    private final StoreService storeService;
    
    public SaveToFilesCommand(StoreService storeService) {
        this.storeService = storeService;
    }
    
    @ShellMethod("Finish")
    public void finish() {
        storeService.saveToFiles();
        System.exit(0);
    }
    
    @ShellMethod("Refresh files")
    public boolean save_to_files() {
        return storeService.saveToFiles();
        
    }
    
}
