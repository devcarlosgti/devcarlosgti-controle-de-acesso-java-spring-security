package io.github.devcarlosgti.sbootexp_security.config;

import io.github.devcarlosgti.sbootexp_security.domain.security.CustomAuthentication;
import io.github.devcarlosgti.sbootexp_security.domain.security.IdentificacaoUsuario;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class CustomFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String secretHeader = request.getHeader("x-secret");

        if(secretHeader != null){
            if(secretHeader.equals("secr3t")){
                var identificacaoUsuario = new IdentificacaoUsuario(
                        "id-secret",
                        "Muito Secreto",
                        "x-secret",
                        List.of("USER")
                );
//                Authentication authentication = new UsernamePasswordAuthenticationToken(
////                        "Muiito secreto", null, List.of(new SimpleGrantedAuthority("ADMIN")));
//                                "Muiito secreto", null, List.of(new SimpleGrantedAuthority("USER")));

                Authentication authentication = new CustomAuthentication(identificacaoUsuario);

                SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(authentication);

            }
        }
        filterChain.doFilter(request, response); //ele mostra quem está autenticado
    }
}
