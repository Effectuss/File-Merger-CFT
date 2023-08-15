package cft.mergesort.main;

import cft.mergesort.logic.classes.CommandLineParser;
import cft.mergesort.logic.classes.SortConfiguration;

public class Main {
    public static void main(String[] args) {
        try {
            SortConfiguration configuration = CommandLineParser.parse(args);

        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }
}
