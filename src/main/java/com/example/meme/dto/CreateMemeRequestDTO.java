package com.example.meme.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class CreateMemeRequestDTO {

  private String name;
  private String url;
  private List<String> taglist;

  public CreateMemeRequestDTO(String name, String url, List<String> taglist) {
    this.name = name;
    this.url = url;
    this.taglist = taglist;
  }
}
