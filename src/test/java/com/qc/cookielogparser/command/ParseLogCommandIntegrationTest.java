package com.qc.cookielogparser.command;

import com.qc.cookielogparser.data.common.AppConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.net.URL;
import java.text.ParseException;

/**
 * Integration test for ParseLogCommand.
 */
@SpringBootTest
public class ParseLogCommandIntegrationTest
{
    public static final String CSV_COOKIE_LOG_CSV = "/csv/cookie_log.csv";
    public static final String CSV_NO_SUCH_FILE = "/invalid/no_file.csv";

    @Autowired
    private ParseLogCommand parseLogCommand;

    @Test
    void testCallWhenMultipleCookieMatchByDateButFoundSingleMostActive()
            throws ParseException
    {
        URL url = this.getClass().getResource(CSV_COOKIE_LOG_CSV);
        ReflectionTestUtils.setField(parseLogCommand, "file", new File(url.getFile()));
        ReflectionTestUtils.setField(parseLogCommand, "date", AppConstants.SDF_DATE.parse("2018-12-08"));
        Integer exitCode = parseLogCommand.call();
        Assertions.assertEquals(0, exitCode);
    }

    @Test
    void testCallWhenSingleMostActiveCookieMatchByDate()
            throws ParseException
    {
        URL url = this.getClass().getResource(CSV_COOKIE_LOG_CSV);
        ReflectionTestUtils.setField(parseLogCommand, "file", new File(url.getFile()));
        ReflectionTestUtils.setField(parseLogCommand, "date", AppConstants.SDF_DATE.parse("2018-12-07"));
        Integer exitCode = parseLogCommand.call();
        Assertions.assertEquals(0, exitCode);
    }

    @Test
    void testCallWhenNoCookieMatchByDate()
            throws ParseException
    {
        URL url = this.getClass().getResource(CSV_COOKIE_LOG_CSV);
        ReflectionTestUtils.setField(parseLogCommand, "file", new File(url.getFile()));
        ReflectionTestUtils.setField(parseLogCommand, "date", AppConstants.SDF_DATE.parse("2022-07-04"));
        Integer exitCode = parseLogCommand.call();
        Assertions.assertEquals(0, exitCode);
    }

    @Test
    void testCallWhenFileNotAvailable()
            throws ParseException
    {
        ReflectionTestUtils.setField(parseLogCommand, "file", new File(CSV_NO_SUCH_FILE));
        ReflectionTestUtils.setField(parseLogCommand, "date", AppConstants.SDF_DATE.parse("2022-07-04"));
        Integer exitCode = parseLogCommand.call();
        Assertions.assertEquals(0, exitCode);
    }
}
