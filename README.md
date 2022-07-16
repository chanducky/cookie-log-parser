# cookie-log-parser
A command line program to process the log file and return the most active
cookie for a specific day.

### Assumption

* If multiple cookies meet that criteria, will return all of them on separate lines.
* -d parameter takes date in UTC time zone and YYYY-MM-DD date format.
* Cookies in the log file should to be sorted by timestamp (most recent occurrence is the first line of the file).

### Guides

The following guides illustrate how to use some features concretely:
#### Running from source code using gradle.
* Go to root resource **cookie-log-parser** then run next command.
* `./gradlew bootRun --args='-f=<filepath> -d=<yyyy-MM-dd>'`
* i.e `./gradlew bootRun --args='-f=src/test/resources/csv/cookie_log.csv -d=2018-12-09'`
  or ` ./gradlew bootRun --args='-f=cookie_log.csv -d=2018-12-09' ` when file is available inside root resource.

* Note run using --debug mode to run in debug mode to see log in details. 

#### Running using jar file.
* Generate jar file using `./gradlew clean build `or `./gradlew jar`.
* copy jar file and csv file in same directory and jump to the directory and execute below command.
* `java -jar <jar file name> -f <file path> -d=<date in YYYY-MM-DD format>`
* i.e `java -jar cookie-log-parser-0.0.1-SNAPSHOT.jar -f cookie_log.csv -d=2018-12-09`

### Used Development frameworks, Tools and libraries
###### Development Framework
* SpringBoot 2.7.1
###### Programing language
* Java 8
###### Tool for maintaining coding standard
Checkstyle
######  CSV parser library
* Opencsv 
######  java commandline framework
* Picocli 
######  Unit test frameworks  
* junit-jupiter, Mockito etc.
######  Integration test framework
SpringBootTest
###### Other 
* lombok

### Reference Documentation

For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.1/gradle-plugin/reference/html/)
* [Picocli](https://picocli.info/#_spring_boot_example)
* [Opencsv Users Guide](http://opencsv.sourceforge.net/)
* [Checkstyle](https://checkstyle.sourceforge.io/)
