package com.example.meme.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler (value = UserAlreadyExistsException.class)
  public ResponseEntity handleUserAlreadyExistsException() {
    return ResponseEntity.status(422).build();
  }

  @ExceptionHandler (value = MemeNotFoundException.class)
  public ResponseEntity handleMemeNotFoundException() {
    return ResponseEntity.status(400).build();
  }

  @ExceptionHandler (value = NoAuthorityException.class)
  public ResponseEntity handleNoAuthorityException() {
    return ResponseEntity.status(403).build();
  }
}
