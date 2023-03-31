package com.example.perma.security.config;

import com.example.perma.security.token.Token;
import com.example.perma.security.token.TokenRepository;
import com.example.perma.security.token.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

  private final TokenService tokenService;

  @Override
  public void logout(
      HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication
  ) {
    final String authHeader = request.getHeader("Authorization");
    final String jwt;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    jwt = authHeader.substring(7);
    Token storedToken = tokenService.getTokenByToken(jwt).orElse(null);
    if (storedToken != null) {
      storedToken.setRevoked(true);
      tokenService.save(storedToken);
      SecurityContextHolder.clearContext();
    }
  }
}
