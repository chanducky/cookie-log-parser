package com.qc.cookielogparser;

import com.qc.cookielogparser.data.common.AppConstants;
import com.qc.cookielogparser.data.model.CookieDetail;
import com.qc.cookielogparser.service.CookieLogService;
import com.qc.cookielogparser.service.FileParserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;

@SpringBootTest
class AppIntegrationTest
{
    @Autowired
    private FileParserService fileParserService;

    @Autowired
    private CookieLogService cookieLogService;

    @Test
    void testCall()
            throws IOException, ParseException
    {
        URL url = this.getClass().getResource("/csv/cookie_log.csv");
        List<CookieDetail> cookieLogs = fileParserService.parseCsvToBean(new File(url.getFile()),
                                                                               CookieDetail.class);

        Date date = AppConstants.SDF_DATE.parse("2018-12-08");
        Set<CookieDetail> mostActiveCookies = cookieLogService.searchMostActiveCookiesForDate(cookieLogs, date);
        Assertions.assertNotNull(mostActiveCookies);
        Assertions.assertEquals(1, mostActiveCookies.size());

         date = AppConstants.SDF_DATE.parse("2018-12-07");
        mostActiveCookies = cookieLogService.searchMostActiveCookiesForDate(cookieLogs, date);
        Assertions.assertNotNull(mostActiveCookies);
        Assertions.assertEquals(1, mostActiveCookies.size());
    }
}
