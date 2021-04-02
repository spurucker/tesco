package com.tesco.springboot.service;

import com.tesco.domain.Url;
import com.tesco.exception.NotFoundException;
import com.tesco.repository.UrlDao;
import com.tesco.service.CodecService;
import com.tesco.service.UrlService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import java.util.Optional;

import static com.tesco.springboot.fixture.UrlFixture.TINY_URL_TEST;
import static com.tesco.springboot.fixture.UrlFixture.URL_ID_TEST;
import static com.tesco.springboot.fixture.UrlFixture.URL_TEST;
import static com.tesco.springboot.fixture.UrlFixture.getUrlAccessedTest;
import static com.tesco.springboot.fixture.UrlFixture.getUrlSecondTimeTest;
import static com.tesco.springboot.fixture.UrlFixture.getUrlTest;
import static com.tesco.springboot.fixture.UrlFixture.getUrlWithoutIdOrShortenedUrlTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UrlServiceTest {
    private UrlService service;

    @MockBean
    private UrlDao urlDao;
    @MockBean
    private CodecService codecService;

    @PostConstruct
    public void init(){
        service = new UrlService(urlDao, codecService);
    }

    @Test
    public void shorten(){
        when(urlDao.getUrlByValue(URL_TEST))
            .thenReturn(Optional.empty());
        when(urlDao.save(getUrlWithoutIdOrShortenedUrlTest()))
            .thenReturn(URL_ID_TEST);
        when(codecService.encode62(URL_ID_TEST))
            .thenReturn(TINY_URL_TEST);

        Url got = service.shorten(URL_TEST);

        assertThat(got).isEqualTo(getUrlTest());

        verify(urlDao).getUrlByValue(eq(URL_TEST));
        verify(codecService).encode62(eq(URL_ID_TEST));
        verify(urlDao).save(getUrlTest());
        verify(urlDao, never()).update(any());
    }

    @Test
    public void shorten_withoutPrefix(){
        when(urlDao.getUrlByValue(URL_TEST))
            .thenReturn(Optional.empty());
        when(urlDao.save(getUrlWithoutIdOrShortenedUrlTest()))
            .thenReturn(URL_ID_TEST);
        when(codecService.encode62(URL_ID_TEST))
            .thenReturn(TINY_URL_TEST);

        Url got = service.shorten("url.com");

        assertThat(got).isEqualTo(getUrlTest());

        verify(urlDao).getUrlByValue(eq(URL_TEST));
        verify(codecService).encode62(eq(URL_ID_TEST));
        verify(urlDao).save(getUrlTest());
        verify(urlDao, never()).update(any());
    }

    @Test
    public void shorten_existentUrl(){
        when(urlDao.getUrlByValue(URL_TEST))
            .thenReturn(Optional.of(getUrlTest()));
        when(codecService.encode62(URL_ID_TEST))
            .thenReturn(TINY_URL_TEST);

        Url got = service.shorten(URL_TEST);

        assertThat(got).isEqualTo(getUrlSecondTimeTest());

        verify(urlDao).getUrlByValue(eq(URL_TEST));
        verify(codecService).encode62(eq(URL_ID_TEST));
        verify(urlDao).update(getUrlSecondTimeTest());
        verify(urlDao, never()).save(any());
    }

    @Test
    public void getUrl() throws NotFoundException {
        when(urlDao.getUrlById(URL_ID_TEST))
            .thenReturn(Optional.of(getUrlTest()));
        when(codecService.decode62(TINY_URL_TEST))
            .thenReturn(URL_ID_TEST);

        Url got = service.getUrl(TINY_URL_TEST);

        assertThat(got).isEqualTo(getUrlAccessedTest());

        verify(urlDao).getUrlById(eq(URL_ID_TEST));
        verify(codecService).decode62(eq(TINY_URL_TEST));
        verify(urlDao).update(getUrlAccessedTest());
    }

    @Test
    public void getUrl_notFound() {
        when(urlDao.getUrlById(URL_ID_TEST))
            .thenReturn(Optional.empty());
        when(codecService.decode62(TINY_URL_TEST))
            .thenReturn(URL_ID_TEST);

        assertThatThrownBy(() -> service.getUrl(TINY_URL_TEST))
            .isExactlyInstanceOf(NotFoundException.class)
            .hasMessage("Url 000001 was not found");

        verify(urlDao).getUrlById(eq(URL_ID_TEST));
        verify(codecService).decode62(eq(TINY_URL_TEST));
        verify(urlDao, never()).update(any());
    }
}
