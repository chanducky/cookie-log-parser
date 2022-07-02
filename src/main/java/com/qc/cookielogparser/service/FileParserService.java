package com.qc.cookielogparser.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileParserService
{
    public <T> List<T> parseCsvToBean(File file, Class<T> clazz)
            throws IOException;
}
