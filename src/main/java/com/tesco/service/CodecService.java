package com.tesco.service;

import org.springframework.stereotype.Service;

@Service
public class CodecService {
    private final String base62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // Decodes a shortened URL to its original URL.
    public Long decode62(String shortUrl) {
        String base62Encoded = shortUrl.substring(shortUrl.lastIndexOf("/") + 1);
        Long decode = 0L;
        for(int i = 0; i < base62Encoded.length(); i++) {
            decode = decode * 62 + base62.indexOf("" + base62Encoded.charAt(i));
        }
        return decode;
    }

    public String encode62(Long value) {
        StringBuilder sb = new StringBuilder();
        while (value != 0) {
            sb.append(base62.charAt((int)(value % 62)));
            value /= 62;
        }
        while (sb.length() < 6) {
            sb.append(0);
        }
        return sb.reverse().toString();
    }
}
