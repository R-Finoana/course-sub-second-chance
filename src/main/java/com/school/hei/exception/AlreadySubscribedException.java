package com.school.hei.exception;

import org.springframework.http.HttpStatus;

public class AlreadySubscribedException extends ApiException {
  public AlreadySubscribedException(String message) {
    super(message, HttpStatus.CONFLICT);
  }
}
