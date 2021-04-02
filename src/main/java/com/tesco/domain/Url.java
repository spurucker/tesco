package com.tesco.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Url {
    @JsonIgnore
    private Long id;
    private String value;
    private String shortenedUrl;
    private Statistics statistics;
}
