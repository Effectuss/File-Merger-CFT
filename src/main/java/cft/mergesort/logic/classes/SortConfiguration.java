package cft.mergesort.logic.classes;

import cft.mergesort.logic.enums.SortMode;
import cft.mergesort.logic.enums.DataType;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class SortConfiguration {
    private DataType dataType;
    private SortMode sortMode;
    private File outputFile;
    private HashMap<File, Boolean> inputFiles;

    public SortConfiguration() {
        this.sortMode = SortMode.ASCENDING;
        this.dataType = null;
        this.outputFile = null;
        this.inputFiles = new HashMap<>();
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public SortMode getSortMode() {
        return sortMode;
    }

    public void setSortMode(SortMode sortMode) {
        this.sortMode = sortMode;
    }

    public File getOutputFile() {
        return this.outputFile;
    }

    public void setOutputFile(File outputFile) throws IOException {
        if (outputFile.createNewFile()) {
            System.out.println("The output file does not exist, but the file " + outputFile.getName() + " was created.");
        }
        this.outputFile = outputFile;
    }

    public HashMap<File, Boolean> getInputFiles() {
        return this.inputFiles;
    }

    public void addInputFile(File inputFile) throws IOException {
        if (inputFile.exists()) {
            this.inputFiles.put(inputFile, true);
        } else {
            throw new IOException("The input file " + inputFile.getName() + " does not exist.");
        }
    }

    public void setInputFiles(HashMap<File, Boolean> inputFiles) {
        this.inputFiles = inputFiles;
    }

    @Override
    public String toString() {
        return "SortConfiguration{" +
                "dataType=" + dataType +
                ", sortMode=" + sortMode +
                ", outputFile=" + outputFile +
                ", inputFiles=" + inputFiles +
                '}';
    }
}
