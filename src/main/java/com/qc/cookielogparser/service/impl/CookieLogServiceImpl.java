package com.qc.cookielogparser.service.impl;

import com.qc.cookielogparser.data.model.CookieDetail;
import com.qc.cookielogparser.service.CookieLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
    public List<CookieDetail> searchCookiesByDate(
            @NonNull final List<CookieDetail> cookieList,
            @NonNull final Date date)
    {
        CookieDetail cookie = new CookieDetail();
        cookie.setTimestamp(date);
        int keyIndex = Collections.binarySearch(cookieList, cookie);
        if (keyIndex >= 0)
        {
            ArrayList<CookieDetail> foundCookies = new ArrayList<>();
            foundCookies.add(cookieList.get(keyIndex));
            searchBackward(cookieList, foundCookies, cookie, keyIndex);
            searchForward(cookieList, foundCookies, cookie, keyIndex);
            return foundCookies;
        }
        return null;
    }

    private void searchForward(List<CookieDetail> cookieList, ArrayList<CookieDetail> foundCookies, CookieDetail cookie,
                               int keyIndex)
    {
        ++keyIndex;

        if (isCookieMatchedSearchCriteria(cookieList, keyIndex, cookie))
        {
            foundCookies.add(cookieList.get(keyIndex));
            searchForward(cookieList, foundCookies, cookie, keyIndex);
        }
    }

    private void searchBackward(List<CookieDetail> cookieList, ArrayList<CookieDetail> foundCookies,
                                CookieDetail cookie, int keyIndex)
    {
        --keyIndex;
        if (isCookieMatchedSearchCriteria(cookieList, keyIndex, cookie))
        {
            foundCookies.add(cookieList.get(keyIndex));
            searchBackward(cookieList, foundCookies, cookie, keyIndex);
        }
    }

    private boolean isCookieMatchedSearchCriteria(List<CookieDetail> cookieList, int keyIndex, CookieDetail cookie)
    {
        if (keyIndex < cookieList.size() && keyIndex >= 0)
        {
            return cookieList.get(keyIndex).compareTo(cookie) == 0;
        }
        return false;
    }
}
