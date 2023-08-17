package cft.mergesort.logic.classes;

import cft.mergesort.logic.enums.DataType;
import cft.mergesort.logic.interfaces.FileMerger;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SortedFileMerger implements FileMerger {
    private SortConfiguration configuration;
    private List<String> currentLine;
    private List<String> previousLine;
    private List<Boolean> stopAlgorithm;

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

        currentLine = new ArrayList<>(Collections.nCopies(readers.size(), null));
        previousLine = new ArrayList<>(Collections.nCopies(readers.size(), null));
        stopAlgorithm = new ArrayList<>(Collections.nCopies(readers.size(), false));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.configuration.getOutputFile()))) {
            readFirstLineEachFile(readers);
            for (String s : currentLine) {
                if (s != null) {
                    writer.write(s);
                    writer.newLine();
                }
            }
        } finally {
            closeReaders(readers);
        }
    }

    private void readFirstLineEachFile(List<BufferedReader> readers) throws IOException {
        for (int i = 0; i < readers.size(); ++i) {
            String firstLine = readers.get(i).readLine();
            if (isValidData(firstLine, i)) {
                currentLine.add(i, firstLine);
                previousLine.add(i, firstLine);
            }
        }
    }

    private boolean isValidData(String line, int index) {
        if (line.contains(" ") || line.isEmpty()) {
            return false;
        } else if (configuration.getDataType() == DataType.INTEGER) {
            try {
                Integer.parseInt(line);
                return true;
            } catch (NumberFormatException e) {
                System.err.println("Data cant be number");
                stopAlgorithm.add(index, true);
                return false;
            }
        }
        return true;
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
