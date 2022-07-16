package com.qc.cookielogparser.data.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import com.qc.cookielogparser.data.common.AppConstants;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Date;

/**
 * Cookie data.
 */
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class CookieDetail
{
    @NonNull
    @CsvBindByName(required = true)
    private String cookie;

    @NonNull
    @CsvDate(AppConstants.CSV_DATE_TIME_PATTERN)
    @CsvBindByName(required = true)
    private Date timestamp;
}
