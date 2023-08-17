package cft.mergesort.logic.interfaces;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileMerger {
    void merge() throws IOException;
}
