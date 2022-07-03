package com.qc.cookielogparser.service.impl;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.qc.cookielogparser.service.FileParserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.List;

/**
 * {@inheritdoc}
 */
@Slf4j
@Service
public class FileParserServiceImpl implements FileParserService
{
    /**
     * {@inheritdoc}
     */
    @Override
    public <T> List<T> parseCsvToBean(@NonNull final File file, Class<T> clazz)
            throws IOException
    {
        Reader reader = Files.newBufferedReader(file.toPath());
        HeaderColumnNameMappingStrategy<T> strategy
                = new HeaderColumnNameMappingStrategy<>();
        strategy.setType(clazz);
        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
                .withMappingStrategy(strategy)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
        return csvToBean.parse();
    }
}
