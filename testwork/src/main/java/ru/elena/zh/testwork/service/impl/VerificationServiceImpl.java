package ru.elena.zh.testwork.service.impl;

import org.springframework.stereotype.Service;
import ru.elena.zh.testwork.service.VerificationService;
import ru.elena.zh.testwork.domain.Issue;

@Service("verificationService")
public class VerificationServiceImpl implements VerificationService {

    private final static String PROJECT_ID_LACK = "ERROR: project_id is required; ";
    private final static String DESCRIPTION_LACK = "ERROR: description is required; ";
    private final static String PROJECT_VERSION_LACK = "ERROR: project_version is required; ";
    private final static String VERIFIED = "verified";

    @Override
    public String checkIssue(Issue issue) {
        boolean checked = true;
        StringBuilder builder = new StringBuilder();
        if (issue.getProject_id() == 0) {
            checked = false;
            builder.append(PROJECT_ID_LACK);
        }
        if (issue.getDescription().equals("")) {
            checked = false;
            builder.append(DESCRIPTION_LACK);
        }
        if (issue.getProject_version_before() == 0) {
            checked = false;
            builder.append(PROJECT_VERSION_LACK);
        }

        if (checked == true) {
            builder.append(VERIFIED);
        }
        return builder.toString();
    }
}
