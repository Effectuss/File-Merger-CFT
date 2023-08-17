package cft.mergesort.logic.classes;

import cft.mergesort.logic.interfaces.FileMerger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SortedFileMerger implements FileMerger {
    private SortConfiguration configuration;

    public SortedFileMerger(SortConfiguration configuration) {
        this.setSortConfiguration(configuration);
    }

    @Override
    public void merge() throws IOException {
        List<BufferedReader> readers = this.initReaders();

        if (readers.isEmpty()) {
            return;
        }

        System.out.println("No exit");
    }

    private List<BufferedReader> initReaders() {
        List<BufferedReader> readers = new ArrayList<>();
        List<File> f = new ArrayList<>();
        f.add(new File("in1.txt"));
        f.add(new File("in2.txt"));
        f.add(new File("in3.txt"));
        f.add(new File("in4.txt"));
        for (File file : f) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                readers.add(reader);
            } catch (IOException e) {
                System.err.println("Reader initialization failed for file " + file.getName());
            }
        }

//        for (File file : this.configuration.getInputFiles()) {
//            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//                readers.add(reader);
//            } catch (IOException e) {
//                System.err.println("Reader initialization failed for file " + file.getName());
//            }
//        }

        return readers;
    }

    public SortConfiguration getSortConfiguration() {
        return configuration;
    }

    public void setSortConfiguration(SortConfiguration configuration) {
        this.configuration = configuration;
    }

    private class Element<T extends Comparable<T>> implements Comparable<Element<T>> {
        T value;
        int fileIndex;

        public Element(T value, int fileIndex) {
            this.value = value;
            this.fileIndex = fileIndex;
        }

        @Override
        public int compareTo(Element<T> other) {
            return this.value.compareTo(other.value);
        }
    }
}
