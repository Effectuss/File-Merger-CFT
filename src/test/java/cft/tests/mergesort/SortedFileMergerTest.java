package cft.tests.mergesort;

import cft.mergesort.logic.classes.SortConfiguration;
import cft.mergesort.logic.classes.SortedFileMerger;
import cft.mergesort.logic.enums.DataType;
import cft.mergesort.logic.enums.SortMode;
import cft.mergesort.logic.interfaces.FileMerger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortedFileMergerTest {
    private static final URL wayToRes = CommandLineParserTest.class
            .getClassLoader()
            .getResource("test_resources_sorted_file_merger" + File.separator);

    private String testCase;

    @Test
    void testSortedFileMergerCorrectIntegerAscending() throws IOException {
        testCase = wayToRes.getPath() + "case1" + File.separator;
        File testFile = new File(testCase + "out.txt");
        File outputFile = new File(testCase + "test.txt");

        List<File> inputFiles = new ArrayList<>(Arrays.asList(
                new File(testCase + "in1.txt"),
                new File(testCase + "in2.txt"),
                new File(testCase + "in3.txt")
        ));

        SortConfiguration configuration = new SortConfiguration(DataType.INTEGER,
                SortMode.ASCENDING,
                outputFile, inputFiles);
        FileMerger fileMerger = new SortedFileMerger(configuration);
        fileMerger.merge();

        String fileContent1 = new String(Files.readAllBytes(testFile.toPath()));
        String fileContent2 = new String(Files.readAllBytes(outputFile.toPath()));
        assertEquals(fileContent1, fileContent2);
    }

    @Test
    void testSortedFileMergerCorrectStringAscending() throws IOException {
        testCase = wayToRes.getPath() + "case2" + File.separator;

        File testFile = new File(testCase + "out.txt");
        File outputFile = new File(testCase + "test.txt");

        List<File> inputFiles = new ArrayList<>(Arrays.asList(
                new File(testCase + "in1.txt"),
                new File(testCase + "in2.txt")
        ));

        SortConfiguration configuration = new SortConfiguration(DataType.STRING,
                SortMode.ASCENDING,
                outputFile, inputFiles);
        FileMerger fileMerger = new SortedFileMerger(configuration);
        fileMerger.merge();

        String fileContent1 = new String(Files.readAllBytes(testFile.toPath()));
        String fileContent2 = new String(Files.readAllBytes(outputFile.toPath()));
        assertEquals(fileContent1, fileContent2);
    }

    @Test
    void testSortedFileMergerCorrectIntegerDescending() throws IOException {
        testCase = wayToRes.getPath() + "case3" + File.separator;

        File testFile = new File(testCase + "out.txt");
        File outputFile = new File(testCase + "test.txt");

        List<File> inputFiles = new ArrayList<>(Arrays.asList(
                new File(testCase + "in1.txt"),
                new File(testCase + "in2.txt"),
                new File(testCase + "in3.txt")
        ));

        SortConfiguration configuration = new SortConfiguration(DataType.INTEGER,
                SortMode.DESCENDING,
                outputFile, inputFiles);

        FileMerger fileMerger = new SortedFileMerger(configuration);
        fileMerger.merge();

        String fileContent1 = new String(Files.readAllBytes(testFile.toPath()));
        String fileContent2 = new String(Files.readAllBytes(outputFile.toPath()));
        assertEquals(fileContent1, fileContent2);
    }

    @Test
    void testSortedFileMergerCorrectStringDescending() throws IOException {
        testCase = wayToRes.getPath() + "case4" + File.separator;

        File testFile = new File(testCase + "out.txt");
        File outputFile = new File(testCase + "test.txt");

        List<File> inputFiles = new ArrayList<>(Arrays.asList(
                new File(testCase + "in1.txt"),
                new File(testCase + "in2.txt"),
                new File(testCase + "in3.txt")
        ));

        SortConfiguration configuration = new SortConfiguration(DataType.STRING,
                SortMode.DESCENDING,
                outputFile, inputFiles);

        FileMerger fileMerger = new SortedFileMerger(configuration);
        fileMerger.merge();

        String fileContent1 = new String(Files.readAllBytes(testFile.toPath()));
        String fileContent2 = new String(Files.readAllBytes(outputFile.toPath()));
        assertEquals(fileContent1, fileContent2);
    }
}