package com.example.meme.controller;

import com.example.meme.dto.CreateMemeRequestDTO;
import com.example.meme.dto.CreateMemeResponseDTO;
import com.example.meme.dto.LoginDTO;
import com.example.meme.dto.LoginResponseDTO;
import com.example.meme.dto.MemeResponseDTO;
import com.example.meme.dto.ResponseDTO;
import com.example.meme.exception.MemeNotFoundException;
import com.example.meme.exception.NoAuthorityException;
import com.example.meme.exception.UserAlreadyExistsException;
import com.example.meme.exception.UserNotFoundException;
import com.example.meme.security.JwtUtil;
import com.example.meme.service.MemeService;
import com.example.meme.service.MyUserDetailsService;
import com.example.meme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class UserController {
  private final UserService userService;
  private final MemeService memeService;
  private final AuthenticationManager authenticationManager;
  private final MyUserDetailsService myUserDetailsService;
  private final JwtUtil jwtUtil;

  @Autowired
  public UserController(UserService userService,
                        AuthenticationManager authenticationManager,
                        MyUserDetailsService myUserDetailsService, JwtUtil jwtUtil, MemeService memeService) {
    this.userService = userService;
    this.authenticationManager = authenticationManager;
    this.myUserDetailsService = myUserDetailsService;
    this.jwtUtil = jwtUtil;
    this.memeService =memeService;
  }

  @PostMapping("/register")
  @ResponseBody
  public ResponseEntity<?> register(@RequestBody LoginDTO loginDTO) throws UserAlreadyExistsException {
    userService.createUser(loginDTO);
    ResponseDTO dto = new ResponseDTO("registration successful");
    return ResponseEntity.ok(dto);
  }

  @PostMapping("/login")
  @ResponseBody
  public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginDTO authenticationRequest) throws Exception {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
      );
    } catch (BadCredentialsException e) {
      throw new Exception("Incorrect or missing username or password", e);
    }
    String username = authenticationRequest.getUsername();
    final UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
    final String jwt = jwtUtil.generateToken(userDetails);
    LoginResponseDTO response = new LoginResponseDTO(jwt);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/feed/{id}")
  @ResponseBody
  public ResponseEntity setFeedFlagTrue(@PathVariable Long id, Principal principal)
      throws UserAlreadyExistsException, UserNotFoundException, NoAuthorityException, MemeNotFoundException {
    return ResponseEntity.ok().body(memeService.updateIsOnFeedTag(principal,id));
  }

  @GetMapping("/feed")
  public ResponseEntity<?> getFeed(@RequestParam(required = false) Integer page) {
    if (page == null) {
      List<MemeResponseDTO> memeResponseDTOList = memeService.getFeed(1);
      return ResponseEntity.ok(memeResponseDTOList);
    }
    return ResponseEntity.ok(memeService.getFeed(page));
  }

  @PostMapping("/create")
  @ResponseBody
  public ResponseEntity<?> createMeme(@RequestBody CreateMemeRequestDTO createMemeRequestDTO, Principal principal)
      throws UserNotFoundException {
    CreateMemeResponseDTO createMemeResponseDTO = memeService.createMeme(principal,createMemeRequestDTO);
    return ResponseEntity.ok().body(createMemeResponseDTO);
  }

  @GetMapping("/meme")
  public ResponseEntity<List<MemeResponseDTO>> getMemesNotOnFeed(Principal principal) throws UserNotFoundException {
    return ResponseEntity.ok(memeService.getAllNotOnFeed(principal));
  }

  @PostMapping("/react/{id}")
  public ResponseEntity<MemeResponseDTO> addReaction(@RequestBody List<String> emotions,
                                                     @PathVariable Long id) throws MemeNotFoundException {
    return ResponseEntity.ok(memeService.react(emotions, id));
  }
}
