package service;

import model.Student;
import model.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class StudentMapper {

    public static Student parseStudent(String line) {
        String[] parts = line.split("\\[", 2);
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid input format: Missing subject data.");
        }

        String infoPart = parts[0].trim();
        String subjectsPart = parts[1].trim();

        String[] info = infoPart.split("\\s+");
        if (info.length < 3) {
            throw new IllegalArgumentException("Invalid input format: Missing student information.");
        }

        String firstName = info[0];
        String lastName = info[1];
        String group = info[2];

        String workplace = info.length > 3 ? info[3] : null;

        List<Subject> subjects = parseSubjects(subjectsPart);

        return new Student(firstName, lastName, group, subjects, workplace);
    }

    private static List<Subject> parseSubjects(String subjectsPart) {
        List<Subject> subjects = new ArrayList<>();

        subjectsPart = subjectsPart.replaceAll("]$", "");

        String[] subjectEntries = subjectsPart.split("]\\s*\\[");

        for (String entry : subjectEntries) {
            entry = entry.trim().replaceAll("^\\[|]$", "");
            int colonIndex = entry.indexOf(":");
            if (colonIndex < 1) {
                throw new IllegalArgumentException("Invalid subject format: " + entry);
            }

            String subjectName = entry.substring(0, colonIndex).trim();
            String gradesPart = entry.substring(colonIndex + 1).trim();

            List<Double> grades = parseGrades(gradesPart);
            subjects.add(new Subject(subjectName, grades));
        }

        return subjects;
    }

    private static List<Double> parseGrades(String gradesPart) {
        return Stream.of(gradesPart.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(grade -> {
                    try {
                        return Double.parseDouble(grade);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Invalid grade format: " + grade);
                    }
                })
                .toList();
    }
}
