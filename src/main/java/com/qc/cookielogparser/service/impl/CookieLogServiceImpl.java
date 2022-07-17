package com.qc.cookielogparser.service.impl;

import com.qc.cookielogparser.data.common.AppConstants;
import com.qc.cookielogparser.data.model.CookieDetail;
import com.qc.cookielogparser.service.CookieLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * {@inheritdoc}
 */
@Slf4j
@Service
public class CookieLogServiceImpl
        implements CookieLogService
{
    /**
     * {@inheritdoc}
     */
    @Override
    public @Nullable Set<CookieDetail> searchMostActiveCookiesForDate(
            @NonNull final List<CookieDetail> cookieList,
            @NonNull final Date date)
    {
        Assert.notNull(cookieList, "cookieList can't be null.");
        Assert.notNull(date, "date can't be null.");

        Set<CookieDetail> mostActiveCookies = null;
        CookieDetail mostActiveCookie = null;

        for (CookieDetail cookie : cookieList)
        {
            if (mostActiveCookie == null)
            {
                String cookieDate = AppConstants.SDF_DATE.format(cookie.getTimestamp());
                String searchDate = AppConstants.SDF_DATE.format(date);

                if (cookieDate.equals(searchDate))
                {
                    mostActiveCookie = cookie;
                    mostActiveCookies = new HashSet<>();
                    mostActiveCookies.add(cookie);
                }
            }
            else if (mostActiveCookie.getTimestamp().compareTo(cookie.getTimestamp()) == 0)
            {
                mostActiveCookies.add(cookie);
            }
            else
            {
                break;
            }
        }
        return mostActiveCookies;
    }
}
