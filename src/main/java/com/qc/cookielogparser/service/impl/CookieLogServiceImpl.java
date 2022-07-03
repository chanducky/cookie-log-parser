package com.qc.cookielogparser.service.impl;

import com.qc.cookielogparser.data.model.CookieDetail;
import com.qc.cookielogparser.service.CookieLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
    public @Nullable List<CookieDetail> searchCookiesByDate(
            @NonNull final List<CookieDetail> cookieList,
            @NonNull final Date date)
    {
        Assert.notNull(cookieList, "cookieList can't be null.");
        Assert.notNull(date, "date can't be null.");

        CookieDetail searchKey = new CookieDetail(null, date);
        int keyIndex = Collections.binarySearch(cookieList, searchKey);
        if (keyIndex >= 0)
        {
            ArrayList<CookieDetail> foundCookies = new ArrayList<>();
            foundCookies.add(cookieList.get(keyIndex));
            searchBackward(cookieList, foundCookies, searchKey, keyIndex);
            searchForward(cookieList, foundCookies, searchKey, keyIndex);
            return foundCookies;
        }
        return null;
    }

    private void searchForward(List<CookieDetail> cookieList, ArrayList<CookieDetail> foundCookies,
                               CookieDetail searchKey,
                               int keyIndex)
    {
        ++keyIndex;

        if (isCookieSearchCriteriaMatched(cookieList, keyIndex, searchKey))
        {
            foundCookies.add(cookieList.get(keyIndex));
            searchForward(cookieList, foundCookies, searchKey, keyIndex);
        }
    }

    private void searchBackward(List<CookieDetail> cookieList, ArrayList<CookieDetail> foundCookies,
                                CookieDetail searchKey, int keyIndex)
    {
        --keyIndex;
        if (isCookieSearchCriteriaMatched(cookieList, keyIndex, searchKey))
        {
            foundCookies.add(cookieList.get(keyIndex));
            searchBackward(cookieList, foundCookies, searchKey, keyIndex);
        }
    }

    private boolean isCookieSearchCriteriaMatched(List<CookieDetail> cookieList, int keyIndex, CookieDetail searchKey)
    {
        if (keyIndex < cookieList.size() && keyIndex >= 0)
        {
            return cookieList.get(keyIndex).compareTo(searchKey) == 0;
        }
        return false;
    }
}
