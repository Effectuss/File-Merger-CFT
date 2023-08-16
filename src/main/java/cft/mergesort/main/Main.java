package cft.mergesort.main;

import cft.mergesort.logic.classes.CommandLineParser;
import cft.mergesort.logic.classes.SortConfiguration;
import cft.mergesort.logic.exceptions.CommandLineParserException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            SortConfiguration configuration = CommandLineParser.parse(args);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}