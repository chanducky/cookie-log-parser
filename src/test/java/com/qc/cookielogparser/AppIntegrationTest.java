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
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

        Set<CookieDetail> mostActiveCookiesOn10ThDec = cookieLogService
                .searchMostActiveCookiesForDate(cookieLogs, AppConstants.SDF_DATE.parse("2018-12-10"));
        Assertions.assertNull(mostActiveCookiesOn10ThDec);

        Set<CookieDetail> mostActiveCookiesOn9ThDec = cookieLogService
                .searchMostActiveCookiesForDate(cookieLogs, AppConstants.SDF_DATE.parse("2018-12-09"));
        Assertions.assertNotNull(mostActiveCookiesOn9ThDec);
        Assertions.assertEquals(1, mostActiveCookiesOn9ThDec.size());
        Assertions.assertEquals("AtY0laUfhglK3lC7", mostActiveCookiesOn9ThDec.stream().findFirst().get().getCookie());

        Set<CookieDetail> mostActiveCookiesOn8ThDec = cookieLogService
                .searchMostActiveCookiesForDate(cookieLogs, AppConstants.SDF_DATE.parse("2018-12-08"));
        Assertions.assertNotNull(mostActiveCookiesOn8ThDec);
        Assertions.assertEquals(1, mostActiveCookiesOn8ThDec.size());
        Assertions.assertEquals("SAZuXPGUrfbcn5UA", mostActiveCookiesOn8ThDec.stream().findFirst().get().getCookie());

        Set<CookieDetail> mostActiveCookiesOn7ThDec = cookieLogService
                .searchMostActiveCookiesForDate(cookieLogs, AppConstants.SDF_DATE.parse("2018-12-07"));
        Assertions.assertNotNull(mostActiveCookiesOn7ThDec);
        Assertions.assertEquals(1, mostActiveCookiesOn7ThDec.size());
        Assertions.assertEquals("4sMM2LxV07bPJzwf", mostActiveCookiesOn7ThDec.stream().findFirst().get().getCookie());

        Set<CookieDetail> mostActiveCookiesOn5ThJuly = cookieLogService
                .searchMostActiveCookiesForDate(cookieLogs, AppConstants.SDF_DATE.parse("2018-07-05"));
        Assertions.assertNotNull(mostActiveCookiesOn5ThJuly);
        Assertions.assertEquals(2, mostActiveCookiesOn5ThJuly.size());
        Assertions.assertEquals("72dc98e4-0f94-4a", mostActiveCookiesOn5ThJuly.stream().findFirst().get().getCookie());
        Assertions.assertEquals(Arrays.asList("72dc98e4-0f94-4a", "a3a2cf25-391d-4c"),
                                mostActiveCookiesOn5ThJuly.stream().map(cd -> cd.getCookie()).collect(
                                        Collectors.toList()));
    }
}
