package com.qc.cookielogparser.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Service is being used to parse file and convert to Bean.
 */
public interface FileParserService
{
    /**
     * Parse csv file to the bean.
     * @param file file to being parse to bean.
     * @param clazz type of bean.
     * @param <T> type of bean.
     * @return List of beans.
     * @throws IOException throw IO exceptions.
     */
    <T> List<T> parseCsvToBean(File file, Class<T> clazz)
            throws IOException;
}
