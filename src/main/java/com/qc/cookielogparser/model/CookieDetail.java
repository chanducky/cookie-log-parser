package com.qc.cookielogparser.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
public class CookieDetail implements Comparable<CookieDetail>
{
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    @CsvBindByName
    private String cookie;

    @CsvDate("yyyy-MM-dd'T'HH:mm:ss'+'00:00")
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
