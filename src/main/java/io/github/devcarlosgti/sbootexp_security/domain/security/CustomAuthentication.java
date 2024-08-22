package io.github.devcarlosgti.sbootexp_security.domain.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomAuthentication implements Authentication {

    private final IdentificacaoUsuario identificacaoUsuario;

    public CustomAuthentication(IdentificacaoUsuario identificacaoUsuario) {
        if(identificacaoUsuario == null){
            throw new ExceptionInInitializerError("Não é possível criar um customauthentication sem a identificação de usuário");
        }
        this.identificacaoUsuario = identificacaoUsuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //return List.of();
        return this.identificacaoUsuario
                .getPermissoes()
                .stream()
                .map(perm -> new SimpleGrantedAuthority(perm))
                .collect(Collectors.toList());
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        //return null;
        return this.identificacaoUsuario;
    }

    @Override
    public boolean isAuthenticated() {
//        return false;
        return true; //p conseguir logar com esse user identificado
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw new IllegalArgumentException("Não precisa chamar, já estamos autenticados ");
    }

    @Override
    public String getName() {
//        return "";
        return this.identificacaoUsuario.getNome();
    }
}
