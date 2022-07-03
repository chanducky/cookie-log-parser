package com.qc.cookielogparser.data.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import com.qc.cookielogparser.data.common.AppConstants;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.qc.cookielogparser.data.common.AppConstants.SDF_ONLY_DATE;

/**
 * Cookie data.
 */
@Data
@NoArgsConstructor
public class CookieDetail implements Comparable<CookieDetail>
{
    @CsvBindByName
    private String cookie;

    @CsvDate(AppConstants.CSV_DATE_PATTERN)
    @CsvBindByName
    private Date timestamp;

    @Override
    public int compareTo(CookieDetail o)
    {
        String d1 = SDF_ONLY_DATE.format(this.getTimestamp());
        String d2= SDF_ONLY_DATE.format(o.getTimestamp());
        return d2.compareTo(d1);
    }
}
