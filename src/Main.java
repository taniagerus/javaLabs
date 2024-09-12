import model.Student;
import service.StudentService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Student> students = StudentService.getAllStudents();
        StudentService.divideStudents(students);
        StudentService.groupByGroupNumber(students);
        StudentService.getStudentsGroupedByGrade(students, "ykr");
        StudentService.sortByAverageGrade(students);
        StudentService.getUniqueSubjects(students);

    }
}