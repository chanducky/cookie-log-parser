package com.qc.cookielogparser.command;

import com.qc.cookielogparser.service.CookieLogService;
import com.qc.cookielogparser.service.FileParserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Unit tests for ParseLogCommand.
 */
class ParseLogCommandUnitTest
{
    @Mock
    private FileParserService fileParserService;
    @Mock
    private CookieLogService cookieLogService;

    private ParseLogCommand parseLogCommand;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
        parseLogCommand = new ParseLogCommand(fileParserService, cookieLogService);
    }

    @Test
    void testCallSuccess()
            throws IOException
    {
        Mockito.doReturn(new ArrayList<>())
                .when(fileParserService).parseCsvToBean(Mockito.nullable(File.class), Mockito.any(Class.class));
        Mockito.doReturn(new ArrayList<>())
                .when(cookieLogService).searchCookiesByDate(Mockito.anyList(), Mockito.nullable(Date.class));

        int exitCode = parseLogCommand.call();
        Assertions.assertEquals(0, exitCode);

        Mockito.verify(fileParserService, Mockito.times(1))
                .parseCsvToBean(Mockito.nullable(File.class), Mockito.any(Class.class));
        Mockito.verify(cookieLogService, Mockito.times(1))
                .searchCookiesByDate(Mockito.anyList(), Mockito.nullable(Date.class));
    }

    @Test
    void testCallWithNoSuchFileException()
            throws IOException
    {
        Mockito.doThrow(new NoSuchFileException("File not found"))
                .when(fileParserService).parseCsvToBean(Mockito.nullable(File.class), Mockito.any(Class.class));

        int exitCode = parseLogCommand.call();
        Assertions.assertEquals(1, exitCode);

        Mockito.verify(fileParserService, Mockito.times(1))
                .parseCsvToBean(Mockito.nullable(File.class), Mockito.any(Class.class));
        Mockito.verify(cookieLogService, Mockito.times(0))
                .searchCookiesByDate(Mockito.anyList(), Mockito.any(Date.class));
    }
}
