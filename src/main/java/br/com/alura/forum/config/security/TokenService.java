package br.com.alura.forum.config.security;

import br.com.alura.forum.modelo.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${forum.jwt.secret}")
    private String secret;

    @Value("${forum.jwt.expiration}")
    private String expiration;

    public String gerarToken(Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        Date date = new Date();

        return Jwts.builder()
                .setIssuer("API do Fórum da Aulra")
                .setSubject(usuario.getId().toString())
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + Long.parseLong(expiration)))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

}
