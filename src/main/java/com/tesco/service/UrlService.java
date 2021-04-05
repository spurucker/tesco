package com.tesco.service;

import com.tesco.domain.Statistics;
import com.tesco.domain.Url;
import com.tesco.exception.NotFoundException;
import com.tesco.repository.UrlDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.tesco.Constants.HTTPS_PREFIX;
import static com.tesco.Constants.HTTP_PREFIX;

@Service
@AllArgsConstructor
public class UrlService {
    private final UrlDao urlDao;
    private final CodecService codecService;

    public Url shorten(String url){
        String urlFormatted = formatUrl(url);
        Optional<Url> savedUrl = urlDao.getUrlByValue(urlFormatted);

        Url shortUrl = savedUrl.map(this::addUrlShortened)
            .orElseGet(() -> createUrl(urlFormatted));

        shortUrl.setShortenedUrl(codecService.encode62(shortUrl.getId()));
        return shortUrl;
    }

    public Url getUrl(String shortUrl) throws NotFoundException {
        Long id = codecService.decode62(shortUrl);
        return urlDao.getUrlById(id)
            .map(this::addUrlAccess)
            .map(u -> new Url(u, shortUrl))
            .orElseThrow(() -> new NotFoundException(String.format("Url %s was not found", shortUrl)));
    }

    private Url addUrlAccess(Url url){
        Statistics statistics = url.getStatistics();
        statistics.setAccessed(statistics.getAccessed() + 1);
        urlDao.update(url);
        return url;
    }

    private Url addUrlShortened(Url url){
        Statistics statistics = url.getStatistics();
        statistics.setShortened(statistics.getShortened() + 1);
        urlDao.update(url);
        return url;
    }

    private Url createUrl(String url){
        Url shortUrl = Url.builder()
            .value(url)
            .statistics(new Statistics(1, 0))
            .build();

        Long id = urlDao.save(shortUrl);
        shortUrl.setId(id);
        return shortUrl;
    }

    private String formatUrl(String url){
        if(!url.startsWith(HTTP_PREFIX) && !url.startsWith(HTTPS_PREFIX)){
            return HTTP_PREFIX + url;
        }
        return url;
    }
}
