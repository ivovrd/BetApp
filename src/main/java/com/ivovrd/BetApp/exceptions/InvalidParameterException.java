package com.ivovrd.BetApp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid parameters sent.")
public class InvalidParameterException extends RuntimeException{
}
