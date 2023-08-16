package cft.mergesort.logic.classes;

import cft.mergesort.logic.enums.DataType;
import cft.mergesort.logic.enums.SortMode;
import cft.mergesort.logic.exceptions.CommandLineParserException;

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

        int lenOptions = parsingArgumentStartsWithDash(args, configuration);


        if (configuration.getDataType() == null) {
            throw new CommandLineParserException("DataType is not -s for string or -i for integer");
        }

        return configuration;
    }

    static int parsingArgumentStartsWithDash(String[] args, SortConfiguration configuration) {
        int count = 0;

        while (args[count].startsWith("-")) {
            parseOption(configuration, args[count++]);
        }

        return count;
    }

    static void parseOption(SortConfiguration configuration, String option) {
        if (DATA_TYPE_HASH_MAP.containsKey(option)) {
            configuration.setDataType(DATA_TYPE_HASH_MAP.get(option));
        } else if (SORT_MODE_HASH_MAP.containsKey(option)) {
            configuration.setSortMode(SORT_MODE_HASH_MAP.get(option));
        } else {
            System.err.println("The option " + option + " is not supported.");
        }
    }

}