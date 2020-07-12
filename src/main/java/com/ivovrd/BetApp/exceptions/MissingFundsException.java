package com.ivovrd.BetApp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The exception that will be raised in case there is not enough funds to place a bet
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Not enough funds in account.")
public class MissingFundsException extends RuntimeException {
}
