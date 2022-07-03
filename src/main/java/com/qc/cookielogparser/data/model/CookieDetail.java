package com.qc.cookielogparser.data.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import com.qc.cookielogparser.data.common.AppConstants;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Cookie data.
 */
@Data
@NoArgsConstructor
public class CookieDetail implements Comparable<CookieDetail>
{
    private final SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.SEARCH_KEY_DATE_PATTERN);
    @CsvBindByName
    private String cookie;

    @CsvDate(AppConstants.CSV_DATE_PATTERN)
    @CsvBindByName
    private Date timestamp;

    @Override
    public int compareTo(CookieDetail o)
    {
        String d1 = sdf.format(this.getTimestamp());
        String d2= sdf.format(o.getTimestamp());
        return d2.compareTo(d1);
    }
}
