package enums;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;

public enum CommandRegexPatterns {
    ADD_COURSE("1 .+ \\d+"),
    ADD_STUDENT("2 \\w+ \\d+"),
    ADD_TEACHER("3 \\w+ \\w+"),
    ADD_TEACHER_TO_COURSE("4 \\d+ .+"),
    ADD_STUDENT_TO_COURSE("5 \\d+ .+"),
    ADD_GRADE_TO_STUDENT_IN_COURSE("6 \\d+ .+ \\d{1}.?\\d{0,2}"),
    SHOW_AVERAGE_GRADE_FOR_STUDENTS_IN_COURSE("9 .+"),
    SHOW_STUDENT_TOTAL_AVERAGE_GRADE("10 \\d+");

    private String pattern;

    CommandRegexPatterns(String pattern) {
        this.pattern = pattern;
    }

    public String get() {
        return this.pattern;
    }
}
