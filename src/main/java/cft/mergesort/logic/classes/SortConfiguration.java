package cft.mergesort.logic.classes;

import cft.mergesort.logic.enums.SortMode;
import cft.mergesort.logic.enums.DataType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SortConfiguration {
    private DataType dataType;
    private SortMode sortMode;
    private File outputFile;
    private List<File> inputFiles;

    public SortConfiguration() {
        setSortMode(SortMode.ASCENDING);
        this.setDataType(null);
        this.outputFile = null;
        this.setInputFiles(new ArrayList<>());
    }

    public SortConfiguration(DataType dataType, SortMode sortMode, File outputFile, List<File> inputFiles) throws IOException {
        setSortMode(sortMode);
        this.setDataType(dataType);
        this.setOutputFile(outputFile);
        this.setInputFiles(inputFiles);
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

    public List<File> getInputFiles() {
        return this.inputFiles;
    }

    public void addInputFile(File inputFile) throws IOException {
        if (inputFile.exists()) {
            this.inputFiles.add(inputFile);
        } else {
            throw new IOException("The input file " + inputFile.getName() + " does not exist.");
        }
    }

    public void setInputFiles(List<File> inputFiles) {
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
