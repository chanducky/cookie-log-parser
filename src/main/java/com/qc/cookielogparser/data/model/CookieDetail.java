package com.qc.cookielogparser.data.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import com.qc.cookielogparser.data.common.AppConstants;

import java.util.Date;
import java.util.Objects;

import static com.qc.cookielogparser.data.common.AppConstants.SDF_ONLY_DATE;

/**
 * Cookie data.
 */
public class CookieDetail implements Comparable<CookieDetail>
{

    @CsvBindByName
    private String cookie;

    @CsvDate(AppConstants.CSV_DATE_PATTERN)
    @CsvBindByName
    private Date timestamp;

    public CookieDetail()
    {
    }

    public CookieDetail(String cookie, Date timestamp)
    {
        this.cookie = cookie;
        this.timestamp = timestamp;
    }

    public String getCookie()
    {
        return cookie;
    }

    public void setCookie(String cookie)
    {
        this.cookie = cookie;
    }

    public Date getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(Date timestamp)
    {
        this.timestamp = timestamp;
    }

    @Override
    public int compareTo(CookieDetail o)
    {
        String d1 = SDF_ONLY_DATE.format(this.getTimestamp());
        String d2= SDF_ONLY_DATE.format(o.getTimestamp());
        return d2.compareTo(d1);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        CookieDetail that = (CookieDetail) o;
        return cookie.equals(that.cookie) && timestamp.equals(that.timestamp);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(cookie, timestamp);
    }
}
