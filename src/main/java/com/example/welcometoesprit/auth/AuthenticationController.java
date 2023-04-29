package com.example.welcometoesprit.auth;

import com.example.welcometoesprit.Helpers.UserNotFoundException;
import com.example.welcometoesprit.ServicesImpl.UserServiceImp;
import com.example.welcometoesprit.config.JwtService;
import com.example.welcometoesprit.entities.User;
import com.example.welcometoesprit.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthenticationController {

  private final AuthenticationService service;

  @Autowired
  private AuthenticationManager authManager;

  @Autowired
  private UserServiceImp userDetailsService;

  @Autowired
  private JwtService jwtUtils;



  private final TokenRepository tokenRepository;
  private final UserServiceImp userServiceImp;

  @PostMapping("/generate-token")
  public ResponseEntity<?>generateToken(@RequestBody AuthenticationRequest jwtRequest) throws Exception{


    try {


      authenticatee(jwtRequest.getEmail(), jwtRequest.getPassword());

    }catch(UserNotFoundException e) {

      e.printStackTrace();
      throw new Exception("User not found");

    }


    UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtRequest.getEmail());

    String token  = this.jwtUtils.generateToken(userDetails);

    return ResponseEntity.ok(service.authenticate(jwtRequest));
  }

  private void authenticatee(String email, String password) throws Exception {


    try {

      authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

    }catch(DisabledException e){


      throw new Exception ("User Disabled" + e.getMessage());

    }catch(BadCredentialsException e) {

      throw new Exception("Invalid Credential" + e.getMessage());

    }
  }
//  @DeleteMapping("/api/v1/auth/logout")
//  public void logoutUser(User user){
//    this.service.logoutUser(user);
//  }
  @GetMapping("/current-user")
  public User getCurrentUser(String token) {
    return (userServiceImp.getCurrentUser(token));

  }

  @GetMapping("/getUserByEmail/{email}")
  public Optional<User> getUserByEmail(@PathVariable("email") String email){
    return userServiceImp.loadUserByEmail(email);
  }
//  @GetMapping("/current-user")
//  public Optional<User> getCurrentUser(String email){
//   return  userServiceImp.getCurrentUser(email);}

  @GetMapping("/getRole")
  public String findRoleByEmail(String email){
    return String.valueOf(userServiceImp.findRoleByEmail(email));
  }
  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody RegisterRequest request
  ) {
    return ResponseEntity.ok(service.register(request));
  }
  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    return ResponseEntity.ok(service.authenticate(request));
  }
  @GetMapping(path = "/registration/confirm")
  public String confirm(@RequestParam("token") String token) {
    return service.confirmToken(token);
  }

}
