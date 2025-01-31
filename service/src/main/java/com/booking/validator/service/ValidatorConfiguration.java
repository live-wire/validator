package com.booking.validator.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Created by psalimov on 9/16/16.
 */
public class ValidatorConfiguration {

    public static class DataSource {

        private String name;
        private String type;

        private Map<String,String> configuration;

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public Map<String, String> getConfiguration() {
            return configuration;
        }

    }

    public static class TaskSupplier {

        private String type;
        private Map<String,String> configuration;

        public String getType() {
            return type;
        }

        public Map<String, String> getConfiguration() {
            return configuration;
        }
    }

    public static class Reporter {

        private String type;
        private Map<String,String> configuration;

        public String getType() {
            return type;
        }

        public Map<String, String> getConfiguration() {
            return configuration;
        }
    }

    public static class RetryPolicy {

        @JsonProperty("delay")
        private long[] delay = {3000, 6000, 12000};

        @JsonProperty("queue_size")
        private int queueSize = 1000;

    }

    public static ValidatorConfiguration fromFile(String path) throws IOException {

        if (path == null) throw new IllegalArgumentException("The path to file to is missing");

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        try (InputStream in = Files.newInputStream(Paths.get(path))){

            return mapper.readValue( in , ValidatorConfiguration.class);

        }

    }

    @JsonProperty("data_sources")
    private Iterable<DataSource> dataSources;

    @JsonProperty("task_supplier")
    private TaskSupplier taskSupplier;

    @JsonProperty("reporter")
    private Reporter reporter;

    @JsonProperty("retry_policy")
    private RetryPolicy retryPolicy = new RetryPolicy();

    public Iterable<DataSource> getDataSources() {
        return dataSources;
    }

    public TaskSupplier getTaskSupplier() {
        return taskSupplier;
    }

    public Reporter getReporter() { return reporter; }

    public com.booking.validator.service.RetryPolicy getRetryPolicy() {
        return new com.booking.validator.service.RetryPolicy(
                retryPolicy.delay, retryPolicy.queueSize, retryPolicy.delay.length
        );
    }

}
