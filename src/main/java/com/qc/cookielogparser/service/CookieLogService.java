package com.qc.cookielogparser.service;

import com.qc.cookielogparser.data.model.CookieDetail;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * This Service is being used for Cookie log related operations.
 */
public interface CookieLogService
{
    /**
     * Search most active cookies for the date.
     * @param cookieList List of cookie.
     * @param date cookie date.
     * @return most active cookies for the date.
     */
    @Nullable
    Set<CookieDetail> searchMostActiveCookiesForDate(@NonNull List<CookieDetail> cookieList, @NonNull Date date);
}
