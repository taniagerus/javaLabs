package service;

import model.Student;
import model.Subject;

import java.util.*;

public class StudentServiceWithoutStream {

        private static final String filePath = "./students";

        public static List<Student> getAllStudents() {
            List<String> lines = FileService.readStudentsFromFile(filePath);
            List<Student> students = new ArrayList<>();
            for (String line : lines) {
                try {
                    students.add(StudentMapper.parseStudent(line));
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                }
            }
            return students;
        }

        public static void divideStudents(List<Student> students) {
            List<Student> partTimeStudents = new ArrayList<>();
            List<Student> fullTimeStudents = new ArrayList<>();

            for (Student student : students) {
                if (student.isPartTime()) {
                    partTimeStudents.add(student);
                } else {
                    fullTimeStudents.add(student);
                }
            }

            System.out.println("Part-time students:");
            printStudents(partTimeStudents);
            System.out.println("Full-time students:");
            printStudents(fullTimeStudents);
        }

        private static void printStudents(List<Student> students) {
            if (students.isEmpty()) {
                System.out.println("No students to display.");
            } else {
                for (Student student : students) {
                    System.out.println(student);
                }
            }
        }

        public static void groupByGroupNumber(List<Student> students) {
            Map<String, List<Student>> groupedByGroupNumber = new HashMap<>();

            for (Student student : students) {
                String groupNumber = student.getGroupNumber();
                groupedByGroupNumber.computeIfAbsent(groupNumber, k -> new ArrayList<>()).add(student);
            }

            for (Map.Entry<String, List<Student>> entry : groupedByGroupNumber.entrySet()) {
                System.out.println(entry.getKey() + " :");
                printStudents(entry.getValue());
            }
        }

        public static void getStudentsGroupedByGrade(List<Student> students, String subjectName) {
            Map<Double, List<String>> gradeToStudentsMap = new HashMap<>();

            for (Student student : students) {
                for (Subject subject : student.getSubjects()) {
                    if (subject.getName().equals(subjectName)) {
                        for (Double grade : subject.getGrades()) {
                            gradeToStudentsMap.computeIfAbsent(grade, k -> new ArrayList<>()).add(student.getLastName());
                        }
                    }
                }
            }

            for (Map.Entry<Double, List<String>> entry : gradeToStudentsMap.entrySet()) {
                System.out.println(entry.getKey() + " :");
                for (String lastName : entry.getValue()) {
                    System.out.println(lastName);
                }
            }
        }

        private static double calculateAverageGrade(Student student) {
            double total = 0.0;
            int count = 0;

            for (Subject subject : student.getSubjects()) {
                for (Double grade : subject.getGrades()) {
                    total += grade;
                    count++;
                }
            }

            return count == 0 ? 0.0 : total / count;
        }

    public static List<Student> sortByAverageGrade(List<Student> students) {
        List<Student> sortedStudents = new ArrayList<>(students);
        sortedStudents.sort((s1, s2) -> {
            double avg1 = calculateAverageGrade(s1);
            double avg2 = calculateAverageGrade(s2);
            return Double.compare(avg2, avg1); // Сортування за спаданням
        });

        System.out.println("Students sorted by average grade:");
        for (Student student : sortedStudents) {
            double averageGrade = calculateAverageGrade(student);
            System.out.println("Name: " + student.getFirstName() + student.getLastName() + averageGrade);
        }

        return sortedStudents;
    }
        public static void getUniqueSubjects(List<Student> students) {
            Set<String> uniqueSubjects = new HashSet<>();

            for (Student student : students) {
                for (Subject subject : student.getSubjects()) {
                    uniqueSubjects.add(subject.getName());
                }
            }

            System.out.println("Unique subjects:");
            for (String subject : uniqueSubjects) {
                System.out.println(subject);
            }
        }

        public static void findTopStudentInSubject(List<Student> students, String subjectName) {
            Student topStudent = null;
            double highestAverage = Double.NEGATIVE_INFINITY;

            for (Student student : students) {
                for (Subject subject : student.getSubjects()) {
                    if (subject.getName().equals(subjectName)) {
                        double average = calculateSubjectAverage(subject);
                        if (average > highestAverage) {
                            highestAverage = average;
                            topStudent = student;
                        }
                    }
                }
            }

            if (topStudent != null) {
                System.out.println("Top student in " + subjectName + ":");
                System.out.println(topStudent);
            } else {
                System.out.println("No students found for the subject: " + subjectName);
            }
        }

        private static double calculateSubjectAverage(Subject subject) {
            double total = 0.0;
            for (Double grade : subject.getGrades()) {
                total += grade;
            }
            return subject.getGrades().isEmpty() ? 0.0 : total / subject.getGrades().size();
        }
    }

