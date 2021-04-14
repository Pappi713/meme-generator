package com.example.meme.service;

import com.example.meme.exception.MemeNotFoundException;
import com.example.meme.exception.NoAuthorityException;
import com.example.meme.exception.UserNotFoundException;
import com.example.meme.model.Meme;
import com.example.meme.model.User;
import com.example.meme.repository.MemeRepository;
import com.example.meme.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class MemeService {

  private UserRepository userRepository;
  private MemeRepository memeRepository;

  @Autowired
  public MemeService(UserRepository userRepository, MemeRepository memeRepository) {
    this.userRepository = userRepository;
    this.memeRepository = memeRepository;
  }

  public String updateIsOnFeedTag(Principal principal, Long id)
      throws UserNotFoundException, MemeNotFoundException, NoAuthorityException {
    Optional<User> optionalUser = userRepository.findById(principal.getName());
    if (!optionalUser.isPresent()) {
      throw new UserNotFoundException("No such user");
    }
    User user = optionalUser.get();
    Optional<Meme> optionalMeme = memeRepository.findById(id);
    if (!optionalMeme.isPresent()) {
      throw new MemeNotFoundException("No such meme");
    }
    Meme meme = optionalMeme.get();
    if (!user.getMemeList().contains(meme)) {
      throw new NoAuthorityException("No Authority");
    }
    else {
      if(meme.getIsOnFeed()){
        return "Meme is already on feed";
      }
      else{
        meme.setIsOnFeed(true);
        memeRepository.save(meme);
        return "You placed meme on feed";
      }
    }
  }
}
