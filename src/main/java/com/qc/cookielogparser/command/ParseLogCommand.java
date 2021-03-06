package com.qc.cookielogparser.command;

import com.qc.cookielogparser.data.common.AppConstants;
import com.qc.cookielogparser.data.model.CookieDetail;
import com.qc.cookielogparser.service.CookieLogService;
import com.qc.cookielogparser.service.FileParserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.nio.file.NoSuchFileException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * This component take input from command line arguments and process accordingly.
 */
@Slf4j
@Component
@Command(name = "search-cookies", mixinStandardHelpOptions = true,
        description = "Search cookies from the log file by date.")
public class ParseLogCommand
        implements Callable<Integer>
{
    private final FileParserService fileParserService;
    private final CookieLogService cookieLogService;

    @Autowired
    public ParseLogCommand(FileParserService fileParserService,
                           CookieLogService cookieLogService)
    {
        this.fileParserService = fileParserService;
        this.cookieLogService = cookieLogService;
    }

    @Option(names = {"-f", "--file"}, required = true, paramLabel = "File",
            description = "Cookie log file path.", arity = "1")
    private File file;

    @Option(names = {"-d", "--date"}, required = true, paramLabel = "Date",
            description = "Date of cookie generation in yyyy-MM-dd format.", arity = "1")
    private Date date;

    @Override
    public Integer call()
    {
        try
        {
            log.debug("file : {}", file);
            log.debug("Date : {}", date);

            List<CookieDetail> cookieDetailList = fileParserService.parseCsvToBean(file, CookieDetail.class);
            if (log.isDebugEnabled())
            {
                cookieDetailList.forEach(System.out::println);
            }
            Set<CookieDetail> mostActiveCookies =
                    cookieLogService.searchMostActiveCookiesForDate(cookieDetailList, date);

            if (mostActiveCookies != null)
            {
                mostActiveCookies.stream().map(CookieDetail::getCookie).forEach(System.out::println);
            }
            else
            {
                System.out.println(
                        String.format("No cookie found for the date : %s.", AppConstants.SDF_DATE.format(date)));
            }
            return 0;
        }
        catch (NoSuchFileException e)
        {
            System.out.printf("%n File : %s not found.", file);
            return 0;
        }
        catch (Exception e)
        {
            log.error("Unknown error.", e);
        }
        return 1;
    }
}
