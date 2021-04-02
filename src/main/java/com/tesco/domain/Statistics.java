package com.tesco.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class Statistics {
    @ApiModelProperty(notes = "number of times that the url was shorten")
    private long shortened;
    @ApiModelProperty(notes = "number of times that the url was accessed")
    private long accessed;
}
