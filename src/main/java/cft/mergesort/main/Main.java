package cft.mergesort.main;

import cft.mergesort.logic.classes.CommandLineParser;
import cft.mergesort.logic.classes.FileMerger;


public class Main {
    public static void main(String[] args) {
        FileMerger fileMerger = new FileMerger();
        CommandLineParser parser = new CommandLineParser(args);
        System.out.println("Hello World!");
    }
}
