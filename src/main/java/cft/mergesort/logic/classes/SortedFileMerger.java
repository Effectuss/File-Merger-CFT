package cft.mergesort.logic.classes;

import cft.mergesort.logic.interfaces.FileMerger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SortedFileMerger implements FileMerger {
    private SortConfiguration configuration;

    public SortedFileMerger(SortConfiguration configuration) {
        this.setSortConfiguration(configuration);
    }

    @Override
    public void merge() throws IOException {
        List<BufferedReader> readers = this.createReaders();

        if (readers.isEmpty()) {
            System.err.println("No input files");
            return;
        }

        List<String> currentLine = new ArrayList<>(readers.size());
        List<String> previousLine = new ArrayList<>(readers.size());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.configuration.getOutputFile()))) {
        } finally {
            closeReaders(readers);
        }
    }


    private List<BufferedReader> createReaders() {
        return configuration.getInputFiles().stream()
                .map(file -> {
                    try {
                        return new BufferedReader(new FileReader(file));
                    } catch (FileNotFoundException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private void closeReaders(List<BufferedReader> readers) {
        for (BufferedReader reader : readers) {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.err.println("Failed to close reader");
            }
        }
    }

    public SortConfiguration getSortConfiguration() {
        return configuration;
    }

    public void setSortConfiguration(SortConfiguration configuration) {
        this.configuration = configuration;
    }

}
