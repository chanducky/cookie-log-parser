package com.qc.cookielogparser.service.impl;

import com.qc.cookielogparser.data.model.CookieDetail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.NoSuchFileException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for FileParserServiceImpl.
 */
class FileParserServiceImplUnitTest
{
    @InjectMocks
    private FileParserServiceImpl fileParserService;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testParseCsvToBeanWhenFileIsNull()
    {
        assertThrows(NullPointerException.class,
                     () -> fileParserService.parseCsvToBean(null, CookieDetail.class));
    }

    @Test
    void testParseCsvToBeanFileWhenFileNotAvailable()
    {
        Assertions.assertThrows(NoSuchFileException.class,
                                () -> fileParserService.parseCsvToBean(
                                        new File("/invalid/cookie_log.csv"),
                                        CookieDetail.class));
    }

    @Test
    void testParseCsvToBeanWithEmptyFile()
    {
        URL url = this.getClass().getResource("/csv/empty.csv");
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class,
                                                          () -> fileParserService.parseCsvToBean(
                                                                  new File(url.getFile()), CookieDetail.class));
        Assertions.assertEquals("Error capturing CSV header!", thrown.getMessage());
    }

    @Test
    void testParseCsvToBeanFileWithHeaderButNoData()
            throws IOException
    {
        URL url = this.getClass().getResource("/csv/cookie_log_only_header.csv");
        List<CookieDetail> cookieDetailList = fileParserService.parseCsvToBean(new File(url.getFile()),
                                                                               CookieDetail.class);
        Assertions.assertNotNull(cookieDetailList);
        Assertions.assertEquals(0, cookieDetailList.size());
    }

    @Test
    void testParseCsvToBeanFileWithCorrectData()
            throws IOException
    {
        URL url = this.getClass().getResource("/csv/cookie_log.csv");
        List<CookieDetail> cookieDetailList = fileParserService.parseCsvToBean(new File(url.getFile()),
                                                                               CookieDetail.class);
        Assertions.assertNotNull(cookieDetailList);
        Assertions.assertTrue(cookieDetailList.size() > 0);
    }

    @Test
    void testParseCsvToBeanFileWithNullData()
    {
        URL url = this.getClass().getResource("/csv/cookie_log_invalid.csv");
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class,
                                () -> fileParserService.parseCsvToBean(new File(url.getFile()), CookieDetail.class));
        Assertions.assertEquals("Error parsing CSV line: 4, values: null", thrown.getMessage());
    }
}
