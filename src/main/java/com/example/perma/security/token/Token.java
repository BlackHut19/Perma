package com.example.perma.security.token;

import com.example.perma.models.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "_token")
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
