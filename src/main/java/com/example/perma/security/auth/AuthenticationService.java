package com.example.perma.security.auth;

import com.example.perma.email.EmailSender;
import com.example.perma.email.EmailService;
import com.example.perma.security.config.JwtService;
import com.example.perma.security.token.Token;
import com.example.perma.security.token.TokenRepository;
import com.example.perma.security.token.TokenService;
import com.example.perma.security.token.TokenType;
import com.example.perma.security.user.Role;
import com.example.perma.security.user.User;
import com.example.perma.security.user.UserRepository;
import com.example.perma.security.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final TokenService tokenService;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final UserService userService;
  private final EmailValidator emailValidator;
  private final EmailSender emailSender;
  private final EmailService emailService;

  public AuthenticationResponse register(RegisterRequest request) {
    if (!emailValidator.test(request.getEmail())){
      return AuthenticationResponse.notValidEmail();
    }
    User user = User.builder()
            .firstname(request.getFirstname())
            .lastname(request.getLastname())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.USER)
            .isEnabled(false)
            .build();
    User savedUser = userService.signUpUser(user);
    String jwtToken = jwtService.generateToken(user);
    saveUserToken(savedUser, jwtToken);

    String link = "http://localhost:8080/api/v1/auth/confirm?token=" + jwtToken;
    emailSender.send(request.getEmail(), emailService.buildEmail(user.getFirstname(), link));


    return AuthenticationResponse.confirmationEmailSend();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    User user = userService.loadUserByUsername(request.getEmail());
    String jwtToken = jwtService.generateToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }

  @Transactional
  public AuthenticationResponse confirmToken(String token){
    Token _Token = tokenService.getTokenByToken(token).orElseThrow(() -> new IllegalStateException("Token not found"));
    if (_Token.getValidated()) throw new IllegalStateException("Email already confirmed");

    LocalDateTime expiredAt = _Token.getExpiresAt();
    if (expiredAt.isBefore(LocalDateTime.now())) throw new IllegalStateException("Token expired");

    tokenService.confirm(_Token);
    userService.enableUser(_Token.getUser().getEmail());
    return AuthenticationResponse.confirmedEmail();
  }


  private void saveUserToken(User user, String jwtToken) {
    Token token = Token.builder()
            .user(user)
            .token(jwtToken)
            .tokenType(TokenType.BEARER)
            .createdAt(LocalDateTime.now())
            .expiresAt(LocalDateTime.now().plusMinutes(15))
            .validated(false)
            .revoked(false)
            .build();
    tokenService.save(token);
  }

  private void revokeAllUserTokens(User user) {
    List<Token> validUserTokens = tokenService.getAllTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setRevoked(true);
    });
    tokenService.saveAll(validUserTokens);
  }
}
