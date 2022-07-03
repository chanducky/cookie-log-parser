package com.qc.cookielogparser.service;

import com.qc.cookielogparser.data.model.CookieDetail;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.List;

/**
 * This Service is being used for Cookie log related operations.
 */
public interface CookieLogService
{
    /**
     * Search cookies from cookie list by cookie generated date.
     * @param cookieList List of cookie.
     * @param date cookie date.
     * @return @Nullable cookies generated on the search date.
     */
    @Nullable
    List<CookieDetail> searchCookiesByDate(List<CookieDetail> cookieList, Date date);
}
