package hotel.bao.security.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                                // Rotas completamente públicas (não autenticadas)
                                .requestMatchers("/auth/login").permitAll()
                                .requestMatchers(HttpMethod.GET, "/quarto").permitAll()

                                // Rotas que requerem autenticação mas com roles específicas
                                .requestMatchers("/auth/recuperar-senha").hasRole("CLIENT")
//                        .requestMatchers("/auth/register").hasAnyRole("ADMIN", "CLIENT")

                                // Rotas de usuário - específicas por método
                                .requestMatchers(HttpMethod.GET, "/usuario/**", "/usuario").hasAnyRole("ADMIN", "CLIENT")
                                .requestMatchers(HttpMethod.POST, "/usuario").hasAnyRole("ADMIN", "CLIENT")
                                .requestMatchers(HttpMethod.PUT, "/usuario/**").hasAnyRole("ADMIN", "CLIENT")
                                .requestMatchers(HttpMethod.DELETE, "/usuario/**").hasRole("ADMIN")

                                // Rotas de quarto (exceto GET que já está permitAll)
                                .requestMatchers(HttpMethod.POST, "/quarto").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/quarto/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/quarto/**").hasRole("ADMIN")

                                //rotas de estadia
                                .requestMatchers(HttpMethod.POST, "/estadia").hasAnyRole("CLIENT", "ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/estadia/**").hasAnyRole("CLIENT", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/estadia/**").hasAnyRole("CLIENT", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/estadia/**").hasAnyRole("CLIENT", "ADMIN")

                                //rotas nota fiscal
                                .requestMatchers(HttpMethod.GET, "/relatorio/cliente/**").hasAnyRole("ADMIN", "CLIENT")
                                .requestMatchers(HttpMethod.GET, "/relatorio/cliente/menor-valor/**").hasAnyRole("ADMIN", "CLIENT")
                                .requestMatchers(HttpMethod.GET, "/relatorio/cliente/maior-valor/**").hasAnyRole("ADMIN", "CLIENT")
                                .requestMatchers(HttpMethod.GET, "/relatorio/cliente/maior-aluguel/**").hasAnyRole("ADMIN", "CLIENT")


                                // Swagger e documentação
                                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                //banco de dados
                                .requestMatchers("/admin/database/cleanup").hasRole("ADMIN")


                                // Qualquer outra rota requer autenticação
                                .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    @Profile("test")
    @Order(1)
    public SecurityFilterChain h2SecurityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher(PathRequest.toH2Console())
                .csrf(csrf -> csrf.disable())
                .headers(headers ->
                        headers.frameOptions(frameOptions -> frameOptions.disable()));
        return http.build();
    }
}