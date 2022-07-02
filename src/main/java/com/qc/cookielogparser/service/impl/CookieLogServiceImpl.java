package com.qc.cookielogparser.service.impl;

import com.qc.cookielogparser.model.CookieDetail;
import com.qc.cookielogparser.service.CookieLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class CookieLogServiceImpl implements CookieLogService
{
    @Override
    public List<CookieDetail> searchCookiesByDate(List<CookieDetail> cookieList, Date date)
    {
        CookieDetail cd = new CookieDetail();
        cd.setTimestamp(date);
        int index = Collections.binarySearch(cookieList, cd);
        ArrayList<CookieDetail> list = new ArrayList<>();
        list.add(cookieList.get(index));
        return list;
    }
}
