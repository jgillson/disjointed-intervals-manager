package com.yourmechanic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author John Gillson
 * @since 08/2020
 */
class DisjointedIntervalsFileHandlerTest {

    File resourcesDir = new File("src/test/resources/com/yourmechanic");
    File outputFile;

    @BeforeEach
    void init() {
        outputFile = new File(resourcesDir.getAbsolutePath(), "output_file.txt");
        if (outputFile.exists()) {
            outputFile.delete();
        }
        try {
            outputFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testHandleFiles() {
        ClassLoader classLoader = getClass().getClassLoader();
        String csvFile = Objects.requireNonNull(classLoader.getResource("com/yourmechanic/input_file.csv")).getFile();

        DisjointedIntervalsFileHandler.handleFiles(csvFile, outputFile.getPath());

        List<String> lines = Arrays.asList(
                "[[1, 5]]", "[[1, 2], [3, 5]]", "[[1, 2], [3, 5], [6, 8]]",
                "[[1, 2], [3, 4], [7, 8]]", "[[1, 8]]");

        assertAll(
                () -> assertTrue(outputFile.exists()),
                () -> assertLinesMatch(lines, Files.readAllLines(outputFile.toPath()))
        );
    }
}
