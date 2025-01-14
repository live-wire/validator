package com.booking.validator.utils;

/**
 * Created by psalimov on 10/3/16.
 */
public class CommandLineArguments {

    private String configurationPath;

    public CommandLineArguments(String... args){

        for (int i=0; i<args.length; i++){
            if ("--config".equals(args[i])){
                configurationPath = args[++i];
            }
        }

        if (configurationPath == null || configurationPath.isEmpty()) throw new RuntimeException("--config file path is not provided");

    }

    public String getConfigurationPath() {

        return configurationPath;

    }

}
