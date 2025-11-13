package com.company.config;

import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrors {

  @ExceptionHandler(IllegalArgumentException.class)
  ProblemDetail handleIllegalArg(IllegalArgumentException ex) {
    ProblemDetail pd = ProblemDetail.forStatus(400);
    pd.setTitle("Bad Request");
    pd.setDetail(ex.getMessage());
    return pd;
  }
}
