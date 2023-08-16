package cft.mergesort.main;

import cft.mergesort.logic.classes.CommandLineParser;
import cft.mergesort.logic.classes.SortConfiguration;

public class Main {
    public static void main(String[] args) {
        try {
            SortConfiguration configuration = CommandLineParser.parse(args);
            System.out.println(configuration.toString());
        } catch (Throwable e) {
            System.err.println(e.getMessage());
        }
    }
}