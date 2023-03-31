package com.example.perma.security.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public void save(Token token){
        tokenRepository.save(token);
    }

    public List<Token> getAllTokenByUser(Integer id){
        return tokenRepository.findAllTokenByUser(id);
    }

    public void saveAll(List<Token> tokens) {
        tokenRepository.saveAll(tokens);
    }


    public Optional<Token> getTokenByToken(String jwt) {
        return tokenRepository.findByToken(jwt);
    }


    public void confirm(Token token) {
        token.setValidated(true);
    }
}
