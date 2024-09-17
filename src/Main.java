import model.Student;
import service.StudentService;
import service.StudentServiceWithoutStream;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Student> students = StudentService.getAllStudents();
        boolean useStreams = true;

        while (true) {
            System.out.println("Оберіть режим: 1 - використовувати Stream, 2 - без Stream");
            int modeChoice = scanner.nextInt();

            if (modeChoice == 1) {
                useStreams = true;
            } else if (modeChoice == 2) {
                useStreams = false;
            } else {
                System.out.println("Неправильний вибір! Спробуйте ще раз.");
                continue;
            }

            System.out.println("Оберіть функцію:");
            System.out.println("1 - Групування за номером групи");
            System.out.println("2 - Розподіл студентів");
            System.out.println("3 - Знайти найкращого студента за предметом");
            System.out.println("4 - Отримати унікальні предмети");
            System.out.println("5 - Сортувати за середнім балом");
            System.out.println("0 - Вийти");

            int functionChoice = scanner.nextInt();

            if (functionChoice == 0) {
                break;
            }

            if (functionChoice == 3) {
                System.out.println("Оберіть предмет: 1 - ykr, 2 - bio, 3 - math");
                int subjectChoice = scanner.nextInt();
                String subject;
                switch (subjectChoice) {
                    case 1:
                        subject = "ykr";
                        break;
                    case 2:
                        subject = "bio";
                        break;
                    case 3:
                        subject = "math";
                        break;
                    default:
                        System.out.println("Неправильний ввід! Спробуйте ще раз.");
                        continue;
                }

                if (useStreams) {
                    StudentService.findTopStudentInSubject(students, subject);
                } else {
                    StudentServiceWithoutStream.findTopStudentInSubject(students, subject);
                }
            } else {
                switch (functionChoice) {
                    case 1:
                        if (useStreams) {
                            StudentService.groupByGroupNumber(students);
                        } else {
                            StudentServiceWithoutStream.groupByGroupNumber(students);
                        }
                        break;
                    case 2:
                        if (useStreams) {
                            StudentService.divideStudents(students);
                        } else {
                            StudentServiceWithoutStream.divideStudents(students);
                        }
                        break;
                    case 4:
                        if (useStreams) {
                            StudentService.getUniqueSubjects(students);
                        } else {
                            StudentServiceWithoutStream.getUniqueSubjects(students);
                        }
                        break;
                    case 5:
                        if (useStreams) {
                            StudentService.sortByAverageGrade(students);
                        } else {
                            StudentServiceWithoutStream.sortByAverageGrade(students);
                        }
                        break;
                    default:
                        System.out.println("Неправильний ввід! Спробуйте ще раз.");
                        break;
                }
            }
        }

        System.out.println("Програму завершено.");
    }
}
