package com.qc.cookielogparser.command;

import com.qc.cookielogparser.model.CookieDetail;
import com.qc.cookielogparser.service.CookieLogService;
import com.qc.cookielogparser.service.FileParserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
@Component
@Command(name = "parseCookieLog", mixinStandardHelpOptions = true,
        description = "Parse and search cookie from log file")
public class ParseLogCommand
        implements Callable<Integer>
{
    private final FileParserService _fileParserService;
    private final CookieLogService _cookieLogService;

    @Autowired
    public ParseLogCommand(FileParserService fileParserService,
                           CookieLogService cookieLogService)
    {
        _fileParserService = fileParserService;
        _cookieLogService = cookieLogService;
    }

    @Option(names = {"-f", "--file"},required = true, paramLabel = "File",
            description = "Cookie log file relative path.")
    private File file;

    @Option(names = {"-d", "--date"},required = true, paramLabel = "Date",
            description = "Date of cookie generation in yyyy-MM-dd format.")
    private Date date;

    @Override
    public Integer call()
    {
        log.info("file : {}", file.getAbsolutePath());
        log.info("Date : {}", date);
        try
        {
            List<CookieDetail> cookieDetailList = _fileParserService.parseCsvToBean(file, CookieDetail.class);
            //cookieDetailList.stream().forEach(System.out::println);
            List<CookieDetail> resultList = _cookieLogService.searchCookiesByDate(cookieDetailList, date);
            resultList.stream().map(CookieDetail::getCookie).forEach(System.out::println);
        }
        catch (IOException e)
        {
            log.error("Error wile parsing the csv file.", e);
            return 1;
        }
        return 0;
    }
}
