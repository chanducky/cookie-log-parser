package com.qc.cookielogparser.service;

import com.qc.cookielogparser.data.model.CookieDetail;

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
     * @return cookies generated on the search date.
     */
    List<CookieDetail> searchCookiesByDate(List<CookieDetail> cookieList, Date date);
}
