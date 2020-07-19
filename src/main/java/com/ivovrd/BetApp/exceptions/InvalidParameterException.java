package com.ivovrd.BetApp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The exception that will be raised in case no valid parameters were sent in post request
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid parameters sent.")
public class InvalidParameterException extends RuntimeException{
}
