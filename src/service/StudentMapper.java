package service;

import model.Student;
import model.Subject;

import java.util.ArrayList;
import java.util.List;

public class StudentMapper {

    public static Student parseStudent(String line) {
        String[] parts = line.split("\\[");
        String infoPart = parts[0].trim();
        String subjectsPart = parts[1].replace("]", "").trim();

        String[] info = infoPart.split("\\s+");

        String firstName = info[0];
        String lastName = info[1];
        String group = info[2];

        String workplace = null;

        if (info.length == 4) {
            workplace = info[3];
        }

        List<Subject> subjects = parseSubjects(subjectsPart);

        return new Student(firstName, lastName, group, subjects, workplace);
    }

    private static List<Subject> parseSubjects(String subjectsPart) {
        List<Subject> subjects = new ArrayList<>();
        String[] subjectEntries = subjectsPart.split(",");

        for (String entry : subjectEntries) {
            String[] subjectAndMark = entry.split(":");
            String subjectName = subjectAndMark[0].trim();
            double mark = Double.parseDouble(subjectAndMark[1].trim());
            subjects.add(new Subject(subjectName, mark));
        }

        return subjects;
    }
}
