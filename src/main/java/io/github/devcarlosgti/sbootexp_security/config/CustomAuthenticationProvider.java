package io.github.devcarlosgti.sbootexp_security.config;

import io.github.devcarlosgti.sbootexp_security.domain.entity.Usuario;
import io.github.devcarlosgti.sbootexp_security.domain.security.CustomAuthentication;
import io.github.devcarlosgti.sbootexp_security.domain.security.IdentificacaoUsuario;
import io.github.devcarlosgti.sbootexp_security.domain.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component //p poder ser gerenciado
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String login = authentication.getName();
        String senha = (String) authentication.getCredentials();

        Usuario usuario = usuarioService.obterUsuarioComPermissoes(login);
        if(usuario != null){
            //matches() compara senha digita com a senha criptografada
            boolean senhasBatem = passwordEncoder.matches(senha, usuario.getSenha());
            if(senhasBatem){
                IdentificacaoUsuario identificacaoUsuario = new IdentificacaoUsuario(
                        usuario.getId(),
                        usuario.getNome(),
                        usuario.getLogin(),
                        usuario.getPermissoes()
                );
                return new CustomAuthentication(identificacaoUsuario);
            }
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
//        return false;
//        return true;
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
