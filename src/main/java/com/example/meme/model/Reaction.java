package com.example.meme.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String type;

  @ManyToOne
  @JoinColumn(name = "meme_id", nullable = false)
  private Meme meme;

  public Reaction(String type, Meme meme) {
    this.type = type;
    this.meme = meme;
  }
}
