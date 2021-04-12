package com.example.meme.controller;

import com.example.meme.dto.LoginDTO;
import com.example.meme.exception.UserAlreadyExistsException;
import com.example.meme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity register(@RequestBody LoginDTO loginDTO) throws UserAlreadyExistsException {
    userService.createUser(loginDTO);
    return ResponseEntity.ok().build();
  }
}
