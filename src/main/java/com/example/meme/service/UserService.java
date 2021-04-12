package com.example.meme.service;

import com.example.meme.dto.LoginDTO;
import com.example.meme.exception.UserAlreadyExistsException;
import com.example.meme.model.User;
import com.example.meme.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User createUser(LoginDTO loginDTO) throws UserAlreadyExistsException {
    String username = loginDTO.getUsername();
    if(!existsByUsername(username)) {
      throw new UserAlreadyExistsException("The following username is already taken: " + username);
    }
    return userRepository.save(new User(username, loginDTO.getPassword()));
  }



  public Boolean existsByUsername(String username) {
    return userRepository.existsById(username);
  }

}
