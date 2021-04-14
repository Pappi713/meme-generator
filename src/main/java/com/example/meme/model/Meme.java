package com.example.meme.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Meme {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String url;
  @JsonIgnore
  private Boolean isOnFeed = false;

  @OneToMany(mappedBy = "meme", fetch = FetchType.LAZY)
  private List<Reaction> reactionList;


  @ManyToOne
  @JoinColumn(name = "user_name",nullable = false)
  @JsonIgnore
  private User user;

}
