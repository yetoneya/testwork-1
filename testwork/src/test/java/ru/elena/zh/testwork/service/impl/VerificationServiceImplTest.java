package ru.elena.zh.testwork.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.elena.zh.testwork.domain.Issue;
import ru.elena.zh.testwork.service.VerificationService;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
    ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"})
public class VerificationServiceImplTest {

    @Autowired
    private VerificationService verificationService;

    @Test
    public void testCheckIssue() {
        Issue issue = new Issue();
        String result = verificationService.checkIssue(issue);
        assertNotEquals(result, "verified");
        issue.setProject_id(1);
        issue.setDescription("meow");
        issue.setProject_version_before(1);
        result = verificationService.checkIssue(issue);
        assertEquals(result, "verified");
    }

}
