package com.qc.cookielogparser.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.IFactory;

/**
 * Component executes command lines.
 */
@Slf4j
@Component
public class AppCommandLineRunner
        implements CommandLineRunner, ExitCodeGenerator
{
    private final ParseLogCommand parseLogCommand;
    private final IFactory factory;

    private int exitCode;

    @Autowired
    public AppCommandLineRunner(ParseLogCommand parseLogCommand, IFactory factory)
    {
        this.parseLogCommand = parseLogCommand;
        this.factory = factory;
    }

    @Override
    public void run(String... args)
    {
        exitCode = new CommandLine(parseLogCommand, factory).execute(args);
    }

    @Override
    public int getExitCode()
    {
        return exitCode;
    }
}
