package com.tesco.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Url {
    @JsonIgnore
    private Long id;
    private String value;
    private String shortenedUrl;
    private Statistics statistics;

    public Url(Url url, String shortenedUrl){
        this.id = url.getId();
        this.value = url.getValue();
        this.shortenedUrl = shortenedUrl;
        this.statistics = url.getStatistics();
    }
}
