package com.gy.wm.util;

/**
 * Created by Administrator on 2016/4/28.
 */
public class DomainFilter {
    public static boolean matchDomain(String url,String domain) {
        String pattern = "(http|https)(://)(" + domain +")(.*)";
        return url.matches(pattern);
    }
}
