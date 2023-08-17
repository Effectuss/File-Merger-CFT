package cft.mergesort.main;

import cft.mergesort.logic.classes.CommandLineParser;
import cft.mergesort.logic.classes.SortConfiguration;
import cft.mergesort.logic.classes.SortedFileMerger;
import cft.mergesort.logic.interfaces.FileMerger;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            SortConfiguration configuration = CommandLineParser.parse(args);
            System.out.println(configuration);
            FileMerger merger = new SortedFileMerger(configuration);
            merger.merge();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}