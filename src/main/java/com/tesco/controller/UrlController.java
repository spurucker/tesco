package com.tesco.controller;

import com.tesco.domain.Url;
import com.tesco.dto.ShortenUrlRequest;
import com.tesco.exception.NotFoundException;
import com.tesco.service.UrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@Api(tags = "Short Url Api")
@RequiredArgsConstructor
@RequestMapping("/tesco/challenge/shortened/url")
public class UrlController {
    private final UrlService urlService;

    @PostMapping
    @ApiOperation("Given an url returns a shorten url")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "The url is not valid")})
    public Url shortenUrl(@Valid @RequestBody ShortenUrlRequest request){
        return urlService.shorten(request.getUrl());
    }

    @GetMapping("/{shortUrl}")
    @ApiOperation("Access real url given a shorten url")
    @ApiResponses(value = {
        @ApiResponse(code = 404, message = "The shorten url was not found")})
    public ModelAndView redirect(@PathVariable("shortUrl") String shortUrl) throws NotFoundException {
        Url url = urlService.getUrl(shortUrl);
        return new ModelAndView("redirect:" + url.getValue());
    }

    @GetMapping("/{shortUrl}/statistics")
    @ApiOperation("Access shorten url statistics")
    @ApiResponses(value = {
        @ApiResponse(code = 404, message = "The shorten url was not found")})
    public Url GetStatistics(@PathVariable("shortUrl") String shortUrl) throws NotFoundException {
        return urlService.getUrl(shortUrl);
    }
}
