package com.qc.cookielogparser.data.common;

import java.text.SimpleDateFormat;

/**
 * Constants used in app.
 */
public final class AppConstants
{
    public static final String CSV_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'+'00:00";
    public static final String SEARCH_KEY_DATE_PATTERN = "yyyy-MM-dd";
    public static final SimpleDateFormat SDF_ONLY_DATE = new SimpleDateFormat(AppConstants.SEARCH_KEY_DATE_PATTERN);

    private AppConstants()
    {
    }
}
