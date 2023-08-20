package cft.mergesort.logic.classes;

import cft.mergesort.logic.enums.DataType;
import cft.mergesort.logic.enums.SortMode;
import cft.mergesort.logic.exceptions.CommandLineParserException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class CommandLineParser {
    private static final int MIN_ARGUMENTS = 3;
    private static final HashMap<String, DataType> DATA_TYPE_HASH_MAP = new HashMap<>() {
        {
            put("-s", DataType.STRING);
            put("-i", DataType.INTEGER);
        }
    };
    private static final HashMap<String, SortMode> SORT_MODE_HASH_MAP = new HashMap<>() {
        {
            put("-a", SortMode.ASCENDING);
            put("-d", SortMode.DESCENDING);
        }
    };

    static public SortConfiguration parse(String[] args) throws IOException {
        if (args.length < MIN_ARGUMENTS) {
            throw new CommandLineParserException("Minimum number of arguments is " + MIN_ARGUMENTS);
        }

        SortConfiguration configuration = new SortConfiguration();

        int currenIndex = parsingArgumentStartsWithDash(args, configuration);

        while (++currenIndex < args.length && configuration.getDataType() != null) {
            if (configuration.getOutputFile() == null) {
                configuration.setOutputFile(new File(args[currenIndex]));
            } else {
                try {
                    configuration.addInputFile(new File(args[currenIndex]));
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }

        checkRequiredArguments(configuration);

        return configuration;
    }

    private static int parsingArgumentStartsWithDash(String[] args, SortConfiguration configuration) {
        int count = 0;

        while (count < args.length && args[count].startsWith("-")) {
            try {
                parseOption(configuration, args[count++]);
            } catch (CommandLineParserException e) {
                System.err.println(e.getMessage());
            }
        }

        return count - 1;
    }

    private static void parseOption(SortConfiguration configuration, String option) throws CommandLineParserException {
        if (DATA_TYPE_HASH_MAP.containsKey(option)) {
            configuration.setDataType(DATA_TYPE_HASH_MAP.get(option));
        } else if (SORT_MODE_HASH_MAP.containsKey(option)) {
            configuration.setSortMode(SORT_MODE_HASH_MAP.get(option));
        } else {
            throw new CommandLineParserException("The option " + option + " is not supported and ignored.");
        }
    }

    private static void checkRequiredArguments(SortConfiguration configuration) throws CommandLineParserException {
        if (configuration.getDataType() == null) {
            throw new CommandLineParserException("The data type is not specified.");
        } else if (configuration.getOutputFile() == null) {
            throw new CommandLineParserException("The output file is not specified.");
        } else if (configuration.getInputFiles().isEmpty()) {
            throw new CommandLineParserException("The input files are not specified.");
        }
    }
}
