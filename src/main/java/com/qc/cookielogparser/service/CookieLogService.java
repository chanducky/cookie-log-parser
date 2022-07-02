package com.qc.cookielogparser.service;

import com.qc.cookielogparser.model.CookieDetail;

import java.util.Date;
import java.util.List;

public interface CookieLogService
{
    List<CookieDetail> searchCookiesByDate(List<CookieDetail> cookieDetailList, Date date);
}
