package ait.cohort70.security;

import ait.cohort70.accounting.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.awt.*;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final CustomWebSecurity webSecurity;

    @Bean
    SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/account/register", "/forum/posts/**" )
                .permitAll()

                // ACCOUNT
                .requestMatchers("/account/user/{login}/role/{role}" )
                .hasRole(Role.ADMINISTRATOR.name())
                .requestMatchers(HttpMethod.PATCH, "/account/user/{login}", "/forum/post/{id}/comment/{login}" )
                .access(new WebExpressionAuthorizationManager("#login==authentication.name" ))
                .requestMatchers(HttpMethod.DELETE, "/account/user/{login}" )
                .access(new WebExpressionAuthorizationManager("#login==authentication.name or hasRole('ADMINISTRATOR')" ))

                // FORUM
                .requestMatchers(HttpMethod.POST, "/forum/post/{author}" )
                .access(new WebExpressionAuthorizationManager("#author==authentication.name" ))
                .requestMatchers(HttpMethod.PATCH, "/forum/post/{id}" )
                .access((authentication, context) ->
                        new AuthorizationDecision(webSecurity.isPostOwner(authentication.get().getName(), context.getVariables().get("id" ))))
                .requestMatchers(HttpMethod.DELETE, "/forum/post/{id}" )
                .access((authentication, context) -> {
                    boolean isAuthor = webSecurity.isPostOwner(authentication.get().getName(), context.getVariables().get("id" ));
                    boolean isModerator = context.getRequest().isUserInRole(Role.MODERATOR.name());
                    return new AuthorizationDecision(isAuthor || isModerator);
                })

                .anyRequest()
                .authenticated()
        );
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration  = new CorsConfiguration();
        configuration .setAllowedOriginPatterns(List.of(
                "http://localhost:*",
                "http://127.0.0.1:*"
        ));
        configuration .setAllowedMethods(List.of("GET", "POST", "PATCH", "DELETE", "OPTIONS" ));
        configuration .setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept" ));
        configuration .setAllowCredentials(true);
        configuration .setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration );
        return source;
    }
}
