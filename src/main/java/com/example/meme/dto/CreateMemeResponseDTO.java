package com.example.meme.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateMemeResponseDTO {

  private Long id;
  private String name;
  private String url;

  public CreateMemeResponseDTO(Long id, String name, String url) {
    this.id = id;
    this.name = name;
    this.url = url;
  }
}
