package service;

import model.Student;
import model.Subject;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class StudentService {

    //TODO: define
    private static final String filePath = "./students";

    public static List<Student> getAllStudents() {
        return FileService.readStudentsFromFile(filePath).stream()
                .map(StudentMapper::parseStudent)
                .toList();
    }

    public static void divideStudents(List<Student> students) {
        Map<Boolean, List<Student>> studentsGroupedByIsPartTime = students.stream()
                .collect(Collectors.groupingBy(Student::isPartTime));

        System.out.println("Zao4ni hui");
        printStudents(studentsGroupedByIsPartTime.get(true));
        System.out.println("Ne zao4ni");
        printStudents(studentsGroupedByIsPartTime.get(false));
    }

    //TODO: перевірка чи пустий список
    private static void printStudents(List<Student> students) {

        students.forEach(System.out::println);
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
                // Фільтруємо тільки тих студентів, які мають потрібний предмет у списку
                .flatMap(student -> student.getSubjects().stream()
                        .filter(subject -> subject.getName().equals(subjectName))
                        // Перетворюємо Subject в Map.Entry<Integer, String> де ключ - оцінка, значення - прізвище
                        .map(subject -> Map.entry(subject.getGrade(), student.getLastName())))
                // Групуємо прізвища студентів за оцінками
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())))
                .forEach((key, value) -> {
                    System.out.println(key + " :");
                    value.forEach(System.out::println);
                });
    }
    private static double calculateAverageGrade(Student student) {
        return student.getSubjects().stream()
                .mapToDouble(Subject::getGrade)
                .average()
                .orElse(0.0); // Якщо немає предметів, середній бал = 0
    }
    public static List<Student> sortByAverageGrade(List<Student> students) {
        List<Student> sortedStudents = students.stream()
                .sorted(Comparator.comparingDouble(StudentService::calculateAverageGrade).reversed())
                .collect(Collectors.toList());

        System.out.println("Students sorted by avarage grade");
        printStudents(sortedStudents);

        return sortedStudents;
    }

    public static void getUniqueSubjects(List<Student> students) {
        Set<String> uniqueSubjects = students.stream()
                .flatMap(student -> student.getSubjects().stream())
                .map(Subject::getName)
                .collect(Collectors.toSet()); // Використовуємо Set для уникнення дублікатів

        System.out.println("Unique subjects:");
        uniqueSubjects.forEach(System.out::println);
    }

}
