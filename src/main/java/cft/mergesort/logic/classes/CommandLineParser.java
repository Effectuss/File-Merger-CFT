package cft.mergesort.logic.classes;

import cft.mergesort.logic.enums.DataType;
import cft.mergesort.logic.enums.SortMode;

public class CommandLineParser {
    private SortMode sortMode = SortMode.ASCENDING;
    private DataType dataType;
    private String outFileName;
    private String[] inputFileName;

    public CommandLineParser(String[] args) {

    }
}
