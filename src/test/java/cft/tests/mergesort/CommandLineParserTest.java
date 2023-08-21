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
    private static URI WAY_TO_RESULT;
    private static Path INPUT_FILE_ONE_PATH;
    private static Path INPUT_FILE_TWO_PATH;
    private static Path INPUT_FILE_THREE_PATH;
    private static Path OUTPUT_PATH;

    @BeforeAll
    static void setUp() throws URISyntaxException {
        WAY_TO_RESULT = ClassLoader.getSystemResource("test_resources_command_line_parser").toURI();
        INPUT_FILE_ONE_PATH = Paths.get(Paths.get(WAY_TO_RESULT) + File.separator + "in1.txt");
        INPUT_FILE_TWO_PATH = Paths.get(Paths.get(WAY_TO_RESULT) + File.separator + "in2.txt");
        INPUT_FILE_THREE_PATH = Paths.get(Paths.get(WAY_TO_RESULT) + File.separator + "in3.txt");
        OUTPUT_PATH = Paths.get(Paths.get(WAY_TO_RESULT) + File.separator + "out.txt");
    }

    @Test
    void testCommandLineParserWithValidArguments1() throws IOException {
        String[] args = {"-s",
                "-a",
                OUTPUT_PATH.toString(),
                INPUT_FILE_ONE_PATH.toString(),
                INPUT_FILE_TWO_PATH.toString(),
                INPUT_FILE_THREE_PATH.toString()};

        SortConfiguration configuration = CommandLineParser.parse(args);

        assertEquals(SortMode.ASCENDING, configuration.getSortMode());
        assertEquals(DataType.STRING, configuration.getDataType());
        assertEquals(OUTPUT_PATH.toFile(), configuration.getOutputFile());
        assertEquals(Arrays.asList(INPUT_FILE_ONE_PATH.toFile(), INPUT_FILE_TWO_PATH.toFile(), INPUT_FILE_THREE_PATH.toFile()),
                configuration.getInputFiles());
        assertEquals(OUTPUT_PATH.getFileName().toString(), configuration.getOutputFile().getName());
    }

    @Test
    void testCommandLineParserWithValidArguments2() throws IOException {
        String[] args = {"-i",
                "-d",
                OUTPUT_PATH.toString(),
                INPUT_FILE_ONE_PATH.toString(),
                INPUT_FILE_TWO_PATH.toString(),
                INPUT_FILE_THREE_PATH.toString()};

        SortConfiguration configuration = CommandLineParser.parse(args);

        assertEquals(SortMode.DESCENDING, configuration.getSortMode());
        assertEquals(DataType.INTEGER, configuration.getDataType());
        assertEquals(OUTPUT_PATH.toFile(), configuration.getOutputFile());
        assertEquals(Arrays.asList(INPUT_FILE_ONE_PATH.toFile(), INPUT_FILE_TWO_PATH.toFile(), INPUT_FILE_THREE_PATH.toFile()),
                configuration.getInputFiles());
        assertEquals(OUTPUT_PATH.getFileName().toString(), configuration.getOutputFile().getName());
    }

    @Test
    void testCommandLineParserWithValidArgumentsDefaultSortMode() throws IOException {
        String[] args = {"-i",
                OUTPUT_PATH.toString(),
                INPUT_FILE_ONE_PATH.toString(),
                INPUT_FILE_TWO_PATH.toString(),
                INPUT_FILE_THREE_PATH.toString()};

        SortConfiguration configuration = CommandLineParser.parse(args);

        assertEquals(SortMode.ASCENDING, configuration.getSortMode());
        assertEquals(DataType.INTEGER, configuration.getDataType());
        assertEquals(OUTPUT_PATH.toFile(), configuration.getOutputFile());
        assertEquals(Arrays.asList(INPUT_FILE_ONE_PATH.toFile(), INPUT_FILE_TWO_PATH.toFile(), INPUT_FILE_THREE_PATH.toFile()),
                configuration.getInputFiles());
        assertEquals(OUTPUT_PATH.getFileName().toString(), configuration.getOutputFile().getName());
    }

    @Test
    void testCommandLineParserWithValidArguments4() throws IOException {
        String[] args = {"-d",
                "-s",
                OUTPUT_PATH.toString(),
                INPUT_FILE_ONE_PATH.toString(),
                INPUT_FILE_TWO_PATH.toString(),
                INPUT_FILE_THREE_PATH.toString()};

        SortConfiguration configuration = CommandLineParser.parse(args);

        assertEquals(SortMode.DESCENDING, configuration.getSortMode());
        assertEquals(DataType.STRING, configuration.getDataType());
        assertEquals(OUTPUT_PATH.toFile(), configuration.getOutputFile());
        assertEquals(Arrays.asList(INPUT_FILE_ONE_PATH.toFile(), INPUT_FILE_TWO_PATH.toFile(), INPUT_FILE_THREE_PATH.toFile()),
                configuration.getInputFiles());
        assertEquals(OUTPUT_PATH.getFileName().toString(), configuration.getOutputFile().getName());
    }

    @Test
    void testCommandLineParserWithInvalidArguments() throws IOException {
        String[] args = {"-l",
                "-k",
                "-p",
                "-s",
                OUTPUT_PATH.toString(),
                INPUT_FILE_ONE_PATH.toString()};

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
        Path outputPath = Paths.get(Paths.get(WAY_TO_RESULT) + File.separator + "not_exist_out.txt");
        String[] args = {"-s",
                "-a",
                outputPath.toString(),
                INPUT_FILE_ONE_PATH.toString(),
                INPUT_FILE_TWO_PATH.toString(),
                INPUT_FILE_THREE_PATH.toString()};

        SortConfiguration configuration = CommandLineParser.parse(args);
        assertNotNull(configuration.getOutputFile());
        assertTrue(configuration.getOutputFile().exists());
    }

    @Test
    void testExceptionCommandLineParserWithMissingDataTypeOption() {
        String[] args = {"-a",
                OUTPUT_PATH.toString(),
                INPUT_FILE_ONE_PATH.toString(),
                INPUT_FILE_TWO_PATH.toString(),
                INPUT_FILE_THREE_PATH.toString()};

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
                OUTPUT_PATH.toString()};

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
