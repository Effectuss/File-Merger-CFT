package cft.mergesort.main;

import cft.mergesort.logic.classes.CommandLineParser;
import cft.mergesort.logic.classes.FileMerger;

import com.beust.jcommander.JCommander;
import org.apache.commons.cli.*;


public class Main {
    public static void main(String[] args) {
        try {
            FileMerger fileMerger = new FileMerger();
            CommandLineParser CommandLineParser = new CommandLineParser();
            JCommander.newBuilder()
                    .addObject(CommandLineParser)
                    .build()
                    .parse(args);

            System.out.println(CommandLineParser.getSortMode());
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }
    }
}
