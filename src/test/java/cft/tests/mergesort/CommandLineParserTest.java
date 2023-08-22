package cft.tests.mergesort;


import cft.mergesort.logic.classes.CommandLineParser;
import cft.mergesort.logic.classes.SortConfiguration;
import cft.mergesort.logic.enums.DataType;
import cft.mergesort.logic.enums.SortMode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommandLineParserTest {
    private static URI wayToResult;
    private static Path inputFileOnePath;
    private static Path inputFileTwoPath;
    private static Path inputFileThreePath;
    private static Path outputPath;

    @BeforeAll
    static void setUp() throws URISyntaxException {
        wayToResult = ClassLoader.getSystemResource("test_resources_command_line_parser").toURI();
        inputFileOnePath = Paths.get(Paths.get(wayToResult) + File.separator + "in1.txt");
        inputFileTwoPath = Paths.get(Paths.get(wayToResult) + File.separator + "in2.txt");
        inputFileThreePath = Paths.get(Paths.get(wayToResult) + File.separator + "in3.txt");
        outputPath = Paths.get(Paths.get(wayToResult) + File.separator + "out.txt");
    }

    @Test
    void testCommandLineParserWithValidArguments1() throws IOException {
        String[] args = {"-s",
                "-a",
                outputPath.toString(),
                inputFileOnePath.toString(),
                inputFileTwoPath.toString(),
                inputFileThreePath.toString()};

        SortConfiguration configuration = CommandLineParser.parse(args);

        assertEquals(SortMode.ASCENDING, configuration.getSortMode());
        assertEquals(DataType.STRING, configuration.getDataType());
        assertEquals(outputPath.toFile(), configuration.getOutputFile());
        assertEquals(Arrays.asList(inputFileOnePath.toFile(), inputFileTwoPath.toFile(), inputFileThreePath.toFile()),
                configuration.getInputFiles());
        assertEquals(outputPath.getFileName().toString(), configuration.getOutputFile().getName());
    }

    @Test
    void testCommandLineParserWithValidArguments2() throws IOException {
        String[] args = {"-i",
                "-d",
                outputPath.toString(),
                inputFileOnePath.toString(),
                inputFileTwoPath.toString(),
                inputFileThreePath.toString()};

        SortConfiguration configuration = CommandLineParser.parse(args);

        assertEquals(SortMode.DESCENDING, configuration.getSortMode());
        assertEquals(DataType.INTEGER, configuration.getDataType());
        assertEquals(outputPath.toFile(), configuration.getOutputFile());
        assertEquals(Arrays.asList(inputFileOnePath.toFile(), inputFileTwoPath.toFile(), inputFileThreePath.toFile()),
                configuration.getInputFiles());
        assertEquals(outputPath.getFileName().toString(), configuration.getOutputFile().getName());
    }

    @Test
    void testCommandLineParserWithValidArgumentsDefaultSortMode() throws IOException {
        String[] args = {"-i",
                outputPath.toString(),
                inputFileOnePath.toString(),
                inputFileTwoPath.toString(),
                inputFileThreePath.toString()};

        SortConfiguration configuration = CommandLineParser.parse(args);

        assertEquals(SortMode.ASCENDING, configuration.getSortMode());
        assertEquals(DataType.INTEGER, configuration.getDataType());
        assertEquals(outputPath.toFile(), configuration.getOutputFile());
        assertEquals(Arrays.asList(inputFileOnePath.toFile(), inputFileTwoPath.toFile(), inputFileThreePath.toFile()),
                configuration.getInputFiles());
        assertEquals(outputPath.getFileName().toString(), configuration.getOutputFile().getName());
    }

    @Test
    void testCommandLineParserWithValidArguments4() throws IOException {
        String[] args = {"-d",
                "-s",
                outputPath.toString(),
                inputFileOnePath.toString(),
                inputFileTwoPath.toString(),
                inputFileThreePath.toString()};

        SortConfiguration configuration = CommandLineParser.parse(args);

        assertEquals(SortMode.DESCENDING, configuration.getSortMode());
        assertEquals(DataType.STRING, configuration.getDataType());
        assertEquals(outputPath.toFile(), configuration.getOutputFile());
        assertEquals(Arrays.asList(inputFileOnePath.toFile(), inputFileTwoPath.toFile(), inputFileThreePath.toFile()),
                configuration.getInputFiles());
        assertEquals(outputPath.getFileName().toString(), configuration.getOutputFile().getName());
    }

    @Test
    void testCommandLineParserWithInvalidArguments() throws IOException {
        String[] args = {"-l",
                "-k",
                "-p",
                "-s",
                outputPath.toString(),
                inputFileOnePath.toString()};

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalErrorStream = System.err;
        System.setErr(new PrintStream(outputStream));

        try {
            CommandLineParser.parse(args);

            List<String> errorOutput = Arrays.asList(outputStream.toString().split("\\R"));

            List<String> expectedErrors = Arrays.asList(
                    "The option -l is not supported and ignored.",
                    "The option -k is not supported and ignored.",
                    "The option -p is not supported and ignored."
            );

            assertLinesMatch(expectedErrors, errorOutput);
        } finally {
            System.setErr(originalErrorStream);
        }
    }

    @Test
    void testCommandLineParserWithMissingOutputFile() throws IOException {
        Path outputPath = Paths.get(Paths.get(wayToResult) + File.separator + "not_exist_out.txt");
        String[] args = {"-s",
                "-a",
                outputPath.toString(),
                inputFileOnePath.toString(),
                inputFileTwoPath.toString(),
                inputFileThreePath.toString()};

        SortConfiguration configuration = CommandLineParser.parse(args);
        assertNotNull(configuration.getOutputFile());
        assertTrue(configuration.getOutputFile().exists());
    }

    @Test
    void testExceptionCommandLineParserWithMissingDataTypeOption() {
        String[] args = {"-a",
                outputPath.toString(),
                inputFileOnePath.toString(),
                inputFileTwoPath.toString(),
                inputFileThreePath.toString()};

        assertThrows(IOException.class, () -> CommandLineParser.parse(args));

        try {
            CommandLineParser.parse(args);
        } catch (IOException e) {
            assertEquals("The data type is not specified.", e.getMessage());
        }

    }

    @Test
    void testExceptionCommandLineParserWithoutInputFile() {
        String[] args = {"-s",
                "-a",
                outputPath.toString()};

        assertThrows(IOException.class, () -> CommandLineParser.parse(args));

        try {
            CommandLineParser.parse(args);
        } catch (IOException e) {
            assertEquals("The input files are not specified.", e.getMessage());
        }
    }

    @Test
    void testExceptionCommandLineParserShortInputArguments() {
        String[] args = {"-s", "-a"};

        assertThrows(IOException.class, () -> CommandLineParser.parse(args));

        try {
            CommandLineParser.parse(args);
        } catch (IOException e) {
            assertEquals("Minimum number of arguments is 3", e.getMessage());
        }
    }

    @Test
    void testCommandLineParserOnlyOptions() {
        String[] args = {"-i", "-i", "-s", "-s", "-d"};

        assertThrows(IOException.class, () -> CommandLineParser.parse(args));

        try {
            CommandLineParser.parse(args);
        } catch (IOException e) {
            assertEquals("The output file is not specified.", e.getMessage());
        }
    }
}
