package service;

import model.Student;
import model.Subject;

import java.util.*;
import java.util.stream.Collectors;

public class StudentService {

    private static final String filePath = "./students";

    public static List<Student> getAllStudents() {
        return FileService.readStudentsFromFile(filePath).stream()
                .map(studentString -> {
                    try {
                        return StudentMapper.parseStudent(studentString);
                    } catch (IllegalArgumentException e) {
                        System.err.println(e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }

    public static void divideStudents(List<Student> students) {
        Map<Boolean, List<Student>> studentsGroupedByIsPartTime = students.stream()
                .collect(Collectors.groupingBy(Student::isPartTime));

        System.out.println("Part-time students:");
        printStudents(studentsGroupedByIsPartTime.getOrDefault(true, List.of()));
        System.out.println("Full-time students:");
        printStudents(studentsGroupedByIsPartTime.getOrDefault(false, List.of()));
    }

    private static void printStudents(List<Student> students) {
        if (students.isEmpty()) {
            System.out.println("No students to display.");
        } else {
            students.forEach(System.out::println);
        }
    }

    public static void groupByGroupNumber(List<Student> students) {
        students.stream()
                .collect(Collectors.groupingBy(Student::getGroupNumber))
                .forEach((key, value) -> {
                    System.out.println(key + " :");
                    printStudents(value);
                });
    }

    public static void getStudentsGroupedByGrade(List<Student> students, String subjectName) {
        students.stream()
                .flatMap(student -> student.getSubjects().stream()
                        .filter(subject -> subject.getName().equals(subjectName))
                        .flatMap(subject -> subject.getGrades().stream()
                                .map(grade -> Map.entry(grade, student.getLastName())))
                )
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())))
                .forEach((key, value) -> {
                    System.out.println(key + " :");
                    value.forEach(System.out::println);
                });
    }

    private static double calculateAverageGrade(Student student) {
        return student.getSubjects().stream()
                .flatMap(subject -> subject.getGrades().stream())
                .mapToDouble(grade -> grade)
                .average()
                .orElse(0.0); // If no grades, average = 0
    }

    public static List<Student> sortByAverageGrade(List<Student> students) {
        List<Student> sortedStudents = students.stream()
                .sorted(Comparator.comparingDouble(StudentService::calculateAverageGrade).reversed())
                .collect(Collectors.toList());

        System.out.println("Students sorted by average grade:");
        printStudents(sortedStudents);

        return sortedStudents;
    }

    public static void getUniqueSubjects(List<Student> students) {
        Set<String> uniqueSubjects = students.stream()
                .flatMap(student -> student.getSubjects().stream())
                .map(Subject::getName)
                .collect(Collectors.toSet());

        System.out.println("Unique subjects:");
        uniqueSubjects.forEach(System.out::println);
    }

    // Method to find the student with the highest average grade in a specific subject
    public static void findTopStudentInSubject(List<Student> students, String subjectName) {
        Optional<Student> topStudent = students.stream()
                .flatMap(student -> student.getSubjects().stream()
                        .filter(subject -> subject.getName().equals(subjectName))
                        .map(subject -> Map.entry(student, calculateSubjectAverage(subject)))
                )
                .max(Comparator.comparingDouble(Map.Entry::getValue))
                .map(Map.Entry::getKey);

        if (topStudent.isPresent()) {
            System.out.println("Top student in " + subjectName + ":");
            System.out.println(topStudent.get());
        } else {
            System.out.println("No students found for the subject: " + subjectName);
        }
    }

    private static double calculateSubjectAverage(Subject subject) {
        return subject.getGrades().stream()
                .mapToDouble(grade -> grade)
                .average()
                .orElse(0.0); // If no grades, average = 0
    }
}
