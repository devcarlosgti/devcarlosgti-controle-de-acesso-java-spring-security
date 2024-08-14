package io.github.devcarlosgti.sbootexp_security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //role -> grupo de usuario(perfil de usuario) -> master, gerente, frente de loja, vendedor ...
    //authority -> permissões -> cadastrar, acessar tela de relatórios ...

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http, SenhaMasterAuthenticationProvider senhaMasterAuthenticationProvider,
            CustomFilter customFilter) throws Exception{
        return http
                .authorizeHttpRequests(customizer ->{
                    customizer.requestMatchers("/public").permitAll(); //autoriza todos
//                    customizer.requestMatchers("/private").hasRole("MASTER"); //somente o master
                    customizer.requestMatchers("/admin").hasRole("ADMIN");
                    customizer.anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .authenticationProvider(senhaMasterAuthenticationProvider)
                .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    //criar users na memória para teste
    @Bean
    public UserDetailsService userDetailsService(){

        UserDetails commonUser = User.builder()
                .username("user")
                .password(passwordEncoder().encode("123"))
                .roles("USER")
                .build();

        UserDetails adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("USER","ADMIN")
                .build();

        return new InMemoryUserDetailsManager(commonUser, adminUser);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults(){
        return new GrantedAuthorityDefaults(""); // isso p ñ precisar colocar o prefixo la no ADMIN
    }
}
