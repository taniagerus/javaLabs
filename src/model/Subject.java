package model;

import java.util.List;
import java.util.stream.Collectors;

public class Subject {
    private String name;
    private List<Double> grades;

    public Subject(String name, List<Double> grades) {
        this.name = name;
        this.grades = grades;
    }

    public String getName() {
        return name;
    }

    public List<Double> getGrades() {
        return grades;
    }

    @Override
    public String toString() {
        return name + ": " + grades.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
    }
}
