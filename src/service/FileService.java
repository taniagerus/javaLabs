package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class FileService {

    public static List<String> readStudentsFromFile(String filePath) {
        Path path = Paths.get(filePath);

        try {
            return Arrays.asList(Files.readString(path).split("\n"));
        } catch (IOException e) {
            throw new IllegalStateException("Something went wrong when reading file. " + e.getMessage());
        }
    }
}
