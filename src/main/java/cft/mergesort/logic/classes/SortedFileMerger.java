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
            System.err.println("No input files");
            return;
        }

        initDefaultValueForArrays(readers.size());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.configuration.getOutputFile()))) {
            while (true) {
                fillCurrentLine(readers);
                String line = findBestLine();
                if (line == null) break;
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("The file cant be written" + e.getMessage());
        } finally {
            closeReaders(readers);
        }
    }

    private String findBestLine() {
        String bestValue = null;
        int bestElementIndex = -1;

        SortMode sortMode = configuration.getSortMode();
        DataType dataType = configuration.getDataType();

        for (int i = 0; i < currentLine.size(); ++i) {
            String currentValue = currentLine.get(i);

            if (currentValue == null) {
                continue;
            }

            if (bestValue == null || shouldUpdateBestValue(currentValue, bestValue, sortMode, dataType)) {
                bestValue = currentValue;
                bestElementIndex = i;
            }
        }

        if (bestValue != null) currentLine.set(bestElementIndex, null);
        return bestValue;
    }

    private boolean shouldUpdateBestValue(String currentValue, String bestValue, SortMode sortMode, DataType dataType) {
        int comparisonResult = 0;

        if (dataType == DataType.STRING) {
            comparisonResult = currentValue.compareTo(bestValue);
        } else if (dataType == DataType.INTEGER) {
            comparisonResult = Integer.compare(Integer.parseInt(currentValue), Integer.parseInt(bestValue));
        }

        return (sortMode == SortMode.ASCENDING && comparisonResult < 0)
                || (sortMode == SortMode.DESCENDING && comparisonResult > 0);
    }

    private void fillCurrentLine(List<BufferedReader> readers) throws IOException {
        for (int i = 0; i < readers.size(); ++i) {
            if (currentLine.get(i) != null) continue;
            String line = null;
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
        if (configuration.getDataType() == DataType.INTEGER) {
            try {
                int currentInt = Integer.parseInt(currentLine);
                int previousInt = Integer.parseInt(previousLine);
                if (configuration.getSortMode() == SortMode.ASCENDING) {
                    return currentInt >= previousInt;
                } else if (configuration.getSortMode() == SortMode.DESCENDING) {
                    return currentInt <= previousInt;
                }
            } catch (NumberFormatException e) {
                System.err.println("The data in file " + configuration.getInputFiles().get(index) + " cant be number");
                return false;
            }
        } else if (configuration.getDataType() == DataType.STRING) {
            if (configuration.getSortMode() == SortMode.ASCENDING) {
                return currentLine.compareTo(previousLine) >= 0;
            } else if (configuration.getSortMode() == SortMode.DESCENDING) {
                return currentLine.compareTo(previousLine) <= 0;
            }
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
                stopAlgorithm.add(index, true);
                return false;
            }
        }
        return true;
    }

    private void initDefaultValueForArrays(int size) {
        currentLine = new ArrayList<>();
        previousLine = new ArrayList<>();
        stopAlgorithm = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            stopAlgorithm.add(false);
            previousLine.add(null);
            currentLine.add(null);
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
