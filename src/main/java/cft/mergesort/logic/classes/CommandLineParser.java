package cft.mergesort.logic.classes;

import cft.mergesort.logic.enums.DataType;
import cft.mergesort.logic.enums.SortMode;
import com.beust.jcommander.*;

import java.util.HashMap;
import java.util.Map;

public class CommandLineParser {

    @Parameter(names = {"-a"}, arity = 0, description = "Sort ascending mode.", validateWith = UniqueOptionValidator.class)
    private boolean ascending;

    @Parameter(names = {"-d"}, arity = 0, description = "Sort descending mode.", validateWith = UniqueOptionValidator.class)
    private boolean descending;

    @Parameter(names = {"-s", "-i"}, arity = 0, description = "Type of data for sort")
    private boolean dataType;

    private static CommandLineParser instance;

    public CommandLineParser() {
        instance = this;
    }

    public static CommandLineParser getInstance() {
        return instance;
    }

    public SortMode getSortMode() {
        if (ascending) {
            return SortMode.ASCENDING;
        } else if (descending) {
            return SortMode.DESCENDING;
        }
        return SortMode.ASCENDING;
    }

    public static class UniqueOptionValidator implements IParameterValidator {
        @Override
        public void validate(String name, String value) throws ParameterException {
            if (name.equals("-a") && CommandLineParser.getInstance().descending) {
                throw new ParameterException("Option -a cannot be used together with option -d");
            } else if (name.equals("-d") && CommandLineParser.getInstance().ascending) {
                throw new ParameterException("Option -d cannot be used together with option -a");
            } else if (name.equals("-a") && CommandLineParser.getInstance().ascending) {
                throw new ParameterException("Option -a cannot be used together with option -a");
            } else if (name.equals("-d") && CommandLineParser.getInstance().descending) {
                throw new ParameterException("Option -d cannot be used together with option -d");
            }
        }
    }
}