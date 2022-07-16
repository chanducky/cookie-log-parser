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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for CookieLogServiceImpl.
 */
class CookieLogServiceImplUniTest
{
    @InjectMocks
    private CookieLogServiceImpl cookieLogService;

    private static List<CookieDetail> cookieLogs;

    private void loadCsvLogs()
            throws IOException
    {
        URL url = this.getClass().getResource("/csv/cookie_log.csv");
        List<String> allLines = Files.readAllLines(new File(url.getFile()).toPath());

        cookieLogs = new ArrayList<>(allLines.size());
        allLines.stream().skip(1).forEach(line -> {
            String[] columns = line.split(",");
            Date date = null;
            try
            {
                date = AppConstants.SDF_CSV_DATE_TIME.parse(columns[1].trim());
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
            throws IOException
    {
        MockitoAnnotations.openMocks(this);
        loadCsvLogs();
        Assertions.assertNotNull(cookieLogs);
    }

    @Test
    void testSearchMostActiveCookiesForDateWithNullParams()
    {
        assertThrows(IllegalArgumentException.class,
                     () -> cookieLogService.searchMostActiveCookiesForDate(null, new Date()));
        assertThrows(IllegalArgumentException.class,
                     () -> cookieLogService.searchMostActiveCookiesForDate(new ArrayList<>(), null));
    }

    @Test
    void testSearchMostActiveCookiesForDateWithEmptyList()
    {
        Set<CookieDetail> mostActiveCookies = cookieLogService.searchMostActiveCookiesForDate(new ArrayList<>(),
                                                                                         new Date());
        Assertions.assertNull(mostActiveCookies);
    }

    @Test
    void testSearchMostActiveCookiesForDateWithSingleMatch()
            throws ParseException
    {
        Date date = AppConstants.SDF_DATE.parse("2018-12-07");
        Set<CookieDetail> mostActiveCookies = cookieLogService.searchMostActiveCookiesForDate(cookieLogs, date);
        Assertions.assertNotNull(mostActiveCookies);
        Assertions.assertEquals(1, mostActiveCookies.size());
    }

    @Test
    void testSearchMostActiveCookiesForDateWithMultipleMatchButSingleMostActive()
            throws ParseException
    {
        Date date = AppConstants.SDF_DATE.parse("2018-12-08");
        Set<CookieDetail> mostActiveCookies = cookieLogService.searchMostActiveCookiesForDate(cookieLogs, date);
        Assertions.assertNotNull(mostActiveCookies);
        Assertions.assertEquals(1, mostActiveCookies.size());
    }

    @Test
    void testSearchMostActiveCookiesForDateWithNoMatch()
            throws ParseException
    {
        Date date = AppConstants.SDF_DATE.parse("2018-12-10");
        Set<CookieDetail> mostActiveCookies = cookieLogService.searchMostActiveCookiesForDate(cookieLogs, date);
        Assertions.assertNull(mostActiveCookies);
    }

    @Test
    void testSearchMostActiveCookiesForDateWithMultipleActiveMatch()
            throws ParseException
    {
        Date date = AppConstants.SDF_DATE.parse("2022-07-05");
        Set<CookieDetail> mostActiveCookies = cookieLogService.searchMostActiveCookiesForDate(cookieLogs, date);
        Assertions.assertNotNull(mostActiveCookies);
        Assertions.assertEquals(2, mostActiveCookies.size());
    }
}
