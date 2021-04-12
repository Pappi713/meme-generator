package com.example.meme.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponseDTO {
  private final String status = "Authenticated";
  private String token;

  public LoginResponseDTO(String token) {
    this.token = token;
  }
}
