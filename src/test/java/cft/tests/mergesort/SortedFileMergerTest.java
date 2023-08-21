package cft.tests.mergesort;

import cft.mergesort.logic.classes.SortConfiguration;
import cft.mergesort.logic.classes.SortedFileMerger;
import cft.mergesort.logic.enums.DataType;
import cft.mergesort.logic.enums.SortMode;
import cft.mergesort.logic.interfaces.FileMerger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortedFileMergerTest {
    private static final String TEST_RESOURCES_PATH = "test_resources_sorted_file_merger";
    private static final String CASE_PREFIX = "case";

    private URL testResourcesURL;

    @BeforeEach
    void setUp() {
        testResourcesURL = CommandLineParserTest.class.getClassLoader().getResource(TEST_RESOURCES_PATH + File.separator);
    }

    private void runAndAssertMerge(String testCasePath, DataType dataType, SortMode sortMode) throws IOException {
        File testCaseFolder = new File(testCasePath);
        File testFile = new File(testCaseFolder, "out.txt");
        File outputFile = new File(testCaseFolder, "test.txt");

        List<File> inputFiles = new ArrayList<>(Arrays.asList(
                new File(testCaseFolder, "in1.txt"),
                new File(testCaseFolder, "in2.txt"),
                new File(testCaseFolder, "in3.txt")
        ));

        SortConfiguration configuration = new SortConfiguration(dataType, sortMode, outputFile, inputFiles);
        FileMerger fileMerger = new SortedFileMerger(configuration);
        fileMerger.merge();

        String fileContent1 = new String(Files.readAllBytes(testFile.toPath()));
        String fileContent2 = new String(Files.readAllBytes(outputFile.toPath()));
        assertEquals(fileContent1, fileContent2);
    }

    @Test
    void testSortedFileMergerCorrectIntegerAscending() throws IOException {
        String testCasePath = testResourcesURL.getPath() + CASE_PREFIX + "1" + File.separator;
        runAndAssertMerge(testCasePath, DataType.INTEGER, SortMode.ASCENDING);
    }

    @Test
    void testSortedFileMergerCorrectStringAscending() throws IOException {
        String testCasePath = testResourcesURL.getPath() + CASE_PREFIX + "2" + File.separator;
        runAndAssertMerge(testCasePath, DataType.STRING, SortMode.ASCENDING);
    }

    @Test
    void testSortedFileMergerCorrectIntegerDescending() throws IOException {
        String testCasePath = testResourcesURL.getPath() + CASE_PREFIX + "3" + File.separator;
        runAndAssertMerge(testCasePath, DataType.INTEGER, SortMode.DESCENDING);
    }

    @Test
    void testSortedFileMergerWithBadOrderStringDescending() throws IOException {
        String testCasePath = testResourcesURL.getPath() + CASE_PREFIX + "4" + File.separator;
        runAndAssertMerge(testCasePath, DataType.STRING, SortMode.DESCENDING);
    }

    @Test
    void testSortedFileMergerStringWithSpace() throws IOException {
        String testCasePath = testResourcesURL.getPath() + CASE_PREFIX + "5" + File.separator;
        runAndAssertMerge(testCasePath, DataType.STRING, SortMode.ASCENDING);
    }

    @Test
    void testSortedFileMergerIntegerWithSpace() throws IOException {
        String testCasePath = testResourcesURL.getPath() + CASE_PREFIX + "6" + File.separator;
        runAndAssertMerge(testCasePath, DataType.INTEGER, SortMode.ASCENDING);
    }

    @Test
    void testSortedFileMergerIntegerWithPartialSortAscending() throws IOException {
        String testCasePath = testResourcesURL.getPath() + CASE_PREFIX + "7" + File.separator;
        runAndAssertMerge(testCasePath, DataType.INTEGER, SortMode.ASCENDING);
    }

    @Test
    void testSortedFileMergerIntegerWithPartialSortDescending() throws IOException {
        String testCasePath = testResourcesURL.getPath() + CASE_PREFIX + "8" + File.separator;
        runAndAssertMerge(testCasePath, DataType.INTEGER, SortMode.DESCENDING);
    }

    @Test
    void testSortedFileMergerStringWithPartialSortDescending() throws IOException {
        String testCasePath = testResourcesURL.getPath() + CASE_PREFIX + "9" + File.separator;
        runAndAssertMerge(testCasePath, DataType.STRING, SortMode.DESCENDING);
    }

    @Test
    void testSortedFileMergerStringWithPartialSortAscending() throws IOException {
        String testCasePath = testResourcesURL.getPath() + CASE_PREFIX + "10" + File.separator;
        runAndAssertMerge(testCasePath, DataType.STRING, SortMode.ASCENDING);
    }

    @Test
    void testSortedFileMergerWithBadDataTypeForIntegerAscending() throws IOException {
        String testCasePath = testResourcesURL.getPath() + CASE_PREFIX + "11" + File.separator;
        runAndAssertMerge(testCasePath, DataType.INTEGER, SortMode.ASCENDING);
    }

    @Test
    void testSortedFileMergerWithBadDataTypeForIntegerDescending() throws IOException {
        String testCasePath = testResourcesURL.getPath() + CASE_PREFIX + "12" + File.separator;
        runAndAssertMerge(testCasePath, DataType.INTEGER, SortMode.DESCENDING);
    }

    @Test
    void testSortedFileMergerWithBadDataTypeForStringAscending() throws IOException {
        String testCasePath = testResourcesURL.getPath() + CASE_PREFIX + "13" + File.separator;
        runAndAssertMerge(testCasePath, DataType.STRING, SortMode.ASCENDING);
    }

    @Test
    void testSortedFileMergerWithBadDataTypeForStringDescending() throws IOException {
        String testCasePath = testResourcesURL.getPath() + CASE_PREFIX + "14" + File.separator;
        runAndAssertMerge(testCasePath, DataType.STRING, SortMode.DESCENDING);
    }

}