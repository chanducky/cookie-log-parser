package com.qc.cookielogparser;

import com.qc.cookielogparser.service.CookieLogService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


class MainTests
{
    @Autowired
    private CookieLogService _cookieLogService;

    @Test
    void contextLoads()
    {
        Assertions.assertNotNull(_cookieLogService);
    }
}
