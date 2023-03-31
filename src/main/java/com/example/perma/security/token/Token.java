package com.example.perma.security.token;

import com.example.perma.security.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Token {

  @Id
  @GeneratedValue
  private Integer id;

  @Column(unique = true)
  private String token;

  @Enumerated(EnumType.STRING)
  private TokenType tokenType = TokenType.BEARER;

  private LocalDateTime createdAt;
  private LocalDateTime expiresAt;
  private Boolean validated;
  private Boolean revoked;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
}
