package com.foobarquix.batch;

import com.foobarquix.core.NumberTransformationEngine;

import java.nio.file.Path;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

/**
author : Wassim MNASSER
*/
@Configuration
@EnableBatchProcessing
@EnableConfigurationProperties(BatchSettings.class)
@Profile("batch")
public class BatchJobConfig {

    @Bean
    public FileNumberLineReader reader(@Value("${batch.in}") Resource input) {
        return new FileNumberLineReader(input);
    }

    @Bean
    public TransformItemProcessor processor(NumberTransformationEngine engine) {
        return new TransformItemProcessor(engine);
    }

    @Bean
    public ResultLineWriter writer(@Value("${batch.out}") String outputFile) {
        return new ResultLineWriter(Path.of(outputFile));
    }

    @Bean
    public FoobarSkipPolicy skipPolicy() {
        return new FoobarSkipPolicy();
    }

    @Bean
    public Step transformStep(JobRepository jobRepository,
                              PlatformTransactionManager txManager,
                              FileNumberLineReader reader,
                              TransformItemProcessor processor,
                              ResultLineWriter writer,
                              FoobarSkipPolicy skipPolicy) {

        return new StepBuilder("transformStep", jobRepository)
                .<Integer, String>chunk(50, txManager)
                .reader(reader)
                .processor(i -> {
                    String result = processor.process(i);
                    return i + " -> \"" + result + "\"";
                })
                .writer(writer)
                .faultTolerant()
                .skipPolicy(skipPolicy)
                .build();
    }

    @Bean
    public Job transformJob(JobRepository jobRepository, Step transformStep) {
        return new JobBuilder("transformJob", jobRepository)
                .start(transformStep)
                .build();
    }
}
