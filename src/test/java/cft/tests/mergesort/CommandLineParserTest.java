package cft.tests.mergesort;


import cft.mergesort.logic.classes.CommandLineParser;
import cft.mergesort.logic.classes.SortConfiguration;
import cft.mergesort.logic.enums.DataType;
import cft.mergesort.logic.enums.SortMode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;


public class CommandLineParserTest {
    private static File inputFileOne;
    private static File inputFileTwo;
    private static File inputFileThree;
    private static File outputFile;

    @BeforeAll
    static void setUp() {
        URL inputFileOneUrl = CommandLineParserTest.class
                .getClassLoader()
                .getResource("test_resources_command_line_parser/in1.txt");
        URL inputFileTwoUrl = CommandLineParserTest.class
                .getClassLoader()
                .getResource("test_resources_command_line_parser/in2.txt");
        URL inputFileThreeUrl = CommandLineParserTest.class
                .getClassLoader()
                .getResource("test_resources_command_line_parser/in3.txt");
        URL outputFileUrl = CommandLineParserTest.class
                .getClassLoader()
                .getResource("test_resources_command_line_parser/out.txt");

        Path inputFileOnePath = Paths.get(inputFileOneUrl.getPath());
        Path inputFileTwoPath = Paths.get(inputFileTwoUrl.getPath());
        Path inputFileThreePath = Paths.get(inputFileThreeUrl.getPath());
        Path outputPath = Paths.get(outputFileUrl.getPath());

        inputFileOne = inputFileOnePath.toFile();
        inputFileTwo = inputFileTwoPath.toFile();
        inputFileThree = inputFileThreePath.toFile();
        outputFile = outputPath.toFile();
    }

    @Test
    void testCommandLineParserWithValidArguments1() throws IOException {
        String[] args = {"-s",
                "-a",
                outputFile.getPath(),
                inputFileOne.getPath(),
                inputFileTwo.getPath(),
                inputFileThree.getPath()};

        SortConfiguration configuration = CommandLineParser.parse(args);

        assertEquals(SortMode.ASCENDING, configuration.getSortMode());
        assertEquals(DataType.STRING, configuration.getDataType());
        assertEquals(outputFile, configuration.getOutputFile());
        assertTrue(configuration.getInputFiles().containsKey(inputFileOne));
        assertTrue(configuration.getInputFiles().containsKey(inputFileTwo));
        assertTrue(configuration.getInputFiles().containsKey(inputFileThree));
        assertEquals(outputFile.getName(), configuration.getOutputFile().getName());
    }

    @Test
    void testCommandLineParserWithValidArguments2() throws IOException {
        String[] args = {"-i",
                "-d",
                outputFile.getPath(),
                inputFileOne.getPath(),
                inputFileTwo.getPath(),
                inputFileThree.getPath()};

        SortConfiguration configuration = CommandLineParser.parse(args);

        assertEquals(SortMode.DESCENDING, configuration.getSortMode());
        assertEquals(DataType.INTEGER, configuration.getDataType());
        assertEquals(outputFile, configuration.getOutputFile());
        assertTrue(configuration.getInputFiles().containsKey(inputFileOne));
        assertTrue(configuration.getInputFiles().containsKey(inputFileTwo));
        assertTrue(configuration.getInputFiles().containsKey(inputFileThree));
        assertEquals(outputFile.getName(), configuration.getOutputFile().getName());
    }

    @Test
    void testCommandLineParserWithValidArguments3() throws IOException {
        String[] args = {"-i",
                outputFile.getPath(),
                inputFileOne.getPath(),
                inputFileTwo.getPath(),
                inputFileThree.getPath()};

        SortConfiguration configuration = CommandLineParser.parse(args);

        assertEquals(SortMode.ASCENDING, configuration.getSortMode());
        assertEquals(DataType.INTEGER, configuration.getDataType());
        assertEquals(outputFile, configuration.getOutputFile());
        assertTrue(configuration.getInputFiles().containsKey(inputFileOne));
        assertTrue(configuration.getInputFiles().containsKey(inputFileTwo));
        assertTrue(configuration.getInputFiles().containsKey(inputFileThree));
        assertEquals(outputFile.getName(), configuration.getOutputFile().getName());
    }

    @Test
    void testCommandLineParserWithValidArguments4() throws IOException {
        String[] args = {"-d",
                "-s",
                outputFile.getPath(),
                inputFileOne.getPath(),
                inputFileTwo.getPath(),
                inputFileThree.getPath()};

        SortConfiguration configuration = CommandLineParser.parse(args);

        assertEquals(SortMode.DESCENDING, configuration.getSortMode());
        assertEquals(DataType.STRING, configuration.getDataType());
        assertEquals(outputFile, configuration.getOutputFile());
        assertTrue(configuration.getInputFiles().containsKey(inputFileOne));
        assertTrue(configuration.getInputFiles().containsKey(inputFileTwo));
        assertTrue(configuration.getInputFiles().containsKey(inputFileThree));
        assertEquals(outputFile.getName(), configuration.getOutputFile().getName());
    }

    @Test
    void testCommandLineParserWithInvalidArguments() throws IOException {
        String[] args = {"-l",
                "-k",
                "-p",
                "-s",
                outputFile.getPath(),
                inputFileOne.getPath()};

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalErrorStream = System.err;
        System.setErr(new PrintStream(outputStream));

        try {
            CommandLineParser.parse(args);

            List<String> errorOutput = Arrays.asList(outputStream.toString().split("\\R"));

            List<String> expectedErrors = Arrays.asList(
                    "The option -l is not supported.",
                    "The option -k is not supported.",
                    "The option -p is not supported."
            );

            assertLinesMatch(expectedErrors, errorOutput);
        } finally {
            System.setErr(originalErrorStream);
        }
    }

    @Test
    void testCommandLineParserWithMissingOutputFile() {

    }

    @Test
    void testCommandLineParserWithMissingOption() {

    }

    @Test
    void testCommandLineParserWithInvalidOption() {

    }

    @Test
    void testCommandLineParserWithNotExistingInputFile() {

    }

}