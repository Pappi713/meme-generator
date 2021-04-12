package com.example.meme.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String type;

  @OneToOne(mappedBy = "reaction", cascade = CascadeType.ALL)
  private Meme meme;

  @OneToOne(mappedBy = "reaction", cascade = CascadeType.ALL)
  private User user;


}
