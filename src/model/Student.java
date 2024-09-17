package model;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// Базовий клас для студента
public class Student {
    private String firstName;
    private String lastName;
    private String groupNumber;
    private List<Subject> subjects;
    private String workplace;

    public Student(String firstName, String lastName, String groupNumber, List<Subject> subjects, String workplace) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.groupNumber = groupNumber;
        this.subjects = subjects;
        this.workplace = workplace;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public String getWorkplace() {
        return workplace;
    }

    public boolean isPartTime() {
        return !Objects.isNull(workplace);
    }

    @Override
    public String toString() {
        String subjectsString = subjects.stream()
                .map(Subject::toString)
                .collect(Collectors.joining(", "));
        String workplacePart = isPartTime() ? ", workplace: " + workplace : "";
        return "%s %s, group: %s, subjects: [%s]%s".formatted(
                firstName, lastName, groupNumber, subjectsString, workplacePart
        );
    }


}