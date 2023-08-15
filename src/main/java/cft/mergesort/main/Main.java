package cft.mergesort.main;

import cft.mergesort.logic.classes.CommandLineParser;
import cft.mergesort.logic.classes.FileMerger;

import com.beust.jcommander.JCommander;


public class Main {
    public static void main(String[] args) {
        try {
            FileMerger fileMerger = new FileMerger();
            CommandLineParser commandLineParser = new CommandLineParser();
            JCommander.newBuilder()
                    .addObject(commandLineParser)
                    .build()
                    .parse(args);

            System.out.println(commandLineParser.getSortMode());
            System.out.println(commandLineParser.getDataType());
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }
}
