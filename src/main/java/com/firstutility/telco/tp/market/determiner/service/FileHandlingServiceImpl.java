package com.firstutility.telco.tp.market.determiner.service;

import com.firstutility.telco.tp.market.determiner.domain.DeterminedAvailabilityResponseMsg;
import com.firstutility.telco.tp.market.determiner.domain.EnergyCustomerRequestMsg;
import com.firstutility.telco.tp.market.determiner.service.transformer.DeterminedAvailabilityResponseTransformer;
import com.firstutility.telco.tp.market.determiner.service.transformer.EnergyCustomerRequestTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class FileHandlingServiceImpl implements FileHandlingService {

    private final Logger LOG = LoggerFactory.getLogger(FileHandlingServiceImpl.class);

    @Value("${unprocessed.file.dir}")
    private String unprocessedFileDir;

    @Value("${processed.file.dir}")
    private String processedFileDir;

    final FileSystem defaultFileSystems = FileSystems.getDefault();

    private static final String CSV_EXT = ".csv";
    private static final String CSV_PROCESSING_EXT = ".csv.processing";
    private static final String CSV_PROCESSED_EXT = ".csv.processed";
    private static final String TMP_EXT = ".tmp";

    private final EnergyCustomerRequestTransformer energyCustomerRequestTransformer;

    private final DeterminedAvailabilityResponseTransformer determinedAvailabilityResponseTransformer;

    @Autowired
    public FileHandlingServiceImpl(
            final EnergyCustomerRequestTransformer energyCustomerRequestTransformer,
            final DeterminedAvailabilityResponseTransformer determinedAvailabilityResponseTransformer) {
        this.energyCustomerRequestTransformer = energyCustomerRequestTransformer;
        this.determinedAvailabilityResponseTransformer = determinedAvailabilityResponseTransformer;
    }

    @Override
    public Optional<Path> getFirstFileToProcess() throws IOException {
        return Files.list(defaultFileSystems.getPath(unprocessedFileDir)).filter(
                f -> f.getFileName().toString().endsWith(CSV_EXT)).findFirst();
    }

    @Override
    public List<EnergyCustomerRequestMsg> loadFile(final Path path) throws IOException {
        LOG.info("processing {}", path.getFileName());

        final Path processingPath = defaultFileSystems.getPath(
                unprocessedFileDir, path.getFileName().toString().replace(CSV_EXT, CSV_PROCESSING_EXT));

        Files.move(path, processingPath);

        final List<EnergyCustomerRequestMsg> requests = new ArrayList<>();
        Files.lines(processingPath).forEach(l -> {
            final EnergyCustomerRequestMsg energyCustomerRequestMsg = energyCustomerRequestTransformer.transform(
                    path.getFileName().toString(), l);
            requests.add(energyCustomerRequestMsg);
        });

        return requests;
    }

    @Override
    public void handleResponse(final DeterminedAvailabilityResponseMsg determinedAvailabilityResponseMsg) {

        final Path tmpOutputPath = defaultFileSystems.getPath(
                processedFileDir, determinedAvailabilityResponseMsg.getFileName().replace(CSV_EXT, TMP_EXT));

        final String text = determinedAvailabilityResponseTransformer.transform(determinedAvailabilityResponseMsg);

        appendToOututFile(tmpOutputPath, text);
    }


    @Override
    public void finalise(final String fileName) throws IOException {

        final Path tmpOutputPath = defaultFileSystems.getPath(processedFileDir, fileName.replace(CSV_EXT, TMP_EXT));

        final Path finalOuptputPath = defaultFileSystems.getPath(processedFileDir, fileName);

        final Path processingPath = defaultFileSystems.getPath(unprocessedFileDir, fileName.replace(CSV_EXT, CSV_PROCESSING_EXT));

        final Path processedPath = defaultFileSystems.getPath(unprocessedFileDir, fileName.replace(CSV_EXT, CSV_PROCESSED_EXT));

        Files.move(tmpOutputPath, finalOuptputPath);

        Files.move(processingPath, processedPath);
    }

    private synchronized void appendToOututFile(final Path inputPath, final String stringToAppend) {
        try {
            if (!Files.exists(inputPath)) {
                Files.createFile(inputPath);
            }
            Files.write(inputPath, stringToAppend.getBytes(), StandardOpenOption.APPEND);
            Files.write(inputPath, "\n".getBytes(), StandardOpenOption.APPEND);
        } catch (final IOException e) {
            LOG.error("exception writing to output", e);
        }
    }
}
