package service;

import model.Student;
import model.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentMapper {

    public static Student parseStudent(String line) {
        // Split by the first occurrence of '[' to separate student info from subjects
        String[] parts = line.split("\\[", 2);
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid input format: Missing subject data.");
        }

        // Extract student information and subject parts
        String infoPart = parts[0].trim();
        String subjectsPart = parts[1].trim();

        // Split student information
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

        // Remove trailing ']'
        subjectsPart = subjectsPart.replaceAll("]$", "");

        // Split by '][' to handle multiple subjects
        String[] subjectEntries = subjectsPart.split("\\]\\s*\\[");

        for (String entry : subjectEntries) {
            // Clean up entry
            entry = entry.trim().replaceAll("^\\[|\\]$", "");
            int colonIndex = entry.indexOf(":");
            if (colonIndex < 0) {
                System.err.println("Invalid subject format: " + entry);
                continue;
            }

            String subjectName = entry.substring(0, colonIndex).trim();
            String gradesPart = entry.substring(colonIndex + 1).trim();

            List<Double> grades = parseGrades(gradesPart);
            subjects.add(new Subject(subjectName, grades));
        }

        return subjects;
    }

    private static List<Double> parseGrades(String gradesPart) {
        return List.of(gradesPart.split(","))
                .stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(grade -> {
                    try {
                        return Double.parseDouble(grade);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid grade format: " + grade);
                        return null;
                    }
                })
                .filter(grade -> grade != null)
                .collect(Collectors.toList());
    }
}
