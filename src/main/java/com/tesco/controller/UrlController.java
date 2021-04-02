package com.tesco.controller;

import com.tesco.domain.Url;
import com.tesco.dto.ShortenUrlRequest;
import com.tesco.exception.NotFoundException;
import com.tesco.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tesco/challenge/shortened/url")
public class UrlController {
    private final UrlService urlService;

    @PostMapping
    public Url shortenUrl(@Valid @RequestBody ShortenUrlRequest request){
        return urlService.shorten(request.getUrl());
    }

    @GetMapping("/{shortUrl}")
    public ModelAndView redirect(@PathVariable("shortUrl") String shortUrl) throws NotFoundException {
        Url url = urlService.getUrl(shortUrl);
        return new ModelAndView("redirect:" + url.getValue());
    }
}
