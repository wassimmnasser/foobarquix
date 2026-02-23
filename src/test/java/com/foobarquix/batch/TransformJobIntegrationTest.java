package com.foobarquix.batch;
/**
author : Wassim MNASSER
*/


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBatchTest
@SpringBootTest(properties = {
        "batch.in=file:${java.io.tmpdir}/foobarquix-input.txt",
        "batch.out=${java.io.tmpdir}/foobarquix-output.txt"
})
@ActiveProfiles("batch")
class TransformJobIntegrationTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private Job transformJob;

    @TempDir
    Path tempDir;

    private Path inputFile;
    private Path outputFile;

    @BeforeEach
    void setUp() throws Exception {
        jobLauncherTestUtils.setJob(transformJob);

        inputFile = tempDir.resolve("input.txt");
        outputFile = tempDir.resolve("output.txt");

        System.setProperty("batch.in", "file:" + inputFile.toAbsolutePath());
        System.setProperty("batch.out", outputFile.toAbsolutePath().toString());

        Files.writeString(inputFile, String.join("\n",
                "1",
                "3",
                "",
                "abc",
                "150",
                "15"
        ));
    }

    @Test
    void jobShouldCompleteAndWriteOnlyValidLines() throws Exception {
        JobExecution exec = jobLauncherTestUtils.launchJob(
                new JobParametersBuilder()
                        .addLong("run.id", System.currentTimeMillis())
                        .toJobParameters()
        );

        assertEquals(BatchStatus.COMPLETED, exec.getStatus(), "Job should complete even with invalid lines (skip)");

        assertTrue(Files.exists(outputFile), "Output file must exist");

        List<String> lines = Files.readAllLines(outputFile);

        assertEquals(3, lines.size());

        assertEquals("1 -> \"1\"", lines.get(0));
        assertEquals("3 -> \"FOOFOO\"", lines.get(1));
        assertEquals("15 -> \"FOOBARBAR\"", lines.get(2));
    }
}
