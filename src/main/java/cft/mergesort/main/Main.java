package cft.mergesort.main;

import cft.mergesort.logic.classes.CommandLineParser;
import cft.mergesort.logic.classes.SortConfiguration;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            SortConfiguration configuration = CommandLineParser.parse(args);
            System.out.println(configuration);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}