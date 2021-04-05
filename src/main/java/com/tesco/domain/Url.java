package com.tesco.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class Url {
    @JsonIgnore
    private Long id;
    @ApiModelProperty(notes = "original url")
    private String value;
    @ApiModelProperty(notes = "shorten url")
    private String shortenedUrl;
    @ApiModelProperty(notes = "statistics")
    private Statistics statistics;

    public Url(Url url, String shortenedUrl){
        this.id = url.getId();
        this.value = url.getValue();
        this.shortenedUrl = shortenedUrl;
        this.statistics = url.getStatistics();
    }
}
