package cft.mergesort.logic.classes;

import cft.mergesort.logic.enums.DataType;
import cft.mergesort.logic.enums.SortMode;
import cft.mergesort.logic.interfaces.FileMerger;

import java.io.*;
import java.util.*;
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
            throw new IOException("There are no files to read");
        }

        initDefaultValueForArrays(readers.size());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.configuration.getOutputFile()))) {
            String line = "";
            while (line != null) {
                fillCurrentLine(readers);
                line = findValueForWrite();
                if (line != null) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("The file cant be written" + e.getMessage());
        } finally {
            closeReaders(readers);
        }
    }

    private String findValueForWrite() {
        String bestValue = null;
        int bestElementIndex = -1;

        for (int i = 0; i < currentLine.size(); ++i) {
            String currentValue = currentLine.get(i);

            if (currentValue == null) {
                continue;
            }

            if (bestValue == null || shouldUpdateBestValue(currentValue, bestValue)) {
                bestValue = currentValue;
                bestElementIndex = i;
            }
        }

        if (bestValue != null) {
            currentLine.set(bestElementIndex, null);
        }

        return bestValue;
    }

    private boolean shouldUpdateBestValue(String currentValue, String bestValue) {
        int comparisonResult = 0;

        if (configuration.getDataType() == DataType.STRING) {
            comparisonResult = currentValue.compareTo(bestValue);
        } else if (configuration.getDataType() == DataType.INTEGER) {
            comparisonResult = Integer.compare(Integer.parseInt(currentValue), Integer.parseInt(bestValue));
        }

        return (configuration.getSortMode() == SortMode.ASCENDING && comparisonResult < 0)
                || (configuration.getSortMode() == SortMode.DESCENDING && comparisonResult > 0);
    }

    private void fillCurrentLine(List<BufferedReader> readers) throws IOException {
        for (int i = 0; i < readers.size(); ++i) {
            String line = null;

            if (currentLine.get(i) != null) continue;

            while (!stopAlgorithm.get(i)) {
                if ((line = readers.get(i).readLine()) == null) {
                    stopAlgorithm.set(i, true);
                } else if (isValidData(line, i)) {
                    currentLine.set(i, line);
                    break;
                }
            }

            if (line != null && !isCorrectDataInFile(i)) {
                stopAlgorithm.set(i, true);
            }
        }
    }

    private boolean isCorrectDataInFile(int index) {
        if (previousLine.get(index) == null || isCorrectDataOrder(currentLine.get(index), previousLine.get(index), index)) {
            previousLine.set(index, currentLine.get(index));
            return true;
        }
        currentLine.set(index, null);
        System.err.println("The sort order in file " + configuration.getInputFiles().get(index) + " is incorrect, stop reading data");
        return false;
    }

    private boolean isCorrectDataOrder(String currentLine, String previousLine, int index) {
        DataType dataType = configuration.getDataType();
        SortMode sortMode = configuration.getSortMode();

        if (dataType == DataType.INTEGER) {
            try {
                int currentInt = Integer.parseInt(currentLine);
                int previousInt = Integer.parseInt(previousLine);
                return sortMode == SortMode.ASCENDING ? currentInt >= previousInt : currentInt <= previousInt;
            } catch (NumberFormatException e) {
                System.err.println("The data in file " + configuration.getInputFiles().get(index) + " can't be a number");
                return false;
            }
        } else if (dataType == DataType.STRING) {
            int comparisonResult = currentLine.compareTo(previousLine);
            return sortMode == SortMode.ASCENDING ? comparisonResult >= 0 : comparisonResult <= 0;
        }

        return true;
    }

    private boolean isValidData(String line, int index) {
        if (line.contains(" ") || line.isEmpty()) {
            return false;
        } else if (configuration.getDataType() == DataType.INTEGER) {
            try {
                Integer.parseInt(line);
                return true;
            } catch (NumberFormatException e) {
                stopAlgorithm.set(index, true);
                return false;
            }
        }
        return true;
    }

    private void initDefaultValueForArrays(int size) {
        currentLine = new ArrayList<>(Collections.nCopies(size, null));
        previousLine = new ArrayList<>(Collections.nCopies(size, null));
        stopAlgorithm = new ArrayList<>(Collections.nCopies(size, false));
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
