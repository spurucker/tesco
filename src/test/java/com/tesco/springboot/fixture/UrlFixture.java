package com.tesco.springboot.fixture;

import com.tesco.domain.Statistics;
import com.tesco.domain.Url;

public class UrlFixture {
    public static final String URL_TEST = "http://url.com";
    public static final String TINY_URL_TEST = "000001";
    public static final Long URL_ID_TEST = 1L;

    public static Url getUrlWithoutIdOrShortenedUrlTest(){
        return Url.builder()
            .id(null)
            .value(URL_TEST)
            .statistics(new Statistics(1,0))
            .build();
    }

    public static Url getUrlShortenedUrlTest(){
        return Url.builder()
            .id(URL_ID_TEST)
            .value(URL_TEST)
            .statistics(new Statistics(1,0))
            .build();
    }

    public static Url getUrlTest(){
        return Url.builder()
            .id(URL_ID_TEST)
            .value(URL_TEST)
            .shortenedUrl("000001")
            .statistics(new Statistics(1,0))
            .build();
    }

    public static Url getUrlSecondTimeTest(){
        return Url.builder()
            .id(URL_ID_TEST)
            .value(URL_TEST)
            .shortenedUrl("000001")
            .statistics(new Statistics(2,0))
            .build();
    }

    public static Url getUrlAccessedTest(){
        return Url.builder()
            .id(URL_ID_TEST)
            .value(URL_TEST)
            .shortenedUrl("000001")
            .statistics(new Statistics(1,1))
            .build();
    }
}
