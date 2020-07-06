package com.ivovrd.BetApp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Not enough funds in account.")
public class MissingFundsException extends RuntimeException {
}
