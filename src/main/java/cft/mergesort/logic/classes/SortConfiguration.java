package cft.mergesort.logic.classes;

import cft.mergesort.logic.enums.SortMode;
import cft.mergesort.logic.enums.DataType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SortConfiguration {
    private DataType dataType;
    private SortMode sortMode;
    private File outputFile;
    private List<File> inputFiles;
    private List<File> notFoundInputFiles;

    public SortConfiguration() {
        this.sortMode = SortMode.ASCENDING;
        this.dataType = null;
        this.outputFile = null;
        this.inputFiles = new ArrayList<>();
        this.notFoundInputFiles = new ArrayList<>();
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
        return outputFile;
    }

    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

    public List<File> getInputFiles() {
        return inputFiles;
    }

    public void setInputFiles(List<File> inputFiles) {
        this.inputFiles = inputFiles;
    }

    public List<File> getNotFoundInputFiles() {
        return notFoundInputFiles;
    }

    public void setNotFoundInputFiles(List<File> notFoundInputFiles) {
        this.notFoundInputFiles = notFoundInputFiles;
    }
}
