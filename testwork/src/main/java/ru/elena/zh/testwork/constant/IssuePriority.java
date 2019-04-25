package ru.elena.zh.testwork.constant;

public enum IssuePriority {

    LOW(3), MIDDLE(2), HIGH(1);
    int level;

    IssuePriority(int level) {
        this.level = level;
    }
}
