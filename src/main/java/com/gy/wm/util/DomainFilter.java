package com.gy.wm.util;

/**
 * Created by Administrator on 2016/4/28.
 */
public class DomainFilter {
    public static boolean matchDomain(String url,String domain) {
        String pattern = "(http|https)(://)(" + domain +")(.*)";
        return url.matches(pattern);
    }

    public static boolean linkFilter(String url) {
        String skip = "gif|GIF|jpg|JPG|png|PNG|ico|ICO|css|sit|eps|wmf|zip|ppt|mpg|xls|gz|rpm|tgz|mov|MOV|exe|jpeg|JPEG|bmp|BMP|rar|" +
                "js|css|pdf|JS|CSS|PDF|RAR";
        String [] skipOptions = skip.split("\\|");
        boolean skipTag = true;
        for(int i=0; i<skipOptions.length; i++)   {
            skipTag = skipTag&&!url.endsWith(skipOptions[i]);
        }
        return skipTag;
    }

    //test
    public static void main(String[] args) {
        System.out.println(new DomainFilter().linkFilter("www.baidu.com"));
    }
}
