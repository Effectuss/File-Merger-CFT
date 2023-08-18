package cft.tests.mergesort;

import cft.mergesort.logic.classes.SortConfiguration;
import cft.mergesort.logic.classes.SortedFileMerger;
import cft.mergesort.logic.enums.DataType;
import cft.mergesort.logic.enums.SortMode;
import cft.mergesort.logic.interfaces.FileMerger;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortedFileMergerTest {
    private static final URL wayToRes = CommandLineParserTest.class
            .getClassLoader()
            .getResource("test_resources_sorted_file_merger" + File.separator);

    @Test
    void testSortedFileMergerCorrectIntegerAscending() throws IOException {
        String case1 = wayToRes.getPath() + "case1" + File.separator;
        File outputFile = new File(case1 + "test.txt");
        List<File> inputFiles = new ArrayList<>(Arrays.asList(
                new File(case1 + "in1.txt"),
                new File(case1 + "in2.txt"),
                new File(case1 + "in3.txt")
        ));
        SortConfiguration configuration = new SortConfiguration(DataType.INTEGER,
                SortMode.ASCENDING,
                outputFile, inputFiles);
        FileMerger fileMerger = new SortedFileMerger(configuration);
    }
}