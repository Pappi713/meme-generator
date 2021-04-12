package com.example.meme.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

  @Id
  @Column(unique = true)
  private String username;
  private String password;

  @JsonManagedReference
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private List<Meme> memeList;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "id", referencedColumnName = "id")
  private Reaction reaction;

  public User(String username) {
    this.username = username;
  }

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }
}
