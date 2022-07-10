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
        URL url = this.getClass().getResource("/csv/cookie_log_data.csv");
        List<CookieDetail> cookieLogs = fileParserService.parseCsvToBean(new File(url.getFile()),
                                                                               CookieDetail.class);

        Date date = AppConstants.SDF_DATE.parse("2018-12-08");
        List<CookieDetail> foundCookies = cookieLogService.searchCookiesByDate(cookieLogs, date);
        Assertions.assertNotNull(foundCookies);
        Assertions.assertEquals(3, foundCookies.size());

         date = AppConstants.SDF_DATE.parse("2018-12-03");
        foundCookies = cookieLogService.searchCookiesByDate(cookieLogs, date);
        Assertions.assertNotNull(foundCookies);
        Assertions.assertEquals(1, foundCookies.size());
    }
}
