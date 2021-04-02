package com.tesco.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiError {
	private String error;
	private String message;
	private Integer status;
}
