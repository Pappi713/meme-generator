package com.example.meme.service;

import com.example.meme.dto.MemeResponseDTO;
import com.example.meme.exception.MemeNotFoundException;
import com.example.meme.exception.NoAuthorityException;
import com.example.meme.exception.UserNotFoundException;
import com.example.meme.model.Meme;
import com.example.meme.model.User;
import com.example.meme.repository.MemeRepository;
import com.example.meme.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

  public List<MemeResponseDTO> getFeed(Integer page) {
    Pageable requested = PageRequest.of(page-1, 20);
    List<Meme> memeList = memeRepository.findAllByIsOnFeed(true, requested);
    if(memeList.size() == 0 && page - 1 != 0){
      return getFeed(0);
    }
    List<MemeResponseDTO> result = memeList
        .stream()
        .map(i -> new MemeResponseDTO(i.getId(), i.getName(), i.getUrl(), i.getReactionList()))
        .collect(
        Collectors.toList());
    return result;
  }
}
