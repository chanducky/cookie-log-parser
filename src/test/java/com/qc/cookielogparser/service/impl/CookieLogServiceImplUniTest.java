package com.qc.cookielogparser.service.impl;

import com.qc.cookielogparser.data.common.AppConstants;
import com.qc.cookielogparser.data.model.CookieDetail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for CookieLogServiceImpl.
 */
class CookieLogServiceImplUniTest
{
    @InjectMocks
    private CookieLogServiceImpl cookieLogService;

    private static List<CookieDetail> cookieLogs;

    void loadCsvLogs()
            throws IOException
    {
        URL url = this.getClass().getResource("/csv/cookie_log_data.csv");
        List<String> allLines = Files.readAllLines(new File(url.getFile()).toPath());

        cookieLogs = new ArrayList<>(allLines.size());
        allLines.stream().skip(1).forEach(line -> {
            String[] columns = line.split(",");
            Date date = null;
            try
            {
                date = AppConstants.SDF_CSV_DATE.parse(columns[1].trim());
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
            final CookieDetail cookieDetail = new CookieDetail(columns[0], date);
            cookieLogs.add(cookieDetail);
        });
        cookieLogs = Collections.unmodifiableList(cookieLogs);
    }

    @BeforeEach
    void setUp()
            throws IOException, ParseException
    {
        MockitoAnnotations.openMocks(this);
        loadCsvLogs();
        Assertions.assertNotNull(cookieLogs);
    }

    @Test
    void testSearchCookiesByDateWithNullParams()
    {
        assertThrows(IllegalArgumentException.class,
                     () -> cookieLogService.searchCookiesByDate(null, new Date()));
        assertThrows(IllegalArgumentException.class,
                     () -> cookieLogService.searchCookiesByDate(new ArrayList<>(), null));
    }

    @Test
    void testSearchCookiesByDateWithEmptyList()
    {
        List<CookieDetail> foundCookies = cookieLogService.searchCookiesByDate(new ArrayList<>(), new Date());
        Assertions.assertNull(foundCookies);
    }

    @Test
    void testSearchCookiesByDateWithSingleMatch()
            throws ParseException
    {
        Date date = AppConstants.SDF_ONLY_DATE.parse("2018-12-03");
        List<CookieDetail> foundCookies = cookieLogService.searchCookiesByDate(cookieLogs, date);
        Assertions.assertNotNull(foundCookies);
        Assertions.assertEquals(1, foundCookies.size());
    }

    @Test
    void testSearchCookiesByDateWithMultipleMatch()
            throws ParseException
    {
        Date date = AppConstants.SDF_ONLY_DATE.parse("2018-12-08");
        List<CookieDetail> foundCookies = cookieLogService.searchCookiesByDate(cookieLogs, date);
        Assertions.assertNotNull(foundCookies);
        Assertions.assertEquals(3, foundCookies.size());
    }

    @Test
    void testSearchCookiesByDateWithNoMatch()
            throws ParseException
    {
        Date date = AppConstants.SDF_ONLY_DATE.parse("2018-12-10");
        List<CookieDetail> foundCookies = cookieLogService.searchCookiesByDate(cookieLogs, date);
        Assertions.assertNull(foundCookies);
    }
}
